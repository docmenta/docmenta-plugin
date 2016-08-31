/*
 * Group.java
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
 * The <code>Group</code> interface represents nodes, that can act as a 
 * container for other nodes, the so called  "child nodes".
 * In other words, a <code>Group</code> node is a node that can act as a
 * parent node for other nodes (see the {@link Node#getParent() method}).
 *
 * <p>A <code>Group</code> object can itself be a child node of another 
 * <code>Group</code> object, forming a tree-structure of nodes.</p>
 *
 * @author MP
 */
public interface Group extends Node 
{

    /**
     * Returns whether this node is an ancestor of the node identified by 
     * <code>node_id</code>.
     *
     * @param node_id  the identifier of another node
     * @return  <code>true</code> if this node is an ancestor of the node 
     *          identified by <code>node_id</code>, otherwise 
     *          <code>false</code>
     * @throws DocmaException  if the return value cannot be retrieved, 
     *                         for example due to a connection error
     */
    boolean isAncestorOf(String node_id) throws DocmaException;

    /**
     * Returns the child node at the given position. The <code>index</code>
     * value has to be equal or greater than 0, and less than the value   
     * returned by {@link #getChildCount()}.
     *
     * <p>Be aware that every invocation of this method may return a new 
     * instance, even if an instance for the same node has already been  
     * returned in a previous invocation.
     * Therefore, to compare nodes for equality, the <code>equals()</code>  
     * method has to be used instead of the <code>==</code> operator.</p>
     * 
     * @param index  the position of the child node, starting with index 0 for 
     *               the first child
     * @return  the child node at the given position
     * @throws OutOfRangeException  if <code>index</code> is out of range
     * @throws DocmaException  if the child node cannot be retrieved for some 
     *                         other reason, for example due to a connection 
     *                         error
     */
    Node getChild(int index) throws DocmaException;

    /**
     * Returns all child nodes.
     * Note that the returned array is just a snapshot of the child-list.
     * That means, assignments to the returned array have no impact on the
     * child-membership of this node (unless the modified array is  
     * assigned back to this node by supplying it as argument 
     * to the <code>addChildren</code> or <code>insertChildren</code> 
     * method).
     *
     * @return all child nodes, or an empty array if no child node exists
     * @throws DocmaException  if the child nodes cannot be retrieved, 
     *                         for example due to a connection error
     */
    Node[] getChildren() throws DocmaException;
    
    /**
     * Returns the child node identified by the given alias.
     * If no child with the given alias exists, then <code>null</code> is 
     * returned. This method only searches for 1st-level children 
     * (in other words, children of children are not included in the search).
     *
     * <p>Be aware that every invocation of this method may return a new node
     * instance, even if an instance for the same node has already been  
     * returned in a previous invocation.
     * Therefore, to compare nodes for equality, the <code>equals()</code>  
     * method has to be used instead of the <code>==</code> operator.</p>
     *
     * @param alias  the alias to search for
     * @return  the child node with the given alias, or <code>null</code>
     * @throws DocmaException  if the search failed, 
     *                         for example due to a connection error
     */
    Node getChildByAlias(String alias) throws DocmaException;

    /**
     * Returns the child identified by the given node id.
     * If no child with the given id exists, then <code>null</code> is 
     * returned. This method only searches for 1st-level children 
     * (in other words, children of children are not included in the search).
     *
     * <p>Be aware that every invocation of this method may return a new node
     * instance, even if an instance for the same node has already been  
     * returned in a previous invocation.
     * Therefore, to compare nodes for equality, the <code>equals()</code>  
     * method has to be used instead of the <code>==</code> operator.</p>
     *
     * @param node_id  the node identifier to search for
     * @return  the child node identified by <code>node_id</code>, or 
     *          <code>null</code>
     * @throws DocmaException  if the search failed, 
     *                         for example due to a connection error
     * @see Node#getId()
     */
    Node getChildById(String node_id) throws DocmaException;
    
    /**
     * Returns the number of child nodes.
     *
     * @return  the number of child nodes
     * @throws DocmaException  if the number of children cannot be retrieved, 
     *                         for example due to a connection error
     */
    int getChildCount() throws DocmaException;

    /**
     * Returns the index position of the given child node.
     * The numbering starts with 0 for the first child.
     * If the given child node is not a child of this node, then -1 is returned.
     * 
     * @param child  the child node to search for
     * @return  the position of the child, or -1 if the node is not a child 
     *          of this node
     * @throws DocmaException  if the position cannot be retrieved, 
     *                         for example due to a connection error
     */
    int getChildPos(Node child) throws DocmaException;

    /**
     * Removes the child-node at the given position. The <code>index</code>
     * argument has to be equal or greater than 0, and less than the value   
     * returned by {@link #getChildCount()}.
     *
     * <p>This method is has the same effect as the invocation  
     * <code>removeChildren(index, index)</code>.</p>
     *
     * <p><em>Translation-mode:</em><br>
     * Removing nodes, while the session is in translation-mode, is not  
     * allowed and causes an exception.</p>
     *
     * @param index  the position of the node to be removed
     * @throws OutOfRangeException  if <code>index</code> is out of range
     * @throws DocmaException  if removal of the node is not possible
     *                         (for example, due to access rights or a 
     *                         connection error)
     */
    void removeChild(int index) throws DocmaException;
    
    /**
     * Removes the given child node. If the <code>nd</code> argument is no child 
     * of this node, then the child-list is unmodified and <code>false</code>
     * is returned. Otherwise <code>true</code> is returned.
     *
     * <p><em>Translation-mode:</em><br>
     * Removing nodes, while the session is in translation-mode, is not  
     * allowed and causes an exception.</p>
     *
     * @param nd  the node to be removed
     * @return <code>true</code> if <code>nd</code> was a child of this node;
     *         <code>false</code> otherwise
     * @throws DocmaException  if removal of the node is not possible
     *                         (for example, due to access rights or a 
     *                         connection error)
     */
    boolean removeChild(Node nd) throws DocmaException;

    /**
     * Removes the child-nodes starting at position <code>firstIndex</code> to 
     * position <code>lastIndex</code> (inclusive).
     * The <code>firstIndex</code> and <code>lastIndex</code> arguments have 
     * to be equal or greater than 0, and less than the value   
     * returned by {@link #getChildCount()}. Furthermore,
     * <code>lastIndex</code> has to be equal or greater than 
     * <code>firstIndex</code>.
     *
     * <p><em>Translation-mode:</em><br>
     * Removing nodes, while the session is in translation-mode, is not  
     * allowed and causes an exception.</p>
     *
     * @param firstIndex  the position of the first node to be removed
     * @param lastIndex   the position of the last node to be removed
     * @throws OutOfRangeException  if <code>firstIndex</code> or 
     *                              <code>lastIndex</code> is out of range
     * @throws DocmaException  if removal of the nodes is not possible for some
     *                         other reason (for example, due to access rights  
     *                         or a connection error)
     */
    void removeChildren(int firstIndex, int lastIndex) throws DocmaException;

    /**
     * Adds the given nodes at the end of the child-list.
     * After an invocation of this method, the nodes in <code>nds</code>
     * are the last nodes in the child-list of this node with the 
     * ordering as given in the <code>nds</code> argument.
     *
     * <p>A node can have at most one parent. As a consequence, if a node  
     * <code>n</code> contained in <code>nds</code> is a child of another node    
     * <code>p</code>, then an invocation of this method automatically removes 
     * <code>n</code> from the child-list of <code>p</code>.</p>
     *
     * <p>If a node <code>n</code> contained in <code>nds</code> is   
     * already a child of this node, then <code>n</code> is just moved to the  
     * new position in the child-list.</p>
     *
     * <p><em>Translation-mode:</em><br>
     * Adding nodes, while the session is in translation-mode, is not  
     * allowed and causes an exception.</p>
     *
     * @param nds  the nodes to be added
     * @throws DocmaException  if adding the nodes is not possible 
     *                         (for example, due to access rights or a 
     *                         connection error)
     */
    void addChildren(Node... nds) throws DocmaException;

    /**
     * Inserts the given nodes at the specified position in the child-list. 
     * After insertion, the ordering of the inserted nodes in the child-list 
     * is the same as the ordering given by the <code>nds</code> argument.
     * 
     * <p>The <code>index</code> argument has to be in the range from 0 to 
     * the value returned by {@link #getChildCount()} (inclusive).</p>
     *
     * <p>A node can have at most one parent. As a consequence, if a node  
     * <code>n</code> contained in <code>nds</code> is a child of another node    
     * <code>p</code>, then an invocation of this method automatically removes 
     * <code>n</code> from the child-list of <code>p</code>.</p>
     *
     * <p>If a node <code>n</code> contained in <code>nds</code> is   
     * already a child of this node, then <code>n</code> just moves to the  
     * new position in the child-list.
     * In this case the <code>index</code> argument defines the insert  
     * position <em>before</em> insertion, not after insertion. This makes a
     * difference, because after insertion, the actual position of the first 
     * inserted node may be less than specified by the <code>index</code> 
     * argument. For example, assuming that <code>n</code> is already a child 
     * of <code>parent</code>, but at a lower position than
     * <code>index</code>, then after the invocation of 
     * <code>parent.insertChildren(index, n)</code>, the new position of node 
     * <code>n</code> is <code>index - 1</code> (in words: one less than 
     * <code>index</code>).</p>
     *
     * <p><em>Translation-mode:</em><br>
     * Inserting nodes, while the session is in translation-mode, is not  
     * allowed and causes an exception.</p>
     *
     * @param index  the insert position
     * @param nds  the nodes to be inserted
     * @throws OutOfRangeException  if <code>index</code> is out of range
     * @throws DocmaException  if insertion of the nodes is not possible for
     *                         some other reason (for example, due to access
     *                         rights or a connection error)
     */ 
    void insertChildren(int index, Node... nds) throws DocmaException;

    /**
     * Inserts the given nodes before <code>refNode</code> in the child-list. 
     * After insertion, the ordering of the inserted nodes in the child-list 
     * is the same as the ordering given by the <code>nds</code> argument.
     * 
     * <p>A node can have at most one parent. As a consequence, if a node  
     * <code>n</code> contained in <code>nds</code> is a child of another node    
     * <code>p</code>, then an invocation of this method automatically removes 
     * <code>n</code> from the child-list of <code>p</code>.</p>
     *
     * <p>If a node <code>n</code> contained in <code>nds</code> is   
     * already a child of this node, then <code>n</code> just moves to the  
     * new position in the child-list.</p>
     *
     * <p><em>Translation-mode:</em><br>
     * Inserting nodes, while the session is in translation-mode, is not  
     * allowed and causes an exception.</p>
     *
     * @param refNode  the node before which the <code>nds</code> nodes must 
     *                 be inserted
     * @param nds  the nodes to be inserted
     * @throws DocmaException  if <code>refNode</code> is not a child of
     *                         this node or the insertion of child nodes is not
     *                         possible (for example, due to access rights or
     *                         a connection error)
     */ 
    void insertChildren(Node refNode, Node... nds) throws DocmaException;

    /**
     * Deletes this node and all child-nodes recursively.
     * If this node has a parent node, then an invocation of this method 
     * automatically removes this node from the child-list of the parent.
     *
     * <p>After a node has been deleted, the node instance must no longer
     * be used. An invocation of a method on a deleted instance may cause 
     * an exception.</p>
     *
     * <p><em>Translation-mode:</em><br>
     * Deleting nodes, while the session is in translation-mode, is not  
     * allowed and causes an exception.</p>
     *
     * @throws DocmaException  if deletion of the nodes is not possible 
     *                         (for example, due to access rights or a 
     *                         connection error)
     */
    void deleteRecursive() throws DocmaException;

    //************************************************************
    //**************   Methods to think about   ******************  
    //************************************************************

    // public void deleteContentRecursive()

    /*
     * Returns the position of the first node that implements the 
     * given interface. If no such child node exists, then -1 is returned.
     * This is a convenience method to find child nodes of a given type 
     * without having to iterate over all children.
     *
     * <p>For example, the expression 
     * <code>grp.getFirstChildPos(org.docma.plugin.PubSection.class)</code>
     * returns the position of the first child node of <code>grp</code>, that
     * is a <code>PubSection</code> node.</p>
     *
     * @param cls   a reference to a Java interface  
     * @return  the position of the first child that implements 
     *          <code>cls</code>, or -1 if no such node exists
     */
    // int getFirstChildPos(Class cls);
    
    /*
     * Returns the position of the last node that implements the 
     * given interface. If no such child node exists, then -1 is returned.
     * This is a convenience method to find child nodes of a given type 
     * without having to iterate over all children.
     *
     * <p>For example, the expression 
     * <code>grp.getLastChildPos(org.docma.plugin.Content.class)</code>
     * returns the position of the last child node of <code>grp</code>, that
     * is a <code>Content</code> node.</p>
     *
     * @param cls   a reference to a Java interface  
     * @return  the position of the first child that implements 
     *          <code>cls</code>, or -1 if no such node exists
     */
    // int getLastChildPos(Class... cls);
    
    /*
     * Adds the given node as last child to the child-list.
     * If <code>nd</code> is already a child of this node, then <code>nd</code>
     * is moved to the last position of the child-list.
     *
     * <p>A node can have at most one parent. That means, if <code>nd</code> 
     * is a child of another node <code>p</code>, then an invocation  
     * of this method automatically removes <code>nd</code> from the child-list   
     * of <code>p</code>.</p>
     *
     * @param nd  the node to be added
     * @throws java.lang.Exception  if adding a child node is not allowed 
	 *                              (for example, due to missing rights)
     */ 
    // void addChild(Node nd) throws Exception;

    /*
     * Inserts the given node as a child-node at the given position. 
     * The index has to be in the range from 0 to getChildCount() (inclusive).
     * If the index is out of range or the node cannot be inserted due to other reasons 
     * (e.g. access rights), an exception is thrown.
     */ 
    // void insertChild(int index, Node newNode) throws Exception;
    
}
