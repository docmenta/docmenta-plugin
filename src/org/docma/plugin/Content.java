/*
 * Content.java
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

import java.io.InputStream;

/**
 *
 * @author MP
 */
public interface Content extends Node 
{

    /**
     * Returns the content of the node.
     * If no content is assigned, then <code>null</code> is returned.
     * 
     * <p>If the session is in translation-mode, and the content has been
     * translated, then this method returns the translated content.
     * If the session is in translation-mode, but no translated content
     * exists, then this method returns the original content  
     * (that is the content that has been set in original-mode).</p>
     * 
     * @return  the content or <code>null</code>
     * @throws DocmaException  if the content cannot be retrieved, 
     *                         for example due to a connection error
     */
    byte[] getContent() throws DocmaException;
    
    /**
     * Returns the content of the node as a string.
     * If no content is assigned, then <code>null</code> is returned.
     * 
     * <p>If a character set has been assigned to this node 
     * (see {@link #setFileCharset(String)} and {@link #getCharset()}),
     * then the assigned character set is used for the encoding of the string.
     * If no character set is assigned, then UTF-8 is used for the encoding.
     * </p>
     * 
     * <p>If the session is in translation-mode, and the content has been
     * translated, then this method returns the translated content.
     * If the session is in translation-mode, but no translated content
     * exists, then this method returns the original content  
     * (that is the content that has been set in original-mode).</p>
     * 
     * @return  the content or <code>null</code>
     * @throws DocmaException  if the content cannot be retrieved, 
     *                         for example due to a connection error
     */
    String getContentString() throws DocmaException;

    /**
     * Sets the content of the node to the given byte array.
     * 
     * <p>If the session is in original-mode (in other words,
     * <em>not</em> in translation-mode), then this method sets the content  
     * for the original language.</p>
     *
     * <p>If the session is in translation-mode, then this method 
     * sets the content for the current translation language.</p>
     *
     * @param content  the content, not <code>null</code>
     * @throws DocmaException  If setting the content is not possible
     *                         (for example, due to access rights or a 
     *                         connection error)
     */
    void setContent(byte[] content) throws DocmaException;

    /**
     * Sets the content of the node to the given input stream.
     *
     * <p>If the session is in original-mode, (in other words,
     * <em>not</em> in translation-mode), then this method sets the content 
     * for the original language.</p>
     *
     * <p>If the session is in translation-mode, then this method 
     * sets the content for the current translation language.</p>
     *
     * @param content  the content, not <code>null</code>
     * @throws DocmaException  If setting the content is not possible
     *                         (for example, due to access rights or a 
     *                         connection error)
     */
    void setContentStream(InputStream content) throws DocmaException;
    
    /**
     * Sets the content of the node to the given string.
     * This method uses the character encoding as given by the method
     * {@link #getCharset()}. 
     *
     * <p>Note: The character encoding can only be changed for nodes of type 
     * <code>FileContent</code> (via the method 
     * {@link FileContent#setCharset(String)}). For other types of nodes, 
     * the character set is defined by the application setup.</p>
     * 
     * <p>If the session is in original-mode, (in other words,
     * <em>not</em> in translation-mode), then this method sets the content 
     * for the original language.</p>
     *
     * <p>If the session is in translation-mode, then this method 
     * sets the content for the current translation language.</p>
     *
     * @param content  the content, not <code>null</code>
     * @throws DocmaException  If setting the content is not possible
     *                         (for example, due to access rights or a 
     *                         connection error)
     * @see #getContent()
     * @see #getContentString()
     */
    void setContentString(String content) throws DocmaException;

    /**
     * Removes the assigned content.
     * After calling this method, the methods {@link #getContent()} and 
     * {@link #getContentString()} return <code>null</code>. 
     *
     * <p>If the session is in original-mode (in other words, <em>not</em> in 
     * translation-mode), then the content is removed including all 
     * translations (if existent).</p>
     *
     * <p>If the session is in translation-mode, then only the translated  
     * content for the current translation language is removed.</p>
     * 
     * @throws DocmaException  If modifying the content is not possible
     *                         (for example, due to access rights or a 
     *                         connection error)
     */
    void clearContent() throws DocmaException;
	
    /**
     * Returns the length of the content in bytes.
     * 
     * <p>If the session is in translation-mode, then this method returns the
     * length of the translated content.
     * If the session is in translation-mode, but no translated content
     * exists, then this method returns the length of the original content.</p>
     *
     * @return the length of the content, or 0 if no content is assigned
     * @throws DocmaException  if the content length cannot be retrieved, 
     *                         for example due to a connection error
     */
    long getContentLength() throws DocmaException;
    
    /**
     * Returns the MIME type of the node.
     *
     * <p>For nodes of type {@link FileContent} and 
     * {@link ImageContent}, the MIME type is determined from the file 
     * extension.</p>
     *
     * <p>For nodes of type {@link PubContent}, the returned MIME type  
     * determines the format of the publication content.</p> 
     *
     * <p><em>Implementation specific:</em> In the current implementation, 
     * the MIME type <code>"text/html"</code> is returned for all nodes of 
     * type <code>PubContent</code>.
     * This is true, even though the content has to follow the rules of XML, and 
     * might therefore be considered as XHTML, for which the MIME type 
     * <code>"application/xhtml+xml"</code> could be used. However, as the  
     * content does not completely follow the XHTML standards (for example, a  
     * node contains only fragments of an XHTML page), the less restrictive  
     * MIME type <code>"text/html"</code> is returned.</p>
     *
     * @return  the MIME type, or <code>null</code> if unknown
     * @throws DocmaException  if the content type cannot be retrieved, 
     *                         for example due to a connection error
     */
    String getContentType() throws DocmaException;

    /**
     * Returns the assigned character set.
     * If no character set has been assigned, this returns the name of the
     * default character set. 
     * The default character set is <code>"UTF-8"</code>. 
     *
     * <p>The character set that is returned by this method is used by 
     * {@link #getContentString()} and {@link #setContentString()} to 
     * decode/encode the content.</p>
     *
     * <p>Note: The character set can only be changed for nodes of type 
     * <code>FileContent</code> (via the method 
     * {@link FileContent#setCharset(String)}).</p>
     *
     * @return  the character set used to encode/decode content
     * @throws DocmaException  if the character set cannot be retrieved, 
     *                         for example due to a connection error
     */
    String getCharset() throws DocmaException;

    /**
     * Indicates whether content for the given language exists or not.
     * If the <code>lang_code</code> argument is <code>null</code>, then this 
     * method returns whether content for the original language exists or not. 
     * If the <code>lang_code</code> argument is the language code of a 
     * translation language, then this method indicates whether a translation 
     * of the content for the given translation language exists or not.
     * 
     * <p>Note that the return value <code>true</code> for a given language 
     * code does <em>not</em> mean, that the content is semantically translated. 
     * This method just returns,  
     * whether some value has been stored for the given language or not.
     * For example, it is possible to read the content of a node,
     * then switch to translation-mode and set the original content 
     * as the translated content. In this case, the method returns 
     * <code>true</code> for the given translation language, even though the 
     * translated content is identical to the original content.</p>
     *
     * @param lang_code  a language code, or <code>null</code>
     * @return  <code>true</code> if content exists; 
     *          otherwise <code>false</code>
     * @throws DocmaException  if the return value cannot be retrieved, 
     *                         for example due to a connection error
     */
    boolean hasContent(String lang_code) throws DocmaException;

    /**
     * Indicates whether invoking one of the <code>setContent</code> methods is
     * allowed.
     * If modifying the content is allowed, this method does nothing.
     * Otherwise an exception is thrown.
     * 
     * @throws DocmaException  if updating the content is not allowed, 
     *                         due to missing access rights or because the
     *                         version is already released
     * @see #checkEditContentAllowed() 
     */
    void checkUpdateContentAllowed() throws DocmaException;
    
    /**
     * Indicates whether the node's content can be modified by the user.
     * If modifying the content is allowed, this method does nothing.
     * Otherwise an exception is thrown.
     * This method is the same as {@link #checkUpdateContentAllowed() }, except
     * that this method also considers the node's workflow status.
     * 
     * @throws DocmaException  if updating the content is not allowed, for
     *                         example, because the version is released, 
     *                         due to missing access rights, or wrong workflow 
     *                         status 
     * @see #checkUpdateContentAllowed() 
     */
    void checkEditContentAllowed() throws DocmaException;
    
    //************************************************************
    //**************    Revision methods        ******************  
    //************************************************************
    
    /**
     * Creates a new revision for this node, which contains a snapshot of the  
     * current content.
     * If the node supports the creation of revisions and the creation was 
     * successful, then <code>true</code> is returned.
     * If the node does not support revisions, then <code>false</code> is 
     * returned.
     * 
     * @return  whether revision has been created, or not
     * @throws DocmaException  If this node supports revisions, but the 
     *                         creation failed (for example, due to 
     *                         access rights or a connection error)
     */
    boolean makeRevision() throws DocmaException;

    /**
     * Returns all available revisions for this node.
     * The revisions are sorted by creation time, where the first element 
     * in the array (index 0) contains the oldest revision, and the last  
     * element contains the latest revision.
     * 
     * <p>If the node does not support revisions, then an empty array is 
     * returned.</p>
     * 
     * @return  all revisions sorted by creation time
     * @throws DocmaException  if the revisions cannot be retrieved, 
     *                         for example due to a connection error
     */
    ContentRevision[] getRevisions() throws DocmaException;

}
