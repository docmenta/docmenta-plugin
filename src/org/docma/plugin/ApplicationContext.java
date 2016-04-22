/*
 * ApplicationContext.java
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

import org.docma.userapi.UserManager;

/**
 *
 * @author MP
 */
public interface ApplicationContext 
{
    // String getApplicationProperty(String name);
    // void   setApplicationProperty(String name, String value);
    // void   setApplicationProperties(Properties props);
    boolean hasObject(String objectName);
    Object getObject(String objectName);
    void setObject(String objectName, Object instance);
    UserManager getUserManager();
    void setUserManager(UserManager um);
    boolean userExists(String userId);
    User getUser(String userId);
}
