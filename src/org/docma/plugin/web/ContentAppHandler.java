/*
 * ContentAppHandler.java
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

import org.docma.plugin.CharEntity;

/**
 *
 * @author MP
 */
public interface ContentAppHandler 
{
    void initialize(File webBasePath, String relativeAppPath, Properties props) throws Exception;
    String getApplicationId();
    String getApplicationName();
    String[] getSupportedEditExtensions();
    String[] getSupportedViewExtensions();
    String getViewURL(WebUserSession webSess, String nodeId);
    void openEditor(WebUserSession webSess, String nodeId) throws Exception;
    void setCharEntities(CharEntity[] entities);
}
