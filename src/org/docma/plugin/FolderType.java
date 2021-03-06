/*
 * FolderType.java
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
public class FolderType 
{
    public static final FolderType GENERAL = new FolderType("GENERAL_FOLDER");
    public static final FolderType IMAGE = new FolderType("IMAGE_FOLDER");
    
    private final String folder_type;

    FolderType(String folder_type) 
    {
        this.folder_type = folder_type;
    }
    
    @Override
    public int hashCode() 
    {
        return (this.folder_type != null) ? this.folder_type.hashCode() : 0;
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
        final FolderType other = (FolderType) obj;
        if ((this.folder_type == null) ? (other.folder_type != null) 
                                       : !this.folder_type.equals(other.folder_type)) {
            return false;
        }
        return true;
    }
    
    @Override
    public String toString() 
    {
        return (folder_type == null) ? "null" : folder_type;
    }
    
}
