/*
 * Style.java
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
public interface Style 
{
    /**
     * Returns the style identifier.
     * The style identifier has to start with a letter.
     * Following characters can be letters, digits or underscore (_).
     * If the style identifier includes a dash character (-), then the part
     * before the dash character is called the <em>base-identifier</em>.  
     * The part after the dash character is called the <em>variant name</em>.
     * 
     * @return  the style identifier
     * @see #getBaseId() 
     * @see #getVariantName() 
     */
    String getId();

    /**
     * Returns the style's base-identifier.
     * If the style identifier does not contain a dash character (-), then
     * this method returns the same value as the {@link #getId() } method.
     * 
     * @return  the base identifier.
     */    
    String getBaseId();

    /**
     * The variant name.
     * 
     * @return  the variant name
     */    
    String getVariantName();
    
    /**
     * Returns the style title. 
     * 
     * @return  a descriptive style name
     */
    String getTitle();
    
    /**
     * Sets the style title.
     * 
     * @param styleTitle  a descriptive style name
     */
    void setTitle(String styleTitle);
    
    boolean isInlineStyle();
    boolean isBlockStyle();
    boolean isInternalStyle();
    boolean isVariant();
    
    boolean isHidden();
    void setHidden(boolean hidden);
    
    String getCSS();
    void setCSS(String styleCSS);

    int countAutoFormatCalls();
    AutoFormatCall getAutoFormatCall(int idx);
    void addAutoFormatCall(String clsname, String args);
    void addAutoFormatCall(int pos, String clsname, String args);
    void removeAutoFormatCall(int pos);
    void clearAutoFormatCalls();
}
