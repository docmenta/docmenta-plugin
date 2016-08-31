/*
 * Folder.java
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
 * An instance of <code>Folder</code> represents a named container, that can 
 * store all kinds of nodes. It provides basically the functionality of a 
 * folder in a filesystem. Compared to a <code>PubSection</code> node, 
 * a <code>Folder</code> node is <em>not</em> rendered as content  
 * (chapter/section/...) in a publication. However, a <code>Folder</code> node
 * can be used to store nodes that are referenced from within the publication
 * content. For example, a <code>Folder</code> node can store image files that 
 * are included in the publication content as figures.
 *
 * @author MP
 */
public interface Folder extends Group 
{
    /**
     * Returns the folder name.
     * If no name is assigned, then an empty string is returned.
     * 
     * <p><em>Translation-mode:</em><br>
     * <p>If the session is in translation-mode, then this method returns the 
     * translated name.
     * If no translation of the name exists, then the name for the original 
     * language is returned (that is the value that has been assigned in 
     * original-mode).</p>
     *
     * @return the name of this folder, or an empty string
     * @throws DocmaException  if the name cannot be retrieved, 
     *                         for example due to a connection error
     * @see #setName(String)
     */
    String getName() throws DocmaException;

    /**
     * Sets the folder name.
     * If the session is <em>not</em> in translation-mode and <code>null</code>
     * is assigned, then the name is removed (including all translations).
     *
     * <p><em>Translation-mode:</em><br>
     * If the session is in translation-mode, then this method 
     * sets the translated name for the current translation language.
     * If <code>null</code> is assigned and the session is in 
     * translation-mode, then the translated name for the current 
     * translation language is removed.</p>
     *
     * @param value  the new folder name, or <code>null</code>
     * @throws DocmaException  if setting the name is not possible 
     *                         (for example, due to access rights or a 
     *                         connection error)
     * @see #getName()
     */
    void setName(String value) throws DocmaException;

    /**
     * Returns the folder-type.
     *
     * @return  the folder type
     * @throws DocmaException  if the folder type cannot be retrieved, 
     *                         for example due to a connection error
     * @see setFolderType(String)
     * @see FolderType
     */  
    FolderType getFolderType() throws DocmaException;
    
    /**
     * Sets the folder type to the supplied value.
     *
     * @param folder_type  the new folder type
     * @throws DocmaException  if setting the folder type is not possible 
     *                         (for example, due to access rights or a 
     *                         connection error)
     * @see getFolderType()
     * @see FolderType
     */
    void setFolderType(FolderType folder_type) throws DocmaException;
    

    /*
     * Sorts the child-nodes by type and name.
     * Child nodes of type {@link Folder} are moved to the beginning of the 
     * child-list. The folder nodes are sorted by the folder name in ascending
     * order (see {@link #getName()}).
     * Nodes of type {@link FileContent} are placed after <code>Folder</code> 
     * nodes (if existent), but before any other node types. 
     * The <code>FileContent</code> nodes are sorted by filename
     * in ascending order (see {@link FileContent#getFileName()}).
     * The ordering of nodes that have any other type than <code>Folder</code>
     * or <code>FileContent</code> is undefined.
     *
     * @throws java.lang.Exception  If changing the order of the child nodes 
     *                              is not allowed (for example, due to 
     *                              access rights)
     */
    // void sortByName() throws Exception;

    // public void setFolderTypeImage()
    // public void setFolderTypeFile()   // setFolderTypeGeneral()

    // public boolean isImageFolder();
    // public boolean isFileFolder();   // isGeneralFolder()
    

}
