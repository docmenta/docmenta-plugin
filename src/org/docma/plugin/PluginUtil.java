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
import java.text.MessageFormat;
import org.docma.plugin.internals.LabelUtil;

/**
 * This class provides utility methods to be used by Docmenta plug-ins.
 *
 * @author MP
 */
public class PluginUtil
{
    private static final Map<String, Properties> resourceMap = new Hashtable();  // use Hashtable because it is synchronized!
    private static final Map<String, MessageFormat> msgFormatMap = new Hashtable();

    /**
     * Private constructor to avoid the creation of instances. 
     * This class shall only provide static utility methods.
     */
    private PluginUtil()
    {
    }

    /**
     * Returns the label for the given <code>locale</code> and <code>key</code>.
     * If no localization for the given <code>key</code> exists, 
     * then <code>key</code> itself is returned.
     * 
     * @param locale  the locale for which to retrieve the label
     * @param key     the label identifier
     * @param args    the arguments to be inserted for placeholders
     * @return  the localized label 
     */
    public static String getLabel(Locale locale, String key, Object[] args) 
    {
        return LabelUtil.getLabel(locale, key, args);
    }
    
    /**
     * Returns the label for the given <code>language</code> and <code>key</code>.
     * If no localization for the given <code>key</code> exists, 
     * then <code>key</code> itself is returned.
     * 
     * @param languageCode  the ISO 639 language code 
     * @param key     the label identifier
     * @param args    the arguments to be inserted for placeholders
     * @return  the localized label 
     */
    public static String getLabel(String languageCode, String key, Object[] args) 
    {
        return LabelUtil.getLabel(languageCode, key, args);
    }

    public static String getResourceString(Class cls, String languageCode, String name)
    {
        return getResourceString(cls, languageCode, name, null);
    }

    /**
     * Tries to find an XML properties file for the given class and language and
     * returns the specified property value. This utility method can be used to
     * load localized strings for a given class from an XML properties file.
     * <p>
     * The path of the properties file is derived from the arguments
     * <tt>cls</tt> and <tt>languageCode</tt> by following Java expression:
     * </p>
     * <pre>
     *   cls.getName().replace('.', '/') + "_" + languageCode + ".xml";
     * </pre>
     * <p>
     * For example, if the class-name is <tt>"org.docma.example.MyClass"</tt> and
     * the language code is <tt>"en"</tt>, then
     * the derived path is <tt>"org/docma/example/MyClass_en.xml"</tt>.
     * Following an example of a XML properties file which contains two 
     * properties with names <tt>name1</tt> and <tt>name2</tt>:
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
     * <p>
     * The properties file is loaded as resource stream using
     * the class loader of the <tt>cls</tt> argument, as follows:
     * </p>
     * <pre>
     *   cls.getClassLoader().getResourceAsStream(path);
     * </pre>
     * <p>
     * If a properties file does not exist for the given language code, then
     * the language code <tt>"en"</tt> is used as fall-back.
     * </p>
     *
     * @param cls The class for which the properties shall be loaded.
     * @param languageCode The language code.
     * @param name The property name.
     * @param args The placeholder values or <code>null</code>.
     * @return The property value or an empty string if the property is not found.
     */
    public static String getResourceString(Class cls, String languageCode, String name, Object[] args)
    {
        languageCode = (languageCode == null) ? "en" : languageCode.toLowerCase();
        
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
            return getResourceString(cls, "en", name, args);
        }
        
        String val = (props != null) ? props.getProperty(name, "") : "";
        if ((args != null) && (args.length > 0)) {
            String key = languageCode + "::" + val;
            MessageFormat mf = msgFormatMap.get(key);
            if (mf == null) {
                mf = new MessageFormat(val, new Locale(languageCode));
                msgFormatMap.put(key, mf);
            }
            val = mf.format(args);
        }
        return val;
    }


}
