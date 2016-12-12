/*
 * FileContent.java
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
 * The <code>FileContent</code> interface represents content that has a
 * user-defined filename and format.
 * Generally, <code>FileContent</code> nodes can be used to store files of 
 * any format that have been created with external applications. 
 * For example, this can be plain text files or binary files, like multimedia 
 * files.
 *
 * <p><em>Image files:</em> 
 * For supported image formats, the sub-interface {@link ImageFile} exists.
 * This sub-interface changes the filename handling and adds further 
 * functionality. 
 * A file has a supported image format, if its filename ends with one of the 
 * extensions returned by
 * <code>PluginUtil.getImageFileExtensions()</code>).
 * See the documentation of <code>ImageFile</code> for further information.</p>
 *
 * @author MP
 * @see ImageFile
 */
public interface FileContent extends Content 
{

    /**
     * Returns the assigned file extension. The file extension is the suffix 
     * that is used in the filename (see {@link #getFileName()}).
     * If no file extension exists, then an empty string is returned.
     * 
     * <p><em>Translation-mode:</em><br>
     * The file extension of translated content is always identical to the file 
     * extension of the original content.</p>
     * 
     * @return  the file extension (<em>without</em> leading dot character), 
                or an empty string
     * @throws DocmaException  if the file extension cannot be retrieved, 
     *                         for example due to a connection error
     * @see #setFileExtension(String).
     * @see #getFileName().
     * @see #setFileName(String).
     */
    String getFileExtension() throws DocmaException;

    /**
     * Sets the file extension that is used as suffix in the filename.
     * Supplying <code>null</code> removes any previously assigned 
     * file extension.
     * 
     * <p><em>Translation-mode:</em><br>
     * Setting the file extension, while the session is in translation-mode, is  
     * <em>not</em> allowed and causes an exception. The file extension of  
     * translated content is always identical to the file extension of the 
     * original content.</p>
     *
     * @param ext  the file extension, or <code>null</code>
     * @throws DocmaException  If <code>ext</code> is no valid file 
     *                         extension or modifying the extension 
     *                         is not possible (for example, due to access  
     *                         rights or a connection error)
     * @see #getFileExtension().
     * @see #getFileName().
     * @see #setFileName(String).
     */
    void setFileExtension(String ext) throws DocmaException;
    
    /**
     * Returns the node's filename. Different filenames are returned depending
     * on whether the session is in translation-mode or not.
     *
     * <p><em>Original-mode:</em><br>
     * If the session is <em>not</em> in translation-mode and 
     * a file-extension <i>ext</i> is assigned to the node, 
     * then the format of the returned filename is</p>
     *
     * <p><i>name</i>.<i>ext</i></p>
     *
     * <p>In other words, a <i>name</i>-part is followed by an 
     * <i>ext</i>-part. Both parts are separated by a dot.
     * The <i>ext</i>-part can also be retrieved separately, through the  
     * {@link #getFileExtension()} method.
     * If no file extension is assigned, then the filename consists only of the 
     * <i>name</i>-part.</p>
     *
     * <p>For file types that are <em>not</em> specially handled by Docmenta,
     * the <i>name</i>-part can be a user-defined string. There is 
     * <em>no</em> check whether the name is already used by any other node.
     * For supported image files, Docmenta provides the sub-interface
     * {@link ImageFile}. This sub-interface imposes more restrictive rules 
     * on the filename.
     * See the <code>ImageFile</code> documentation for details.</p>
     *
     * <p><em>Translation-mode:</em><br>
     * If translated content is assigned to this node, then the filename of the 
     * translated content is automatically derived from the original filename, 
     * by appending <code>[<i>XX</i>]</code> to the <i>name</i>-part of the 
     * original filename, where <i>XX</i> is the language code of the  
     * translation language (in upper-case letters).
     * In other words, if the session is in translation-mode, and translated 
     * content exists for the current translation language, then the returned
     * filename has one of the following formats, depending on whether a file 
     * extension is assigned or not:</p>
     * <p>
     * <i>name</i>[<i>XX</i>]<br>
     * <i>name</i>[<i>XX</i>].<i>ext</i>
     * </p>
     * <p><em>Operating System specific:</em><br>
     * Be aware that the API does not guarantee that the returned filename is 
     * valid in every filesystem.
     * For example, the assigned <i>name</i>-part may contain characters
     * that are not allowed for filenames in the target filesystem.
     * Such conditions have to be handled separately by the plug-in.</p>
     *
     * @return  the filename of this node
     * @throws DocmaException  if the filename cannot be retrieved, 
     *                         for example due to a connection error
     * @see #setFileName(String)
     * @see #getFileExtension()
     * @see #setFileExtension()
     */
    String getFileName() throws DocmaException;

    /**
     * Sets the filename for this node. 
     * The <code>filename</code> argument can have one of the following two 
     * formats:
     * <p>
     * <i>name</i><br>
     * <i>name</i>.<i>ext</i>
     * </p>
     * <p>The first format applies, if the filename does <em>not</em> contain 
     * a dot. That means, the filename consists of the <i>name</i>-part only.
     * If the filename contains a dot, then the sub-string 
     * from the last dot to the end of the filename is assigned as 
     * file extension <i>ext</i> the same way as through invoking 
     * {@link #setFileExtension(String)}.</p>
     * 
     * <p>The <i>name</i>-part depends on the file type, which is determined
     * from the file extension.
     * For file types that are <em>not</em> specially handled by Docmenta,
     * the <i>name</i>-part can be any user-defined string. There is 
     * <em>no</em> check whether the name is already used by another node.
     * For supported image files, Docmenta provides the sub-interface
     * {@link ImageFile}. This sub-interface imposes more
     * restrictive rules on the filename. 
     * See the <code>ImageFile</code> documentation for details.</p>
     * 
     * <p><em>Translation-mode:</em><br>
     * Setting the filename, while the session is in translation-mode, is  
     * <em>not</em> allowed and causes an exception. The filename of translated 
     * content is automatically derived from the original filename. See the 
     * documentation of {@link #getFileName()} for details.</p>
     *
     * <p><em>Operating System specific:</em><br>
     * Be aware that the API does not check whether the assigned filename 
     * is valid in every filesystem. 
     * For example, the <code>filename</code> argument may contain characters   
     * that are not allowed for filenames in the target filesystem.
     * Such conditions have to be handled separately by the plug-in.</p>
     *
     * @param  filename  the new filename for this node
     * @throws DocmaException  If changing the filename to the new name 
     *                         is not possible (for example, due to an invalid 
     *                         filename, missing rights or a connection error)
     */
    void setFileName(String filename) throws DocmaException;


    /**
     * Returns whether this node is a text-file or not. 
     * This method returns <code>true</code> if the assigned file extension 
     * is a registered text-file extension.
     * This is a convenience method. An invocation of the form 
     * <code>node.isTextFile()</code> returns the same value as 
     * <code>node.getStoreConnection().isTextFileExtension(node.getFileExtension())</code>.
     *
     * @return  <code>true</code> if the file extension denotes a text-file, 
     *          otherwise <code>false</code>
     * @throws DocmaException  if the return value cannot be retrieved, 
     *                         for example due to a connection error
     * @see StoreConnection#isTextFileExtension(String).
     */
    boolean isTextFile() throws DocmaException;

    /**
     * Sets the character set for this file node. 
     * The assigned character set is stored as a node attribute (that means 
     * the character set persists over sessions and is valid for all users). 
     * If the <code>charsetName</code> argument is <code>null</code>, then the
     * character set is set back to the default (UTF-8).
     * 
     * <p>Be aware that invoking this method does <em>not</em> change the   
     * encoding of existing content. This method just sets the character set    
     * to be used in <em>upcoming</em> invocations of 
     * {@link Content#getContentString()} and 
     * {@link Content#setContentString(String)}.
     * Therefore, to store the string <code>txt</code> as content of the  
     * node <code>n</code> with a user-defined character set <code>cs</code>, 
     * the statement <code>n.setCharset(cs)</code> has to be executed before  
     * <code>n.setContentString(txt)</code>.</p>
     *
     * @param charsetName  the character set to be used for encoding/decoding 
     *                     content, or <code>null</code>
     * @throws DocmaException  If changing the character set is not possible
     *                         (for example, due to access rights or a 
     *                         connection error)
     */
    void setCharset(String charsetName) throws DocmaException;

}