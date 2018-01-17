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
import javax.servlet.http.HttpSession;
import org.docma.plugin.CharEntity;
import org.docma.plugin.Lock;
import org.docma.plugin.Node;
import org.docma.plugin.PluginUtil;
import org.docma.plugin.User;
import org.docma.plugin.internals.WindowPositionStorage;
import org.docma.plugin.internals.WindowSizeStorage;

/**
 *
 * @author MP
 */
public class DefaultContentAppHandler implements ContentAppHandler, WindowSizeStorage, WindowPositionStorage
{
    // HTTP session attributes used to store the window position. 
    private static final String ATTRIBUTE_WIN_POS_X = "win_pos_x";
    private static final String ATTRIBUTE_WIN_POS_Y = "win_pos_y";

    // User properties used to store the window size.
    private static final String USER_PROP_WIN_WIDTH = "win_width";
    private static final String USER_PROP_WIN_HEIGHT = "win_height";
    
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
    public static final String PROP_SET_EDIT_LOCK = "set_edit_lock";

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
    private boolean set_edit_lock;
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
        set_edit_lock = props.getProperty(PROP_SET_EDIT_LOCK, "").equalsIgnoreCase("true");
        
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

    public void openEditor(final WebUserSession webSess, final String nodeId) throws Exception 
    {
        final String url = getEditURL(webSess, nodeId);
        final User sessUser = webSess.getUser();
        if (set_edit_lock) {
            final Node node = webSess.getOpenedStore().getNodeById(nodeId);

            // Check if lock exists
            Lock lock = node.getLock();
            if (lock != null) {
                String usr_id = lock.getUserId();
                if ((usr_id != null) && usr_id.equals(sessUser.getId())) {
                    webSess.showMessage(
                        webSess.getLabel("text.confirm_self_locked"), 
                        webSess.getLabel("question.continue"), MessageType.QUESTION, 
                        new ButtonType[] { ButtonType.OK, ButtonType.CANCEL }, 
                        ButtonType.OK, 
                        new UIListener() {
                            public void onEvent(UIEvent evt) 
                            {
                                // If user clicks okay, refresh lock and open editor window
                                if (evt.isClick() && evt.isButtonTarget() &&
                                    ButtonType.OK.equals(evt.getButtonType())) {
                                    node.refreshLock();
                                    openWindow(webSess, sessUser, url);
                                }
                            }
                        }
                    );
                    return;
                } else {  // Node is locked by another user 
                    User usr = webSess.getApplicationContext().getUser(usr_id);
                    String usr_name = (usr != null) ? usr.getName() : null;
                    if ((usr_name == null) || usr_name.equals("")) {
                        usr_name = "'" + usr_id + "'";
                    } else {
                        usr_name = "'" + usr_name + "' [" + usr_id + "]";
                    }
                    webSess.showMessage(webSess.getLabel("text.locked_by_user", usr_name));
                    return;
                }
            }

            // Set lock
            if (! node.setLock()) {
                webSess.showMessage(webSess.getLabel("text.could_not_set_lock"));
                return;
            }
        }
        
        openWindow(webSess, sessUser, url);
    }

    public void openViewer(WebUserSession webSess, String nodeId) throws Exception 
    {
        String url = getViewURL(webSess, nodeId);
        openWindow(webSess, webSess.getUser(), url);
    }

    public void setCharEntities(CharEntity[] entities) 
    {
        // Do nothing by default. Should be overwritten by content editor handlers.
    }

    /* --------------  Interface WindowPositionStorage ---------------------- */

    public String getWindowPosLeft(WebUserSession webSess)
    {
        HttpSession hsess = webSess.getHttpSession();
        String att_prefix = WebUserSession.class.getName() + "." + getApplicationId() + "."; 
        Object posx = hsess.getAttribute(att_prefix + ATTRIBUTE_WIN_POS_X);
        String win_left = (posx instanceof Integer) ? posx.toString() : "" + WINDOW_DEFAULT_POSITION_X;
        return win_left;
    }
    
    public String getWindowPosTop(WebUserSession webSess)
    {
        HttpSession hsess = webSess.getHttpSession();
        String att_prefix = WebUserSession.class.getName() + "." + getApplicationId() + "."; 
        Object posy = hsess.getAttribute(att_prefix + ATTRIBUTE_WIN_POS_Y);
        String win_top = (posy instanceof Integer) ? posy.toString() : "" + WINDOW_DEFAULT_POSITION_Y;
        return win_top;
    }

    public void setWindowPos(WebUserSession webSess, String win_xpos, String win_ypos)
    {
        if (win_xpos == null) win_xpos = "";
        if (win_ypos == null) win_ypos = "";
        try {
            // Save window position in session object
            if (! (win_xpos.equals("") || win_ypos.equals(""))) {
                HttpSession hsess = webSess.getHttpSession();
                String att_prefix = WebUserSession.class.getName() + "." + getApplicationId() + "."; 
                hsess.setAttribute(att_prefix + ATTRIBUTE_WIN_POS_X, new Integer(win_xpos));
                hsess.setAttribute(att_prefix + ATTRIBUTE_WIN_POS_Y, new Integer(win_ypos));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    
    /* --------------  Interface WindowSizeStorage ---------------------- */

    public String getWindowWidth(WebUserSession webSess)
    {
        User sessUser = webSess.getUser();
        String prop_prefix = ContentAppHandler.class.getName() + "." + getApplicationId() + ".";
        String win_width = sessUser.getProperty(prop_prefix + USER_PROP_WIN_WIDTH);
        if ((win_width == null) || win_width.equals("")) {
            win_width = "" + win_default_width;
        }
        return win_width;
    }
    
    public String getWindowHeight(WebUserSession webSess)
    {
        User sessUser = webSess.getUser();
        String prop_prefix = ContentAppHandler.class.getName() + "." + getApplicationId() + ".";
        String win_height = sessUser.getProperty(prop_prefix + USER_PROP_WIN_HEIGHT);
        if ((win_height == null) || win_height.equals("")) {
            win_height = "" + win_default_height;
        }
        return win_height;
    }
    
    public void setWindowSize(WebUserSession webSess, String win_width, String win_height)
    {
        if (win_width == null) win_width = "";
        if (win_height == null) win_height = "";
        try {
            // Save window size as user property:
            User usr = webSess.getUser();
            String prop_prefix = ContentAppHandler.class.getName() + "." + getApplicationId() + ".";
            String prop_width = prop_prefix + USER_PROP_WIN_WIDTH;
            String prop_height = prop_prefix + USER_PROP_WIN_HEIGHT;
            String old_width = usr.getProperty(prop_width);
            String old_height = usr.getProperty(prop_height);
            if (! (win_width.equals(old_width) && win_height.equals(old_height))) {
                String[] p_names = { prop_width, prop_height };
                String[] p_vals = { win_width, win_height };
                usr.setProperties(p_names, p_vals);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
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
        // Get editor position 
        HttpSession hsess = webSess.getHttpSession();
        String att_prefix = WebUserSession.class.getName() + "." + getApplicationId() + "."; 
        Object posx = hsess.getAttribute(att_prefix + ATTRIBUTE_WIN_POS_X);
        Object posy = hsess.getAttribute(att_prefix + ATTRIBUTE_WIN_POS_Y);
        String win_left = (posx instanceof Integer) ? posx.toString() : "" + WINDOW_DEFAULT_POSITION_X;
        String win_top = (posy instanceof Integer) ? posy.toString() : "" + WINDOW_DEFAULT_POSITION_Y;
        
        // Get editor width and height
        User sessUser = webSess.getUser();
        String prop_prefix = ContentAppHandler.class.getName() + "." + getApplicationId() + ".";
        String win_width = sessUser.getProperty(prop_prefix + USER_PROP_WIN_WIDTH);
        String win_height = sessUser.getProperty(prop_prefix + USER_PROP_WIN_HEIGHT);
        if ((win_width == null) || win_width.equals("")) {
            win_width = "" + win_default_width;
        }
        if ((win_height == null) || win_height.equals("")) {
            win_height = "" + win_default_height;
        }
        return replaceJSPlaceholders(js_open_command, url, win_left, win_top, win_width, win_height);
    }

    /* --------------  Private methods  ---------------------- */

    private void openWindow(WebUserSession webSess, User sessUser, String url)
    {
        String js = getJSOpenWindow(webSess, url);
        webSess.evalJavaScript(js);
    }

    private String replaceURLPlaceholders(String url, String sessId, String nodeId) 
    {
        return url.replace("{docsess}", sessId)
                  .replace("{nodeid}", nodeId) 
                  .replace("{appid}", applicationId) 
                  .replace("{stamp}", Long.toString(System.currentTimeMillis()));
                  // .replace("{edit}", edit ? "true" : "false");
    }
    
    private String replaceJSPlaceholders(String js, String url, String left, String top, String width, String height)
    {
        return js.replace("{url}", url)
                 .replace("{win_left}", left)
                 .replace("{win_top}", top)
                 .replace("{win_width}", width)
                 .replace("{win_height}", height);
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
