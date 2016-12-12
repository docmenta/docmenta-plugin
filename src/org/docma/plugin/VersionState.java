/*
 * VersionState.java
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
public class VersionState 
{
    public static final VersionState DRAFT = new VersionState("DRAFT");
    public static final VersionState RELEASED = new VersionState("RELEASED");
    public static final VersionState TRANSLATION_PENDING = new VersionState("TRANSLATION_PENDING");

    private final String version_state;

    VersionState(String version_state) 
    {
        this.version_state = version_state;
    }

    @Override
    public int hashCode() 
    {
        return (this.version_state != null) ? this.version_state.hashCode() : 0;
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
        final VersionState other = (VersionState) obj;
        if ((this.version_state == null) ? (other.version_state != null) 
                                         : !this.version_state.equals(other.version_state)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() 
    {
        return (version_state == null) ? "null" : version_state;
    }

}
