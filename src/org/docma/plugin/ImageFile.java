/*
 * ImageFile.java
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
 * An <code>ImageFile</code> node represents a supported image file.
 * A file has a supported image format, if its filename ends with one of the 
 * extensions returned by
 * <code>PluginUtil.getImageFileExtensions()</code>.
 *
 * <p>This interface extends the <code>FileContent</code> interface. Besides  
 * added functionality, it also changes the filename handling
 * (see the {@link #setFileName(String)} method for more information).</p>
 *
 * @author MP
 * @see FileContent
 */
public interface ImageFile extends FileContent 
{

    /**
     * Returns the filename of the image. Different filenames are returned 
     * depending on whether the session is in translation-mode or not.
     * 
     * <p><em>Original-mode:</em><br> 
     * If the session is <em>not</em> in translation-mode, then the format 
     * of the filename is</p>
     *
     * <p><i>name</i>.<i>ext</i></p>
     *
     * <p>where <i>ext</i> is one of the supported image file extensions 
     * as returned by <code>PluginUtil.getImageFileExtensions()</code>.
     * If an alias is assigned to the node, then the <i>name</i>-part is the 
     * alias as returned by the {@link #getAlias()} method. 
     * Otherwise the <i>name</i>-part is the assigned image title as returned 
     * by {@link #getTitle()}.
     * The <i>name</i>-part and the <i>ext</i>-part are separated by a dot.
     * </p>
     *
     * <p><em>Translation-mode:</em><br> 
     * <p>If the session is in translation-mode, then the format of the 
     * filename is</p>
     *
     * <p><i>name</i>[<i>XX</i>].<i>ext</i></p>
     *
     * <p>where <i>XX</i> is the language code of the current translation 
     * language (in upper-case letters). The <i>name</i>-part and the 
     * <i>ext</i>-part are the same as for the filename in original-mode.
     * Note that if no alias is assigned, then the <i>name</i>-part is the 
     * node's title for the original language, even if a  
     * translated title exists.</p>
     *
     * <p><em>Operating System specific:</em><br>
     * Be aware that the API does not guarantee that the returned filename is 
     * valid in every filesystem.
     * For example, if no alias is assigned to the node, then the image title is 
     * used as first part of the filename. But the title may contain 
     * characters that are not allowed for filenames in the target filesystem.
     * Such conditions have to be handled separately by the plug-in.</p>
     * 
     * @return  the filename of this image
     * @throws DocmaException  if the filename cannot be retrieved, 
     *                         for example due to a connection error
     * @see #setFileName(String)
     */
    String getFileName() throws DocmaException;

    /**
     * Sets the filename for this image node. 
     * The <code>filename</code> argument must have following format
     * <p>
     * <i>name.ext</i><br>
     * </p>
     * <p>where <i>ext</i>  has to be one of the supported image file 
     * extensions as returned by 
     * <code>PluginUtil.getImageFileExtensions()</code>.
     * The <i>name</i>-part is handled differently depending on whether 
     * an alias is assigned to this node or not:</p>
     *
     * <p>If an alias is assigned to this node, then the <i>name</i>-part
     * is mapped to the node's alias. In other words, an invocation of 
     * the form <code>setFileName(<i>name</i> + "." + </i>ext</i>)</code>
     * updates the alias exactly the same way as through calling 
     * {@link #setAlias(String)}<code>(<i>name</i>)</code>.</p>
     * 
     * <p>If <em>no</em> alias is assigned to this node, then the 
     * <i>name</i>-part is mapped to the image title. In other words, this 
     * method updates the image title the same way as through calling 
     * {@link #setTitle(String)}<code>(<i>name</i>)</code></p>.
     *
     * <p>In both cases, the file extension is updated the same way as through 
     * calling 
     * {@link #setFileExtension(String)}<code>(<i>ext</i>)</code>.</p>
     * 
     * <p>Be aware, that changing the alias via the <code>setAlias</code>  
     * method implicitly changes the filename returned by this method.
     * If no alias is assigned to this node, then changing the    
     * image title for the original language via the <code>setTitle</code>   
     * method implicitly changes the filename returned by this method.</p>
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
     * @param  filename  the new filename of the image
     * @throws DocmaException  If changing the filename to the new name 
     *                         is not possible (for example, due to an invalid 
     *                         filename, missing rights or a connection error)
     */
    void setFileName(String filename) throws DocmaException;

    /**
     * Returns the assigned image title.
     * If no title is assigned, then an empty string is returned.
     * 
     * <p>If this image has an alias and is included as a figure in
     * a {@link PubContent} node, then the assigned title is used as the 
     * default figure caption.
     * If this image has <em>no</em> alias,  
     * then the title is also part of the filename that is
     * returned by the {@link #getFileName()} method.</p>
     * 
     * <p><em>Translation-mode:</em><br>
     * <p>If the session is in translation-mode, then this method returns the 
     * translated title.
     * If no translation of the title exists, then the title for the original 
     * language is returned (that is the value that has been assigned in 
     * original-mode).</p>
     *
     * @return the title of the image or an empty string
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
     * <code>image.getTitleEntityEncoded()</code> returns the same value as
     * the invocation</p> 
     * <p><code>
     * image.getStoreConnection().encodeCharEntities(image.getTitle(), false);
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
     * Sets the image title.
     * If the session is <em>not</em> in translation-mode and <code>null</code>
     * is assigned, then the title is removed (including all translations).
     *
     * <p>If this image has <em>no</em> alias, then the title is part of the
     * filename returned by the {@link #getFileName()} method.
     * Furthermore, when a user is inserting an image as a figure in a  
     * {@link PubContent} node, the editor-application can present the 
     * image title as the proposed figure caption to the user.
     * Note that an image can only be inserted as a figure, if it has an alias
     * assigned. Therefore, the image title is either part of the filename, or 
     * used as figure caption.</p>
     *
     * <p><em>Translation-mode:</em><br>
     * If the session is in translation-mode, then this method 
     * sets the translated title for the current translation language.
     * If <code>null</code> is assigned and the session is in 
     * translation-mode, then the translated title for the current 
     * translation language is removed.</p>
     *
     * @param value  the image title, or <code>null</code>
     * @throws DocmaException  If setting the title is not possible 
     *                         (for example, due to access rights or a 
     *                         connection error)
     * @see #getTitle()
     */
    void setTitle(String value) throws DocmaException;

    /**
     * Returns this image in a different format and/or rescaled as defined for 
     * the given rendition name. For example, this method can be used to  
     * retrieve thumbnail renditions of the image. The rendition name has to be   
     * one of the values returned by the
     * {@link StoreConnection#listImageRenditionNames()} method.
     * The format and scale of the returned image can be retrieved by calling 
     * the {@link StoreConnection#getImageRenditionInfo()} method.
     *
     * @param renditionName  the rendition name
     * @return  the image rendition for the given rendition name
     * @throws DocmaException  if the rendition cannot be retrieved, 
     *                         for example due to a connection error
     * @see StoreConnection#getImageRenditionInfo(String)
     */
    byte[] getImageRendition(String renditionName) throws DocmaException;

    // int getWidth();
    
    // int getHeight();
}