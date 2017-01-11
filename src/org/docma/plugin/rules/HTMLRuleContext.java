/*
 * HTMLRuleContext.java
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
package org.docma.plugin.rules;

import org.docma.plugin.StoreConnection;

/**
 *
 * @author MP
 */
public interface HTMLRuleContext 
{
    /**
     * Returns the identifier of the node to which the rule is applied.
     * Generally the identified node is of type 
     * {@link org.docma.plugin.PubContent} and the MIME type of the content
     * is <code>"text/html"</code> or <code>"application/xhtml+xml"</code>.
     * 
     * @return  the id of the node to which the rule is applied
     */
    String getNodeId();

    /**
     * 
     * @param checkId
     * @return 
     */
    boolean isEnabled(String checkId);
    
    /**
     * 
     * @param checkId
     * @return 
     */
    boolean isAutoCorrect(String checkId);

    /**
     * 
     * @param checkId
     * @param msg
     * @param args 
     */
    void log(String checkId, String msg, Object... args);
    
    /**
     * 
     * @param checkId
     * @param contentPosition
     * @param msg
     * @param args 
     */
    void log(String checkId, int contentPosition, String msg, Object... args);
    
    /**
     * Returns the current store connection.
     * 
     * @return  the current store connection
     */
    StoreConnection getStoreConnection();
    
}
