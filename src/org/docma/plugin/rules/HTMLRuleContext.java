/*
 * HTMLRuleContext.java
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
import org.docma.plugin.StoreConnection;

/**
 *
 * @author MP
 */
public interface HTMLRuleContext 
{
    /**
     * Returns the identifier of the node the rule is applied to.
     * Note that the identified node should be a 
     * {@link org.docma.plugin.PubContent} node with MIME type 
     * <code>"text/html"</code> or <code>"application/xhtml+xml"</code>,
     * because rules of type <code>HTMLRule</code> are only applied to HTML  
     * content nodes.
     * 
     * @return  the id of the node to which the rule is applied
     */
    String getNodeId();

    /**
     * Indicates whether the given check is enabled.
     * 
     * @param checkId  the check identifier
     * @return  <code>true</code> if the check is enabled; <code>false</code> otherwise
     */
    boolean isEnabled(String checkId);
    
    /**
     * Indicates whether auto-correction is enabled for the given check.
     * 
     * @param checkId  the check identifier
     * @return  <code>true</code> if auto-correction is enabled; <code>false</code> otherwise
     */
    boolean isAutoCorrect(String checkId);

    /**
     * Logs a message for the check identified by <code>checkId</code>. 
     * The message is logged with the user-defined log level that is configured  
     * for the given check. For example, if the configured log level   
     * is {@link org.docma.plugin.LogLevel#ERROR } then the message is logged 
     * as an error.
     * <p>
     * The <code>msg</code> argument may be a plain text message or a key
     * identifying a localized message in a resource bundle. The message 
     * can contain placeholders as defined by the class 
     * <code>java.text.MessageFormat</code>.
     * </p>
     * 
     * @param checkId  the identifier of the check for which the message is to be logged
     * @param msg      the message to be logged, or a message key
     * @param args     the values to be inserted for the placeholders in the message  
     */
    void log(String checkId, String msg, Object... args);
    
    /**
     * Logs a message related to the given content position for the check 
     * identified by <code>checkId</code>. 
     * The message is logged with the user-defined log level that is configured  
     * for the given check.
     * <p>
     * This method is identical to the {@link #log(String, String, Object...)} 
     * method, except that additionally the content position is supplied to 
     * which the message refers to.
     * Depending on the logger implementation, the log output may include an 
     * extract of the content at the given position.
     * </p>
     * 
     * @param checkId  the identifier of the check for which the message is to be logged
     * @param contentPosition  the content position the message is referring to
     * @param msg      the message to be logged, or a message key
     * @param args     the values to be inserted for the placeholders in the message
     */
    void log(String checkId, int contentPosition, String msg, Object... args);

    /**
     * Logs an informal message for the check identified by <code>checkId</code>. 
     * The message is logged with the log level 
     * {@link org.docma.plugin.LogLevel#INFO }.
     * <p>
     * The <code>msg</code> argument may be a plain text message or a key
     * identifying a localized message in a resource bundle. The message 
     * can contain placeholders as defined by the class 
     * <code>java.text.MessageFormat</code>.
     * </p>
     * 
     * @param checkId  the identifier of the check for which the message is to be logged
     * @param msg      the message to be logged, or a message key
     * @param args     the values to be inserted for the placeholders in the message  
     */
    void logInfo(String checkId, String msg, Object... args);
    
    /**
     * Logs an informal message related to the given content position for the  
     * check identified by <code>checkId</code>. This method is identical to the 
     * {@link #logInfo(String, String, Object...)} method, except that 
     * additionally the content position is supplied to which the message refers 
     * to. Depending on the logger implementation, the log output may include an 
     * extract of the content at the given position.
     * 
     * @param checkId  the identifier of the check for which the message is to be logged
     * @param contentPosition  the content position the message is referring to
     * @param msg      the message to be logged, or a message key
     * @param args     the values to be inserted for the placeholders in the message
     */
    void logInfo(String checkId, int contentPosition, String msg, Object... args);

    /**
     * Logs a message with the given log level. This method should only be 
     * used for messages that are <em>not</em> related to any check.
     * Messages that are related to a specific check should be logged by 
     * the <code>log</code> or <code>logInfo</code> methods where the 
     * check identifier is passed as argument.
     * <p>
     * The <code>msg</code> argument may be a plain text message or a key
     * identifying a localized message in a resource bundle. The message 
     * can contain placeholders as defined by the class 
     * <code>java.text.MessageFormat</code>.
     * </p>
     * 
     * @param level  the log level
     * @param msg    the message to be logged, or a message key
     * @param args   the values to be inserted for the placeholders in the message
     */
    void log(LogLevel level, String msg, Object... args);
    
    /**
     * Logs a message related to the given content position with the given
     * log level. This method should only be used for messages that are 
     * <em>not</em> related to any check.
     * Messages that are related to a specific check should be logged by 
     * the <code>log</code> or <code>logInfo</code> methods where the 
     * check identifier is passed as argument.     
     * <p>
     * This method is identical to the 
     * {@link #log(org.docma.plugin.LogLevel, String, Object...)} method, 
     * except that additionally the content position is supplied to which the 
     * message refers to.
     * Depending on the logger implementation, the log output may include an 
     * extract of the content at the given position.
     * </p>
     * 
     * @param level  the log level
     * @param contentPosition  the content position the message is referring to
     * @param msg    the message to be logged, or a message key
     * @param args   the values to be inserted for the placeholders in the message
     */
    void log(LogLevel level, int contentPosition, String msg, Object... args);
    
    /**
     * Returns the current store connection.
     * 
     * @return  the current store connection
     */
    StoreConnection getStoreConnection();
    
}
