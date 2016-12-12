/*
 * ContentRevision.java
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
import java.util.Date;

/**
 *
 * @author MP
 */
public interface ContentRevision 
{
    /**
     * Returns the revision date. Generally, this is the last modification date
     * of the content, at the time when the revision has been created.
     * 
     * @return the revision date
     * @throws DocmaException  if the date cannot be retrieved, 
     *                         for example due to a connection error
     */
    Date        getDate() throws DocmaException;
    
    /**
     * Returns the user who has most recently updated the content, at the
     * time when the revision has been created.
     * 
     * @return the user id
     * @throws DocmaException  if the user id cannot be retrieved, 
     *                         for example due to a connection error
     */
    String      getUserId() throws DocmaException;

    /**
     * The content at the time when the revision has been created.
     * 
     * @return  the revised content
     * @throws DocmaException  if the content cannot be retrieved, 
     *                         for example due to a connection error
     */
    byte[]      getContent() throws DocmaException;
    
    /**
     * The content at the time when the revision has been created, returned
     * as binary stream.
     * 
     * @return  the revised content as a stream
     * @throws DocmaException  if the content cannot be retrieved, 
     *                         for example due to a connection error
     */
    InputStream getContentStream() throws DocmaException;

    /**
     * The content at the time when the revision has been created, returned
     * as a string. If the content is binary content, then the content is
     * encoded as a string using the UTF-8 character set.
     * 
     * @return  the revised content
     * @throws DocmaException  if the content cannot be retrieved, 
     *                         for example due to a connection error
     */    
    String      getContentString() throws DocmaException;

    /**
     * The content at the time when the revision has been created, returned
     * as a string with the given character set. If the content is binary  
     * content, then the content is encoded as a string using the character set
     * supplied in the <code>charsetName</code> argument.
     * 
     * @param charsetName  the character set to be used
     * @return  the revised content
     * @throws DocmaException  if the content cannot be retrieved, 
     *                         for example due to a connection error
     */    
    String      getContentString(String charsetName) throws DocmaException;

    /**
     * Returns the length of the content.
     * 
     * @return  the content length in bytes
     * @throws DocmaException  if the content length cannot be retrieved, 
     *                         for example due to a connection error
     */
    long        getContentLength() throws DocmaException;
}
