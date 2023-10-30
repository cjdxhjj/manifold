/*
 * Copyright (c) 2020 - Manifold Systems LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package manifold.rt.api.util;

import java.io.Serializable;
import java.util.Arrays;

/**
 * <p>This class is directly derived from org.apache.commons.lang.ObjectUtils and is
 * intended to avoid dependencies on that project.</p>
 *
 * @author <a href="mailto:nissim@nksystems.com">Nissim Karpenstein</a>
 * @author <a href="mailto:janekdb@yahoo.co.uk">Janek Bogucki</a>
 * @author Daniel L. Rall
 * @author Stephen Colebourne
 * @author Gary Gregory
 * @author Mario Winterer
 *         <a href="mailto:david@davidkarlsen.com">David J. M. Karlsen</a>
 */
public class ManObjectUtil
{
  /**
   * <p>Singleton used as a <code>null</code> placeholder where
   * <code>null</code> has another meaning.</p>
   * <p/>
   * <p>For example, in a <code>HashMap</code> the
   * {@link java.util.HashMap#get(Object)} method returns
   * <code>null</code> if the <code>Map</code> contains
   * <code>null</code> or if there is no matching key. The
   * <code>Null</code> placeholder can be used to distinguish between
   * these two cases.</p>
   * <p/>
   * <p>Another example is <code>Hashtable</code>, where <code>null</code>
   * cannot be stored.</p>
   * <p/>
   * <p>This instance is Serializable.</p>
   */
  public static final Null NULL = new Null();

  // Defaulting
  //-----------------------------------------------------------------------

  /**
   * <p>Returns a default value if the object passed is
   * <code>null</code>.</p>
   * <p/>
   * <pre>
   * ObjectUtils.defaultIfNull(null, null)      = null
   * ObjectUtils.defaultIfNull(null, "")        = ""
   * ObjectUtils.defaultIfNull(null, "zz")      = "zz"
   * ObjectUtils.defaultIfNull("abc", *)        = "abc"
   * ObjectUtils.defaultIfNull(Boolean.TRUE, *) = Boolean.TRUE
   * </pre>
   *
   * @param object       the <code>Object</code> to test, may be <code>null</code>
   * @param defaultValue the default value to return, may be <code>null</code>
   *
   * @return <code>object</code> if it is not <code>null</code>, defaultValue otherwise
   */
  public static Object defaultIfNull( Object object, Object defaultValue )
  {
    return object != null ? object : defaultValue;
  }

  /**
   * <p>Compares two objects for equality, where either one or both
   * objects may be <code>null</code>.</p>
   * <p/>
   * <pre>
   * ObjectUtils.equals(null, null)                  = true
   * ObjectUtils.equals(null, "")                    = false
   * ObjectUtils.equals("", null)                    = false
   * ObjectUtils.equals("", "")                      = true
   * ObjectUtils.equals(Boolean.TRUE, null)          = false
   * ObjectUtils.equals(Boolean.TRUE, "true")        = false
   * ObjectUtils.equals(Boolean.TRUE, Boolean.TRUE)  = true
   * ObjectUtils.equals(Boolean.TRUE, Boolean.FALSE) = false
   * </pre>
   *
   * @param object1 the first object, may be <code>null</code>
   * @param object2 the second object, may be <code>null</code>
   *
   * @return <code>true</code> if the values of both objects are the same
   */
  public static boolean equals( Object object1, Object object2 )
  {
    if( object1 == object2 )
    {
      return true;
    }
    if( (object1 == null) || (object2 == null) )
    {
      return false;
    }
    return object1.equals( object2 );
  }

  /**
   * <p>Gets the hash code of an object returning zero when the
   * object is <code>null</code>.</p>
   * <p/>
   * <pre>
   * ObjectUtils.hashCode(null)   = 0
   * ObjectUtils.hashCode(obj)    = obj.hashCode()
   * </pre>
   *
   * @param obj the object to obtain the hash code of, may be <code>null</code>
   *
   * @return the hash code of the object, or zero if null
   *
   * @since 2.1
   */
  public static int hashCode( Object obj )
  {
    return (obj == null) ? 0 : obj.hashCode();
  }

  // Identity ToString
  //-----------------------------------------------------------------------

  /**
   * <p>Gets the toString that would be produced by <code>Object</code>
   * if a class did not override toString itself. <code>null</code>
   * will return <code>null</code>.</p>
   * <p/>
   * <pre>
   * ObjectUtils.identityToString(null)         = null
   * ObjectUtils.identityToString("")           = "java.lang.String@1e23"
   * ObjectUtils.identityToString(Boolean.TRUE) = "java.lang.Boolean@7fa"
   * </pre>
   *
   * @param object the object to create a toString for, may be
   *               <code>null</code>
   *
   * @return the default toString text, or <code>null</code> if
   * <code>null</code> passed in
   */
  public static String identityToString( Object object )
  {
    if( object == null )
    {
      return null;
    }
    StringBuffer buffer = new StringBuffer();
    identityToString( buffer, object );
    return buffer.toString();
  }

  /**
   * @return true if the given object is a pure java object array, false otherwise
   */
  public static boolean isJavaReferenceArray( Object o )
  {
    return o instanceof Object[];
  }

  /**
   * <p>Appends the toString that would be produced by <code>Object</code>
   * if a class did not override toString itself. <code>null</code>
   * will throw a NullPointerException for either of the two parameters. </p>
   * <p/>
   * <pre>
   * ObjectUtils.identityToString(buf, "")            = buf.append("java.lang.String@1e23"
   * ObjectUtils.identityToString(buf, Boolean.TRUE)  = buf.append("java.lang.Boolean@7fa"
   * ObjectUtils.identityToString(buf, Boolean.TRUE)  = buf.append("java.lang.Boolean@7fa")
   * </pre>
   *
   * @param buffer the buffer to append to
   * @param object the object to create a toString for
   *
   * @since 2.4
   */
  public static void identityToString( StringBuffer buffer, Object object )
  {
    if( object == null )
    {
      throw new NullPointerException( "Cannot get the toString of a null identity" );
    }
    buffer.append( object.getClass().getName() )
      .append( '@' )
      .append( Integer.toHexString( System.identityHashCode( object ) ) );
  }

  /**
   * <p>Appends the toString that would be produced by <code>Object</code>
   * if a class did not override toString itself. <code>null</code>
   * will return <code>null</code>.</p>
   * <p/>
   * <pre>
   * ObjectUtils.appendIdentityToString(*, null)            = null
   * ObjectUtils.appendIdentityToString(null, "")           = "java.lang.String@1e23"
   * ObjectUtils.appendIdentityToString(null, Boolean.TRUE) = "java.lang.Boolean@7fa"
   * ObjectUtils.appendIdentityToString(buf, Boolean.TRUE)  = buf.append("java.lang.Boolean@7fa")
   * </pre>
   *
   * @param buffer the buffer to append to, may be <code>null</code>
   * @param object the object to create a toString for, may be <code>null</code>
   *
   * @return the default toString text, or <code>null</code> if
   * <code>null</code> passed in
   *
   * @since 2.0
   * @deprecated The design of this method is bad - see LANG-360. Instead, use identityToString(StringBuffer, Object).
   */
  public static StringBuffer appendIdentityToString( StringBuffer buffer, Object object )
  {
    if( object == null )
    {
      return null;
    }
    if( buffer == null )
    {
      buffer = new StringBuffer();
    }
    return buffer
      .append( object.getClass().getName() )
      .append( '@' )
      .append( Integer.toHexString( System.identityHashCode( object ) ) );
  }

  // ToString
  //-----------------------------------------------------------------------

  /**
   * <p>Gets the <code>toString</code> of an <code>Object</code> returning
   * an empty string ("") if <code>null</code> input.</p>
   * <p/>
   * <pre>
   * ObjectUtils.toString(null)         = ""
   * ObjectUtils.toString("")           = ""
   * ObjectUtils.toString("bat")        = "bat"
   * ObjectUtils.toString(Boolean.TRUE) = "true"
   * </pre>
   *
   * @param obj the Object to <code>toString</code>, may be null
   *
   * @return the passed in Object's toString, or nullStr if <code>null</code> input
   *
   * @see String#valueOf(Object)
   * @since 2.0
   */
  public static String toString( Object obj )
  {
    return toString( obj, "null" );
  }

  /**
   * <p>Gets the <code>toString</code> of an <code>Object</code> returning
   * a specified text if <code>null</code> input.</p>
   * <p/>
   * <pre>
   * ObjectUtils.toString(null, null)           = null
   * ObjectUtils.toString(null, "null")         = "null"
   * ObjectUtils.toString("", "null")           = ""
   * ObjectUtils.toString("bat", "null")        = "bat"
   * ObjectUtils.toString(Boolean.TRUE, "null") = "true"
   * </pre>
   *
   * @param obj     the Object to <code>toString</code>, may be null
   * @param nullStr the String to return if <code>null</code> input, may be null
   *
   * @return the passed in Object's toString, or nullStr if <code>null</code> input
   *
   * @see String#valueOf(Object)
   * @since 2.0
   */
  public static String toString( Object obj, String nullStr )
  {
    return obj == null
      ? nullStr
      : obj.getClass().isArray()
        ? arrayToString( obj )
        : obj.toString();
  }

  public static String arrayToString( Object array )
  {
    if( array == null )
    {
      return "null";
    }

    Class<?> componentType = array.getClass().getComponentType();
    if( componentType.isPrimitive() )
    {
      switch( componentType.getTypeName() )
      {
        case "byte":
          return Arrays.toString( (byte[])array );
        case "short":
          return Arrays.toString( (short[])array );
        case "int":
          return Arrays.toString( (int[])array );
        case "long":
          return Arrays.toString( (long[])array );
        case "float":
          return Arrays.toString( (float[])array );
        case "double":
          return Arrays.toString( (double[])array );
        case "char":
          return Arrays.toString( (char[])array );
        case "boolean":
          return Arrays.toString( (boolean[])array );
        default:
          throw new IllegalStateException();
      }
    }
    else if( !componentType.isArray() )
    {
      return Arrays.toString( (Object[])array );
    }
    return Arrays.deepToString( (Object[])array );
  }

  // Min/Max
  //-----------------------------------------------------------------------

  /**
   * Null safe comparison of Comparables.
   *
   * @param c1 the first comparable, may be null
   * @param c2 the second comparable, may be null
   *
   * @return <ul>
   * <li>If both objects are non-null and unequal, the lesser object.
   * <li>If both objects are non-null and equal, c1.
   * <li>If one of the comparables is null, the non-null object.
   * <li>If both the comparables are null, null is returned.
   * </ul>
   */
  public static Object min( Comparable c1, Comparable c2 )
  {
    if( c1 != null && c2 != null )
    {
      return c1.compareTo( c2 ) < 1 ? c1 : c2;
    }
    else
    {
      return c1 != null ? c1 : c2;
    }
  }

  /**
   * Null safe comparison of Comparables.
   *
   * @param c1 the first comparable, may be null
   * @param c2 the second comparable, may be null
   *
   * @return <ul>
   * <li>If both objects are non-null and unequal, the greater object.
   * <li>If both objects are non-null and equal, c1.
   * <li>If one of the comparables is null, the non-null object.
   * <li>If both the comparables are null, null is returned.
   * </ul>
   */
  public static Object max( Comparable c1, Comparable c2 )
  {
    if( c1 != null && c2 != null )
    {
      return c1.compareTo( c2 ) >= 0 ? c1 : c2;
    }
    else
    {
      return c1 != null ? c1 : c2;
    }
  }

  // Null
  //-----------------------------------------------------------------------

  /**
   * <p>Class used as a null placeholder where <code>null</code>
   * has another meaning.</p>
   * <p/>
   * <p>For example, in a <code>HashMap</code> the
   * {@link java.util.HashMap#get(Object)} method returns
   * <code>null</code> if the <code>Map</code> contains
   * <code>null</code> or if there is no matching key. The
   * <code>Null</code> placeholder can be used to distinguish between
   * these two cases.</p>
   * <p/>
   * <p>Another example is <code>Hashtable</code>, where <code>null</code>
   * cannot be stored.</p>
   */
  public static class Null implements Serializable
  {
    /**
     * Required for serialization support. Declare serialization compatibility with Commons Lang 1.0
     *
     * @see Serializable
     */
    private static final long serialVersionUID = 7092611880189329093L;

    /**
     * Restricted constructor - singleton.
     */
    Null()
    {
      super();
    }

    /**
     * <p>Ensure singleton.</p>
     *
     * @return the singleton value
     */
    private Object readResolve()
    {
      return ManObjectUtil.NULL;
    }
  }
}