/*
 * HTMLRule.java
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
package org.docma.plugin.rules;

import org.docma.plugin.LogLevel;

/**
 * This interface defines the functionality of a Docmenta <em>HTML rule</em>. 
 * To create a new HTML rule, a new class that implements this interface
 * has to be created.
 * <p>
 * For the main functionality of an HTML rule, see the 
 * {@link #apply(java.lang.String, boolean, HTMLRuleContext) } method.
 * </p>
 *
 * @author MP
 */
public interface HTMLRule 
{
    /**
     * Returns a single-line sentence describing the provided
     * functionality of this rule.
     * This has to be plain text <em>without</em> markup.
     * The text is intended to be displayed in the user interface.
     * Note that the application is allowed to cut-off text that
     * exceeds the length of 80 characters.
     * <p>
     * The <i>languageCode</i> argument gives the language code
     * of the user interface language that the user has currently selected.
     * The language code is a two-letter string as defined by the standard ISO-639.
     * Therefore, the text returned by this method should
     * be in the language given by <i>languageCode</i>. If the text is
     * not available in this language, then the returned description shall be in
     * English (i.e. at least English has to be supported).
     * </p>
     * <p>
     * To allow the translation of the text without having to touch the source 
     * code of the implementing class, the returned text should be stored in an  
     * external resource files. To achieve this, the static utitlity method 
     * {@link org.docma.plugin.PluginUtil#getResourceString(java.lang.Class, java.lang.String, java.lang.String) }
     * can be used to load a language dependent property from an external 
     * resource file. For example, following implementation of the
     * <code>getShortInfo</code> method, reads the returned text from a resource
     * file:
     * </p>
     * <pre>
     *     public String getShortInfo(String languageCode) {
     *         return PluginUtil.getResourceString(this.getClass(), languageCode, "shortInfo");
     *     }
     * </pre>
     * <p>
     * See the documentation of <code>PluginUtil.getResourceString</code>
     * for an example properties file.
     * </p>
     *
     * @param languageCode  the language of the user interface.
     * @return  sentence describing the functionality of this HTML rule.
     */
    String getShortInfo(String languageCode);

    /**
     * Returns a human-readable description of
     * this HTML rule. This should be a complete usage reference,
     * including a description of all arguments (if any).
     * For example, this information is displayed in the user-interface.
     * To improve readability, the returned text can include basic
     * XHTML markup. Following XHTML elements are allowed:
     * <tt>b, i, tt, big, small, sup, sub, em, strong, samp, kbd, code, var,
     * cite, dfn, abbr, acronym, span, br, p, div, pre, blockquote,
     * dl, dt, dd, ol, ul, li, table, tr, td, th</tt>.
     * All other XHTML elements may be filtered out by the application before
     * the text is presented to the user.
     * <p>
     * The <i>languageCode</i> argument gives the language code
     * of the user interface language that the user has currently selected.
     * The language code is a two-letter string as defined by the standard ISO-639.
     * Therefore, the text returned by this method should
     * be in the language given by <i>languageCode</i>. If the text is
     * not available in this language, then the returned description shall be in
     * English (i.e. at least English has to be supported).
     * </p>
     * <p>
     * To allow the translation of the text without having to touch the source 
     * code of the implementing class, the returned text should be stored in an  
     * external resource files. To achieve this, the static utitlity method 
     * {@link org.docma.plugin.PluginUtil#getResourceString(java.lang.Class, java.lang.String, java.lang.String) }
     * can be used to load a language dependent property from an external 
     * resource file. For example, following implementation of the
     * <code>getLongInfo</code> method, reads the returned text from a resource
     * file:
     * </p>
     * <pre>
     *     public String getLongInfo(String languageCode) {
     *         return PluginUtil.getResourceString(this.getClass(), languageCode, "longInfo");
     *     }
     * </pre>
     * <p>
     * See the documentation of <code>PluginUtil.getResourceString</code>
     * for an example properties file.
     * </p>
     *
     * @param languageCode  the language of the user interface.
     * @return  a complete usage description of this HTML rule.
     */
    String getLongInfo(String languageCode);

    /**
     * Sets the configuration for this HTML rule.
     * This method is called by the framework to provide the configuration data
     * that has been set by the user (for example, by the administrator).
     * The framework assures that this method is called at least once, before
     * the first invocation of the 
     * {@link #apply(java.lang.String, boolean, HTMLRuleContext) } method.
     * 
     * @param conf   the configuration data
     */
    void configure(HTMLRuleConfig conf);

    /**
     * Applies the checks that are provided by this rule to the 
     * <code>content</code> argument.
     * A check can be any kind of operation applied to the content, that
     * checks the consistency of the HTML. If a check supports auto-correction
     * then the supplied content may also be modified.
     * <p>
     * Log messages may be written by calling one of the log 
     * methods provided by the <code>context</code> argument.
     * </p>
     * 
     * @param content  the HTML content 
     * @param context  the context information
     */
    void apply(StringBuilder content, HTMLRuleContext context);

    /**
     * Returns the list of supported check identifiers 
     * 
     * @return supported check identifiers
     */
    String[] getCheckIds();

    /**
     * Returns a single-line description of the check.
     * This has to be plain text <em>without</em> markup.
     * The text is intended to be displayed in the user interface.
     * Note that the application is allowed to cut-off text that
     * exceeds the length of 80 characters.
     * 
     * @param checkId  the check identifier
     * @param languageCode  the language of the user interface.
     * @return  a short check description
     */
    String getCheckTitle(String checkId, String languageCode);
    
    /**
     * Indicates whether the check identified by <code>checkId</code> supports 
     * auto-correction or not.
     * The returned value may only depend on the rule configuration.
     * That means, the method always returns the same value, as long as the
     * configuration is not changed by an invocation of the 
     * {@link #configure(HTMLRuleConfig) } method.
     * 
     * @param checkId  the check identifier
     * @return <code>true</code> if auto-correction is supported by the given 
     *         check; <code>false</code> otherwise
     */
    boolean supportsAutoCorrection(String checkId);
    
    /**
     * Indicates the default log level for the check identified by 
     * <code>checkId</code>.
     * The returned value may be displayed as a hint to the user.
     * <p>
     * The returned value may only depend on the rule configuration.
     * That means, the method always returns the same log level, as long as the 
     * configuration is not changed by an invocation of the 
     * {@link #configure(HTMLRuleConfig) } method.
     * </p>
     * 
     * @param checkId  the check identifier
     * @return  the default log level for the given check
     */
    LogLevel getDefaultLogLevel(String checkId);

}
