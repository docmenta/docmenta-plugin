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

import java.io.File;
import java.io.OutputStream;
import java.util.Map;
import java.util.Properties;

/**
 *
 * @author MP
 */
public interface StoreConnection 
{
    //
    // ***************  Basic connection methods  *****************
    //
    
    /**
     * Returns the unique connection identifier. This is an arbitrary string of 
     * non-whitespace ASCII characters. The identifier is unique in the context
     * of the web-application. The identifier remains unchanged
     * during the complete lifetime of the connection.
     *
     * @return  the connection identifier
     */
    String getConnectionId();
    
    /**
     * Returns the user session which owns this connection.
     *
     * @return  the user session instance
     */     
    UserSession getUserSession();

    /**
     * Closes this connection.
     * 
     * @throws DocmaException   if closing fails, or if an error occurs during 
     *                          closing of the connection
     */
    void close() throws DocmaException;
    
    /**
     * Indicates whether this connection is closed.
     * 
     * @return  <code>true</code> if the connection is closed;
     *          <code>false</code> otherwise
     * @see #close()
     */
    boolean isClosed();
    
    /**
     * Returns the identifier of the store.
     *
     * @return  the store identifier
     */
    String getStoreId();
    
    /**
     * Returns the version of the store.
     *
     * @return  the version identifier
     */
    VersionId getVersionId();
    
    /**
     * Returns the title of the store. 
     * Note that in contrast to the store id, the store title can  
     * contain spaces and can be changed at any time. 
     *
     * @return  the store title
     * @throws DocmaException  if the title cannot be retrieved,
     *                         for example due to a connection error
     */
    String getStoreTitle() throws DocmaException;
    
    /**
     * Returns the configured text-file extensions for this store.
     *
     * @return  the text-file extensions for this store
     * @throws DocmaException  if the extensions cannot be retrieved,
     *                         for example due to a connection error
     */
    String[] getTextFileExtensions() throws DocmaException;
    
    /**
     * Indicates whether the given string is a text-file extension.
     * This method returns <code>true</code>, if the given string is contained
     * in the array returned by {@link #getTextFileExtensions()}.
     * Otherwise <code>false</code> is returned.
     *
     * @param ext  file extension to be checked
     * @return <code>true</code> if the given string is a text-file extension;
     *         <code>false</code> otherwise
     * @throws DocmaException  if the return value cannot be retrieved,
     *                         for example due to a connection error
     */
    boolean isTextFileExtension(String ext) throws DocmaException;

    //    
    // ***************  Language related methods  *****************
    //
    
    /**
     * Enters the translation mode for this connection.
     * Calling this method switches the content language 
     * of the connection to the language identified by <code>lang_code</code>.
     * The language identified by the <code>lang_code</code> argument has to 
     * be one of the languages returned by {@link getTranslationLanguages()}.
     *
     * <p>To switch back to the original language, the 
     * {@link leaveTranslationMode()} method has to be called.</p>
     *
     * <p>If a connection is in translation-mode, then the node-methods 
     * retrieve or set the translated values instead of the original value. 
     * This applies to all node-methods that get or set translatable data.
     * Note that if a translated value does 
     * not exist, then the corresponding get-method of the node returns the value
     * for the original language. For details see the API documentation of  
     * {@link Node} and its sub-interfaces.</p>
     *
     * @param langCode   the language code of a translation language
     * @throws DocmaException  if the translation mode cannot be entered,
     *                         for example due to a connection error or 
     *                         missing access rights
     * @see #leaveTranslationMode()
     */
    void enterTranslationMode(String langCode) throws DocmaException;
    
    /**
     * Leaves the translation-mode. If the connection is <em>not</em> 
     * in translation-mode, then this method does nothing.
     *
     * @throws DocmaException  if leaving the translation mode fails,
     *                         for example due to a connection error  
     * @see #enterTranslationMode(String)
     */
    void leaveTranslationMode() throws DocmaException;
    
    /**
     * Indicates whether this connection is in translation-mode.
     * If the connection is in translation-mode, then the language code 
     * of the current translation language is returned.
     * Otherwise (original-mode) <code>null</code> is returned.
     *
     * @return  the language code if connection is in translation-mode; 
     *          <code>null</code> otherwise
     */
    String getTranslationMode() throws DocmaException;
    
    /**
     * Returns the current content-language for this connection.
     * If the connection is in translation-mode, then this is the current  
     * translation language. Otherwise, this is the original language of the 
     * store.
     * 
     * @return  the current content-language
     */
    Language getCurrentLanguage() throws DocmaException;
    
    /**
     * Returns the language that is defined as the original language for this
     * store.
     *
     * @return the store's original language
     */
    Language getOriginalLanguage() throws DocmaException;
    
    /**
     * Returns the languages that are defined as translation languages
     * for this store.
     *
     * @return  the store's translation languages
     */
    Language[] getTranslationLanguages() throws DocmaException;
    
    /**
     * Indicates whether the given language is defined as 
     * translation language for this store.
     *
     * @param lang_code  the language code to be checked
     * @return  <code>true</code> if the given language is defined as 
     *          translation language; <code>false</code> otherwise 
     */
    boolean hasTranslationLanguage(String lang_code) throws DocmaException;

    //
    // ***************  Transaction methods  *****************
    //
    
    /**
     * Starts a transaction for this connection.
     * 
     * @throws DocmaException  if starting the transaction fails,
     *                         for example due to a connection error, or 
     *                         because a transaction is already running
     */
    void startTransaction() throws DocmaException;
    
    /**
     * Commits a previously started transaction.
     * 
     * @throws DocmaException  if commiting the transaction fails,
     *                         for example due to a connection error
     */
    void commitTransaction() throws DocmaException;
    
    /**
     * Rolls back a previously started transaction.
     * 
     * @throws DocmaException  if rollback fails,
     *                         for example due to a connection error
     */
    void rollbackTransaction() throws DocmaException;
    
    /**
     * Indicates whether a transaction is currently running 
     * for this connection. A transaction is running if the 
     * {@link #startTransaction()} method has been called, but 
     * neither the {@link #commitTransaction()} method nor the 
     * {@link #rollbackTransaction()} has been called since then.
     * 
     * @return  <code>true</code> if a transaction is running;
     *          <code>false</code> otherwise
     */
    boolean runningTransaction();

    //
    // ***************  Node creation methods  ****************
    //
    
    /**
     * Creates a new transient {@link FileContent} node.
     * 
     * @param filename the filename to be assigned to the new node
     * @return  a new file node
     */
    FileContent createFileContent(String filename) throws DocmaException;
    
    /**
     * Creates a new transient {@link FileContent} node and optionally
     * sets the node alias.
     * 
     * @param filename  the filename to be assigned to the new node
     * @param setAlias  indication whether filename shall be set as alias
     * @return  a new file node
     */
    FileContent createFileContent(String filename, boolean setAlias) throws DocmaException;
    
    /**
     * Creates a new transient {@link Folder} node.
     * 
     * @param name  the name of the new folder 
     * @param folderType  the type of the folder to be created
     * @return  a new folder node
     */
    Folder createFolder(String name, FolderType folderType) throws DocmaException;
    
    /**
     * Creates a new transient {@link PubContent} node.
     * 
     * @param title  the title of the new content node
     * @return  a new publication content node
     */
    PubContent createPubContent(String title) throws DocmaException;
    
    /**
     * Creates a new transient {@link PubSection} node.
     * 
     * @param title  the title of the new section node
     * @return  a new section node
     */
    PubSection createPubSection(String title) throws DocmaException;
    
    /**
     * Creates a new transient content-inclusion node.
     * 
     * @return  a new reference node
     */
    Reference createContentInclusion() throws DocmaException;
    
    /**
     * Creates a new transient section-inclusion node.
     * 
     * @return  a new reference node
     */
    Reference createSectionInclusion() throws DocmaException;

    //    
    // ***************  Node retrieval methods  *****************
    //
    
    /**
     * Returns the root node of the store.
     * 
     * @return  the store's root node
     */
    Group getRoot() throws DocmaException;
    
    /**
     * Returns the node identified by the given id value.
     * If no node with the given id exists, then <code>null</code> is 
     * returned.
     * 
     * @param id  the identifier of the node to search for
     * @return  the node with the given id, or <code>null</code>
     */
    Node getNodeById(String id) throws DocmaException;
    
    /**
     * Returns the node identified by the given alias.
     * If no node with the given alias exists, then <code>null</code> is 
     * returned.
     * 
     * @param alias  the alias of the node to search for
     * @return  the node with the given alias, or <code>null</code>
     */
    Node getNodeByAlias(String alias) throws DocmaException;
    
    /**
     * Returns the identifier of the node with the given alias.
     * If no node with the given id exists, then <code>null</code> is 
     * returned.
     *
     * <p>Compared to the {@link #getNodeByAlias(String)} method, this method
     * just returns the node identifier instead of the node instance.
     * Therefore, this method might be more efficient than the 
     * expression <code>getNodeByAlias(alias).getId()</code>.</p>
     * 
     * @param alias  the alias of the node to search for
     * @return   the node identifier, or <code>null</code>
     */
    String getNodeIdByAlias(String alias) throws DocmaException;
    
    /**
     * Returns all nodes identified by the given link name.
     * If the link name is equal to the alias of a node, then 
     * an array with length 1, containing the node identified 
     * by the given alias, is returned.
     * If several nodes share the same link name, then the
     * returned array is sorted by the alias of the nodes.
     * If no node with the given link name exists, then an empty array is 
     * returned. 
     *
     * <p>See {@link Node#getLinkName()} for more information on link names.
     * </p>
     *
     * @param linkName  the link name to search for
     * @return  the nodes identified by the given link name
     * @see Node#getLinkName()
     */
    Node[] getNodesByLinkName(String linkName) throws DocmaException;
    
    /**
     * Returns all alias names for the given node class.
     * Supplying the argument value <code>null</code> gives the same result 
     * as supplying the argument <code>Node.class</code>.
     * The returned array is sorted according to the
     * <code>java.lang.String.compareTo(Object)</code> method.
     * If no alias exists for the given node class, then an empty array is 
     * returned.
     * 
     * <p>Example: The expression <code>conn.getAliases(Node.class)</code>
     * returns the aliases of all existing nodes, because {@link Node} is  
     * the base class of all nodes.</p>
     * 
     * @param nodeClass  the node class to search for, or <code>null</code>
     * @return  sorted array of alias names
     */
    String[] getAliases(Class nodeClass) throws DocmaException;
    
    /**
     * Returns information about all nodes for the given node class.
     * Supplying the argument value <code>null</code> gives the same result 
     * as supplying the argument <code>Node.class</code>.
     * If no nodes exists for the given node class, then an empty array is 
     * returned.
     * Be aware that <em>no</em> sorting should be assumed for the returned 
     * array.
     * 
     * <p>Example: The expression <code>conn.getNodeInfos(Node.class)</code>
     * returns the node information of all existing nodes, because {@link Node}   
     * is the base class of all nodes.</p>
     * 
     * @param nodeClass  the node class to search for, or <code>null</code>
     * @return  the node information of all found nodes
     */
    NodeInfo[] getNodeInfos(Class nodeClass) throws DocmaException;

    //    
    // ***************  Style methods  *****************
    //

    /**
     * Returns the identifiers of all styles.
     * The result includes all base-styles and all variant-styles.
     * 
     * @return  all existing style identifiers of the store
     * @see #getStyles()
     * @see #getStyles(String)
     */
    String[] getStyleIds() throws DocmaException;

    /**
     * Returns all variant names used by the existing styles.
     * 
     * @return  the existing variant names
     */
    String[] getStyleVariantNames() throws DocmaException;

    /**
     * Returns all existing styles. 
     * This is a convenience method. An invocation of the form 
     * <code>conn.getStyles()</code> returns the same result as 
     * <code>conn.getStylesById(conn.getStyleIds())</code>.
     * 
     * @return  all base- and variant-styles
     * @see #getStyles(String)
     * @see #getStyleIds()
     */
    Style[] getStyles() throws DocmaException;

    /**
     * Returns the styles for the given style identifiers.
     * The ordering of the returned array corresponds to the order 
     * of the supplied identifiers. In other words, if <code>res</code> 
     * is the returned array, then <code>res[i].getId()</code> is equal to 
     * the argument <code>id_arr[i]</code> (the argument at position 
     * <code>i</code>).
     * 
     * @param id_values  the style identifiers
     * @return  the styles identified by the supplied id values
     * @see #getStyleById(String) 
     */
    Style[] getStylesById(String... id_values) throws DocmaException;
    
    /**
     * Returns all styles for the given variant, or the base-style if no
     * such variant exists.
     *
     * <p><u>Definition</u>:<br>
     * A style that has a variant name assigned is called <i>variant-style</i>.
     * Otherwise the style is called <i>base-style</i>.
     * </p>
     *
     * <p>This method returns all variant-styles where the variant name is equal
     * to the <code>variantName</code> argument. If a base-style exists, which   
     * has no such variant, then the base-style is returned instead.</p>
     * 
     * <p>If the <code>variantName</code> argument is <code>null</code> or an
     * empty string, then just all base-styles are returned.
     * Note that this is also true, in case the <code>variantName</code>  
     * argument is a non-existing name.</p>
     * 
     * @param variantName  the variant of the styles to be returned
     * @return  the styles for the given variant name
     */
    Style[] getStyles(String variantName) throws DocmaException;
    
    /**
     * Returns the style for the given style identfier.
     * 
     * @param styleId  the id of the style to search for
     * @return  the style with the given id, or <code>null</code>
     * @see #getStylesById(String...) 
     */
    Style getStyleById(String styleId) throws DocmaException;

    /**
     * Returns the style identified by the given base-identifier and 
     * variant name.
     *
     * <p><u>Definition</u>:<br> 
     * A style that has a variant name assigned is called <i>variant-style</i>.
     * Otherwise the style is called <i>base-style</i>.
     * </p>
     *
     * <p>If the <code>variantName</code> argument is not <code>null</code>, 
     * then this method searches for a variant-style with the base-identifier 
     * <code>baseId</code> and the variant name <code>variantName</code>.
     * If no such a style exists, then it searches for a base-style with  
     * base-identifier <code>baseId</code>. If such a style exists, it
     * is returned. Otherwise <code>null</code> is returned.</p>
     *
     * <p>If the <code>variantName</code> argument is <code>null</code>
     * or an empty string, then the base-style with identifier 
     * <code>baseId</code> is returned. If no such style exists, then  
     * <code>null</code> is returned.</p>
     * 
     * @param baseId  the base-identifier of the style to search for
     * @param variantName  the variant name of the style to search for
     * @return  the style that matches the given arguments
     */
    Style getStyleVariant(String baseId, String variantName) throws DocmaException;
    
    /**
     * Creates a style instance with the given identifier 
     * and CSS properties.
     * Note that the returned instance is transient as long as the style is not  
     * saved by passing it as argument to the {@link #saveStyle(Style)} method.
     * 
     * @param styleId  the identifier of the style to be created
     * @param blockStyle  indication whether style shall be a block- or inline-style
     * @param styleTitle  a descriptive style name
     * @param css  the CSS properties of the new style
     * @return  a new style instance
     */
    Style createStyle(String styleId, boolean blockStyle, String styleTitle, String css) throws DocmaException;

    /**
     * Creates a style instance with the given identifier 
     * which is a copy of an existing style.
     * The returned style instance has the same formatting properties as the
     * style provided in the <code>template</code> argument.
     * Note that the returned instance is transient as long as the style is not  
     * saved by passing it as argument to the {@link #saveStyle(Style)} method.
     * 
     * @param styleId  the identifier of the style to be created
     * @param blockStyle  indication whether style shall be a block- or inline-style
     * @param styleTitle  a descriptive style name
     * @param template  another style to be used as template
     * @return  a new style instance
     */
    Style createStyle(String styleId, boolean blockStyle, String styleTitle, Style template) throws DocmaException;
    
    /**
     * Saves the given style instance.
     * If the <code>style</code> argument is a transient instance, then 
     * this method persists the instance. If the <code>style</code> argument
     * is an already persisted style, then all changes to the style instance 
     * since the last <code>saveStyle</code> operation are persisted.
     * 
     * @param style  the style to be saved
     * @see #deleteStyle(String) 
     */
    void saveStyle(Style style) throws DocmaException;
    
    /**
     * Deletes the style with the given identifier.
     * The <code>styleId</code> argument should identify a persisted 
     * style. Otherwise this method has no effect.
     * 
     * @param styleId  the identifier of the style to be deleted
     * @see #saveStyle(Style) 
     */
    void deleteStyle(String styleId) throws DocmaException;
    
    /**
     * Returns the CSS of <em>all</em> styles. The result includes all   
     * base-styles and all variant-styles. Each style is returned as a  
     * CSS class definition, where the style identifier is used as CSS class 
     * name.
     * 
     * @return  a string of CSS class definitions
     */
    String getCSS() throws DocmaException;
    
    /**
     * Returns the CSS of all styles for the given variant name. 
     * The result includes the same styles as returned by the 
     * {@link getStyles(String)} method.
     * Each style is returned as a CSS class definition, 
     * where the base-identifier of each style is used as CSS class name.
     * 
     * @param variant  the variant of the styles to be retrieved
     * @return  a string of CSS class definitions
     */
    String getCSS(String variant) throws DocmaException;

    //
    // ***************  Applicability methods  *****************
    //

    /**
     * Returns the list of declared applicability names. 
     * 
     * @return  the declared applicability names
     */
    String[] getDeclaredApplics() throws DocmaException;

    /**
     * Sets the list declared applicability names.
     * 
     * @param applics  the applicability names to be set
     */
    void setDeclaredApplics(String... applics) throws DocmaException;

    /**
     * Adds the given names to the list of declared applicability names.
     * 
     * @param applics  the applicability names to be added
     */
    void addDeclaredApplics(String... applics) throws DocmaException;

    /**
     * Removes the given names from the list of declared applicability names.
     * 
     * @param applics  the applicability names to be removed
     */
    void removeDeclaredApplics(String... applics) throws DocmaException;

    //
    // ***************  Output configuration methods  *****************
    //

    /**
     * Returns all output configuration identifiers.
     * 
     * @return  the output configuration identifiers
     */
    String[] getOutputConfigIds() throws DocmaException;
    
    /**
     * Returns the output configuration for the given identifier.
     * If a configuration with the identifier <code>outConfigId</code> does
     * not exist, then <code>null</code> is returned.
     * 
     * @param outConfigId  the output configuration identifier
     * @return  the output configuration, or <code>null</code>
     */
    OutputConfig getOutputConfig(String outConfigId) throws DocmaException;
    
    /**
     * Creates a transient output configuration instance.
     * Note that the returned instance is transient as long as the style is not  
     * saved by passing it as argument to the 
     * {@link #saveOutputConfig(OutputConfig) } method.
     * 
     * <p>
     * Following table list the supported values for the <code>format</code>
     * and <code>subFormat</code> parameters:
     * </p>
     * <table>
     *   <tr>
     *     <th>format</th>
     *     <th>subFormat</th>
     *     <th>Description</th>
     *   </tr>
     *   <tr>
     *     <td rowspan="4"><code>"html"</code></td>
     *     <td><code>""</code> or <code>null</code></td>
     *     <td>HTML (static)</td>
     *   </tr>
     *   <tr>
     *     <td><code>"webhelp1"</code></td>
     *     <td>WebHelp version 1</td>
     *   </tr>
     *   <tr>
     *     <td><code>"webhelp2"</code></td>
     *     <td>WebHelp version 2</td>
     *   </tr>
     *   <tr>
     *     <td><code>"epub"</code></td>
     *     <td>eBook (EPUB)</td>
     *   </tr>
     *   <tr>
     *     <td><code>"pdf"</code></td>
     *     <td><code>""</code> or <code>null</code></td>
     *     <td>PDF</td>
     *   </tr>
     *   <tr>
     *     <td><code>"docbook"</code></td>
     *     <td><code>""</code> or <code>null</code></td>
     *     <td>DocBook</td>
     *   </tr>
     * </table>
     * 
     * @param outConfigId  the identifier of the new output configuration
     * @param format  the output format 
     * @param subFormat  the sub-format, or <code>null</code>
     * @return  a new output configuration instance
     */
    OutputConfig createOutputConfig(String outConfigId, String format, String subFormat) throws DocmaException;
    
    /**
     * Saves the given output configuration.
     * If the <code>outConf</code> argument is a transient instance, then 
     * this method persists the instance. If the <code>outConf</code> argument
     * is a persisted configuration, then all changes to the instance since 
     * the last <code>saveOutputConfig</code> operation are persisted.
     * 
     * @param outConf  the output configuration to be saved
     */
    void saveOutputConfig(OutputConfig outConf) throws DocmaException;
    
    /**
     * Deletes the output configuration with the given identifier.
     * The <code>outConfigId</code> argument should identify a persisted 
     * configuration. Otherwise this method has no effect.
     * 
     * @param outConfigId  the id of the output configuration to be deleted
     */
    void deleteOutputConfig(String outConfigId) throws DocmaException;

    //
    // ***************  Publication configuration methods  *****************
    //

    /**
     * Returns all publication configuration identifiers.
     * 
     * @return  the publication configuration identifiers
     */
    String[] getPublicationConfigIds() throws DocmaException;
    
    /**
     * Returns the publication configuration for the given identifier.
     * If a configuration with the identifier <code>pubConfigId</code> does
     * not exist, then <code>null</code> is returned.
     * 
     * @param pubConfigId  the publication configuration identifier
     * @return  the publication configuration, or <code>null</code>
     */
    PublicationConfig getPublicationConfig(String pubConfigId) throws DocmaException;
    
    /**
     * Creates a transient publication configuration instance.
     * Note that the returned instance is transient as long as the style is not  
     * saved by passing it as argument to the 
     * {@link #savePublicationConfig(PublicationConfig) } method.
     * 
     * @param pubConfigId  the identifier of the new publication configuration
     * @return  a new publication configuration instance
     */
    PublicationConfig createPublicationConfig(String pubConfigId) throws DocmaException;
    
    /**
     * Saves the given publication configuration.
     * If the <code>pubConf</code> argument is a transient instance, then 
     * this method persists the instance. If the <code>pubConf</code> argument
     * is a persisted configuration, then all changes to the instance since 
     * the last <code>savePublicationConfig</code> operation are persisted.
     * 
     * @param pubConf  the publication configuration to be saved
     */
    void savePublicationConfig(PublicationConfig pubConf) throws DocmaException;
    
    /**
     * Deletes the publication configuration with the given identifier.
     * The <code>pubConfigId</code> argument should identify a persisted 
     * configuration. Otherwise this method has no effect.
     * 
     * @param pubConfigId  the id of the publication configuration to be deleted
     */
    void deletePublicationConfig(String pubConfigId) throws DocmaException;

    //
    // ***************  Publication export methods  *****************
    //

    /**
     * Lists all archived publications for the given language and release state.
     * If the <code>langCode</code> argument is <code>null</code>, then 
     * the exported publications for all languages are returned
     * (for the original language and for all translation languages).
     * If the <code>versionState</code> argument is <code>null</code>, then 
     * the exported publications for all version states are returned 
     * (draft and released publications).
     * 
     * @param langCode  the language code, or <code>null</code>
     * @param versionState  the release state, or <code>null</code>
     * @return  all exported publications for the given language and release state
     */
    Publication[] listPublications(String langCode, VersionState versionState) throws DocmaException;
    
    /**
     * Deletes the archived publication with the given identifier.
     * 
     * @param publicationId  the identifier of the publication to be deleted
     */
    void deletePublication(String publicationId) throws DocmaException;

    /**
     * Returns the identifiers of all archived publications.
     * 
     * @return  the identifiers of the archived publications
     */
    String[] getPublicationIds() throws DocmaException;
            
    /**
     * Returns the archived publication with the given identifier.
     * If a publication with the identifier <code>publicationId</code> does
     * not exist, then <code>null</code> is returned.
     * 
     * @param publicationId  the publication identifier
     * @return  the publication identified by the parameter 
     *          <code>publicationId</code>, or <code>null</code>
     */
    Publication getPublication(String publicationId) throws DocmaException;
    
    /**
     * Exports a new publication and adds it to the publication archive.
     * This method blocks until the publication has been exported and archived.
     * The archived publication can be accessed through the returned 
     * {@link Publication} instance.
     * 
     * @param pubConfigId  the publication configuration to be used
     * @param outConfigId  the output configuration to be used
     * @param langCode  the language code of the content language (original or translation language)
     * @param filename  the filename to be assigned to the exported publication
     * @return  the exported publication instance
     */
    Publication exportPublication(String pubConfigId, String outConfigId, String langCode, String filename) throws DocmaException;
    
    /**
     * Exports a new publication asynchronously and adds it to the publication archive.
     * This method returns immediatelly after export has been started.
     * For the export and archiving of the publication, a new thread is created.
     * The publication can be accessed through the returned {@link Publication}
     * instance. Details about the export status can be retrieved through the 
     * {@link #getExportJob(String)} method.
     * 
     * @param pubConfigId  the publication configuration to be used
     * @param outConfigId  the output configuration to be used
     * @param langCode  the language code of the content language (original or translation language)
     * @param filename  the filename to be assigned to the exported publication
     * @return  the exported publication instance
     */
    Publication exportPublicationAsync(String pubConfigId, String outConfigId, String langCode, String filename) throws DocmaException;
    
    /**
     * Returns the export status for the given publication identifier.
     * 
     * @param publicationId  the publication identifier
     * @return  the export job instance
     */
    ExportJob getExportJob(String publicationId) throws DocmaException;
    
    /**
     * Returns the job position in the export queue for the 
     * given publication identifier.
     * A position of 0 means, that the export is currently running.
     * 
     * @param publicationId  the publication identifier
     * @return  the export job position
     */
    int getExportJobPosition(String publicationId) throws DocmaException;
    
    /**
     * Creates a PDF for the given publication- and output-configuration.
     * Note that this method just creates a preview. That means, the 
     * exported publication is <em>not</em> added to the publication archive.
     * 
     * @param outstream  the PDF output stream
     * @param node_id   the node-id of the publication's root node
     * @param pconf  the publication configuration to be used
     * @param oconf  the output configuration to be used
     * @return  the log entries
     */
    LogEntries previewPDF(OutputStream outstream, String node_id, PublicationConfig pconf, OutputConfig oconf) throws DocmaException;
    
    //
    // ***************  Character entity methods  *****************
    //

    /**
     * Returns the store's character entity definitions.
     * 
     * <p>Note that currently this method gives the same result as the 
     * {@link ApplicationContext#getCharEntities()} method.
     * However, future API implementations might allow store-specific 
     * character entity definitions, which differ from the application's 
     * default settings.</p>
     * 
     * @return  the character entity definitions of the store
     * @see ApplicationContext#getCharEntities() 
     */
    CharEntity[] getCharEntities() throws DocmaException;

    /**
     * Replaces all numeric and symbolic character entities
     * in the given string. The character entities are replaced 
     * by the character as defined by the character entity 
     * definitions returned by the 
     * {@link #getCharEntities()} method.
     * 
     * @param str  the string to be decoded
     * @return  the decoded string
     */
    String decodeCharEntities(String str) throws DocmaException;

    /**
     * Replaces special characters by the corresponding character entities.
     * 
     * @param text  the text to be encoded
     * @param symbolic  whether to create symbolic (<code>true</code>) or 
     *                  numeric (<code>false</code>) character entities
     * @return   the encoded text
     * @see #getCharEntities()
     */
    String toCharEntities(String text, boolean symbolic) throws DocmaException;

    /**
     * Replaces special characters by the corresponding character entities.
     * If the <code>keepEntities</code> argument is <code>false</code> and
     * the <code>text</code> argument already contains character entities,
     * then the <code>&amp;</code> character of the character entities is 
     * encoded as <code>&amp;amp;</code>. 
     * On the other hand, if the <code>keepEntities</code> argument is 
     * <code>true</code>, then the <code>&amp;</code> character of character
     * entities in the <code>text</code> argument is <em>not</em> encoded. 
     * 
     * @param text  the text to be encoded
     * @param symbolic  whether to create symbolic (<code>true</code>) or 
     *                  numeric (<code>false</code>) character entities
     * @param keepEntities  whether existing character entities in the 
     *                      <code>text</code> argument are kept
     * @return  the encoded text
     * @see #getCharEntities()
     */
    String toCharEntities(String text, boolean symbolic, boolean keepEntities) throws DocmaException;
    
    /**
     * Encodes the given character into a character entity.
     * If no character entity is defined for the given character,
     * then the character is returned unchanged (as a string of length 1).
     * 
     * @param ch   the character to be encoded
     * @param symbolic  whether to create a symbolic (<code>true</code>) or 
     *                  numeric (<code>false</code>) character entity
     * @return  the character <code>ch</code> encoded as character entity
     * @see #getCharEntities()
     */
    String toCharEntity(char ch, boolean symbolic) throws DocmaException;

    //
    // ***************  Other methods  *****************
    //

    /**
     * Tests whether the current user is allowed to modify this store.
     * If modifying the store is allowed, this method does nothing. 
     * Otherwise an exception is thrown.
     * 
     * @throws DocmaException if updating the store is not allowed, for example,
     *                        because the version is already released, or due to
     *                        missing access rights.
     */
    void checkUpdateVersionAllowed() throws DocmaException;

    /**
     * Copies the given source nodes to the given target position.
     * If <code>refChild</code> is <code>null</code>, then the copied nodes
     * are added as last children of <code>targetParent</code>.
     * Otherwise the copied nodes are inserted before <code>refChild</code>.
     * 
     * @param sourceNodes  the source nodes
     * @param targetParent  the target node, where the copy of the nodes shall be inserted as child nodes
     * @param refChild  the insert position, or <code>null</code>
     */
    void copyNodes(Node[] sourceNodes, Node targetParent, Node refChild) throws DocmaException;
    
    /**
     * Exports the given nodes into a file.
     * The data that is written to <code>exportFile</code> is a zip stream. 
     * The exported nodes can be imported into the same store or into another 
     * store, by extracting the zip file into a directory and passing it as 
     * input to the {@link #importNodes} method.
     * 
     * @param nodes  the nodes to be exported
     * @param exportFile  the output file path
     */
    void exportNodesToFile(Node[] nodes, File exportFile) throws DocmaException;
    
    /**
     * Imports the nodes from the given directory.
     * 
     * @param parentNode  the target node, where the imported nodes shall be inserted as child nodes
     * @param importDir  the input directory
     * @param translations  the translation languages to be imported; 
     *                      <code>null</code> means all translations
     * @see #exportNodesToFile(Node[], java.io.File) 
     * @see UserSession#listImportTranslations(java.io.File) 
     */
    void importNodes(Node parentNode, File importDir, Language[] translations) throws DocmaException;
    
    /**
     * Indicates whether <code>name</code> is a syntactically valid alias.
     * Note that this method only checks the syntactical rules.
     * It does <em>not</em> check whether <code>name</code> is already used
     * by any other node.
     * 
     * @param name  the name to check
     * @return  <code>true</code> if <code>name</code> is a valid alias;
     *          <code>false</code> otherwise
     */
    boolean isValidAlias(String name);

    /**
     * Returns the GenText file node.
     * This method returns the node with alias <code>"gentext"</code>.
     * If no such node exists, then it searches in the folder with alias
     * <code>"system_root"</code> for a file named 
     * <code>"gentext.properties"</code>. In translation-mode, a file
     * named <code>"gentext[<i>XX</i>].properties"</code> is searched instead,  
     * where <code><i>XX</i></code> is the current translation language code. 
     * If such a node exists, then this node is returned. Otherwise 
     * <code>null</code> is returned.
     * 
     * @return  the GenText file node
     * @see #getGenTextProperties() 
     */
    FileContent getGenTextFile() throws DocmaException;

    /**
     * Returns the GenText properties.
     * An invocation of the form 
     * <pre><code>
     *    Properties props = conn.getGenTextProperties()
     * </code></pre>
     * gives basically the same result as the code:
     * <pre><code>
     *    FileContent gentext = conn.getGenTextFile();
     *    Properties props = new Properties();
     *    InputStream in = gentext.getContentStream();
     *    props.load(in);
     *    in.close();
     * </code></pre>
     * However, an invocation of the <code>getGenTextProperties()</code> method 
     * may be more efficient, because the GenText properties are normally 
     * cached by the connection.
     * 
     * @return  the GenText properties
     * @see #getGenTextFile()
     */
    Properties getGenTextProperties() throws DocmaException;
            
    String[] listImageRenditionNames() throws DocmaException;
    
    ImageRenditionInfo getImageRenditionInfo(String renditionName) throws DocmaException;
    
    LogEntries prepareHTMLForSave(StringBuilder content, String nodeId, Map<Object, Object> props) throws DocmaException;
    
    LogEntries consistencyCheck(String nodeId, boolean recursive, boolean autoCorrect, Map<Object, Object> props) throws DocmaException;
}
