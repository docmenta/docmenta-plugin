/*
 * WebUserSession.java
 * 
 *  Copyright (C) 2013  Manfred Paula, http://www.docmenta.org
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

import org.docma.plugin.*;
import javax.servlet.http.HttpSession;

/**
 *
 * @author MP
 */
public interface WebUserSession extends UserSession
{
    final String MSG_QUESTION = "question";
    final String MSG_WARNING = "warning";
    final String MSG_ERROR = "error";
    final String MSG_INFO = "info";
    
    
    String  getLabel(String key);
    
    String addDialog(String zulPath);
    Object getDialog(String dialogId);
    boolean removeDialog(String dialogId);
    
    void addAdminTab(WebPluginContext ctx, String tabId, String title, int pos, String zulPath);
    boolean removeAdminTab(String tabId);
    
    void addPublishingTab(WebPluginContext ctx, String tabId, String title, int pos, String zulPath);
    boolean removePublishingTab(String tabId);
    
    void addUserTab(WebPluginContext ctx, String tabId, String title, int pos, String zulPath);
    boolean removeUserTab(String tabId);

    String getThemeProperty(String propName);
    OutputConfig getPreviewHTMLConfig();

    ContentAppHandler getContentAppHandler(String application_id);
    
    String encodeURL(String url);
    HttpSession getHttpSession();

    void showMessage(String message);
    void showMessage(String message, String title, String msg_type);
    void showMessage(String message, String title, String msg_type, UIListener listener);
    void showMessage(String message, String title, String msg_type, 
                     ButtonType[] btns, ButtonType focus, UIListener listener);
    void evalJavaScript(String javascript);
}
