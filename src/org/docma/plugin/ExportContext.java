/*
 * ExportContext.java
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

/**
 * Provides information about the export context.
 * An instance of this class is passed as argument to the method
 * {@link org.docma.plugin.AutoFormat#initialize(org.docma.plugin.ExportContext) AutoFormat.initialize}.
 * The Auto-Format class can use this instance to access the context of an
 * export. This includes
 * <ul>
 *   <li>reading the content of a node within the product tree</li>
 *   <li>getting the user interface language</li>
 *   <li>getting the export language and format</li>
 *   <li>getting a GenText property value</li>
 *   <li>decoding/encoding character entities</li>
 *   <li>writing to the export log</li>
 * </ul>
 * See the
 * <a href="doc-files/autoformat_overview.html">Auto-Format - Overview and Example</a>
 * page for an example showing the usage of this interface.
 *
 * @author MP
 */
public interface ExportContext
{
    /**
     * Returns the Graphical User Interface (GUI) language that the user
     * who has started the export has currently selected.
     * The language code is a two-letter string as defined by the standard ISO-639.
     *
     * <p>
     * This method can be used by the Auto-Format class to determine the language of the
     * error and warning messages to be written to the export log.
     * Be aware that this is <em>not</em> the
     * content language of the exported publication. To retrieve the
     * content language of the exported publication, use method
     * {@link #getExportLanguage() getExportLanguage}.
     * </p>
     *
     * @return The user interface language.
     */
    String getGUILanguage();

    /**
     * Returns the content of a given node as a string.
     * The node can be any content-node within the product tree, that has
     * an alias name assigned. Inline inclusions within the content are <em>not</em>
     * resolved. This method returns the same result as
     * {@link #getContentStringByAlias(java.lang.String, boolean) getContentStringByAlias(nodeAlias, false)}.
     *
     * @param nodeAlias The alias name of the node.
     * @return The content of the node.
     */
    String getContentStringByAlias(String nodeAlias);

    /**
     * Returns the content of a given node as a string.
     * The node can be any content-node within the product tree, that has
     * an alias name assigned. 
     * If the argument <i>resolveInclusions</i> is <tt>true</tt>, then inline inclusions
     * within the content are resolved. Otherwise inline inclusions are not resolved.
     * <p>
     * See the <a href="doc-files/autoformat_overview.html">Auto-Format - Overview and Example</a>
     * page for an example application of this method.
     * </p>
     * 
     * @param nodeAlias The alias name of the node.
     * @param resolveInclusions If <tt>true</tt> inline inclusions are resolved.
     * @return The content of the node.
     */
    String getContentStringByAlias(String nodeAlias, boolean resolveInclusions);

    /**
     * Returns the content of a given node as a byte array.
     * The node can be any content-node within the product tree, that has
     * an alias name assigned.
     *
     * @param nodeAlias The alias name of the node.
     * @return The content of the node.
     */
    byte[] getContentBytesByAlias(String nodeAlias);

    /**
     * Returns the content language of the exported publication.
     * If a publication is exported in translation mode, then this is the
     * translation language.
     * The returned language code is a two-letter string as defined by the
     * standard ISO-639.
     *
     * @return The content language of the exported publication.
     */
    String getExportLanguage();

    /**
     * Returns the output format created by this export process.
     * Currently this can be any of the following values: "html", "pdf", "docbook".
     *
     * @return The output format created by this export process.
     */
    String getExportFormat();

    /**
     * <p>
     * Returns the sub-format of the exported publication. The possible values
     * depend on the output format returned by method {@link #getExportFormat() getExportFormat}.
     * Currently sub-formats are only defined for the output format
     * "html". The currently supported HTML sub-formats are
     * </p>
     * <table border="1">
     * <tr>
     * <th>Sub-Format</th>
     * <th>Value returned by this method</th>
     * </tr>
     * <tr>
     * <td>static HTML</td>
     * <td>"" <i>(empty string)</i></td>
     * </tr>
     * <tr>
     * <td>Web-Help</td>
     * <td>"<tt>webhelp</tt>"</td>
     * </tr>
     * <tr>
     * <td>EPUB (eBook format)</td>
     * <td>"<tt>epub</tt>"</td>
     * </tr>
     * </table>
     * <p>
     * Additional sub-formats may be supported in the future. Note that
     * sub-format versions may be introduced in the future. For example,
     * to distinguish version 2 and version 3 of the 
     * EPUB format, the sub-formats "epub2" and "epub3" may be introduced in 
     * the future.
     * Therefore future-proof implementations should use the java.lang.String
     * method {@code startsWith()} instead of {@code equals()}. For example,
     * to determine if the output format created by this export process is
     * EPUB, it is recommended to use the code {@code if (subformat.startsWith("epub"))}
     * instead of {@code if (subformat.equals("epub"))}.  
     * </p>
     *
     * @return The sub-format of the exported publication.
     */
    String getExportSubformat();

    /**
     * Returns a generated text (GenText) value. The returned value
     * is the localized generated text identified by
     * <i>propertyName</i>.
     * The localization language is the language
     * returned by the method {@link #getExportLanguage() getExportLanguage}.
     * <p>
     * For example, if the export language is English (i.e. the method
     * {@link #getExportLanguage() getExportLanguage} returns the string "en"),
     * then the code {@code getGenTextProperty("TableofContents")}
     * returns the English phrase for "Table of Contents", i.e.
     * "Table of Contents" or just "Contents" (or whatever is configured).
     * If the export language is German (i.e. the method
     * {@link #getExportLanguage() getExportLanguage} returns the string "de"),
     * then the code {@code getGenTextProperty("TableofContents")}
     * returns the German phrase, e.g. "Inhaltsverzeichnis".
     * </p>
     * <p>
     * Note: The GenText configuration is defined by a file with alias name "gentext".
     * By default this file is located in the system folder of the product tree.
     * </p>
     *
     * @param propertyName The name of the GenText property to be retrieved.
     * @return The localized value of the GenText property.
     */
    String getGenTextProperty(String propertyName);

    /**
     * Returns the character <i>ch</i> encoded as character entity or <tt>null</tt>
     * if an entity representation is not configured for this character. 
     * If the argument <i>symbolic</i> is <tt>true</tt>, then the symbolic entity notation
     * is returned (e.g. &amp;nbsp;). Otherwise the numeric entity
     * notation is returned (e.g. &amp;#160;).
     * 
     * @param ch The character to be encoded.
     * @param symbolic If <tt>true</tt> use symbolic entity encoding, otherwise use numeric encoding.
     * @return The character entity representation of <i>ch</i>.
     */
    String toCharEntity(char ch, boolean symbolic);

    /**
     * Decodes all numeric and symbolic character entities in the specified string.
     * If the string <i>str</i> does not contain any character entities, then
     * the returned string is equal to <i>str</i>. Note that only configured
     * character entities are decoded.
     *
     * @param str The string to be decoded.
     * @return The decoded string.
     */
    String decodeCharEntities(String str);

    /**
     * Writes an error message to the export log.
     *
     * @param message The error message to be written to the export log.
     */
    void logError(String message);

    /**
     * Writes a warning message to the export log.
     *
     * @param message The warning message to be written to the export log.
     */
    void logWarning(String message);
}
