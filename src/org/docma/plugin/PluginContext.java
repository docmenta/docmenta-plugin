/*
 * PluginContext.java
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

import java.io.*;

/**
 *
 * @author MP
 */
public interface PluginContext 
{
    String getPluginId();
    File getPluginDirectory();
    // byte[] readPluginFile(String relative_path);
    // void   writePluginFile(String relative_path, byte[] content);

    ApplicationContext getApplicationContext();

    boolean isPluginLoaded();

    // String getPluginProperty(String name);
    // void   setPluginProperty(String name, String value);
    // void   setPluginProperties(Properties props);

}
