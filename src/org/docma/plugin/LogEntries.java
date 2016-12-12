/*
 * LogEntries.java
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
public interface LogEntries 
{
    int getInfoCount();
    int getWarningCount();
    int getErrorCount();
    
    /**
     * Returns all log messages
     * 
     * @return  all log messages sorted by date
     */
    LogEntry[] getLog();
    
    /**
     * Returns all info messages
     * 
     * @return  all log messages of type "info", sorted by date
     */
    LogEntry[] getInfos();
    
    /**
     * Returns all warnings
     * 
     * @return  all log messages of type "warning", sorted by date
     */
    LogEntry[] getWarnings();
    
    /**
     * Returns all errors
     * 
     * @return  all log messages of type "error", sorted by date
     */
    LogEntry[] getErrors();

}
