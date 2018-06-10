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
    public static final String PROP_URL_IFRAME = "url_iframe";


    public String getIFrameURL(WebUserSession webSess, String nodeId) 
    {
        if (props == null) {
            return null;
        }
        String url = props.getProperty(PROP_URL_IFRAME, "").trim();
        if (url.equals("")) {
            return null;  
        } else {
           url = replaceURLPlaceholders(url, webSess.getSessionId(), nodeId);
           return webSess.encodeURL(getRelativeAppURL() + url);
        }
    }

    public String prepareContentForEdit(String content, WebUserSession webSess)
    {
        return content;
    }
    
    public String prepareContentForSave(String content, WebUserSession webSess)
    {
        return content;
    }
}
