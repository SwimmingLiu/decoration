package com.lamp.decoration.foundation.network.redis.utils;

import java.io.File ;
import java.io.FileOutputStream ;
import java.lang.reflect.Field ;
import java.util.concurrent.atomic.AtomicLong;

import org.objectweb.asm.ClassWriter ;
import org.objectweb.asm.Label ;
import org.objectweb.asm.MethodVisitor ;
import org.objectweb.asm.Opcodes ;

import com.alibaba.fastjson2.TypeReference;
import com.lamp.decoration.foundation.network.redis.annotation.OperationEntity;
import com.lamp.decoration.foundation.network.redis.create.AbstractKeyCreate;
import com.lamp.decoration.foundation.network.redis.create.AmsTypeReference;
import com.lamp.decoration.foundation.network.redis.create.KeyConfigure;
import com.lamp.decoration.foundation.network.redis.create.KeyCreate;
import com.lamp.decoration.foundation.network.redis.create.KeyCreateAndAmsTypeReferenceFactory;
import com.lamp.decoration.foundation.network.redis.create.Value;


public class KeyCreateUtils extends ClassLoader implements Opcodes {

	private static final KeyCreateUtils  keyCreateUtils = new KeyCreateUtils();
	
	private KeyCreateUtils(){}
	
	public static final KeyCreateUtils getInstance(){
		return keyCreateUtils;
	}
	
	
	private static String CLASS_NAME = "_KeyCreate" ;

	private static String TYPEREFERENCE_LIST = "_TypeReference_list" ;

	private static String TYPEREFERENCE_MAP = "_TypeReference_map" ;
	
	private static String VALUE_NAME="value";

	private static String TYPEREFERENCE_PACKAGE_PATH = "com/alibaba/fastjson/TypeReference" ;

	private final AtomicLong atomicLong = new AtomicLong();

	private final static KeyCreateAndAmsTypeReferenceFactory kaaf = KeyCreateAndAmsTypeReferenceFactory.getInstance( ) ;

	@Deprecated
	public void keyCreate ( String className , String key , String stringKey ) throws Exception {
		String methodName = ClassUtils.getMethodName( key ) ;
		String[] amsName = ClassUtils.amsName( className , methodName ) ;
		Class< ? > clazz = Class.forName( className ) ;
		Field field = clazz.getField( key ) ;
		field.getType( ).getClass( ).getName( ) ;

		String amsClassName = ClassUtils.className( key , className , atomicLong.incrementAndGet() , CLASS_NAME ) ;

		ClassWriter cw = new ClassWriter( 0 ) ;
		cw.visit( V1_8 , ACC_PUBLIC , amsClassName , "L" + className , "com/lamp/ledis/create/AbstractKeyCreate" ,
				null ) ;
		MethodVisitor mw = cw.visitMethod( ACC_PUBLIC , "<init>" , "(Ljava/lang/String;)V" , null , null ) ;
		mw.visitVarInsn( ALOAD , 0 ) ;
		mw.visitVarInsn( ALOAD , 1 ) ;
		mw.visitMethodInsn( INVOKESPECIAL , "com/lamp/ledis/create/AbstractKeyCreate" , "<init>" ,
				"(Ljava/lang/String;)V" , false ) ;

		mw.visitInsn( RETURN ) ;
		mw.visitMaxs( 2 , 2 ) ;
		mw.visitEnd( ) ;

		mw = cw.visitMethod( ACC_PUBLIC , "getKeySuffix" , "(L" + className + ";)Ljava/lang/String;" , null , null ) ;
		mw.visitVarInsn( ALOAD , 0 ) ;
		mw.visitVarInsn( ALOAD , 1 ) ;
		mw.visitMethodInsn( INVOKEVIRTUAL , "com/lamp/ledis/entity/TestEntity" , methodName , amsName[0] , false ) ;
		// mw.visitMethodInsn(INVOKEVIRTUAL,
		// "com/lamp/ledis/create/AbstractKeyCreate", "getKey",
		// "(J)Ljava/lang/String;", false);
		mw.visitMethodInsn( INVOKEVIRTUAL , "com/lamp/ledis/create/AbstractKeyCreate" , "getKey" , amsName[1] ,
				false ) ;
		mw.visitInsn( ARETURN ) ;
		mw.visitMaxs( 3 , 4 ) ;
		mw.visitEnd( ) ;

		mw = cw.visitMethod( ACC_PUBLIC , "getKeySuffix" , "(Ljava/lang/Object;)Ljava/lang/String;" , null , null ) ;
		mw.visitVarInsn( ALOAD , 0 ) ;
		mw.visitVarInsn( ALOAD , 1 ) ;
		mw.visitTypeInsn( CHECKCAST , className ) ;
		mw.visitMethodInsn( INVOKEVIRTUAL , "com/lamp/ledis/create/KeyCreate" , "getKeySuffix" , "(L" + className + ";)Ljava/lang/String;" ,
				false ) ;
		mw.visitInsn( ARETURN ) ;
		mw.visitMaxs( 2 , 2 ) ;
		mw.visitEnd( ) ;
		byte[] code = cw.toByteArray( ) ;

		FileOutputStream fos = new FileOutputStream( "FieldExample.class" ) ;
		fos.write( code ) ;
		fos.close( ) ;

		Class< ? > exampleClass = this.defineClass( amsClassName , code , 0 , code.length ) ;
		exampleClass.getConstructor( String.class ).newInstance( stringKey ) ;
		// exampleClass.newInstance();

	}

	public void keyCreate ( String kcKey ,String className , String key , String methodName , String[] amsName , KeyConfigure<?> keyConfigure) throws Exception {

		long increment = atomicLong.incrementAndGet();
		String amsClassName = ClassUtils.className( key , className.replace( '.' , '_' ) , increment , CLASS_NAME ) ;

		ClassWriter cw = new ClassWriter( 0 ) ;
		cw.visit( V1_8 , ACC_PUBLIC , amsClassName , "L" + className.replace( '.' , '/' ) , "com/lamp/ledis/create/AbstractKeyCreate" ,
				null ) ;
		MethodVisitor mw = cw.visitMethod( ACC_PUBLIC , "<init>" , "(Lcom/lamp/ledis/create/KeyConfigure;)V" , null , null ) ;
		mw.visitVarInsn( ALOAD , 0 ) ;
		mw.visitVarInsn( ALOAD , 1 ) ;
		mw.visitMethodInsn( INVOKESPECIAL , "com/lamp/ledis/create/AbstractKeyCreate" , "<init>" ,
				"(Lcom/lamp/ledis/create/KeyConfigure;)V" , false ) ;

		mw.visitInsn( RETURN ) ;
		mw.visitMaxs( 2 , 2 ) ;
		mw.visitEnd( ) ;

		mw = cw.visitMethod( ACC_PUBLIC , "getKeySuffix" , "(L" + className.replace( '.' , '/' ) + ";)Ljava/lang/String;" , null , null ) ;
		mw.visitVarInsn( ALOAD , 0 ) ;
		mw.visitVarInsn( ALOAD , 1 ) ;
		mw.visitMethodInsn( INVOKEVIRTUAL , className.replace( '.' , '/' ) , methodName , amsName[0] , false ) ;
		mw.visitMethodInsn( INVOKEVIRTUAL , "com/lamp/ledis/create/AbstractKeyCreate" , "getKey" , amsName[1] ,
				false ) ;
		mw.visitInsn( ARETURN ) ;
		mw.visitMaxs( 3 , 4 ) ;
		mw.visitEnd( ) ;

		mw = cw.visitMethod( ACC_PUBLIC , "getKeySuffix" , "(Ljava/lang/Object;)Ljava/lang/String;" , null , null ) ;
		mw.visitVarInsn( ALOAD , 0 ) ;
		mw.visitVarInsn( ALOAD , 1 ) ;
		mw.visitTypeInsn( CHECKCAST , className.replace( '.' , '/' ) ) ;
		mw.visitMethodInsn( INVOKEVIRTUAL , "com/lamp/ledis/create/KeyCreate" , "getKeySuffix" , "(L" + className.replace( '.' , '/' ) + ";)Ljava/lang/String;" ,
				false ) ;
		mw.visitInsn( ARETURN ) ;
		mw.visitMaxs( 2 , 2 ) ;
		
		{
		

			
			mw = cw.visitMethod( ACC_PUBLIC , "getKeySuffixBuffer" ,"(L" + className.replace( '.' , '/' ) + ";Ljava/nio/ByteBuffer;)V", null , null );
			mw.visitCode( );
			mw.visitVarInsn( ALOAD , 0 );
			mw.visitVarInsn( ALOAD , 1 );
			mw.visitMethodInsn( INVOKEVIRTUAL , className.replace( '.' , '/' ) , methodName , amsName[0] , false );
			mw.visitVarInsn( ALOAD , 2 );
			mw.visitMethodInsn( INVOKEVIRTUAL , amsClassName , "getKey" ,amsName[2] , false );
			mw.visitInsn( RETURN );
			mw.visitMaxs( 3 , 3 );
			mw.visitEnd( );

			mw = cw.visitMethod( ACC_PUBLIC + ACC_BRIDGE + ACC_SYNTHETIC , "getKeySuffixBuffer" ,"(Ljava/lang/Object;Ljava/nio/ByteBuffer;)V" , null , null );
			mw.visitCode( );
			mw.visitVarInsn( ALOAD , 0 );
			mw.visitVarInsn( ALOAD , 1 );
			mw.visitTypeInsn( CHECKCAST , className.replace( '.' , '/' ) );
			mw.visitVarInsn( ALOAD , 2 );
			mw.visitMethodInsn( INVOKEVIRTUAL , amsClassName , "getKeySuffixBuffer" ,"(L" + className.replace( '.' , '/' ) + ";Ljava/nio/ByteBuffer;)V" , false );
			mw.visitInsn( RETURN );
			mw.visitMaxs( 3 , 3 );
			mw.visitEnd( );
		}
		mw.visitEnd( ) ;
		byte[] code = cw.toByteArray( ) ;
		
		FileOutputStream file  = new FileOutputStream( new File( amsClassName+".class" ) );
		file.write( code );
		file.flush( );
		file.close( );
		Class< ? > exampleClass = this.defineClass( amsClassName , code , 0 , code.length ) ;
		Object o = exampleClass.getConstructor( KeyConfigure.class ).newInstance( keyConfigure ) ;
		kaaf.putKeyConfigure( kcKey , (KeyCreate< ? >) o ) ;

	}

	@Deprecated
	public TypeReference< ? > map ( String keyType , String valueType )
			throws InstantiationException , IllegalAccessException {
		long increment = atomicLong.incrementAndGet();
		String amsClassName = ClassUtils.className( keyType , valueType , increment , TYPEREFERENCE_MAP ) ;

		StringBuffer sb = new StringBuffer( ) ;
		sb.append( "Lcom/alibaba/fastjson/TypeReference<Ljava/util/Map<L" ) ;
		sb.append( keyType ) ;
		sb.append( ";L" ) ;
		sb.append( valueType ) ;
		sb.append( ";>;>;" ) ;

		ClassWriter cw = new ClassWriter( 0 ) ;
		cw.visit( V1_8 , ACC_PUBLIC , amsClassName , sb.toString( ) , TYPEREFERENCE_PACKAGE_PATH , null ) ;
		MethodVisitor mw = cw.visitMethod( ACC_PUBLIC , "<init>" , "()V" , null , null ) ;
		mw.visitVarInsn( ALOAD , 0 ) ;
		mw.visitMethodInsn( INVOKESPECIAL , "com/alibaba/fastjson/TypeReference" , "<init>" , "()V" , false ) ;
		mw.visitInsn( RETURN ) ;
		mw.visitMaxs( 1 , 1 ) ;
		mw.visitEnd( ) ;

		byte[] code = cw.toByteArray( ) ;

		Class< ? > exampleClass = this.defineClass( amsClassName , code , 0 , code.length ) ;

		return ( TypeReference< ? > ) exampleClass.newInstance( ) ;
	}

	@Deprecated
	public TypeReference< ? > list ( String valueType ) throws InstantiationException , IllegalAccessException {
		String amsClassName = ClassUtils.className( "" , valueType , atomicLong.incrementAndGet(), TYPEREFERENCE_MAP ) ;

		StringBuffer sb = new StringBuffer( ) ;
		sb.append( "Lcom/alibaba/fastjson/TypeReference<Ljava/util/List<L" ) ;
		sb.append( valueType ) ;
		sb.append( ";>;>;" ) ;

		ClassWriter cw = new ClassWriter( 0 ) ;
		cw.visit( V1_8 , ACC_PUBLIC , amsClassName , sb.toString( ) , TYPEREFERENCE_PACKAGE_PATH , null ) ;
		MethodVisitor mw = cw.visitMethod( ACC_PUBLIC , "<init>" , "()V" , null , null ) ;
		mw.visitVarInsn( ALOAD , 0 ) ;

		mw.visitMethodInsn( INVOKESPECIAL , "com/alibaba/fastjson/TypeReference" , "<init>" , "()V" , false ) ;

		mw.visitInsn( RETURN ) ;
		mw.visitMaxs( 1 , 1 ) ;
		mw.visitEnd( ) ;

		byte[] code = cw.toByteArray( ) ;

		Class< ? > exampleClass = this.defineClass( amsClassName , code , 0 , code.length ) ;

		return ( TypeReference< ? > ) exampleClass.newInstance( ) ;
	}

	@SuppressWarnings( { "rawtypes", "unchecked" } )
	public KeyCreate createKeyCreate(OperationEntity operationEntity) throws Exception{
		AbstractKeyCreate keyCreate = (AbstractKeyCreate)createKeyCreate( operationEntity.getClazz( ).getName( ) , operationEntity.getKey( ) , operationEntity.getPrefix( ) + operationEntity.getSeparator( ) , operationEntity.getSeparator( )	 , null );
		if( operationEntity.getMapKey( ) != null && !"".equals(operationEntity.getMapKey())){
			keyCreate.setKeyCreate( createKeyCreate( operationEntity.getClazz( ).getName( ) , operationEntity.getMapKey( ) , operationEntity.getMapPrefix( ) , null , null ) );
		}
		if(operationEntity.getValue( ) != null && !"".equals(operationEntity.getValue())){
			((AbstractKeyCreate)keyCreate.getKeyCreate( )).setKeyCreateValue( createKeyCreate(operationEntity.getClazz( ).getName( ) , operationEntity.getValue( ) , null , null	 , null));
			//keyCreate.setKeyCreateValue( createKeyCreate(operationEntity.getClazz( ).getName( ) , operationEntity.getValue( ) , null , null	 , null));
			Value v = value(operationEntity.getClazz( ) , operationEntity.getMapKey( ) , operationEntity.getValue( ));
			keyCreate.setValue( v );
		}
		return keyCreate;
	}
	
	@SuppressWarnings( { "rawtypes", "unchecked" } )
	public KeyCreate createKeyCreate ( String clazzName , String keyName , String prefix ,String separator , String hashKeyPrefix) throws Exception {
		// clazzName + key 成为唯一值，去缓存里面取
		// 如果不存在就创建KeyConfigure对象
		// 去缓存去AmsTypeReference对象，key是 className
		// 判断是否有AmsTypeReference对象，如果没有就创建
		//

		String kcKey = clazzName + "_" + keyName ;
		// TODO 线程安全的问题
		if ( ! kaaf.isKeyConfigure( kcKey ) ) {
			String keyMethodName = ClassUtils.getMethodName( keyName ) ;
			String[] amsName = ClassUtils.amsName( clazzName , keyMethodName ) ;
			Class< ? > clazz = Class.forName( clazzName ) ;
			Field field = clazz.getDeclaredField( keyName ) ;
			String keyType = field.getType( ).getName( ) ;

			if ( ! kaaf.isAmsTypeReference( clazzName ) ) {

				AmsTypeReference atr = new AmsTypeReference( ) ;
				atr.setClazz( clazz ) ;
				atr.setClazzName( clazzName ) ;
				String amsClassName = ClassUtils.className( keyName , clazzName , atomicLong.incrementAndGet() ,CLASS_NAME ) ;
				atr.setClassAmsName( amsClassName ) ;

				amsClassName = ClassUtils.className( clazzName , keyType , atomicLong.incrementAndGet() ,
						TYPEREFERENCE_MAP ) ;
				atr.setTrMapObejctName( amsClassName ) ;
				String typeReferenceMapClassName = getTypeReferenceMapCalssName( clazzName.replace( '.' , '/' ) , ClassUtils.typeStrToAmsTypeStr( keyType ) ) ;
				atr.setTrMap( createTypeReference( amsClassName.replace( '.' , '_' ) , typeReferenceMapClassName ) ) ;

				amsClassName = ClassUtils.className( "" , clazzName , atomicLong.incrementAndGet() , TYPEREFERENCE_LIST ) ;
				atr.setTrListObejctName( amsClassName ) ;

				String typeReferenceListClassName = getTypeReferenceListClassName( clazzName.replace( '.' , '/' ) ) ;
				atr.setTrList( createTypeReference( amsClassName.replace( '.' , '_' ) , typeReferenceListClassName ) ) ;

				kaaf.putAmsTypeReference( clazzName , atr ) ;
			}
			KeyConfigure keyConfigure = new KeyConfigure( prefix , keyName , keyType , keyMethodName ,kaaf.getAmsTypeReference( clazzName ) ) ;
			keyCreate( kcKey ,clazzName , keyName , keyMethodName , amsName , keyConfigure  ) ;

		}
		return kaaf.getKeyCreate( kcKey ) ;
	}

	private TypeReference< ? > createTypeReference ( String amsClassName , String typeRefernece )
			throws InstantiationException , IllegalAccessException {
		ClassWriter cw = new ClassWriter( 0 ) ;
		cw.visit( V1_8 , ACC_PUBLIC , amsClassName , typeRefernece , TYPEREFERENCE_PACKAGE_PATH , null ) ;
		MethodVisitor mw = cw.visitMethod( ACC_PUBLIC , "<init>" , "()V" , null , null ) ;
		mw.visitVarInsn( ALOAD , 0 ) ;

		mw.visitMethodInsn( INVOKESPECIAL , "com/alibaba/fastjson/TypeReference" , "<init>" , "()V" , false ) ;

		mw.visitInsn( RETURN ) ;
		mw.visitMaxs( 1 , 1 ) ;
		mw.visitEnd( ) ;

		byte[] code = cw.toByteArray( ) ;

		Class< ? > exampleClass = this.defineClass( amsClassName , code , 0 , code.length ) ;

		return ( TypeReference< ? > ) exampleClass.newInstance( ) ;

	}

	private String getTypeReferenceMapCalssName ( String clazzName , String keyType ) {
		StringBuffer sb = new StringBuffer( ) ;
		sb.append( "Lcom/alibaba/fastjson/TypeReference<Ljava/util/Map<L" ) ;
		sb.append(  keyType ) ;
		sb.append( ";L" ) ;
		sb.append( clazzName ) ;
		sb.append( ";>;>;" ) ;
		return sb.toString( ) ;
	}

	private String getTypeReferenceListClassName ( String clazzName ) {
		StringBuffer sb = new StringBuffer( ) ;
		sb.append( "Lcom/alibaba/fastjson/TypeReference<Ljava/util/List<L" ) ;
		sb.append( clazzName ) ;
		sb.append( ";>;>;" ) ;
		return sb.toString( ) ;
	}
	
	public  Value value (Class<?> clazz , String id , String value) throws Exception {
		ClassWriter cw = new ClassWriter(0);
		MethodVisitor mv;
		String idMethod = ClassUtils.getMethodName( id ),
		 valueMethod = ClassUtils.getMethodName( value ),
		 classTypeName =clazz.getName( ).replace( '.' , '/' );
		String[] idType = ClassUtils.amsValueName( clazz , idMethod ),
				 valueType = ClassUtils.amsValueName( clazz , valueMethod );
		idMethod = ClassUtils.setMethodName( id );
		valueMethod = ClassUtils.setMethodName( value );
	
		String setValueMethod = new StringBuffer( ).append( "(" ).append( idType[2] )
				                                   .append( valueType[2] ).append( ")L" ).append( classTypeName ).append( ";" ).toString( );
		
		String generic = new StringBuffer( ).append( "Ljava/lang/Object;Lcom/lamp/ledis/entity/Value<" ).append( idType[2] )
                .append( valueType[2] ).append( "L" ).append( classTypeName ).append( ";>;" ).toString( );
		String amsClassName = ClassUtils.className( id , clazz.getName( ).replace( '.' , '_' ) , atomicLong.incrementAndGet() , VALUE_NAME ) ;
		cw.visit(52, ACC_PUBLIC + ACC_SUPER, amsClassName, generic, "com/lamp/ledis/create/AbstractValue", new String[] { "com/lamp/ledis/create/Value" });
	
		cw.visitSource(amsClassName+".java", null);
	
		{
			mv = cw.visitMethod(ACC_PUBLIC, "<init>", "(Ljava/lang/String;Ljava/lang/String;)V", null, null);
			mv.visitCode();
			Label l0 = new Label();
			mv.visitLabel(l0);
			mv.visitLineNumber(3, l0);
			mv.visitVarInsn(ALOAD, 0);
			mv.visitVarInsn( ALOAD , 1 ) ;
			mv.visitVarInsn( ALOAD , 2 ) ;
			mv.visitMethodInsn(INVOKESPECIAL, "com/lamp/ledis/create/AbstractValue", "<init>", "(Ljava/lang/String;Ljava/lang/String;)V", false);
			mv.visitInsn(RETURN);
			Label l1 = new Label();
			mv.visitLabel(l1);
			mv.visitLocalVariable("this", "L"+amsClassName+";", null, l0, l1, 0);
			mv.visitMaxs(3, 3);
			mv.visitEnd();
		}
		{
			mv = cw.visitMethod(ACC_PUBLIC, "setValue", setValueMethod, null, null);
			mv.visitCode();
			Label l0 = new Label();
			mv.visitLabel(l0);
			mv.visitLineNumber(9, l0);
			mv.visitTypeInsn(NEW, classTypeName);
			mv.visitInsn(DUP);
			mv.visitMethodInsn(INVOKESPECIAL, classTypeName, "<init>", "()V", false);
			mv.visitVarInsn(ASTORE, 3);
			Label l1 = new Label();
			mv.visitLabel(l1);
			mv.visitLineNumber(10, l1);
			mv.visitVarInsn(ALOAD, 3);
			mv.visitVarInsn(ALOAD, 2);
			if( valueType[0] == "()I" ){				
				mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Integer", "intValue", "()I", false);
			}
			if( valueType[0] == "()L" ){
				mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Long", "longValue", "()L", false);
			}
			mv.visitMethodInsn(INVOKEVIRTUAL, classTypeName, valueMethod, valueType[1], false);
			Label l2 = new Label();
			mv.visitLabel(l2);
			mv.visitLineNumber(11, l2);
			mv.visitVarInsn(ALOAD, 3);
			mv.visitVarInsn(ALOAD, 1);
			if( idType[0] == "()I" ){				
				mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Integer", "intValue", "()I", false);
			}
			if( idType[0] == "()L" ){
				mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Long", "longValue", "()L", false);
			}
			mv.visitMethodInsn(INVOKEVIRTUAL, classTypeName, idMethod, idType[1], false);
			Label l3 = new Label();
			mv.visitLabel(l3);
			mv.visitLineNumber(12, l3);
			mv.visitVarInsn(ALOAD, 3);
			mv.visitInsn(ARETURN);
			Label l4 = new Label();
			mv.visitLabel(l4);
			mv.visitLocalVariable("this", "L"+amsClassName+";"   , null, l0, l4, 0);
			mv.visitLocalVariable("k",     idType[2]             , null, l0, l4, 1);
			mv.visitLocalVariable("v",     valueType[2]          , null, l0, l4, 2);
			mv.visitLocalVariable("te", "L"+classTypeName+";", null, l1, l4, 3);
			mv.visitMaxs(2, 4);
			mv.visitEnd();
		}
		{
			mv = cw.visitMethod(ACC_PUBLIC + ACC_BRIDGE + ACC_SYNTHETIC, "setValue", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;", null, null);
			mv.visitCode();
			Label l0 = new Label();
			mv.visitLabel(l0);
			mv.visitLineNumber(1, l0);
			mv.visitVarInsn(ALOAD, 0);
			mv.visitVarInsn(ALOAD, 1);
			mv.visitTypeInsn(CHECKCAST, idType[3]);
			mv.visitVarInsn(ALOAD, 2);
			mv.visitTypeInsn(CHECKCAST, valueType[3]);
			mv.visitMethodInsn(INVOKEVIRTUAL, amsClassName, "setValue", setValueMethod, false);
			mv.visitInsn(ARETURN);
			mv.visitMaxs(3, 3);
			mv.visitEnd();
		}
		cw.visitEnd();
		byte[] code = cw.toByteArray( ) ;
		Class< ? > exampleClass = this.defineClass( amsClassName , code , 0 , code.length ) ;
		
		return (Value)exampleClass.getConstructor( String.class , String.class ).newInstance(idType[3], valueType[3]);
	}

}
