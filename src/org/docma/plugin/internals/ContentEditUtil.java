/*
 * ContentEditUtil.java
 * 
 *  Copyright (C) 2018  Manfred Paula, http://www.docmenta.org
 *   
 *  This file is part of Docmenta. Docmenta is free software: you can 
 *  redistribute it and/or modify it under the terms of the GNU Lesser 
 *  General Public License as published by the Free Software Foundation, 
 *  either version 3 of the License, or (at your option) any later version.
 *
 *  You should have received a copy of the GNU Lesser General Public License
 *  along with Docmenta.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.docma.plugin.internals;

/**
 *
 * @author MP
 */
public class ContentEditUtil 
{

    public static boolean contentIsEqual(String content1, String content2, boolean strict_compare)
    {
        if (strict_compare) {
            if (content1 == null) {
                return (content2 == null);
            } else {
                return content1.equals(content2);
            }
        } else {
            if (content1 == null) {
                return (content2 == null) || content2.trim().equals("");
            }
            if (content2 == null) {
                content2 = "";
            }
            // Ignore all whitespace (e.g. after closing paragraph or at end of content). 
            String c1 = content1.replace("&#160;", "").replace(" ", "").replace("\n", "").replace("\r", "");
            String c2 = content2.replace("&#160;", "").replace(" ", "").replace("\n", "").replace("\r", "");
            return c1.equals(c2);
        }
    }

}
