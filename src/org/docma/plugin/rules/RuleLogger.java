/*
 * RuleLogger.java
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
 *
 * @author MP
 */
public interface RuleLogger 
{
    void log(RuleSeverity severity, String msg, Object... args);
    void info(String msg, Object... args);
    void warning(String msg, Object... args);
    void error(String msg, Object... args);
}