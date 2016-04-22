/*
 * UserSession.java
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
public interface UserSession 
{
    String getSessionId();
    ApplicationContext getApplicationContext();
    User getUser();
    VersionId createVersionId(String verId) throws InvalidVersionIdException;
    StoreConnection getOpenedStore();

    StoreConnection createTempStoreConnection(String storeId, String verId) throws Exception;    
    StoreConnection createTempStoreConnection(String storeId, VersionId verId) throws Exception;
    void closeTempStoreConnection(StoreConnection conn);

    String[] listStores();
    VersionId[] listVersions(String storeId);
    VersionId getLatestVersion(String storeId);
    
}
