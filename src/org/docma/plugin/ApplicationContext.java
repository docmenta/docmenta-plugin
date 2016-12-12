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

import java.util.Map;
import org.docma.userapi.UserManager;

/**
 *
 * @author MP
 */
public interface ApplicationContext 
{
    /**
     * Returns the value of the application property identified by 
     * <code>name</code>.
     * 
     * @param name  the name of the property to be retrieved
     * @return  the property value
     */
    String getApplicationProperty(String name) throws DocmaException;
    
    /**
     * Sets the value of the application property identified by 
     * <code>name</code>.
     * 
     * @param name  the property name
     * @param value  the property value
     */
    void setApplicationProperty(String name, String value) throws DocmaException;
    
    /**
     * Sets the application properties given in the <code>props</code> argument.
     * Only the properties given in the <code>props</code> argument are updated.
     * Any other existing application properties are unchanged.
     * 
     * @param props  the application properties to be created or updated
     */
    void setApplicationProperties(Map<String, String> props) throws DocmaException;

    /**
     * Returns the available content languages.
     *
     * @return  all content languages that are supported by the application
     */
    Language[] getSupportedContentLanguages() throws DocmaException;

    /**
     * Returns the application's character entity definitions.
     * 
     * @return  the character entity definitions of the application context
     */
    CharEntity[] getCharEntities() throws DocmaException;

    /**
     * Sets the application's character entity definitions.
     * 
     * <p>Note that modifications to the character entity definitions might not 
     * be visible to already opened user sessions.</p>
     * 
     * @param entities  the character entity definitions
     */
    void setCharEntities(CharEntity[] entities) throws DocmaException;

    /**
     * Creates a new transient character entity definition.
     * <p>Note that the returned character entity definition is <em>not</em>
     * part of the application configuration until it is persisted by passing  
     * it as argument to the { #setCharEntities(CharEntity[])} method.</p>
     * 
     * @param sym   the symbolic character entity representation
     * @param num   the numeric character entity representation
     * @param desc  a descriptive title
     * @return  the new character entity definition
     * @throws DocmaException   if the character entity definition cannot be 
     *                          created, for example, due to invalid symbolic
     *                          or numeric representation
     */ 
    CharEntity createCharEntity(String sym, String num, String desc) throws DocmaException;
    
    /**
     * Creates a new transient character entity definition with the 
     * given visibility.
     * <p>Note that the returned character entity definition is <em>not</em>
     * part of the application configuration until it is persisted by passing  
     * it as argument to the { #setCharEntities(CharEntity[])} method.</p>
     * 
     * @param sym   the symbolic character entity representation
     * @param num   the numeric character entity representation
     * @param desc  a descriptive title
     * @param sel   whether the character entity shall be selectable in the user
     *              interface (<code>true</code>) or hidden (<code>false</code>)
     * @return  the new character entity definition
     * @throws DocmaException   if the character entity definition cannot be 
     *                          created, for example, due to invalid symbolic
     *                          or numeric representation
     */
    CharEntity createCharEntity(String sym, String num, String desc, boolean sel) throws DocmaException;
    
    boolean hasObject(String objectName);
    Object getObject(String objectName);
    void setObject(String objectName, Object instance);

    Logger getLogger();
    
    UserManager getUserManager();
    void setUserManager(UserManager um);
    
    boolean userExists(String userId);
    User getUser(String userId);
}
