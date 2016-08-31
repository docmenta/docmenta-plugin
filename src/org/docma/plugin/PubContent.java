/*
 * PubContent.java
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
public interface PubContent extends Content 
{

    // String prepareXHTML(String content, Properties props) throws Exception;

    /**
     * Returns the assigned title of the node.
     * If no title is assigned, then an empty string is returned.
     *
     * <p><em>Translation-mode:</em><br>
     * <p>If the session is in translation-mode, then this method returns the 
     * translated title.
     * If no translation of the title exists, then the title for the original 
     * language is returned (that is the value that has been assigned in 
     * original-mode).</p>
     * 
     * @return the title of the node or an empty string
     * @throws DocmaException  if the title cannot be retrieved, 
     *                         for example due to a connection error
     * @see #setTitle(String)
     * @see #getTitleEntityEncoded()
     */
    String getTitle() throws DocmaException;

    /**
     * Returns the same value as <code>getTitle()</code>, but with special 
     * characters encoded as character-entities.
     * If no title is assigned, then an empty string is returned.
     * Special characters are defined in the application's 
     * character-entity configuration.
     * 
     * <p>This is a convenience method. An invocation of the form
     * <code>pubcontent.getTitleEntityEncoded()</code> returns the same value as
     * the invocation</p> 
     * <p><code>
     * pubcontent.getStoreConnection().encodeCharEntities(pubcontent.getTitle(), false);
     * </code></p>
     * 
     * @return the character-entity encoded title or an empty string
     * @throws DocmaException  if the title cannot be retrieved, 
     *                         for example due to a connection error
     * @see #getTitle()
     * @see #setTitle(String)
     */
    String getTitleEntityEncoded() throws DocmaException;

    /**
     * Sets a title for this content.
     * If the session is <em>not</em> in translation-mode and <code>null</code> 
     * is assigned, then the title is removed (including all translations).
     *
     * <p><em>Translation-mode:</em><br>
     * If the session is in translation-mode, then this method 
     * sets the translated title for the current translation language.
     * If <code>null</code> is assigned and the session is in 
     * translation-mode, then the translated title for the current 
     * translation language is removed.</p>
     *
     * @param value  the title, or <code>null</code>
     * @throws DocmaException  If setting the title is not possible 
     *                         (for example, due to access rights or a 
     *                         connection error)
     * @see #getTitle()
     */
    void setTitle(String value) throws DocmaException;

    /**
     * Indicates whether a translated title for the language  
     * <code>lang_code</code> exists.
     * 
     * @param lang_code  the language code of the translation language
     * @return  <code>true</code> if a translation for the given language exists;
     *          <code>false</code> otherwise
     * @throws DocmaException  If the translation status cannot be retrieved 
     *                         (for example, due to a connection error)
     * @see #getTitle()
     */
    boolean isTitleTranslated(String lang_code) throws DocmaException;
    
    /**
     * Returns all anchors that exist in the content of this node.
     * An anchor is an XML (XHTML) element that has an <code>id</code> 
     * attribute assigned.
     * The value of the <code>id</code> attribute can be used as an alias,
     * to reference the element from within this node or from any other node  
     * within the store. 
     *
     * <p>If the content does not contain any 
     * anchors, then this methods returns an empty array.</p>
     *
     * <p>Note: The method 
     * <code>StoreConnection.getNodeByAlias(<i>name</i>)</code> 
     * returns the node that has an alias equal to <code><i>name</i></code> or 
     * that contains an anchor with an <code>id</code> value equal to 
     * <code><i>name</i></code>.</p>
     *
     * <p><em>Translation-mode:</em><br>
     * If the session is in translation-mode and translated content has been 
     * assigned, then this method returns the anchors included in the 
     * translated content. Though, to avoid dead links in translated 
     * publications, the <code>id</code> values of the anchors in the 
     * translated content should be the same as in the original content.</p>
     *
     * @return  the anchors in the content 
     * @throws DocmaException  if the anchors cannot be retrieved, 
     *                         for example due to a connection error
     * @see StoreConnection#getNodeByAlias(String).
     */
    ContentAnchor[] getContentAnchors() throws DocmaException;   // empty array anstatt null

    /**
     * Returns whether the content contains anchors.
     * This method returns the same value as the expression 
     * {@code getContentAnchors().length > 0}.
     * However, invoking <code>hasContentAnchors()</code> might be more 
     * efficient than an invocation of <code>getContentAnchors()</code>.
     *
     * @return  <code>true</code> if the content contains one or more anchors; 
     *          <code>false</code> otherwise
     * @throws DocmaException  if the return value cannot be retrieved, 
     *                         for example due to a connection error
     */
    boolean hasContentAnchors() throws DocmaException;

    /**
     * Returns whether the content includes an anchor with the given   
     * <code>anchorId</code> value.
     * Every XML (XHTML) element, that has an <code>id</code> 
     * attribute, is considered to be an anchor. If an anchor exists
     * where the value of the <code>id</code> attribute is equal to 
     * <code>anchorId</code>, then <code>true</code> is returned. 
     * Otherwise <code>false</code> is returned.
     * 
     * @param anchorId  the <code>id</code> value to search for
     * @return  <code>true</code> if the content contains an anchor with the 
     *          given <code>id</code> value, otherwise <code>false</code>
     * @throws DocmaException  if the return value cannot be retrieved, 
     *                         for example due to a connection error
     */
    boolean hasContentAnchor(String anchorId) throws DocmaException;

}
