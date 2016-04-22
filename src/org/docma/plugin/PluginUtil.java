/*
 * PluginUtil.java
 * 
 *  Copyright (C) 2013  Manfred Paula, http://www.docmenta.org
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

import java.util.*;
import java.io.*;

/**
 * This class provides utility methods to be used by Docmenta plug-ins.
 *
 * @author MP
 */
public class PluginUtil
{
    private static Map<String, Properties> resourceMap = new Hashtable();  // use Hashtable because it is synchronized!

    /**
     * Private constructor to avoid the creation of instances. 
     * This class shall only provide static utility methods.
     */
    private PluginUtil()
    {
    }

    /**
     * Tries to find a XML properties file for the given class and language and
     * returns the specified property value. This utility method can be used to
     * load localized strings for a given class from a XML properties file.
     * <p>
     * The path of the properties file is derived from 
     * <tt>cls</tt> and <tt>languageCode</tt> as follows:
     * </p>
     * <pre>
     *   path = cls.getName().replace('.', '/') + "_" + languageCode + ".xml";
     * </pre>
     * <p>
     * For example, if the class-name is <tt>"org.docma.example.MyClass"</tt> and
     * the language code is <tt>"en"</tt>, then
     * the derived path is <tt>"org/docma/example/MyClass_en.xml"</tt>.
     * The properties file is then loaded as resource stream using
     * the <tt>ClassLoader</tt> of <tt>cls</tt>, i.e.:
     * </p>
     * <pre>
     *   cls.getClassLoader().getResourceAsStream(path);
     * </pre>
     * <p>
     * If a properties file does not exist for the given language code, then
     * the language code <tt>"en"</tt> is used as fall-back.
     * </p>
     * <p>
     * Following an example of a XML properties file which contains two properties
     * with names <tt>name1</tt> and <tt>name2</tt>:
     * </p>
     * <pre>
     *     &lt;?xml version="1.0" encoding="UTF-8"?&gt;
     *     &lt;!DOCTYPE properties SYSTEM "http://java.sun.com/dtd/properties.dtd"&gt;
     *     &lt;properties&gt;
     *        &lt;entry key="name1">value 1&lt;/entry&gt;
     *        &lt;entry key="name2">value 2&lt;/entry&gt;
     *     &lt;/properties&gt;
     * </pre>
     * <p>
     * For more information on the properties XML-format, see the
     * documentation of the {@link java.util.Properties java.util.Properties} class.
     * </p>
     *
     * @param cls The class for which the properties shall be loaded.
     * @param languageCode The language code.
     * @param name The property name.
     * @return The property value or an empty string if the property is not found.
     */
    public static String getResourceString(Class cls, String languageCode, String name)
    {
        String res_name = cls.getName().replace('.', '/') + "_" + languageCode + ".xml";
        Properties props = resourceMap.get(res_name);
        if (props == null) {
            try {
                // System.out.print("Try loading resource: " + res_name);
                InputStream in = cls.getClassLoader().getResourceAsStream(res_name);
                if (in != null) {
                    Properties p = new Properties();
                    p.loadFromXML(in);
                    // System.out.println(" ... okay.");
                    props = p;
                    resourceMap.put(res_name, props);
                } else {
                    // System.out.println(" ... not found!");
                }
            } catch (Exception ex) {
                // ex.printStackTrace();
            }
        }
        if ((props == null) && !languageCode.equals("en")) {
            return getResourceString(cls, "en", name);
        }
        return (props != null) ? props.getProperty(name, "") : "";
    }


}
