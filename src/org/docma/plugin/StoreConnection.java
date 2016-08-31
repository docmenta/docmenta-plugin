/*
 * StoreConnection.java
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
package org.docma.plugin;

import java.util.Properties;

/**
 *
 * @author MP
 */
public interface StoreConnection 
{
    String getStoreId();
    VersionId getVersionId();
    boolean isClosed();
    
    Node getNodeById(String id) throws DocmaException;
    Style[] getStyles() throws DocmaException;
    
    CharEntity[] getCharEntities() throws DocmaException;
    String encodeCharEntities(String text, boolean symbolic) throws DocmaException;

    String getTranslationMode() throws DocmaException;
    void enterTranslationMode(String langCode) throws DocmaException;
    void leaveTranslationMode() throws DocmaException;
    String getLanguageCode() throws DocmaException;

    void startTransaction() throws DocmaException;
    void commitTransaction() throws DocmaException;
    void rollbackTransaction();
    boolean runningTransaction();

    /**
     * Indicates whether <code>name</code> complies to the rules of a valid
     * alias. Note that this method only checks the syntactical rules.
     * It does <em>not</em> check whether <code>name</code> is already used
     * by another node.
     * 
     * @param name  the name to check
     * @return  <code>true</code> if <code>name</code> is a valid alias;
     *          <code>false</code> otherwise
     */
    boolean isValidAlias(String name);
    
    String prepareXHTML(String content, Properties props) throws DocmaException;
    
}
