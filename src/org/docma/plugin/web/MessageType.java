/*
 * MessageType.java
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
public class MessageType 
{
    public static final MessageType QUESTION = new MessageType("QUESTION");
    public static final MessageType WARNING = new MessageType("WARNING");
    public static final MessageType ERROR = new MessageType("ERROR");
    public static final MessageType INFO = new MessageType("INFO");
    
    private final String message_type;
    
    MessageType(String msg_type)
    {
        this.message_type = msg_type.toUpperCase();
    }

    @Override
    public int hashCode() 
    {
        return (this.message_type != null) ? this.message_type.hashCode() : 0;
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
        final MessageType other = (MessageType) obj;
        if ((this.message_type == null) ? (other.message_type != null) 
                                        : !this.message_type.equals(other.message_type)) {
            return false;
        }
        return true;
    }
    
}
