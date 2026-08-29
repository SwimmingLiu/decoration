package com.lamp.decoration.foundation.network.redis.utils;

import java.lang.reflect.Method ;
import java.util.HashMap ;
import java.util.List ;
import java.util.Map ;

import com.alibaba.fastjson2.TypeReference;


public final class ClassUtils {

	private static final Map< String , String[] > CLASS_ASM_NAME = new HashMap< String , String[] >( ) ;
	
	private static final Map< String , String[] > CLASS_ASM_VALUE_NAME = new HashMap< String , String[] >( ) ;
	
	private static final Map< String , String >  BASIC_PACKING = new HashMap< String , String >( ) ;
	
	private static final Map<String , TypeReference> TYPEREFERENCE_LIST = new HashMap<>( );
	
	static {
		CLASS_ASM_NAME.put( "java.lang.Long"       , new String[]{ "()Ljava/lang/Long;"    , "(Ljava/lang/Long;)Ljava/lang/String;"   , "(Ljava/lang/Long;Ljava/nio/ByteBuffer;)V"     } );
		CLASS_ASM_NAME.put( "java.lang.Integer"    , new String[]{ "()Ljava/lang/Integer;" , "(Ljava/lang/Integer;)Ljava/lang/String;", "(Ljava/lang/Integer;Ljava/nio/ByteBuffer;)V"  } );
		CLASS_ASM_NAME.put( "java.lang.String"     , new String[]{ "()Ljava/lang/String;" , "(Ljava/lang/String;)Ljava/lang/String;" , "(Ljava/lang/String;Ljava/nio/ByteBuffer;)V"   } );
		CLASS_ASM_NAME.put( "int"                  , new String[]{ "()I"                  , "(I)Ljava/lang/String;"                  , "(ILjava/nio/ByteBuffer;)V"});
		CLASS_ASM_NAME.put( "long"                 , new String[]{ "()L"                  , "(J)Ljava/lang/String;"                  , "(JLjava/nio/ByteBuffer;)v"});
		
		CLASS_ASM_VALUE_NAME.put( "java.lang.Long"       , new String[]{ "()Ljava/lang/Long"    , "(Ljava/lang/Long;)V"    ,  "Ljava/lang/Long;"    ,"java/lang/Long"    } );
		CLASS_ASM_VALUE_NAME.put( "java.lang.Integer"    , new String[]{ "()Ljava/lang/Integer" , "(Ljava/lang/Integer;)V" ,  "Ljava/lang/Integer;" ,"java/lang/Integer" } );
		CLASS_ASM_VALUE_NAME.put( "java.lang.String"     , new String[]{ "()Ljava/lang/String;" , "(Ljava/lang/String;)V"  ,  "Ljava/lang/String;"  ,"java/lang/String"  } );
		CLASS_ASM_VALUE_NAME.put( "int"                  , new String[]{ "()I"                  , "(I)V"                   ,  "Ljava/lang/Integer;" ,"java/lang/Integer" } );
		CLASS_ASM_VALUE_NAME.put( "long"                 , new String[]{ "()L"                  , "(L)V"                   ,  "Ljava/lang/Long;"    ,"java/lang/Long"    } );
		
		
		
		BASIC_PACKING.put( "int"               , "java/lang/Integer" );
		BASIC_PACKING.put( "java.lang.Integer" , "java/lang/Integer" );
		BASIC_PACKING.put( "long"              , "java/lang/Long" );
		BASIC_PACKING.put( "java.lang.Long"    , "java/lang/Long" );
		BASIC_PACKING.put( "java.lang.String"  , "java/lang/String" );
		
		TypeReference tr = new TypeReference<List<Integer>>(){};
		TYPEREFERENCE_LIST.put( "int"               , tr );
		TYPEREFERENCE_LIST.put( "java.lang.Integer" , tr );
		tr = new TypeReference<List<Long>>(){};
		TYPEREFERENCE_LIST.put( "long"              , tr );
		TYPEREFERENCE_LIST.put( "java.lang.Long"    , tr );
		tr = new TypeReference<List<String>>(){};
		TYPEREFERENCE_LIST.put( "java.lang.String"  , tr );
		
		
	}
	
	
	private static final char[] GET_CHAR_ARRAY = "get".toCharArray( );
	
	private static final char[] SET_CHAR_ARRAY = "set".toCharArray( );
	
	private static final Class<?>[] CLASS_NULL_ARRYAL = new Class[]{};

	
	public static final TypeReference getTypeReferenceList(String fileType){
		return TYPEREFERENCE_LIST.get( fileType );
	}
	
	
	public static final String[] amsName ( String className , String methodName ) throws Exception {
		Class< ? > clazz = Class.forName( className );
		Method method = clazz.getMethod( methodName , CLASS_NULL_ARRYAL );	
		String keyClassName = method.getReturnType( ).getName( ) ;
		return CLASS_ASM_NAME.get( keyClassName ) ;
		
	}
	
	public static final String[] amsValueName ( Class<?> clazz , String methodName ) throws Exception {
		Method method = clazz.getMethod( methodName , CLASS_NULL_ARRYAL );		
		String keyClassName = method.getReturnType( ).getName( ) ;
		return CLASS_ASM_VALUE_NAME.get( keyClassName ) ;
	}
	
	
	public static final String typeStrToAmsTypeStr(String typeStr){
		if( BASIC_PACKING.containsKey( typeStr ) ){
			return BASIC_PACKING.get( typeStr );
		}
		return typeStr.replace( '.' , '/' );
	}

	public static final String getMethodName( String key){
		char[] charr = key.toCharArray( );		
		charr[0] = Character.toUpperCase( charr[0]);
		StringBuffer sb = new StringBuffer( 3+ charr.length );
		sb.append( GET_CHAR_ARRAY ).append(  charr );		
		return sb.toString( );
		
	}
	
	public static final String setMethodName( String key){
		char[] charr = key.toCharArray( );		
		charr[0] = Character.toUpperCase( charr[0]);
		StringBuffer sb = new StringBuffer( 3+ charr.length );
		sb.append( SET_CHAR_ARRAY ).append(  charr );		
		return sb.toString( );
		
	}
	
	
	public static final String className(String key , String className , long id ,String name ){
		StringBuffer sb = new StringBuffer( );
		className = className.substring( className.lastIndexOf( '.' )+1  );
		sb.append( key ).append( "_" ).append( id ).append( "_" ).append( className ).append( name );
		return sb.toString( );
	}
	
	
	
}
