/*
 * ButtonType.java
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

/**
 *
 * @author MP
 */
public final class ButtonType 
{
    public static final ButtonType ABORT = new ButtonType("ABORT");
    public static final ButtonType CANCEL = new ButtonType("CANCEL");
    public static final ButtonType CLOSE = new ButtonType("CLOSE");
    public static final ButtonType IGNORE = new ButtonType("IGNORE");
    public static final ButtonType NO = new ButtonType("NO");
    public static final ButtonType OK = new ButtonType("OK");
    public static final ButtonType RETRY = new ButtonType("RETRY"); 
    public static final ButtonType YES = new ButtonType("YES");

    private final String button_type;
    
    ButtonType(String btn_type)
    {
        this.button_type = btn_type.toUpperCase();
    }

    @Override
    public int hashCode() 
    {
        int hash = 5;
        hash = 17 * hash + (this.button_type != null ? this.button_type.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) 
    {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ButtonType other = (ButtonType) obj;
        if ((this.button_type == null) ? (other.button_type != null) : 
                                         !this.button_type.equals(other.button_type)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() 
    {
        return (button_type == null) ? "null" : button_type;
    }
    

}
