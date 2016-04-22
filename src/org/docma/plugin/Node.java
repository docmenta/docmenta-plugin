/*
 * Node.java
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

import java.util.Properties;

/**
 *
 * @author MP
 */
public interface Node 
{
    String getId();
    String getTitle();
    String getTitleEntityEncoded();
    
    String getTranslationMode();
    
    Lock getLock();
    boolean setLock();
    boolean refreshLock();
    Lock removeLock();

    byte[] getContent();
    String getContentString();
    void setContentString(String content) throws Exception;
    String prepareXHTML(String content, Properties props) throws Exception;
    
    boolean makeRevision();
    
    int getProgress();
    void setProgress(int value);
    
}
