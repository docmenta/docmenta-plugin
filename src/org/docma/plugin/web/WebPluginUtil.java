/*
 * WebPluginUtil.java
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
package org.docma.plugin.web;

import org.docma.plugin.internals.WebAppPlugInterface;
import javax.servlet.ServletContext;


/**
 *
 * @author MP
 */
public class WebPluginUtil 
{
    private static final String WEBAPP_CLASS_NAME = "org.docma.webapp.DocmaWebApplication";
    
    /**
     * Private constructor to avoid the creation of instances. 
     * This class shall only provide static utility methods.
     */
    private WebPluginUtil()
    {
    }

    public static WebUserSession getUserSession(ServletContext ctx, String sessId) 
    {
        WebAppPlugInterface webapp = (WebAppPlugInterface) ctx.getAttribute(WEBAPP_CLASS_NAME);
        return (webapp == null) ? null : webapp.getWebSessionPluginInterface(sessId);
    }
            
}
