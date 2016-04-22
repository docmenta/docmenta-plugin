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
    public static final int EDITOR_DEFAULT_POSITION_X = 100;
    public static final int EDITOR_DEFAULT_POSITION_Y = 100;
    public static final int EDITOR_DEFAULT_WIDTH = 680;
    public static final int EDITOR_DEFAULT_HEIGHT = 480;
    public static final String EDITOR_DEFAULT_PARA_INDENT = "24pt";

    // Content-Handler properties
    public static final String PROP_APPLICATION_NAME = "application_name";
    public static final String PROP_URL_EDIT = "url_edit";
    public static final String PROP_URL_VIEW = "url_view";
    public static final String PROP_EXTENSIONS_EDIT = "extensions_edit";
    public static final String PROP_EXTENSIONS_VIEW = "extensions_view";

    private static final String REGEXP_ID = "[A-Za-z][0-9A-Za-z_-]*";

    
    private File webBasePath;
    private File contentAppPath;
    private String contentAppRelativePath;
    
    private String applicationId;
    private String applicationName;
    private String editURL;
    private String viewURL;
    private SortedSet<String> extensionsEdit;
    private SortedSet<String> extensionsView;

    public DefaultContentAppHandler() 
    {
    }
    
    public void initialize(File webBasePath, String relativeAppPath, Properties props) throws Exception
    {
        this.webBasePath = webBasePath;
        contentAppPath = new File(webBasePath, relativeAppPath);
        contentAppRelativePath = relativeAppPath;

        applicationId = contentAppPath.getName().trim();
        if (! applicationId.matches(REGEXP_ID)) {
            throw new Exception("Invalid content application ID. Allowed characters are letters, digits, underscore and dash.");
        }
        
        applicationName = props.getProperty(PROP_APPLICATION_NAME);
        if ((applicationName == null) || applicationName.trim().equals("")) {
            applicationName = applicationId.substring(0, 1).toUpperCase() +
                              applicationId.substring(1);
        }
        
        editURL = props.getProperty(PROP_URL_EDIT, "edit.jsp?nodeid={nodeid}&docmasess={docmasess}");
        viewURL = props.getProperty(PROP_URL_EDIT, "view.jsp?nodeid={nodeid}&docmasess={docmasess}");

        extensionsEdit = extensionsFromString(props.getProperty(PROP_EXTENSIONS_EDIT));
        extensionsView = extensionsFromString(props.getProperty(PROP_EXTENSIONS_VIEW));
    }

    public String getApplicationId() 
    {
        return applicationId;
    }

    public String getApplicationName() 
    {
        return applicationName;
    }

    public String[] getSupportedEditExtensions() 
    {
        return extensionsEdit.toArray(new String[extensionsEdit.size()]);
    }

    public String[] getSupportedViewExtensions() 
    {
        return extensionsView.toArray(new String[extensionsView.size()]);
    }

    public String getEditURL(WebUserSession webSess, String nodeId) 
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public String getViewURL(WebUserSession webSess, String nodeId) 
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void openEditor(WebUserSession webSess, String nodeId) throws Exception 
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void setCharEntities(CharEntity[] entities) 
    {
        // Do nothing by default. Should be overwritten by content editor handlers.
    }


    /* --------------  Private methods  ---------------------- */

    private String replaceURLPlaceholders(String url, String sessId, String nodeId, boolean edit, boolean isWin) 
    {
        return url.replace("{docmasess}", sessId)
                  .replace("{nodeid}", nodeId) 
                  .replace("{stamp}", Long.toString(System.currentTimeMillis()))
                  .replace("{edit}", edit ? "true" : "false")
                  .replace("{iswin}", isWin ? "true" : "false");
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
