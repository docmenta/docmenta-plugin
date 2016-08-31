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
 * The <code>Plugin</code> interface provides hooks for extending the 
 * application's functionality. This interface needs to be implemented by a 
 * plug-in developer.
 * A class that implements this interface will be instanciated by the plug-in 
 * framework using the default constructor of the class (i.e. the constructor 
 * without arguments).
 * The methods of this interface allow the plug-in to react on events that
 * are triggered by the framework during the lifecycle of a plug-in.
 * For example, on startup of the application server, the onLoad event is 
 * triggered for all installed and enabled plug-ins.
 * 
 * @author MP
 */
public interface Plugin 
{
    void onLoad(PluginContext ctx) throws Exception;  // e.g. register AutoFormat classes
    void onUnload(PluginContext ctx) throws Exception;  // e.g. remove AutoFormat classes

    // void onStartSession(PluginContext ctx, UserSession userSess);
    // void onOpenProduct();
    // void onCloseProduct();
}
