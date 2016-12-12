/*
 * Publication.java
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

import java.io.InputStream;
import java.util.Date;

/**
 *
 * @author MP
 */
public interface Publication 
{
    String getId();
    
    String getFilename() throws DocmaException;
    void setFilename(String fn) throws DocmaException;
    String getLanguageCode() throws DocmaException;
    String getTitle() throws DocmaException;
    String getFormat() throws DocmaException;
    String getSubformat() throws DocmaException;
    String getPublicationState() throws DocmaException;
    String getPublicationConfigId() throws DocmaException;
    String getOutputConfigId() throws DocmaException;
    VersionId getVersionId() throws DocmaException;
    Date getReleaseDate() throws DocmaException;
    Date getExportDate() throws DocmaException;
    String getExportedByUser() throws DocmaException;
    String getComment() throws DocmaException;
    long getContentSize() throws DocmaException;
    InputStream getContentStream() throws DocmaException;
    LogEntries getExportLog() throws DocmaException;
    boolean isExportFinished() throws DocmaException;
    String getExportProgressMessage() throws DocmaException;
    int getErrorCount() throws DocmaException;
    int getWarningCount() throws DocmaException;
    boolean hasError() throws DocmaException;
    boolean hasWarning() throws DocmaException;
    boolean isOnline() throws DocmaException;
    void setOnline(boolean is_online) throws DocmaException;
}
