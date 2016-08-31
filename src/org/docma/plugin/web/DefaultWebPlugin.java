/*
 * DefaultWebPlugin.java
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

/**
 *
 * @author MP
 */
public class DefaultWebPlugin extends DefaultPlugin implements WebPlugin
{

    public void onInitMainWindow(WebPluginContext ctx, WebUserSession webSess) 
    {
    }
    
    public void onShowConfigDialog(WebPluginContext ctx, WebUserSession webSess) 
    {
    }



//    public void onEnableClick(WebPluginContext ctx, WebUserSession webSess) 
//    {
//        if (! ctx.isPluginLoaded()) {
//            ctx.loadPlugin();
//        }
//        if (! ctx.isLoadPluginOnStartUp()) {
//            ctx.setLoadPluginOnStartUp(true);
//        }
//    }
//
//    public void onDisableClick(WebPluginContext ctx, WebUserSession webSess) 
//    {
//        if (ctx.isPluginLoaded()) {
//            ctx.unloadPlugin();
//        }
//        if (ctx.isLoadPluginOnStartUp()) {
//            ctx.setLoadPluginOnStartUp(false);
//        }
//    }

}
