/*
 * UserSession.java
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

import java.io.File;
import java.util.Locale;

/**
 *
 * @author MP
 */
public interface UserSession 
{
    //
    // ***************  Basic session methods  *****************
    //
    
    /**
     * Returns the session identifier.
     * The identifier consists of ASCII letters and/or digits only.
     * 
     * @return  the session id
     */
    String getSessionId();

    /**
     * 
     * @return  the application context
     */
    ApplicationContext getApplicationContext();

    /**
     * Returns the current user.
     * 
     * @return  the user that owns this session
     */    
    User getUser();

    //
    // ***************  Internationalization methods  ******************
    //
    
    /**
     * Returns the user interface (UI) locale for this session.
     * 
     * @return  the locale representing the current user interface language/country.
     */
    Locale getCurrentLocale();
    
    /**
     * Returns the localized label for the given <code>key</code>.
     * The label is localized to the current user interface language.
     * If no localization for the current user interface language exists,
     * then the label may be returned in English. 
     * If no localization for the given <code>key</code> exists at all, 
     * then <code>key</code> itself is returned.
     * 
     * @param key  the label name
     * @param args  the arguments to be inserted for the placeholders 
     * @return  the localized label for the current display language
     * @see #getUILanguageCode()
     */
    String  getLabel(String key, Object... args);

    //
    // ***************  Store connection methods  *****************
    //
    
    /**
     * Returns the identifiers of all stores.
     * 
     * @return  all store identifiers
     */
    String[] listStores() throws DocmaException;
    
    /**
     * Returns the list of all versions for the given store.
     * The returned array is sorted according to the ordering defined by 
     * {@link VersionId}.
     * 
     * @param storeId  the store identifier
     * @return  the identifiers of all store versions
     */
    VersionId[] listVersions(String storeId) throws DocmaException;
    
    /**
     * Creates a new version identifier instance from the given string.
     * 
     * @param verId   the string representation of the version identifier
     * @return  the version identifier instance
     * @throws InvalidVersionIdException  if the given string is no valid 
     *                                    version identifier
     */
    VersionId createVersionId(String verId) throws InvalidVersionIdException;

    StoreConnection getOpenedStore();

    /**
     * Creates a new connection to the given store.
     * This method provides the same functionality as the
     * {@link #createTempStoreConnection(java.lang.String, VersionId) } method,
     * except that the version identifier is expected as string.
     * In other words, given that <code>verId</code> is of type  
     * <code>String</code>, the expression 
     * <code>sess.createTempStoreConnection(sId, verId)</code> gives the same 
     * result as 
     * <code>sess.createTempStoreConnection(sId, sess.createVersionId(verId))</code>.
     * 
     * @param storeId  the store to connect to 
     * @param verId  the version to connect to
     * @return  the created store connection
     */
    StoreConnection createTempStoreConnection(String storeId, String verId) throws DocmaException;
    
    /**
     * Creates a new connection to the given store.
     * The returned connection should be closed by calling
     * {@link #closeTempStoreConnection(StoreConnection) } after the connection
     * is no longer used.
     * Note that the returned connection is automatically closed when
     * this user session is closed.
     * 
     * @param storeId  the store to connect to 
     * @param verId  the version to connect to
     * @return  the created store connection
     */
    StoreConnection createTempStoreConnection(String storeId, VersionId verId) throws DocmaException;
    
    void closeTempStoreConnection(StoreConnection conn) throws DocmaException;

    /**
     * Returns the version identifier of the latest version for the given store.
     * 
     * @param storeId  the store identifier
     * @return  the version identifier of the latest version
     */
    VersionId getLatestVersionId(String storeId) throws DocmaException;

    /**
     * Creates a new version for the given store, 
     * which is derived from the version <code>baseVersion</code>.
     * The identifier of the new version is set to
     * <code>newVersion</code>.
     * <p>
     * Note that the version identifier <code>newVersion</code> has to be 
     * higher than <code>baseVersion</code> (according to the ordering 
     * defined by {@link VersionId}).
     * </p>
     * <p>
     * Be aware that the creation of a new version can take a long time,
     * if it is derived from an existing version with large amount of data.
     * Therefore a progress bar should be displayed as long as this operation
     * is running.
     * </p>
     * 
     * @param storeId  the store for which to create a new version
     * @param baseVersion  the base version from which to derive the new version
     * @param newVersion  the identifier of the new version
     */
    void createVersion(String storeId, VersionId baseVersion, VersionId newVersion) throws DocmaException;

    /**
     * Deletes the version <code>verId</code> of store <code>storeId</code>.
     * <p>
     * Be aware that in case the version contains a large amount of data, 
     * the deletion can take a long time.
     * Therefore a progress bar should be displayed as long as this operation
     * is running.
     * </p>
     * 
     * @param storeId  the store identifier
     * @param verId  the identifier of the version to be deleted
     */
    void deleteVersion(String storeId, VersionId verId) throws DocmaException;
    
    /**
     * Changes the version identifier from <code>oldVerId</code> to 
     * <code>newVerId</code>.
     * 
     * @param storeId  the store identifier
     * @param oldVerId  the identifier of the version to be renamed
     * @param newVerId  the new version identifier 
     */
    void renameVersion(String storeId, VersionId oldVerId, VersionId newVerId) throws DocmaException;

    /**
     * Returns the identifiers of all versions that are derived from 
     * the version <code>verId</code>.
     * 
     * @param storeId  the store identifier
     * @param verId  the version identifier
     * @return  the list of versions derived from <code>verId</code>
     */
    VersionId[] getSubVersions(String storeId, VersionId verId) throws DocmaException;
    
    /**
     * Returns the version from which the version <code>verId</code> is 
     * derived from. If <code>verId</code> is not derived 
     * from any other version, then <code>null</code> is returned.
     * 
     * @param storeId  the store identifier
     * @param verId  the version identifier
     * @return  the identifier of the version from which <code>verId</code> 
     *          is derived from, or <code>null</code> if <code>verId</code>
     *          is a root version
     */
    VersionId getVersionDerivedFrom(String storeId, VersionId verId) throws DocmaException;

    /**
     * Returns the currently running and queued export jobs. The order of the 
     * jobs in the returned array reflects the queue position at the time 
     * this method is called.     
     * If <code>res</code> is the returned array, then <code>res[0]</code> is the 
     * currently running export job. The next job in the queue to be processed is 
     * <code>res[1]</code> and so on.
     * 
     * @return  the running and queued export jobs
     */
    ExportJob[] getExportQueue() throws DocmaException;

    /**
     * Returns the existing translation languages for the given import 
     * directory.
     *
     * @param importDir  the import directory
     * @return   the available translation languages for the nodes stored in 
     *           in the given directory
     */
    Language[] listImportTranslations(File importDir) throws DocmaException;

    //
    // ***************  To do  *****************
    //

//    public void createDocStore(String storeId, String[] propNames, String[] propValues) throws DocException 
//    public void addDocStore(String storeId, File externalStorePath) throws DocException
//    public void addExternalDbDocStore(String storeId, String dburl, String driver, String dialect, String usr, String pw) throws DocException
//    public void deleteDocStore(String storeId) throws DocException 
//    public void removeExternalDocStoreConnection(String storeId) throws DocException 
//    public void changeDocStoreId(String oldId, String newId) throws DocException 

//   boolean isValidVersionId(String verId)
//   boolean usersConnected(String storeId, VersionId verId)
//   boolean exportsExist(String storeId, VersionId verId, String versionState)  // better: listPublications(...)
    
    /*
     * Returns the store entry for the given store.
     */
//  StoreEntry getStoreEntry(String storeId)
//      public String getStoreProperty(String name) throws DocmaException
//      public void setStoreProperty(String name, String value) throws DocmaException 
//      public void setStoreProperties(Map<String, String> props) throws DocmaException 
//      Language getOriginalLanguage()
//      void setOriginalLanguage(String lang_code) throws DocmaException
//      String getStoreTitle()       -> getDocStoreTitle()
//      Language[] getTranslationLanguages()
//      boolean hasTranslationLanguage(String lang_code)
//      // public boolean addTranslationLanguage(String store_id, String lang_code) throws DocException
//      // public boolean deleteTranslationLanguage(String store_id, String lang_code) throws DocException

//    /*
//     * Returns the version entry for the given store version.
//     */     
//    VersionEntry getVersionEntry(String storeId, VersionId verId) throws DocmaException    
//      public String getVersionProperty(String storeId, DocVersionId verId, String name)
//      public void setVersionProperty(String storeId, DocVersionId verId, String name, String value) throws DocException
//      public void setVersionProperties(String storeId, DocVersionId verId, String[] names, String[] values) throws DocException
//      public String getVersionComment(String storeId, DocVersionId verId)
//      public void setVersionComment(String storeId, DocVersionId verId, String comment)
//      public Date getVersionCreationDate(String storeId, DocVersionId verId)
//      public Date getVersionLastModifiedDate(String storeId, DocVersionId verId)
//      public Date getVersionReleaseDate(String storeId, DocVersionId verId)
//      public String getVersionState(String storeId, DocVersionId verId)
//      public String getVersionState(String storeId, DocVersionId verId, String lang)
//      public void setVersionState(String storeId, DocVersionId verId, String newState)

//    /*
//     * Starts a background task for this store.
//     */
//    public Activity createStoreActivity(String storeId) throws DocException
//    
//    /*
//     * Retrieves the status of a running background task for this store.
//     */
//    public Activity getStoreActivity(String storeId)
//    
//    // public boolean removeStoreActivity(String storeId)
    
}
