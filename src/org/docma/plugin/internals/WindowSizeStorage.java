/*
 * WindowSizeStorage.java
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

import org.docma.plugin.web.WebUserSession;

/**
 * This interface encapsulates the functionality to store the size of
 * a browser window (for example, the size of the content editor).
 * For example, if a window is opened subsequently several times, then an 
 * implementation of this interface allows to set the initial size of a newly 
 * opened window to the size of the previously closed window. 
 *
 * @author MP
 */
public interface WindowSizeStorage 
{
    String getWindowWidth(WebUserSession webSess);
    String getWindowHeight(WebUserSession webSess);
    void setWindowSize(WebUserSession webSess, String win_width, String win_height);
}
