/*
 * PubSection.java
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
 * An instance of <code>PubSection</code> represents a section in 
 * a publication. Here, the term "section" is used for all hierarchical levels 
 * within a publication. For example, a <code>PubSection</code> node could be  
 * rendered either as a book-part, chapter, preface, appendix or any other kind
 * of section within a publication, depending on the publication settings and 
 * the depth of the node relative to the root node of the publication.
 *
 * <p>A <code>PubSection</code> node can contain all types of child 
 * nodes. However, only child nodes of type <code>PubSection</code> and 
 * {@link PubContent}, as well as {@link Reference} nodes to one of  
 * these node types, appear in an exported publication. 
 * For example, if a <code>PubSection</code> node, that contains a child node 
 * of type <code>Folder</code>, which itself contains child nodes of type 
 * <code>ImageFile</code>, is exported as part of a publication, then the 
 * image files in the folder do not appear in the exported publication, unless 
 * an image is referenced from within an exported <code>PubContent</code> node.
 * </p>
 *
 * @author MP
 */
public interface PubSection extends Group 
{
    /**
     * Returns the section title.
     * If no title is assigned, then an empty string is returned.
     *
     * <p><em>Translation-mode:</em><br>
     * <p>If the session is in translation-mode, then this method returns the 
     * translated title.
     * If no translation of the title exists, then the title for the original 
     * language is returned (that is the value that has been assigned in 
     * original-mode).</p>
     * 
     * @return the title of the section or an empty string
     * @throws DocmaException  if the title cannot be retrieved, 
     *                         for example due to a connection error
     * @see #setTitle(String)
     * @see #getTitleEntityEncoded()
     */
    String getTitle() throws DocmaException;

    /**
     * Returns the section title for the given language code.
     * If the <code>lang_code</code> argument is <code>null</code>,
     * then the title for the original language is returned.
     * If no title for the original language is assigned, then an empty string 
     * is returned.
     *
     * <p><em>Retrieving translated attribute values:</em><br>
     * If the <code>lang_code</code> argument is <em>not</em> <code>null</code>,
     * then this method returns the title for the translation language
     * identified by <code>lang_code</code>. If no translated title for the 
     * language <code>lang_code</code> exists, then <code>null</code> is 
     * returned.
     * 
     * <p>Note that a return value other than <code>null</code> does not  
     * necessarily mean that the title is semantically translated. This method 
     * just returns the title that has been stored for the given translation 
     * language.
     * For example, it is possible to read the section title for the original
     * language, then switch to translation mode and set this value  
     * as the translated title. In this case the translated title is  
     * identical to the original title.</p>
     * 
     * @param lang_code  the language code of a translation language, 
     *                   or <code>null</code>
     * @return  the section title
     * @throws DocmaException  if the title cannot be retrieved, for example
     *                         due to a connection error
     */
    String getTitle(String lang_code) throws DocmaException;

    /**
     * Returns the same value as <code>getTitle()</code>, but with special 
     * characters encoded as character-entities.
     * If no title is assigned, then an empty string is returned.
     * Special characters are defined in the application's 
     * character-entity configuration.
     * 
     * <p>This is a convenience method. An invocation of the form
     * <code>section.getTitleEntityEncoded()</code> returns the same value as
     * the invocation</p> 
     * <p><code>
     * section.getStoreConnection().encodeCharEntities(section.getTitle(), false);
     * </code></p>
     * <p>(assuming that <code>section</code> is an instance of 
     * <code>PubSection</code>).</p>
     * 
     * @return the character-entity encoded title or an empty string
     * @throws DocmaException  if the title cannot be retrieved, 
     *                         for example due to a connection error
     * @see #getTitle()
     * @see #setTitle(String)
     */
    String getTitleEntityEncoded() throws DocmaException;

    /**
     * Sets a title for this section.
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

    //************************************************************
    //**************   Methods to think about   ******************  
    //************************************************************
    
    /*
     * Returns whether the insertion of a content-node at the 
     * given child-position is allowed. 
     * 
     * Inserting a content-node after a section-node gives an invalid 
     * publication structure and may lead to export errors.
     * Therefore, before inserting a content-node as a child, 
     * this method can be used to check, 
     * whether the insertion is allowed at the given position.
     *
     * In case one of the child-nodes is a section-node, then this method 
     * returns true, if the insert position is less or equal to the position 
     * of the first section-node, otherwise false.   
     * In case no section-node exists, then all insert positions are allowed, 
     * i.e. the method returns true if the insert position is in the range from 0
     * to getChildCount() (inclusive).
     *
     * In any case, if the insert position is out of range
     * (less than 0 or greater than getChildCount()), 
     * false is returned.
     */ 
    // public boolean isInsertContentAllowed(int insert_pos);   
    // boolean isInsertHTMLAllowed(int insert_pos);
    
    /*
     * Returns whether the insertion of a section-node at the 
     * given child-position is allowed. 
     *
     * Inserting a section-node before a HTML-node gives an invalid 
     * publication structure and may lead to export errors.
     * Therefore, before inserting a section-node as a child, 
     * this method can be used to check, 
     * whether the insertion is allowed at the given position.
     *
     * In case one of the child-nodes is a HTML-node, this method 
     * returns true, if the insert position is greater than 
     * the position of the last HTML content, otherwise false.
     *
     * In case no HTML-content exists, then a new section can be inserted at 
     * any child-position,
     * i.e. the method returns true if the insert position is in the range from 0
     * to getChildCount() (inclusive).
     *
     * In any case, if the insert position is out of range 
     * (less than 0 or greater than getChildCount()), 
     * false is returned.
     */
    // boolean isInsertSectionAllowed(int insert_pos);

}