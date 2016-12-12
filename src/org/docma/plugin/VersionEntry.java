/*
 * VersionEntry.java
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

import java.util.Date;

/**
 *
 * @author MP
 */
public interface VersionEntry 
{
    String getStoreId();
    VersionId getVersionId();
    
    String getVersionProperty(String name);
    void setVersionProperty(String name, String value) throws DocmaException;
    void setVersionProperties(String[] names, String[] values) throws DocmaException;
    String getVersionComment();
    void setVersionComment(String comment);
    Date getVersionCreationDate();
    Date getVersionLastModifiedDate();
    Date getVersionReleaseDate();
    String getVersionState(String lang);
    void setVersionState(String lang, String newState);
}
