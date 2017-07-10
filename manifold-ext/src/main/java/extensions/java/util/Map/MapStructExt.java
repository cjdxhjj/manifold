package extensions.java.util.Map;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Map;
import manifold.ext.api.Extension;
import manifold.ext.api.ICallHandler;
import manifold.ext.api.This;

/**
 * Interface extension for java.util.Map to add ICallHandler support.
 */
@SuppressWarnings("unused")
@Extension
public abstract class MapStructExt //implements ICallHandler
{
  private static Object NULL = new Object()
  {
    @Override
    public String toString()
    {
      return "NULL";
    }
  };

  @SuppressWarnings("unused")
  public static Object call( @This Map thiz, Class iface, String name, Class returnType, Class[] paramTypes, Object[] args )
  {
    assert paramTypes.length == args.length;

    Object value = NULL;
    if( returnType != void.class && paramTypes.length == 0 )
    {
      value = getValue( thiz, name, "get", returnType, paramTypes, args );
      if( value == NULL )
      {
        value = getValue( thiz, name, "is", returnType, paramTypes, args );
      }
    }
    if( value == NULL )
    {
      if( returnType == void.class )
      {
        value = setValue( thiz, name, paramTypes, args );
      }
      if( value == NULL )
      {
        value = invoke( thiz, name, returnType, paramTypes, args );
      }
      if( value == NULL )
      {
        throw new RuntimeException( "Missing method: " + name + "(" + Arrays.toString( paramTypes ) + ")" );
      }
    }
    return value;
  }

  private static Object getValue( Map thiz, String name, String prefix, Class returnType, Class[] paramTypes, Object[] args )
  {
    int getLen = prefix.length();
    if( name.length() > getLen && name.startsWith( prefix ) )
    {
      char c = name.charAt( getLen );
      if( c == '_' && name.length() > getLen + 1 )
      {
        getLen++;
        c = name.charAt( getLen );
      }
      if( Character.isUpperCase( c ) )
      {
        String key = name.substring( getLen );
        if( thiz.containsKey( key ) )
        {
          return thiz.get( key );
        }
        key = Character.toLowerCase( c ) + name.substring( 1 );
        if( thiz.containsKey( key ) )
        {
          return thiz.get( key );
        }
      }
    }
    return NULL;
  }

  private static Object setValue( Map thiz, String name, Class[] paramTypes, Object[] args )
  {
    int setLen = "set".length();
    if( paramTypes.length == 1 && name.length() > setLen && name.startsWith( "set" ) )
    {
      char c = name.charAt( setLen );
      if( c == '_' && name.length() > setLen + 1 )
      {
        setLen++;
        c = name.charAt( setLen );
      }
      String key;
      if( Character.isUpperCase( c ) )
      {
        String upperKey = name.substring( setLen );
        if( thiz.containsKey( upperKey ) )
        {
          key = upperKey;
        }
        else
        {
          String lowerKey = Character.toLowerCase( c ) + name.substring( 1 );
          if( thiz.containsKey( lowerKey ) )
          {
            key = lowerKey;
          }
          else
          {
            key = upperKey;
          }
        }
      }
      else
      {
        key = name.substring( setLen );
      }
      //noinspection unchecked
      thiz.put( key, args[0] );
      return null;
    }
    return NULL;
  }

  private static Object invoke( Map thiz, String name, Class returnType, Class[] paramTypes, Object[] args )
  {
    Object value = thiz.get( name );
    if( value == null )
    {
      return NULL;
    }
    try
    {
      for( Method m : value.getClass().getMethods() )
      {
        if( !m.isDefault() && !Modifier.isStatic( m.getModifiers() ) )
        {
          m.setAccessible( true );
          return m.invoke( value, args );
        }
      }
      return NULL;
    }
    catch( Exception e )
    {
      throw new RuntimeException( e );
    }
  }
}
