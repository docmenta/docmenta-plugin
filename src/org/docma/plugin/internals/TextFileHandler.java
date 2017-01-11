/*
 * TextFileHandler.java
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
package org.docma.plugin.internals;

import org.docma.plugin.web.DefaultContentAppHandler;
import org.docma.plugin.web.WebUserSession;

/**
 *
 * @author MP
 */
public class TextFileHandler extends DefaultContentAppHandler
{
    // Template positions where plugins are allowed to insert code
    public static final String INSERT_HTML_HEAD = "html_head";
    public static final String INSERT_BODY_START = "body_start";
    public static final String INSERT_BODY_END = "body_end";
    public static final String INSERT_JS_ON_LOAD = "js_on_load";
    public static final String INSERT_JS_BEFORE_SAVE = "js_before_save";
    public static final String INSERT_JS_ENTER_EDIT = "js_enter_edit";
    public static final String INSERT_JS_ENTER_VIEW = "js_enter_view";
    
    
    private static final ScriptInsertions insertions = new ScriptInsertions();

    public static String getHTMLHead(WebUserSession userSess, String ext)
    {
        return insertions.getInsertion(userSess, ext, INSERT_HTML_HEAD);
    }
    
    public static String getHTMLBodyStart(WebUserSession userSess, String ext)
    {
        return insertions.getInsertion(userSess, ext, INSERT_BODY_START);
    }
    
    public static String getHTMLBodyEnd(WebUserSession userSess, String ext)
    {
        return insertions.getInsertion(userSess, ext, INSERT_BODY_END);
    }
    
    public static String getJSOnLoad(WebUserSession userSess, String ext)
    {
        return insertions.getInsertion(userSess, ext, INSERT_JS_ON_LOAD);
    }
    
    public static String getJSBeforeSave(WebUserSession userSess, String ext)
    {
        return insertions.getInsertion(userSess, ext, INSERT_JS_BEFORE_SAVE);
    }
    
    public static String getJSEnterEdit(WebUserSession userSess, String ext)
    {
        return insertions.getInsertion(userSess, ext, INSERT_JS_ENTER_EDIT);
    }
    
    public static String getJSEnterView(WebUserSession userSess, String ext)
    {
        return insertions.getInsertion(userSess, ext, INSERT_JS_ENTER_VIEW);
    }

    public static void insertScript(ScriptInsertion ins)
    {
        insertions.addInsertion(ins);
    }
    
    public static void clearPluginInsertions(String pluginId)
    {
        insertions.clearPluginInsertions(pluginId);
    }
}
