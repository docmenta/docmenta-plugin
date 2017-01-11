/*
 * WebUserSession.java
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

import javax.servlet.http.HttpSession;

import org.docma.plugin.*;

/**
 *
 * @author MP
 */
public interface WebUserSession extends UserSession
{
    // Methods to extend UI    
    String addDialog(String zulPath);
    Object getDialog(String dialogId);
    boolean removeDialog(String dialogId);

    void addAdminTab(WebPluginContext ctx, String tabId, String title, int pos, String zulPath);
    void addPublishingTab(WebPluginContext ctx, String tabId, String title, int pos, String zulPath);
    void addUserTab(WebPluginContext ctx, String tabId, String title, int pos, String zulPath);
    Object getTabComponent(String tabId, String componentId);
    
    void addMenuItem(WebPluginContext ctx, String parentMenuId, String itemId, 
                     String label, String imageUrl, String neighbourId, boolean insertBefore);
    void addMenuSeparator(WebPluginContext ctx, String parentMenuId, String separatorId,
                          String neighbourId, boolean insertBefore);
    void addSubMenu(WebPluginContext ctx, String parentMenuId, String subMenuId, 
                    String label, String imageUrl, String neighbourId, boolean insertBefore);
    String  getMenuLabel(String menuOrItemId);
    void    setMenuLabel(String menuOrItemId, String label);
    String  getMenuImage(String itemId);
    void    setMenuImage(String itemId, String imageUrl);
    boolean isMenuDisabled(String itemId);
    void    setMenuDisabled(String itemId, boolean disabled);
    boolean isMenuVisible(String menuOrItemId);
    void    setMenuVisible(String menuOrItemId, boolean visible);
    boolean isMenuCheckbox(String itemId);
    void    setMenuCheckbox(String itemId, boolean checkbox);
    boolean isMenuChecked(String itemId);
    void    setMenuChecked(String itemId, boolean checked);
    
    void setUIListener(WebPluginContext ctx, UIListener listener);
    
    // UI utility methods
    void showMessage(String message);
    void showMessage(String message, String title, MessageType msg_type);
    void showMessage(String message, String title, MessageType msg_type, UIListener listener);
    void showMessage(String message, String title, MessageType msg_type, 
                     ButtonType[] btns, ButtonType focus, UIListener listener);
    
    // Editor plug-in methods
    ContentAppHandler getContentAppHandler(String application_id);

    // Methods to retrieve user inputs
    int selectedNodesCount();
    Node getSingleSelectedNode(boolean showSelectError);
    Node[] getSelectedSiblingNodes(boolean showSelectError);
    
    // Other application specific UI methods
    String getThemeProperty(String propName);
    OutputConfig getPreviewHTMLConfig();

    // General methods for web development
    String encodeURL(String url);
    HttpSession getHttpSession();
    void evalJavaScript(String javascript);
}
