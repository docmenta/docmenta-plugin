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
     * Returns a human-readable sentence describing the
     * functionality of this HTML rule.
     * This has to be plain text <em>without</em> markup.
     * For example, the returned text is displayed in the user interface.
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
     * @param languageCode The language of the user interface.
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
     * @param languageCode The language of the user interface.
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
     * Applies the rule to the HTML string passed in the <code>content</code> 
     * argument.
     * An HTML rule can be any kind of operation applied to the content, that
     * checks the consistency of the HTML. Two modes are distinguished: 
     * <p>
     * If the <code>autoCorrect</code> argument is <code>true</code>, 
     * then this method tries to correct the provided content, and returns the 
     * corrected content as the method's result value. 
     * If no auto-correction is possible at all for the passed content, then the  
     * content is returned unchanged (in other words, the method's result   
     * value is equal to the value passed in the <code>content</code> argument).
     * In any case, log messages may be written to the logger provided in the 
     * <code>context</code> argument (see {@link HTMLRuleContext#getLogger() }).
     * </p>
     * <p>
     * On the other hand, if the <code>autoCorrect</code> argument is 
     * <code>false</code>, then this method just checks whether the provided
     * content is conforming to the rule. In this case only log messages are 
     * written. The method's result value has no meaning and can be any value, 
     * for example <code>null</code>.
     * That means, if the <code>autoCorrect</code> argument is 
     * <code>false</code>, then the content remains unchanged, independant from
     * the method's result value.
     * </p>
     * 
     * @param content  the HTML content 
     * @param autoCorrect  whether auto-correction shall be applied 
     *                     (<code>true</code>) or not (<code>false</code>)
     * @param context  the context information
     * @return  if <code>autoCorrect</code> is <code>true</code> the 
     *          auto-corrected content; otherwise undefined
     */
    String apply(String content, boolean autoCorrect, HTMLRuleContext context);
    
    /**
     * Indicates whether this rule supports auto-correction or not.
     * The returned value may only depend on the rule configuration.
     * That means, the method always returns the same value, as long as the
     * configuration is not changed by an invocation of the 
     * {@link #configure(HTMLRuleConfig) } method.
     * <p>
     * Note that the value returned by this method determines how the framework 
     * invokes the {@link #apply(java.lang.String, boolean, HTMLRuleContext)} 
     * method. As long as this method returns <code>false</code>, the 
     * <code>apply</code> method is called with its <code>autoCorrect</code> 
     * parameter set to <code>false</code> only.
     * On the other hand, as long as this method returns <code>true</code>,  
     * the <code>apply</code> method may be called with the   
     * <code>autoCorrect</code> parameter set to <code>false</code> or 
     * <code>true</code>.
     * </p>
     * 
     * @return <code>true</code> if auto-correction is supported;
     *         <code>false</code> otherwise
     */
    boolean supportsAutoCorrection();
    
    /**
     * Indicates the maximum severity of the log messages that this rule 
     * creates. The returned value may be displayed as a hint to the user,  
     * but does not change functionality of this rule.
     * <p>
     * For example, if a rule just writes log messages of type <code>INFO</code>
     * and <code>WARNING</code>, then the returned value should be 
     * <code>RuleSeverity.WARNING</code>. 
     * If the same rule also writes messages of type  
     * <code>ERROR</code>, then the returned value should be 
     * <code>RuleSeverity.ERROR</code>.
     * </p>
     * <p>
     * The returned value may only depend on the rule configuration and 
     * must <em>not</em> be <code>null</code>.
     * That means, the method always returns the same severity, as long as the
     * configuration is not changed by an invocation of the 
     * {@link #configure(HTMLRuleConfig) } method.
     * </p>
     * 
     * @return  the maximum severity of the log messages 
     */
    RuleSeverity getMaxSeverity();

}
