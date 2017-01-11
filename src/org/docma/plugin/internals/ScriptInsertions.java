/*
 * ScriptInsertions.java
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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import org.docma.plugin.web.WebUserSession;

/**
 * An helper class that can be used by a file editor/viewer implementation that 
 * has to provide the possibility for plug-ins to insert code/scripts at defined 
 * insert positions. The inserted code/script can depend on the file extension
 * and user settings. The plug-in has to implement the {@link ScriptInsertion}
 * interface.
 *
 * @author MP
 * @see ScriptInsertion
 */
public class ScriptInsertions
{
    // Map feature to list of plugin script insertions
    private final SortedMap<String, List<ScriptInsertion>> insertions = new TreeMap();

    public ScriptInsertions()
    {
    }
    
    public String getInsertion(WebUserSession userSess, String ext, String posId)
    {
        // Map feature to ScriptInsertion
        SortedMap<String, ScriptInsertion> resultMap = new TreeMap();

        // Iterate over all features
        for (String feature : insertions.keySet()) {
            // If a plugin has inserted a script for the extension ext, then
            // this script needs to be included in the result. If no script
            // has been inserted for a feature x and the extension ext, but a 
            // script has been inserted for the feature x and the wildcard
            // extension "*", then this script is included in the result too. 
            // The wildcard extension "*" allows to insert scripts that 
            // are valid for all extensions.
            // If more than one plugin inserts a script for the same feature
            // and extension, then the script of the last loaded plugin is used.
            List<ScriptInsertion> insList = insertions.get(feature);
            for (ScriptInsertion ins : insList) {
                Set<String> exts = ins.getFileExtensions(userSess);
                if (containsExt(exts, ext) || 
                    (containsExt(exts, "*") && !resultMap.containsKey(feature))) {
                    resultMap.put(feature, ins);
                }
            }
        }
        
        // Concatenate the scripts of all features
        StringBuilder sb = new StringBuilder();
        for (ScriptInsertion ins : resultMap.values()) {
            if (sb.length() > 0) {
                sb.append("\n");
            }
            sb.append(ins.getInsertion(userSess, ext, posId));
        }
        return sb.toString();
    }
    
    public void addInsertion(ScriptInsertion ins)
    {
        String feature = ins.getFeature();
        List<ScriptInsertion> insList = insertions.get(feature);
        if (insList == null) {
            insList = new ArrayList<ScriptInsertion>();
            insertions.put(feature, insList);
        } else {
            // Remove any previous insertion from the same plugin
            removeInsertionFromList(ins.getPluginId(), insList);
        }
        insList.add(ins);
    }
    
    public void clearPluginInsertions(String pluginId)
    {
        // Iterate over all features
        for (List<ScriptInsertion> insList : insertions.values()) {
            removeInsertionFromList(pluginId, insList);
        }
    }
    
    /* -------------- Private Methods ---------------- */

    private boolean containsExt(Set<String> exts, String ext)
    {
        for (String e : exts) {
            if (e.equalsIgnoreCase(ext)) {
                return true;
            }
        }
        return false;
    }
    
    private void removeInsertionFromList(String pluginId, List<ScriptInsertion> list) 
    {
        Iterator<ScriptInsertion> it = list.iterator();
        while (it.hasNext()) {
            ScriptInsertion ins = it.next();
            if (pluginId.equals(ins.getPluginId())) {
                it.remove();
                break;
            }
        }
    }

}
