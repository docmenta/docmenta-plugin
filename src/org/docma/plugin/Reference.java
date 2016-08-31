/*
 * Reference.java
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
 * Nodes that implement the <code>Reference</code> interface can reference 
 * other nodes that have an alias assigned. A reference node allows to 
 * virtually include nodes at different locations in the node tree, although
 * physically a node must be located at only one position (defined by the  
 * parent node; see {@link Node#getParent()}).
 *
 * @author MP
 */
public interface Reference extends Node 
{
    /**
     * Returns the title for this reference.
     * If no title is assigned, then an empty string is returned.
     *
     * <p>Generally, a reference does not need a title by itself, given that 
     * the referenced node already has a title. 
     * However, explicitly setting a title for the reference can be used to 
     * overwrite the title of the target node.</p>
     *
     * <p><em>Translation-mode:</em><br>
     * <p>If the session is in translation-mode, then this method returns the 
     * translated title.
     * If no translation of the title exists, then the title for the original 
     * language is returned (that is the value that has been assigned in 
     * original-mode, or an empty string if no title has been set).</p>
     * 
     * @return the title of the reference or an empty string
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
     * <code>ref.getTitleEntityEncoded()</code> returns the same value as
     * the invocation</p> 
     * <p><code>
     * ref.getStoreConnection().encodeCharEntities(ref.getTitle(), false);
     * </code></p>
     * <p>(assuming that <code>ref</code> is an instance of 
     * <code>Refernce</code>).</p>
     * 
     * @return the character-entity encoded title or an empty string
     * @throws DocmaException  if the title cannot be retrieved, 
     *                         for example due to a connection error
     * @see #getTitle()
     * @see #setTitle(String)
     */
    String getTitleEntityEncoded() throws DocmaException;

    /**
     * Sets a title for this reference.
     * If the session is <em>not</em> in translation-mode and <code>null</code> 
     * is assigned, then the title is removed (including all translations).
     *
     * <p>Generally, a reference does not need a title by itself, given that 
     * the referenced node already has a title. 
     * However, explicitly setting a title for the reference can be used to 
     * overwrite the title of the target node.</p>
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
     * Returns the target value. 
     * The target value is an alias or the link-part of an alias,
     * identifying the referenced nodes.
     * This method does <em>not</em> check, whether a node 
     * with the assigned alias (or link-part of an alias) exists or not.
     * This method just returns the value that has been set by 
     * {@link #setTarget(String)}.
     *
     * @return  the assigned target value
     * @throws DocmaException  if the return value cannot be retrieved, 
     *                         for example due to a connection error
     * @see #setTarget(String)
     */
    String getTarget() throws DocmaException;

    /**
     * Sets the target value that identifies the referenced node(s).
     * The <code>target</code> argument has to be an alias or the link-part 
     * of an alias. If only the link-part of an alias is assigned (instead of
     * a complete alias), then more than one node could be referenced, namely
     * all nodes that have an alias with the given link-part.
     *
     * <p>This method does <em>not</em> check, whether a target node with the  
     * given name exists or not. For example, the <code>target</code> argument
     * can be a name that is not yet assigned as alias (or link-part of an   
     * alias) to any node. Though, if a publication that includes such a  
     * reference is exported, an error will appear in the export log, 
     * stating that this reference is broken.</p>
     *
     * @param target  alias or link-part of an alias
     * @throws DocmaException  If setting the target value is not possible 
     *                         (for example, due to access rights or a 
     *                         connection error)
     * @see #getTarget()
     */
    void setTarget(String target) throws DocmaException;

    /**
     * Returns the referenced nodes, namely the nodes identified by the 
     * assigned target value (see {@link #getTarget}).
     * If the target value is a "complete" alias, and a node with 
     * this alias exists, then an array with length 1 containing this 
     * node is returned.
     * If the target value is the link-part of an alias (not a 
     * "complete" alias), then the returned array contains all nodes that 
     * share this link-part. In this case, the returned
     * array is sorted by the alias names of the returned nodes.
     * If the target value is not used as alias or as link-part of an alias, 
     * then an empty array is returned.
     *
     * <p>This is a convenience method. An invocation of the form 
     * <code>ref.getTargetNodes()</code> returns the same array as the 
     * invocation 
     * <code>ref.getStoreConnection().getNodesByLinkAlias(ref.getTarget())<code>.
     * </p>
     *
     * @return  the referenced nodes sorted by alias name, or an empty array
     * @throws DocmaException  if the referenced nodes cannot be retrieved, 
     *                         for example due to a connection error
     * @see #getTarget()
     * @see #setTarget(String)
     */
    Node[] getTargetNodes() throws DocmaException;

    /**
     * Returns whether the node is an include-reference.
     */
    boolean isIncludeReference();
    
    /**
     * Returns whether the node is an image-inclusion.
     */
    // public boolean isImageIncludeReference();
    
    /**
     * Returns whether the node is a content-inclusion.
     */
    boolean isContentIncludeReference();
    
    /**
     * Returns whether the node is a section-inclusion.
     */
    boolean isSectionIncludeReference();
    
}
