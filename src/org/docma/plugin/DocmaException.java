/*
 * DocmaException.java
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
 * The base class for all exceptions in the Docmenta Plug-In API.
 * 
 * @author MP
 */
public class DocmaException extends RuntimeException 
{

    /**
     * Creates a new instance of <code>DocmaException</code> without detail
     * message.
     */
    public DocmaException() 
    {
    }

    /**
     * Constructs an instance of <code>DocmaException</code> with the specified
     * detail message.
     *
     * @param msg the detail message.
     */
    public DocmaException(String msg) 
    {
        super(msg);
    }

    /**
     * Constructs an instance of <code>DocmaException</code> with the specified 
     * cause.
     * 
     * @param cause the cause.
     */
    public DocmaException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructs an instance of <code>DocmaException</code> with the specified 
     * detail message and cause.
     * 
     * @param msg the detail message.
     * @param cause the cause.
     */
    public DocmaException(String msg, Throwable cause) {
        super(msg, cause);
    }

}
