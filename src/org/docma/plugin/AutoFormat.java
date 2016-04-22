/*
 * AutoFormat.java
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
 * This interface needs to be implemented, to create a
 * Docmenta <em>Auto-Format plug-in</em>.
 * A class that implements the {@code AutoFormat} interface is also called
 * <em>Auto-Format class</em> or <em>Auto-Format transformation</em>.
 * A Docmenta style that has an Auto-Format class assigned is called
 * <em>Auto-Format style</em>.
 * If content within a publication has an Auto-Format style assigned, then,
 * during publication export, this content is transformed by the
 * Auto-Format class that is assigned to the style.
 * For an introduction of the Auto-Format concept see the
 * <a href="doc-files/autoformat_overview.html">Auto-Format - Overview and Example</a>
 * page.
 * 
 * <p>
 * If an Auto-Format style is assigned to content within a publication, 
 * then an instance of the corresponding Auto-Format class
 * is created before the first content-element is processed during export.
 * The export process creates an instance of the Auto-Format class by calling 
 * its non-argument constructor. After creation of the Auto-Format instance,
 * the instance is initialized by calling its
 * {@link #initialize(org.docma.plugin.ExportContext) initialize} method.
 * During export, the method 
 * {@link #transform(org.docma.plugin.TransformationContext) transform}
 * of this instance is called for each content-element to be transformed
 * by this Auto-Format class.
 * </p>
 * <p>
 * Note that the same Auto-Format instance is used during the
 * complete export process of a publication.
 * For example, if <em>different</em> styles have the same
 * Auto-Format class assigned, then all content-elements that have one of these
 * styles assigned, are transformed by the same Auto-Format instance
 * (though each style may supply different Auto-Format arguments to the instance).
 * </p>
 * <p>
 * Each export process creates its own instances of Auto-Format
 * classes. Therefore, multiple parallel exports do not interfere.
 * Furthermore, within a single export process, the content-elements are
 * processed one after the other, as they appear in the publication content. 
 * Therefore, the implementations of the methods in the {@link org.docma.plugin.AutoFormat}
 * interface do <i>not</i> have to be thread-safe. Be aware however,
 * that multiple exports can run in parallel, i.e. if you are using static 
 * fields or methods, then these have to be thread-safe or access to these 
 * elements has to be synchronized.
 * </p>
 * <p>
 * The method {@link #finished() finished} is called by the export process,
 * when the export of a publication is finished. This method
 * can be used to release resources that have been allocated during the export process.
 * </p>
 *
 * <p>
 * The methods {@link #getShortInfo(java.lang.String) getShortInfo} and
 * {@link #getLongInfo(java.lang.String) getLongInfo} shall return human-readable
 * information about the usage of the Auto-Format class. This information is
 * displayed to the user of the Auto-Format Plug-in.
 * </p>
 *
 * @author MP
 */
public interface AutoFormat
{
    /**
     * This method is called by the export process before the
     * method {@link #transform(org.docma.plugin.TransformationContext) transform}
     * is called for the first time. This method can be used to allocate
     * resources required by the method
     * {@link #transform(org.docma.plugin.TransformationContext) transform}.
     * For example if the transformation uses XSL stylesheets, this method
     * can be used to initialize a XSLT processor.
     *
     * <p>
     * If access to the export context is required from within the method
     * {@link #transform(org.docma.plugin.TransformationContext) transform},
     * then the argument <i>ctx</i> can be assigned to a member field,
     * which is then used within the method
     * {@link #transform(org.docma.plugin.TransformationContext) transform}.
     * </p>
     *
     * <p>
     * See <a href="doc-files/autoformat_overview.html">Auto-Format - Overview and Example</a>
     * for an example implementation.
     * </p>
     *
     * @param ctx The export context.
     * @see #finished()
     */
    void   initialize(ExportContext ctx);

    /**
     * This method is called by the export process when the export is finished.
     * This method can be used to release any resources that have been allocated
     * within the methods
     * {@link #initialize(org.docma.plugin.ExportContext) initialize} and
     * {@link #transform(org.docma.plugin.TransformationContext) transform}
     * during the export process.
     *
     * <p>
     * See <a href="doc-files/autoformat_overview.html">Auto-Format - Overview and Example</a>
     * for an example implementation.
     * </p>
     *
     * @see #initialize(org.docma.plugin.ExportContext)
     */
    void   finished();

    /**
     * This method gets a XML content-element as input and returns some replacement content.
     * This method is called for each content-element that has to be
     * processed by the AutoFormat instance during publication export.
     * See <a href="doc-files/autoformat_overview.html">Auto-Format - Overview and Example</a>
     * for details on the Auto-Format processing and for an example implementation.
     * <p>
     * Use argument <i>ctx</i> to retrieve the transformation input and to write
     * the transformation output.
     * </p>
     * <p>
     * If this method throws an exception, then the transformation is skipped,
     * i.e. the content-element is exported without being transformed.
     * Furthermore the exception message is written as error to the export log.
     * </p>
     *
     * @param ctx The transformation context.
     * @throws Exception Exception should be thrown if the transformation cannot be applied.
     * @see <a href="doc-files/autoformat_overview.html">Auto-Format - Overview and Example</a>
     */
    void   transform(TransformationContext ctx) throws Exception;

    /**
     * Returns a short human-readable sentence describing the
     * purpose of this Auto-Format transformation.
     * This has to be plain text without markup.
     * The returned text is displayed to the user of the
     * Auto-Format Plug-in.
     * Be aware that the application is allowed to cut-off text that
     * exceeds the length of 80 characters.
     * <p>
     * The argument <i>languageCode</i> supplies the language code
     * of the user interface language that the user has currently selected.
     * The language code is a two-letter string as defined by the standard ISO-639.
     * The text which is returned by this method should
     * be in the language given by <i>languageCode</i>. If the text is
     * not available in this language, then the returned description shall be in
     * English (i.e. at least English has to be supported).
     * </p>
     * <p>
     * See <a href="doc-files/autoformat_overview.html">Auto-Format - Overview and Example</a>
     * for an example implementation.
     * </p>
     *
     * @param languageCode The language of the user interface.
     * @return Short sentence describing the purpose of this Auto-Format transformation.
     */
    String getShortInfo(String languageCode);

    /**
     * Returns a human-readable description of
     * this Auto-Format transformation. This shall be a complete usage reference,
     * including a description of all supported arguments.
     * This information is displayed to the user of the Auto-Format Plug-in.
     * To improve readability, the returned text can be formatted using basic
     * XHTML markup. Following XHTML elements are allowed:
     * <tt>b, i, tt, big, small, sup, sub, em, strong, samp, kbd, code, var,
     * cite, dfn, abbr, acronym, span, br, p, div, pre, blockquote,
     * dl, dt, dd, ol, ul, li, table, tr, td, th</tt>.
     * All other XHTML elements may be filtered out by the application before
     * the text is presented to the user.
     * <p>
     * The argument <i>languageCode</i> supplies the language code
     * of the user interface language that the user has currently selected.
     * The language code is a two-letter string as defined by the standard ISO-639.
     * The text which is returned by this method should
     * be in the language given by <i>languageCode</i>. If the text is
     * not available in this language, then the returned description shall be in
     * English (i.e. at least English has to be supported).
     * </p>
     * <p>
     * See <a href="doc-files/autoformat_overview.html">Auto-Format - Overview and Example</a>
     * for an example implementation.
     * </p>
     *
     * @param languageCode The language of the user interface.
     * @return A complete usage description of this Auto-Format transformation.
     */
    String getLongInfo(String languageCode);
}
