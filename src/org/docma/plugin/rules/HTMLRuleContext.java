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

import org.docma.plugin.UserSession;

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
     * Returns the logger to be used for writing log messages.
     * 
     * @return  the logger instance
     */    
    HTMLRuleLogger getLogger();
    
    /**
     * Returns the current user session.
     * 
     * @return  the session of the current user
     */
    UserSession getUserSession();
    
}
