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

/**
 *
 * @author MP
 */
public interface StoreConnection 
{
    String getStoreId();
    VersionId getVersionId();
    boolean isClosed();
    
    Node getNodeById(String id);
    Style[] getStyles();
    CharEntity[] getCharEntities();

    String getTranslationMode();
    void enterTranslationMode(String langCode);
    void leaveTranslationMode();
    String getLanguageCode();

    void startTransaction() throws Exception;
    void commitTransaction() throws Exception;
    void rollbackTransaction();
    boolean runningTransaction();
    
}
