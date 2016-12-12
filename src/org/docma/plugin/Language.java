/*
 * Language.java
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
 *
 * @author MP
 */
public interface Language 
{
    /**
     * The language code.
     * 
     * @return lowercase two-letter ISO-639 code
     */
    String getCode();
    
    /**
     * Returns a name for the language that is appropriate for display 
     * to the user.
     * 
     * @return human readable language name
     */
    String getDisplayName();

}
