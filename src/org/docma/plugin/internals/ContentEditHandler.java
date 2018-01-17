/*
 * ContentEditHandler.java
 * 
 *  Copyright (C) 2018  Manfred Paula, http://www.docmenta.org
 *   
 *  This file is part of Docmenta. Docmenta is free software: you can 
 *  redistribute it and/or modify it under the terms of the GNU Lesser 
 *  General Public License as published by the Free Software Foundation, 
 *  either version 3 of the License, or (at your option) any later version.
 *
 *  You should have received a copy of the GNU Lesser General Public License
 *  along with Docmenta.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.docma.plugin.internals;

import org.docma.plugin.web.DefaultContentAppHandler;
import org.docma.plugin.web.WebUserSession;

/**
 *
 * @author MP
 */
public class ContentEditHandler extends DefaultContentAppHandler implements EmbeddedContentEditor
{

    public String getIFrameURL(WebUserSession webSess, String nodeId) 
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
