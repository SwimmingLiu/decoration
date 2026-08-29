package com.lamp.decoration.foundation.network.redis.serialize;

import java.lang.reflect.Constructor ;
import java.lang.reflect.InvocationTargetException ;
import java.nio.ByteBuffer ;

import com.alibaba.fastjson2.JSON;


public class JsonDeToSerialize implements Deserialize , Serialize{

	private static final Class< ? > clazz = getClazz();
	
	private static final Constructor< ? > en = getConstructor();
	
	private static final JsonDeToSerialize jsonDeToSerialize = new JsonDeToSerialize();
	
	public static final Serialize SERIALIZE_DEFAULT = jsonDeToSerialize;
	
	public static final Deserialize DESERIALIZE_DEFAULT = jsonDeToSerialize;
	
	private static final Class< ? > getClazz(){
		try {
			return Class.forName( "java.nio.HeapByteBuffer" );
		} catch ( ClassNotFoundException e ) {
			e.printStackTrace();
			return null;
		}
	}
	
	private static final Constructor< ? > getConstructor(){
		try {
			Constructor< ? > en = clazz.getDeclaredConstructor( new Class[]{ byte[].class , int.class, int.class}  );
			en.setAccessible( true );
			return en;
		} catch ( NoSuchMethodException | SecurityException e ) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	@Override
	public ByteBuffer execute(Object o) {
		// 这里会做优化的。
		byte[] by ;
		if(o instanceof String){
			by = ( (String) o).getBytes( );
		}else {
			by = JSON.toJSONBytes( o  ) ;
		}
		try {
			return (ByteBuffer)en.newInstance( new Object[]{ by , by.length  , 0} );
		} catch ( InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e ) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public byte[] executeByte ( Object o ) {
		return JSON.toJSONBytes( o  ) ;
	}


	@Override
	public String executeString ( Object o ) {
		return JSON.toJSONString(o) ;
	}

	@Override
	public <T>T execute(byte[] by, Class<T> clazz) {
		return JSON.parseObject(by, clazz);
	}

}
