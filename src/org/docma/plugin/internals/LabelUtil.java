/*
 * LabelUtil.java
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
package org.docma.plugin.internals;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 *
 * @author MP
 */
public class LabelUtil 
{
    private static LabelProvider labelProvider = null;
    
    // The localeCache field maps language codes to locale instances
    private static final Map<String, Locale> localeCache = new HashMap<String, Locale>(500);
    
    static {
        for (Locale loc : Locale.getAvailableLocales()) {
            localeCache.put(loc.getLanguage(), loc);
        }
    }
    
    /**
     * Private constructor to avoid the creation of instances. 
     * This class only provides static utility methods.
     */
    private LabelUtil() 
    {
    }
    
    public static LabelProvider getLabelProvider() 
    {
        return labelProvider;
    }
    
    public static void setLabelProvider(LabelProvider provider)
    {
        labelProvider = provider;
    }
    
    public static String getLabel(Locale locale, String key, Object[] args) 
    {
        if (labelProvider == null) {
            return key;
        } else {
            return labelProvider.getLabel(locale, key, args);
        }
    }
    
    public static String getLabel(String languageCode, String key, Object[] args) 
    {
        Locale loc = localeCache.get(languageCode);
        if (loc == null) {
            loc = new Locale(languageCode);
            localeCache.put(languageCode, loc);
        }
        return getLabel(loc, key, args);
    }
}
