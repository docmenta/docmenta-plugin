/*
 * Plugin.java
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
package org.docma.plugin;

/**
 *
 * @author MP
 */
public interface Plugin 
{
    void onLoad(PluginContext ctx) throws Exception;  // e.g. check license key, register AutoFormat classes
    void onUnload(PluginContext ctx) throws Exception;  // e.g. remove AutoFormat classes

    // void onStartSession(PluginContext ctx, UserSession userSess);
    // void onOpenProduct();
    // void onCloseProduct();
}
