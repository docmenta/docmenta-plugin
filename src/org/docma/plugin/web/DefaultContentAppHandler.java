/*
 * DefaultContentAppHandler.java
 *
 *  Copyright (C) 2016  Manfred Paula, http://www.docmenta.org
 *   
 *  This file is part of Docmenta. Docmenta is free software: you can 
 *  redistribute it and/or modify it under the terms of the GNU Lesser 
 *  General Public License as published by the Free Software Foundation, 
 *  either version 3 of the License, or (at your option) any later version.
 *
 *  You should have received a copy of the GNU Lesser General Public License
 *  along with Docmenta.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.docma.plugin.web;

import java.io.File;
import java.util.Properties;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.StringTokenizer;
import org.docma.plugin.CharEntity;
import org.docma.plugin.PluginUtil;

/**
 *
 * @author MP
 */
public class DefaultContentAppHandler implements ContentAppHandler
{
    // Created HTTP session attributes 
    public static final String ATTRIBUTE_EDITOR_POS_X = WebUserSession.class.getName() + ".editor_pos_x";
    public static final String ATTRIBUTE_EDITOR_POS_Y = WebUserSession.class.getName() + ".editor_pos_y";

    // Used user properties
    public static final String USER_PROPERTY_EDIT_WIN_WIDTH = "editwin.width";
    public static final String USER_PROPERTY_EDIT_WIN_HEIGHT = "editwin.height";
    
    // Default values
    public static final int WINDOW_DEFAULT_POSITION_X = 100;
    public static final int WINDOW_DEFAULT_POSITION_Y = 100;
    public static final int WINDOW_DEFAULT_WIDTH = 680;
    public static final int WINDOW_DEFAULT_HEIGHT = 480;
    public static final String DEFAULT_PARA_INDENT = "24pt";

    // Content-Handler properties
    public static final String PROP_URL_EDIT = "url_edit";
    public static final String PROP_URL_VIEW = "url_view";
    public static final String PROP_URL_PREVIEW = "url_preview";
    public static final String PROP_WIN_DEFAULT_WIDTH = "win_default_width";
    public static final String PROP_WIN_DEFAULT_HEIGHT = "win_default_height";
    public static final String PROP_JS_OPEN_COMMAND = "js_open_command";
    public static final String PROP_EXTENSIONS_EDIT = "extensions_edit";
    public static final String PROP_EXTENSIONS_VIEW = "extensions_view";

    // Label keys
    public static final String LABEL_KEY_APP_NAME = "application_name";
    
    private static final String REGEXP_ID = "[A-Za-z][0-9A-Za-z_-]*";

    /** 
     * The web-application directory. This field is initialized by the 
     * {@link #initialize(java.io.File, java.lang.String, java.util.Properties)}
     * method.
     */
    protected File webBaseDirectory;

    /**
     * The file path to the content handler application. 
     * The path is relative to the web-application directory. This field is 
     * initialized by the 
     * {@link #initialize(java.io.File, java.lang.String, java.util.Properties)}
     * method.
     */
    protected String relativeAppPath;

    /**
     * The URL path to the content handler application. 
     * The returned URL is relative to the web-application directory and
     * ends with a slash character. For example, if the application is located
     * in the folder named <code>my_app</code>, then the returned string
     * could be <code>apps/my_app/</code>.
     * This field is initialized by the 
     * {@link #initialize(java.io.File, java.lang.String, java.util.Properties)}
     * method.
     */
    private String relativeAppURL;

    /**
     * The content handler properties. This field is initialized by the 
     * {@link #initialize(java.io.File, java.lang.String, java.util.Properties)}
     * method.
     */
    protected Properties props;

    private File contentAppDirectory;
    private String applicationId;
    private String editURL;
    private String viewURL;
    private String previewURL;
    private int win_default_width;
    private int win_default_height;
    private String js_open_command;
    protected SortedSet<String> extensionsEdit;
    protected SortedSet<String> extensionsView;

    public DefaultContentAppHandler() 
    {
    }

    /* -------------  Interface ContentAppHandler ----------------- */
    
    public void initialize(File webBaseDirectory, String relativeAppPath, Properties props) throws Exception
    {
        this.webBaseDirectory = webBaseDirectory;
        this.relativeAppPath = relativeAppPath;
        this.props = props;
        
        contentAppDirectory = new File(webBaseDirectory, relativeAppPath);
        applicationId = contentAppDirectory.getName().trim();
        if (! applicationId.matches(REGEXP_ID)) {
            throw new Exception("Invalid content application ID. Allowed characters are letters, digits, underscore and dash.");
        }
        
        relativeAppURL = relativeAppPath.replace('\\', '/');
        if (! (relativeAppURL.equals("") || relativeAppURL.endsWith("/"))) {
            relativeAppURL += "/";
        }
        editURL = relativeAppURL + props.getProperty(PROP_URL_EDIT, "").trim();
        viewURL = relativeAppURL + props.getProperty(PROP_URL_VIEW, "").trim();
        String purl = props.getProperty(PROP_URL_PREVIEW, "").trim();
        previewURL = purl.equals("") ? null : relativeAppURL + purl;

        String width = props.getProperty(PROP_WIN_DEFAULT_WIDTH, "");
        try {
            win_default_width = Integer.parseInt(width);
        } catch (Exception ex) {
            win_default_width = WINDOW_DEFAULT_WIDTH;
        }

        String height = props.getProperty(PROP_WIN_DEFAULT_HEIGHT, "");
        try {
            win_default_height = Integer.parseInt(height);
        } catch (Exception ex) {
            win_default_height = WINDOW_DEFAULT_HEIGHT;
        }
        
        js_open_command = props.getProperty(PROP_JS_OPEN_COMMAND, "");
        
        extensionsEdit = extensionsFromString(props.getProperty(PROP_EXTENSIONS_EDIT));
        extensionsView = extensionsFromString(props.getProperty(PROP_EXTENSIONS_VIEW));
    }

    public String getApplicationId() 
    {
        return applicationId;
    }

    public String getApplicationName(String languageCode) 
    {
        if (languageCode == null) {
            languageCode = "en";
        }
        final String label_key = getApplicationId() + "." + LABEL_KEY_APP_NAME;
        return PluginUtil.getLabel(languageCode, label_key, null);
    }

    public String[] getSupportedEditExtensions() 
    {
        return extensionsEdit.toArray(new String[extensionsEdit.size()]);
    }

    public String[] getSupportedViewExtensions() 
    {
        return extensionsView.toArray(new String[extensionsView.size()]);
    }

    public String getPreviewURL(WebUserSession webSess, String nodeId) 
    {
        if (previewURL != null) { 
            return webSess.encodeURL(replaceURLPlaceholders(previewURL, webSess.getSessionId(), nodeId));
        } else {
            return null;
        }
    }

    public void openEditor(WebUserSession webSess, String nodeId) throws Exception 
    {
        String url = getEditURL(webSess, nodeId);
        String js = getJSOpenWindow(webSess, url);
        webSess.evalJavaScript(js);
    }

    public void openViewer(WebUserSession webSess, String nodeId) throws Exception 
    {
        String url = getViewURL(webSess, nodeId);
        String js = getJSOpenWindow(webSess, url);
        webSess.evalJavaScript(js);
    }

    public void setCharEntities(CharEntity[] entities) 
    {
        // Do nothing by default. Should be overwritten by content editor handlers.
    }


    /* --------------  Other public methods  ---------------------- */

    public String getRelativeAppURL()
    {
        return relativeAppURL;
    }
    
    public String getEditURL(WebUserSession webSess, String nodeId) 
    {
        return webSess.encodeURL(replaceURLPlaceholders(editURL, webSess.getSessionId(), nodeId));
    }
    
    public String getViewURL(WebUserSession webSess, String nodeId) 
    {
        return webSess.encodeURL(replaceURLPlaceholders(viewURL, webSess.getSessionId(), nodeId));
    }
    
    public String getJSOpenWindow(WebUserSession webSess, String url)
    {
        return replaceJSPlaceholders(js_open_command, url, 0, 0, win_default_width, win_default_height);
    }
    
    /* --------------  Private methods  ---------------------- */

    private String replaceURLPlaceholders(String url, String sessId, String nodeId) 
    {
        return url.replace("{docsess}", sessId)
                  .replace("{nodeid}", nodeId) 
                  .replace("{appid}", applicationId) 
                  .replace("{stamp}", Long.toString(System.currentTimeMillis()));
                  // .replace("{edit}", edit ? "true" : "false");
    }
    
    private String replaceJSPlaceholders(String js, String url, int left, int top, int width, int height)
    {
        return js.replace("{url}", url)
                 .replace("{win_left}", Integer.toString(left))
                 .replace("{win_top}", Integer.toString(top))
                 .replace("{win_width}", Integer.toString(width))
                 .replace("{win_height}", Integer.toString(height));
    }
    
//    private SortedSet<String> mimeTypesFromString(String line)
//    {
//        SortedSet<String> res = new TreeSet<String>();
//        if (line != null) {
//            for (String mtype : line.split(",")) {
//                mtype = mtype.trim();
//                if (! mtype.equals("")) {
//                    res.add(mtype);
//                }
//            }
//        }
//        return res;
//    }

    private SortedSet<String> extensionsFromString(String line)
    {
        SortedSet<String> res = new TreeSet<String>();
        if (line != null) {
            line = line.toLowerCase();
            StringTokenizer st = new StringTokenizer(line, ", ");
            while (st.hasMoreTokens()) {
                String ext = normalizeExt(st.nextToken());
                if (! ext.equals("")) {
                    res.add(ext);
                }
            }
        }
        return res;
    }

    /**
     * Normalizes the file extension to lower case letters and removes any
     * dot at the beginning of the file extension.
     * This is required to allow file extension comparison using the normal
     * equals() implementation of java.lang.String.
     * See also org.docma.webapp.ExtAssignment.normalizeExt().
     * @param ext
     * @return 
     */
    public static String normalizeExt(String ext)
    {
        ext = ext.trim();
        if (ext.startsWith(".")) {
            ext = ext.substring(1).trim();
        }
        return ext.toLowerCase();
    }

}
