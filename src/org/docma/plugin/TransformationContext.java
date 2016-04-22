/*
 * TransformationContext.java
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
 * The transformation context.
 * An instance of this class is passed as argument to the method
 * {@link org.docma.plugin.AutoFormat#transform(org.docma.plugin.TransformationContext) transform}
 * of the {@link org.docma.plugin.AutoFormat AutoFormat} object.
 * This instance provides the transformation input and a <tt>Writer</tt> object
 * to write the transformation output.
 * <p>
 * The input consists of the input-element to be transformed
 * and the Auto-Format arguments that are defined in the
 * Auto-Format style configuration. Following illustration gives an overview of the
 * information provided by the getter-methods:
 * </p>
 * 
 * <img src="doc-files/transform_context.gif" />
 *
 * <p>
 * The method {@link #getOuterString() getOuterString} returns the complete
 * input-element. The tag-name and the attributes of the input-element
 * can be retrieved by calling the methods {@link #getTagName() getTagName} and
 * {@link #getTagAttributes() getTagAttributes}. The method
 * {@link #getInnerString() getInnerString} returns the inner content of the
 * input-element. 
 * </p>
 *
 * @author MP
 */
public interface TransformationContext
{
    /**
     * Returns the number of Auto-Format arguments defined for this transformation.
     * The Auto-Format arguments are defined in the style configuration.
     *
     * @return The number of Auto-Format arguments.
     */
    int    getArgumentCount();

    /**
     * Returns the argument at the specified index.
     * If <i>idx</i> is greater than or equal to the value
     * returned by {@link #getArgumentCount() getArgumentCount}, then
     * null is returned.
     * An exception is thrown if <i>idx</i> is negative.
     *
     * @param idx Index of the argument to return. The first argument has index 0.
     * @return The argument at position <i>idx</i> or null if argument does not exist.
     */
    String getArgument(int idx);

    /**
     * Returns the argument at the specified index.
     * If the argument at position <i>idx</i> does not exist, then <i>defaultValue</i>
     * is returned (i.e. <i>defaultValue</i> is returned, if <i>idx</i> is greater
     * than or equal to the value returned by {@link #getArgumentCount() getArgumentCount}).
     * An exception is thrown if <i>idx</i> is negative.
     *
     * @param idx Index of the argument to return. The first argument has index 0.
     * @param defaultValue The default value to be returned if argument does not exist.
     * @return The argument at position <i>idx</i> or the <i>defaultValue</i> if the argument does not exist.
     */
    String getArgument(int idx, String defaultValue);

    /**
     * Returns the inner content of the input-element.
     * This is a substring of the string returned by
     * {@link #getOuterString() getOuterString}.
     *
     * @return The inner content of the input-element.
     */
    String getInnerString();

    /**
     * Returns the complete input-element as a string.
     *
     * @return The input-element that is to be transformed.
     */
    String getOuterString();

    /**
     * Returns the tag-name of the input-element. For example, the returned
     * string is <tt>"div"</tt>, <tt>"p"</tt>, <tt>"span"</tt> or the
     * name of an other XML/XHTML element.
     *
     * @return The tag-name of the input-element.
     */
    String getTagName();

    /**
     * Returns the attributes defined in the opening tag of the input-element.
     *
     * @return The attributes of the input-element.
     */
    Map<String, String> getTagAttributes();

    /**
     * Returns a <tt>Writer</tt>-object for writing the transformation result.
     *
     * @return The <tt>Writer</tt>-object for writing the transformation result.
     */
    Writer getWriter();

    /**
     * Turns the style recursion on or off. Default is off.
     * <p>
     * If the transformation result contains an element that has the same
     * Auto-Format style assigned as the input-element that caused this
     * transformation, then this flag determines whether the element
     * in the result will be transformed recursively (<tt>true</tt>)
     * or not (<tt>false</tt>).
     * </p>
     * <p>
     * For example, this situation occurs if the transformation preserves the
     * outer-element and just replaces the inner content of the input-element.
     * If style recursion is turned on, than it has to be assured by the
     * transformation algorithm, that the recursion finishes
     * (note: to avoid infinite loops, the recursion depth is
     * limited by the transformation engine).
     * </p>
     *
     * @param styleRecursion Set to <tt>true</tt> to turn style recursion on.
     *                       Set to <tt>false</tt> to turn style recursion off.
     */
    void   setStyleRecursion(boolean styleRecursion);


}
