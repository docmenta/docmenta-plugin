/*
 * Node.java
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

import java.util.Date;

/**
 *
 * @author MP
 */
public interface Node 
{
    //************************************************************
    //**************    Attribute methods       ******************  
    //************************************************************

    /**
     * Returns the node-identifier (node-id), which identifies the node within
     * a store.
     * For newly created nodes, the node-id is automatically generated. 
     * It does not change during the whole lifetime of the node.
     * 
     * @return the node-id
     * @throws DocmaException  if the node-id cannot be retrieved, for example
     *                         due to a connection error
     */
    String getId() throws DocmaException;

    /**
     * Returns the value of an attribute.
     * If an attribute with the given name does not exist, then an empty 
     * string is returned.
     *
     * <p><em>Translation-mode:</em><br>
     * If the session is in translation-mode, then this method returns the 
     * translated value of the attribute.
     * If no translation of the attribute exists,
     * then the value for the original language is returned (namely the value 
     * that has been assigned in original-mode).</p>
     * 
     * @param name  the name of the attribute
     * @return      the value of the attribute, or an empty string
     * @throws DocmaException  if the attribute cannot be retrieved, for example
     *                         due to a connection error
     * @see #getAttribute(String, String)
     * @see #getAttributeNames()
     * @see #setAttribute(String, String)
     * @see StoreConnection#enterTranslationMode(String)
     */
    String getAttribute(String name) throws DocmaException;

    /**
     * Returns the value of an attribute for the given language.
     * If the <code>lang_code</code> argument is <code>null</code>,
     * then the value for the original language is returned. In case
     * an attribute with the given name does not exist, an empty 
     * string is returned.
     *
     * <p><em>Retrieving translated attribute values:</em><br>
     * If the <code>lang_code</code> argument is <em>not</em> <code>null</code>,
     * then this method returns the attribute value for the translation language
     * identified by <code>lang_code</code>. If no translated value for the 
     * language <code>lang_code</code> exists, then <code>null</code> is 
     * returned.
     * <p>Note that a non-<code>null</code> value does not necessarily mean, 
     * that the attribute is semantically translated. This method just returns 
     * the value that has been stored for the given translation language.
     * For example, it is possible to read the attribute value for the original
     * language, then switch to translation mode and set this value  
     * as the translated value. In this case the translated value is  
     * identical to the original value.</p>
     * 
     * @param name  the name of the attribute
     * @param lang_code  the language code of a translation language, 
     *                   or <code>null</code>
     * @return      the value of the attribute, or an empty string
     * @throws DocmaException  if the attribute cannot be retrieved, for example
     *                         due to a connection error
     * @see #getAttribute(String)
     * @see #getAttributeNames()
     * @see #setAttribute(String, String)
     */
    String getAttribute(String name, String lang_code) throws DocmaException;
    
    /**
     * Returns the value of an attribute, with special characters encoded as 
     * character-entities.
     * Only characters that are defined in the application's 
     * character-entity configuration are encoded. 
     * Any existing character-entities in the attribute value are kept.
     * If an attribute with the given name does not exist, then an empty 
     * string is returned.
     * 
     * <p>This is a convenience method. An invocation of the form
     * <code>node.getAttributeEntityEncoded()</code> returns the same value as
     * the invocation</p> 
     * <p><code>
     * node.getStoreConnection().encodeCharEntities(node.getAttribute(<i>name</i>), false);
     * </code></p>
     * 
     * @param name  the name of the attribute
     * @return      the character-entity encoded value of the attribute, or an 
     *              empty string
     * @throws DocmaException  if the attribute cannot be retrieved, for example
     *                         due to a connection error
     * @see #getAttribute(String)
     * @see StoreConnection#encodeCharEntities(String, boolean);
     */  
    String getAttributeEntityEncoded(String name) throws DocmaException;

    /**
     * Returns the names of all attributes assigned to this node.
     * 
     * @return  all attribute names
     * @see #getAttribute(String)
     * @see #getAttribute(String, String)
     */
    String[] getAttributeNames() throws DocmaException;
    
    /**
     * Sets the value of an attribute.
     * 
     * <p><em>Original-mode:</em><br>
     * If the session is <em>not</em> in translation-mode (in other words, in 
     * original-mode), then this method sets the attribute value for
     * the original language.
     * If <code>value</code> is <code>null</code>, then the attribute is  
     * completely removed (including all translations).</p>
     *
     * <p><em>Translation-mode:</em><br>
     * If the session is in translation-mode, then this method 
     * sets the translated value for the current translation language.
     * If <code>value</code> is <code>null</code> and the session is in 
     * translation-mode, then only the translated value for the current 
     * translation language is removed.</p>
     *
     * <p><em>Avoiding plug-in conflicts:</em><br>
     * To avoid naming conflicts,
     * it is highly recommended that plug-in specific attribute names 
     * start with the plug-in identifier followed by a dot. For example, 
     * instead of using the attribute name <code>"abc"</code>, a plug-in 
     * with the id <code>my_plugin</code> should use the attribute name 
     * <code>"my_plugin.abc"</code>.</p>
     *
     * <p><em>Pre-defined attributes:</em><br>
     * Following attribute names are used by the Docmenta application and have 
     * a pre-defined meaning:
     * <p>
     * <table>
     * <tr>
     *   <th>Attribute Name</th>
     *   <th>Description</th>
     * </tr>
     * <tr>
     *   <td><code>applic</code></td>
     *   <td>The node's applicability. An invocation of the form
     *       <code>setAttribute("applic", value)</code> is identical to the 
     *       invocation <code>setApplicability(value)</code>.</td>
     * </tr>
     * <tr>
     *   <td><code>comment</code></td>
     *   <td>A comment assigned to the node. An invocation of the form
     *       <code>setAttribute("comment", value)</code> is identical to the 
     *       invocation <code>setComment(value)</code>.</td>
     * </tr>
     * <tr>
     *   <td><code>lastmod_by</code></td>
     *   <td>The id of the user who has most recently modified the node.</td>
     * </tr>
     * <tr>
     *   <td><code>lastmod_date</code></td>
     *   <td>The node's last modification date. The <code>value</code>
     *       argument has to be a decimal string denoting the number of 
     *       milliseconds since midnight, January 1st, 1970 UTC.</td>
     * </tr>
     * <tr>
     *   <td><code>priority</code></td>
     *   <td>A priority value assigned to the node. An invocation of the form
     *       <code>setAttribute("priority", value)</code> is identical to the 
     *       invocation <code>setPriority(value)</code>.</td>
     * </tr>
     * <tr>
     *   <td><code>progress</code></td>
     *   <td>The progress in percent. An invocation of the form
     *       <code>setAttribute("progress", value)</code> is identical to the 
     *       invocation <code>setProgress(Integer.parseInt(value), false)</code>.
     *   </td>
     * </tr>
     * <tr>
     *   <td><code>wfstate</code></td>
     *   <td>The workflow status. An invocation of the form
     *       <code>setAttribute("wfstate", value)</code> is identical to the 
     *       invocation <code>setWorkflowStatus(value, false)</code>.</td>
     * </tr>
     * </table>
     * </p>
     * <p><em>Reserved attribute names:</em><br>
     * Following names are reserved for internal use and are 
     * forbidden to be passed in the <code>name</code> argument:
     * <p>
     * <code>charset</code>,
     * <code>contenttype</code>,
     * <code>fileext</code>,
     * <code>grouptype</code>,
     * <code>reftype</code>,
     * <code>title</code>,
     * </p>
     * <p>Additionally, attribute names that start with <code>"app."</code>,  
     * <code>"ext."</code> or <code>"system."</code> are reserved.
     * Invoking this method with a reserved attribute name might cause an 
     * exception.</p>
     *
     * @param name   the name of the attribute
     * @param value  the value of the attribute, or <code>null</code>
     * @throws DocmaException  If setting the attribute value is not possible
     *                         (for example, due to access rights or a
     *                         connection error)
     * @see #getAttribute(String)
     * @see #getAttribute(String, String)
     * @see StoreConnection#enterTranslationMode(String)
     */
    void setAttribute(String name, String value) throws DocmaException;

    /**
     * Returns the node's alias.
     * Returns <code>null</code> if the node does not have an alias.
     *
     * <p>Note: The alias cannot be translated. In other words,
     * the returned alias is always the same, no matter whether the session is  
     * in original- or translation-mode.</p>
     *
     * @return  the assigned alias or <code>null</code>
     * @throws DocmaException  if the alias cannot be retrieved, for example
     *                         due to a connection error
     * @see #getLinkAlias()
     * @see #setAlias(String)
     */
    String getAlias() throws DocmaException;

    /**
     * Returns the alias without the variant-part.
     * If an alias contains an exclamation mark, then the sub-string in front of 
     * the exclamation mark is called the "link-name", and the sub-string 
     * after the exclamation mark is called the "variant":
     * 
     * <p><code><i>link-name</i>!<i>variant</i></code></p>
     *
     * <p>This method just returns the link-name of the alias. If the alias  
     * does not contain an exclamation mark, then this method 
     * returns the same value as {@link #getAlias()}.</p>
     *
     * @return  the alias without variant-part, or <code>null</code>
     * @throws DocmaException  if the alias cannot be retrieved, for example
     *                         due to a connection error
     * @see #getAlias()
     * @see PluginUtil#getLinkAlias(String)
     */
    String getLinkName() throws DocmaException;

    /**
     * Sets an alias for this node. 
     * An alias has to be a name that uniquely identifies the node within the 
     * store.
     * A node can have at most one alias. That means, an invocation of this  
     * method replaces any previously assigned alias. If the <code>name</code>
     * argument is <code>null</code> or an empty string, then any previously
     * assigned alias is removed.
     *
     * <p>The alias has to start with a letter or underscore. The remaining
     * characters can be letters, digits, underscore (_), dash (-) or 
     * exclamation mark (!). The maximum length is 40 characters.
     * If the <code>name</code> argument does not comply to these rules,
     * then an exception is thrown. Therefore, before calling this method, the  
     * validity of the supplied argument should be checked by calling the 
     * {@link StoreConnection#isValidAlias(String) } method.</p>
     *
     * <p>Note: An alias cannot be translated. In other words,
     * it makes no difference whether this method is called in original-   
     * or translation-mode.</p>
     * 
     * @param name  the alias for this node
     * @throws DocmaException    if setting the alias fails; possible reasons
     *                           could be: connection error, access rights,
     *                           <code>name</code> is invalid or is already 
     *                           used as alias for another node
     * @see #getAlias()
     */
    void setAlias(String name) throws DocmaException;

    /**
     * Returns the last modification date of the node.
     * The meaning of the last modification date depends on the type of node.
     * If this node is an instance of {@link Content}, then the  
     * returned date gives the time when the content has been updated last time,
     * for example, through one of the <code>setContent...</code> methods.
     * If the node is an instance of {@link PubSection}, then the returned date 
     * gives the time when the section title has been updated last time.
     * If the node is an instance of {@link Folder}, then the returned date 
     * gives the time when the folder name has been updated last time.
     * If the node is an instance of {@link Reference}, then the last 
     * modification date is updated in case a new title or target is 
     * assigned.
     *
     * <p>If the node has <em>not</em> been updated since the creation of the  
     * node, then <code>null</code> may be returned.</p>
     *
     * <p><em>Translation-mode:</em><br>
     * If the session is in translation-mode, then this method returns the 
     * last modification date of the translation.
     * If no translation exists, then the same value as in original-mode 
     * is returned.</p>
     * 
     * <p>Implementation specific: The exact behaviour of this method may 
     * depend on the persistence layer and operating system and should  
     * <em>not</em> be relied on.</p>
     *
     * @return  the last modification date, or <code>null</code>
     * @throws DocmaException  if the date cannot be retrieved, for example
     *                         due to a connection error
     */
    Date getLastModifiedDate() throws DocmaException;

    /**
     * Returns the id of the user who most recently modified the node.
     * If this node is an instance of {@link Content}, then the returned 
     * id identifies the user who has updated the content last time,
     * for example through one of the <code>setContent...</code> methods.
     * If the node is an instance of {@link PubSection}, then the returned id 
     * identifies the user who has most recently updated the section title.
     * If the node is an instance of {@link Folder}, then the returned id
     * identifies the user who has most recently updated the folder name.
     * If the node is an instance of {@link Reference}, then the returned id 
     * identifies the user who has most recently updated the title or target
     * of the reference. 
     *
     * <p>If the node has <em>not</em> been updated since the creation of the  
     * node, then <code>null</code> may be returned.</p>
     *
     * <p><em>Translation-mode:</em><br>
     * If the session is in translation-mode, then this method returns the 
     * id of the user who has most recently created or updated the translation 
     * of the node.
     * If no translation exists, then the same value as in original-mode 
     * is returned.</p>
     * 
     * <p>Implementation specific: The exact behaviour of this method may 
     * depend on the persistence layer and should <em>not</em> be relied on.
     * </p>
     *
     * @return  user-id, or <code>null</code>
     * @throws DocmaException  if the user-id cannot be retrieved, for example
     *                         due to a connection error
     */
    String getLastModifiedBy() throws DocmaException;

    /**
     * Returns the current workflow status of the node.
     * If no workflow status has been assigned yet, then <code>null</code>
     * is returned.
     *
     * <p>For each translation language a separate workflow status 
     * can exist (see {@link #setWorkflowStatus(String)}).</p>
     *
     * @return  the workflow status, or <code>null</code>
     * @throws DocmaException  if the status cannot be retrieved, for example
     *                         due to a connection error
     */
    String getWorkflowStatus() throws DocmaException;

    /**
     * Sets the workflow status of this node and updates the
     * workflow states of the parent nodes (recursively).
     * This is a convenience method. An invocation of this method is identical 
     * to the invocation 
     * {@link #setWorkflowStatus(String, boolean) setWorkflowStatus}<code>(status, true)</code>.
     *
     * @param status  the workflow status, or <code>null</code>
     * @throws DocmaException  If setting the workflow status is not possible 
     *                         (for example, due to access rights or a 
     *                         connection error)
     * @see #setWorkflowStatus(String, boolean)
     */
    void setWorkflowStatus(String status) throws DocmaException;

    /**
     * Sets the workflow status of this node, and optionally 
     * of the parent nodes (recursively).
     * The parameter <code>status</code> can be any of the following 
     * constants:
     *
     * <table>
     * <tr>
     *  <th>Status constant</th>
     *  <th>Status description</th>
     * </tr>
     * <tr>
     *   <td><code>"wip"</code></td>
     *   <td>Work in progress</td>
     * </tr>
     * <tr>
     *   <td><code>"rfa"</code></td>
     *   <td>Content is ready for approval</td>
     * </tr>
     * <tr>
     *   <td><code>"approved"</code></td>
     *   <td>Content is approved</td>
     * </tr>
     * </table>
     *
     * <p><em>Original-mode:</em><br>
     * If an empty string is passed as argument, then the workflow status
     * for the original language is removed.
     * If <code>null</code> is passed as argument, then the workflow status 
     * for the original language and all translations is removed (if existent).
     * </p>
     *
     * The parameter <code>updateParent</code> defines, whether the workflow 
     * status of the parent node is updated (recursively). If this  
     * parameter is set to <code>true</code>, then the workflow status of the 
     * parent node is set to the lowest status of all child nodes, where 
     * the order of the states is defined as:
     *
     * <p>{@code "wip" < "rfa" < "approved"} </p>.
     *
     * <p>For example, if the current workflow status of a node <i>p</i> is  
     * <code>"approved"</code>, and the workflow status of a child node is set 
     * to <code>"wip"</code> with parameter <code>updateParent</code> set 
     * to <code>true</code>, then the workflow status of <i>p</i> is updated 
     * to <code>"wip"</code> as well. This update is done recursively, 
     * that means the status of the parent's parent (and so on) 
     * is updated as well.</p>
     * 
     * <p><em>Translation-mode:</em><br>
     * Each translation language can have its own workflow status. 
     * An invocation of this method in translation-mode sets the workflow 
     * status for the current translation.
     * If <code>null</code> is supplied in translation-mode, then the workflow 
     * status for the current translation language is removed (if existent).
     * </p>
     * 
     * @param status  the workflow status, or <code>null</code>
     * @param updateParent  <code>true</code> if parent nodes shall be updated 
     *                      recursively, otherwise <code>false</code> 
     * @throws DocmaException  If setting the workflow status is not possible 
     *                         (for example, due to access rights or a 
     *                         connection error)
     */
    void setWorkflowStatus(String status, boolean updateParent) throws DocmaException;

    /**
     * Returns the node's applicability expression.
     * If no applicability expression is assigned, then an empty string is
     * returned.
     * 
     * <p><em>Translation-mode:</em><br>
     * If the session is in translation-mode, then this method returns the 
     * node's applicability for the current translation language.
     * If no applicability for the current translation language exists,
     * then the applicability for the original language is returned (that is  
     * the value that has been assigned in original-mode).</p>
     *
     * @return the applicability expression, or an empty string
     * @throws DocmaException  if the applicability cannot be retrieved, 
     *                         for example due to a connection error
     * @see #setApplicability(String)
     */
    String getApplicability() throws DocmaException;

    /**
     * Sets the applicability of the node.
     *
     * <p>This method does <em>not</em> check the syntax of the argument.
     * That means, the <code>expression</code> argument is assigned to this 
     * node, even if it is no valid applicability expression.
     * However, invalid applicability expressions will create errors during 
     * export of a publication.</p>
     *
     * <p><em>Original-mode:</em><br>
     * If the session is <em>not</em> in translation-mode (in other words, in 
     * original-mode), then this method sets the applicability for the
     * original language.
     * If <code>expression</code> is an empty string, then the applicability
     * for the original language is removed.
     * If <code>expression</code> is <code>null</code>, then the applicability  
     * is removed for the original language and all translation languages 
     * (if existent).</p>
     *
     * <p><em>Translation-mode:</em><br>
     * If the session is in translation-mode, then this method 
     * sets the applicability for the current translation language.
     * If <code>expression</code> is <code>null</code> and the session is in 
     * translation-mode, then the applicability for the current 
     * translation language is removed.</p>
     *
     * @param expression  the applicability expression
     * @throws DocmaException  If setting the applicability is not possible
     *                         (for example, due to access rights or a 
     *                         connection error)
     */
    void setApplicability(String expression) throws DocmaException;

    /**
     * Returns the node's progress value or -1 if no progress 
     * has been assigned. The progress value is a percent
     * value between 0 and 100. 
     * 
     * <p>A value of 100 indicates, that work on the content is 
     * completed, in other words: the content is ready to be published.
     * Note that the progress value is just a hint for the users. It has 
     * <em>no</em> impact on the publishing workflow.</p>
     * 
     * @return a value in the range from 0 to 100, or -1
     * @throws DocmaException  if the progress cannot be retrieved, 
     *                         for example due to a connection error
     */
    int getProgress() throws DocmaException;
    
    /**
     * Sets the progress value of this node, and updates the 
     * progress values of the parent nodes (recursively).
     * 
     * <p>This is a convenience method. An invocation of this method is 
     * identical to the invocation 
     * {@link #setProgress(int, boolean) setProgress}<code>(percent, true)</code>.
     * </p>
     * 
     * @param percent  a value in the range from 0 to 100, or -1
     * @throws DocmaException  If setting the progress value is not possible
     *                         (for example, due to access rights or a 
     *                         connection error)
     * @see #getProgress()
     * @see #setProgress(int, boolean)
     */
    void setProgress(int percent) throws DocmaException;

    /**
     * Sets the progress value of this node, and optionally updates the 
     * progress values of the parent nodes (recursively). 
     * The progress value has to be a percent value (a value 
     * between 0 and 100).
     *
     * <p>If a value of <code>-1</code> is passed, then any previously 
     * assigned progress value is removed from this node.</p>
     *
     * <p>If the argument <code>updateParent</code> is <code>true</code>, 
     * then the progress value of the parent node is updated to the average
     * percent value of all child nodes. This update is done recursively
     * (that is the parent's parent, and so on, is updated as well).</p> 
     *
     * <p>If the argument <code>updateParent</code> is <code>false</code>,
     * then just the progress value of this node is updated. The progress 
     * of the parent is unchanged.</p>
     *
     * @param percent  a value in the range from 0 to 100, or -1
     * @param updateParent  <code>true</code> if progress value of parent nodes 
     *                      shall be re-calculated, otherwise <code>false</code>
     * @throws DocmaException  If setting the progress value is not possible
     *                         (for example, due to access rights or a 
     *                         connection error)
     * @see #getProgress()
     */
    void setProgress(int percent, boolean updateParent) throws DocmaException;
    
    
    //************************************************************
    //**************    Translation methods     ******************  
    //************************************************************
    
    /**
     * Returns the current translation mode of the node's session.
     * If the session is in translation-mode, then this method returns 
     * the language code of the current translation language.
     * Otherwise (original-mode) this method returns <code>null</code>.
     * 
     * This is a short-cut method for the expression
     * <code>getStoreConnection().getTranslationMode()</code>.
     * 
     * @return the language code, or <code>null</code>
     * @throws DocmaException  if the translation mode cannot be retrieved, 
     *                         for example due to a connection error
     * @see #isTranslationMode()
     * @see StoreConnection#getTranslationMode(), 
     * @see StoreConnection#enterTranslationMode(String),
     * @see StoreConnection#leaveTranslationMode()
     */
    String getTranslationMode() throws DocmaException;

    /**
     * Returns whether the node's session is in translation-mode or not.
     * This method is a short-cut for the expression
     * <code>getTranslationMode() != null</code>.
     * 
     * @return <code>true</code> if session is in translation-mode, 
     *         otherwise <code>false</code>
     * @throws DocmaException  if the translation mode cannot be retrieved, 
     *                         for example due to a connection error
     * @see #getTranslationMode()
     */
    boolean isTranslationMode() throws DocmaException;

    /**
     * Indicates whether at least one translated value exists.
     * This is a convenience method. An invocation of the
     * form <code>node.isTranslated()</code> gives the same result as the
     * invocation <code>node.isTranslated(node.getTranslationMode())</code>,
     * namely:
     * 
     * <p><em>Original-mode:</em><br>
     * If the session is <em>not</em> in translation-mode, then this method 
     * returns <code>true</code>, if at least one translated value exists for   
     * <em>any</em> translation language. Otherwise <code>false</code> 
     * is returned.</p>
     * 
     * <p><em>Translation-mode:</em><br>
     * If the session is in translation-mode, then this method returns 
     * <code>true</code>, only if a translated value exists for the current 
     * translation language. 
     * If no translation exists for the current translation 
     * language, then this method returns <code>false</code>.</p>
     * 
     * @return  <code>true</code> if a translated value exists, 
     *          otherwise <code>false</code>
     * @throws DocmaException  if the return value cannot be retrieved, 
     *                         for example due to a connection error
     * @see #isTranslated(String)
     * @see #getTranslationMode()
     */
    boolean isTranslated() throws DocmaException;
    
    /**
     * Indicates whether at least one translated value exists for the given 
     * language.
     * A translated value is any value that has been set, while the session is 
     * in translation-mode, for a property that supports translation. 
     * For example, a translated value can be a translated attribute value. 
     * In case of a {@link Content} node this method also returns 
     * <code>true</code>, if a translation for the content exists.
     * 
     * <p>This method returns <code>true</code>, if a translated value  
     * exists for the translation language identified by the 
     * <code>lang_code</code> argument.
     * If no translation exists for the language identified by 
     * <code>lang_code</code>, then this method returns <code>false</code>.</p>
     *
     * <p>If the <code>lang_code</code> argument is <code>null</code>, then  
     * this method returns <code>true</code>, if at least one translated value  
     * exists for <em>any</em> translation language. Otherwise   
     * <code>false</code> is returned.</p>
     * 
     * <p>Note: The return value <code>true</code> does not necessarily mean,   
     * that the value is semantically translated. This method just returns,  
     * whether a value has been stored in translation-mode or not.
     * For example, it is possible to read the content of a node,
     * then switch to translation-mode and set the original content 
     * as the translated content. In this case, the method returns 
     * <code>true</code>, even though the translated content is identical to  
     * the original content.</p>
     * 
     * @param lang_code  the language code, or <code>null</code>
     * @return  <code>true</code> if a translated value exists, 
     *          otherwise <code>false</code>
     * @throws DocmaException  if the return value cannot be retrieved, 
     *                         for example due to a connection error
     */
    boolean isTranslated(String lang_code) throws DocmaException;

    /**
     * Returns all language codes for which at least one translated value 
     * exists.
     * If this node has no translated value, then an empty array is returned.
     * 
     * @return  an array of language codes
     * @throws DocmaException  if the language codes cannot be retrieved, 
     *                         for example due to a connection error
     */
    String[] listTranslations() throws DocmaException;
    
    /**
     * Deletes all translated values for the current translation language.
     * This method only works in translation-mode.
     * If the session is in original-mode (that means <em>not</em> in 
     * translation-mode), then this method does nothing.
     * 
     * @throws DocmaException  If the deletion is not possible 
     *                         (for example, due to access rights or a 
     *                         connection error)
     */
    void deleteTranslation() throws DocmaException;


    //************************************************************
    //**************    Lock methods            ******************  
    //************************************************************
    
    /**
     * Returns the lock information. If the node is not locked, then this 
     * method returns <code>null</code>.
     * 
     * @return the lock information, or <code>null</code>
     * @throws DocmaException  if the lock information cannot be retrieved, 
     *                         for example due to a connection error
     * @see #setLock()
     * @see #refreshLock()
     * @see #removeLock()
     */
    Lock getLock() throws DocmaException;
    
    /**
     * Sets a lock for this node. Returns <code>true</code>, if the lock has  
     * been successfully created.
     * As long as the created lock exists, no other user can create a lock 
     * for this node. If a lock already exists, then the creation of the lock  
     * fails and <code>false</code> is returned.
     *
     * <p>Depending on the application settings, a created lock may timeout 
     * after a certain time (e.g. after 30 minutes). This is to avoid that 
     * nodes are locked infinitely, e.g. because the client application has  
     * crashed and the lock is never removed by the client. However, the lock  
     * can be refreshed by calling the method <code>refreshLock()</code>  
     * before the timeout occurs (this resets the timer for the timeout to 0).
     * </p>
     * 
     * <p>Note: Even if a lock from another user exists, it is still possible 
     * to update the content by calling one of the <code>setContent...</code> 
     * methods. In other words, a lock is just a hint that the content is  
     * currently edited, but it does <em>not</em> prevent content modifications  
     * through API calls.</p>
     *
     * @return  <code>true</code> if lock creation succeeded;
     *          <code>false</code> if the node is already locked
     * @throws DocmaException  If the creation of a lock is not possible, 
     *                         (for example, due to access rights or a 
     *                         connection error)
     * @see #getLock()
     * @see #refreshLock()
     * @see #removeLock()
     */
    boolean setLock() throws DocmaException;
    
    /**
     * Resets the creation time of an existing lock to the current time.
     * If a lock exists and the reset was successful, then <code>true</code>
     * is returned.
     * If no lock exists, then <code>false</code> is returned.
     *
     * @return  <code>true</code> if refresh succeded; 
     *          <code>false</code> if no lock exists
     * @throws DocmaException  if the lock refresh is not possible,
     *                         (for example, due to access rights or a 
     *                         connection error)
     * @see #getLock()
     * @see #setLock()
     * @see #removeLock()
     */
    boolean refreshLock() throws DocmaException;
    
    /**
     * Removes an existing lock. If no lock exists, then this method returns 
     * <code>null</code>.
     *
     * @return  the information about the removed lock, or <code>null</code>
     * @throws DocmaException  if removal of the lock is not possible,
     *                         (for example, due to access rights or a 
     *                         connection error)
     */
    Lock removeLock() throws DocmaException;

    //************************************************************
    //**************    Other methods           ******************  
    //************************************************************
    
    /**
     * Invalidates the cached session data for this node.
     * Data that is retrieved by one of the <code>get...()</code> methods  
     * might be cached in the user session. 
     * Calling this method removes all cached values for this node.
     * Normally, using this method is not required, because the 
     * application follows a caching strategy that automatically 
     * invalidates outdated session data.
     * However, if you experience problems with outdated session data, calling 
     * this method can be a solution.
     */
    void invalidateCache();

    /**
     * Returns whether the given node is an ancestor of this node.
     * If the node identified by <code>node_id</code> is an ancestor of this  
     * node, then <code>true</code> is returned.
     * Otherwise <code>false</code> is returned.
     *
     * @param node_id  the id of another node
     * @return  <code>true</code>, if <code>node_id</code> identifies an
     *          ancestor; otherwise <code>false</code>
     * @throws DocmaException  if the return value cannot be retrieved, 
     *                         for example due to a connection error
     */
    boolean hasAncestor(String node_id) throws DocmaException;

    /**
     * Returns the parent node. 
     * If the node has no parent, then <code>null</code> is returned.
     * A node can have no parent, either because it is the root node that 
     * is returned by {@link StoreConnection#getRoot()}, or because it is 
     * a newly created node that has not been added as a child node yet.
     *
     * <p>Every invocation of this method may return a new node instance,
     * even if a node-instance for the same node has already been returned 
     * in a previous invocation.
     * Therefore, to compare nodes for equality, the <code>equals()</code>  
     * method has to be used instead of the <code>==</code> operator.</p>
     *
     * @return  the parent node, or <code>null</code>
     * @throws DocmaException  if the parent node cannot be retrieved, 
     *                         for example due to a connection error
     */
    Node getParent() throws DocmaException;

    /**
     * Deletes the node.
     * If translations of the node exist, then the translations are deleted 
     * as well.
     *
     * <p>If the node is an instance of <code>Group</code> and child nodes exist,
     * then the method <code>deleteRecursive()</code> has to be used instead.
     * Calling <code>delete()</code> on a node with existing child nodes,  
     * causes an exception.</p>
     *
     * <p>Deleting nodes in translation mode is not allowed and causes an 
     * exception.</p>
     *
     * @throws DocmaException  if deletion of the node is not possible
     *                         (for example, due to access rights or a 
     *                         connection error)
     */
    void delete() throws DocmaException;
    
    /**
     * Returns the <code>StoreConnection</code> instance that owns this 
     * node instance.
     *
     * @return  the store connection for this node instance
     * @see StoreConnection
     */
    StoreConnection getStoreConnection();

    /**
     * Indicates whether some other object represents the same node as this
     * node. Two instances of <code>Node</code> are considered as 
     * "equal" if the node identifiers, as returned by {@link #getId()}, are 
     * identical and both instances have been retrieved from the same 
     * <code>StoreConnection</code> instance. 
     * If <code>obj</code> does not implement the <code>Node</code>
     * interface, then this method returns <code>false</code>.
     *
     * @param  obj  the object with which to compare
     * @return  <code>true</code> if this node represents the same node 
     *          as the <code>obj</code> argument; <code>false</code> otherwise
     */
    @Override
    boolean equals(Object obj);
    
    //************************************************************
    //**************   Methods to think about   ******************  
    //************************************************************
    
    // public boolean isRoot()
    // public boolean isDocumentRoot()
    // public boolean isSystemRoot()
    // public boolean isMediaRoot()
    // public boolean isFileRoot()

    /*
     * Returns true, if this node-type allows adding of child-nodes via one of the methods 
     * addChild(), addChildren(), insertChild, insertChildren().
     * If adding of child-nodes is not supported, false is returned.
     * Same as <code>this instanceof Group</code>.
     */
    // boolean isGroup();

    /*
     * Returns whether the node is a content-node.
     * Same as <code>this instanceof Content</code>.
     */
    // boolean isContent();
    
    /*
     * Returns whether the node is a HTML content-node.
     * Same as <code>this instanceof PubContent</code>.
     */
    // boolean isPubContent();
    
    /*
     * Returns whether the node is an image-node.
     * Same as <code>this instanceof ImageContent</code>.
     */
    // boolean isImageContent();

    /*
     * Returns whether the node is a file-node.
     * Same as <code>this instanceof FileContent</code>.
     */
    // boolean isFileContent();

    /*
     * Returns whether the node is a reference-node.
     * Same as <code>this instanceof Reference</code>.
     */
    // boolean isReference();
    
    /*
     * Returns whether the node is a section-node.
     * Same as <code>this instanceof PubSection</code>.
     */
    // boolean isPubSection();
    
    /*
     * Returns whether the node is a folder-node.
     * Same as <code>this instanceof Folder</code>.
     */
    // boolean isFolder();

}
