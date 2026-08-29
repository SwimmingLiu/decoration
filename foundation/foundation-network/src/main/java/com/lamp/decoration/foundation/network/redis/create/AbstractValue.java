package com.lamp.decoration.foundation.network.redis.create;


import java.nio.ByteBuffer ;

public abstract class AbstractValue< K , V , T > implements Value< K , V , T > {

	private static final GetValue<String>  getString = new GetString( );
	
	private static final GetValue<Long>     getLong   = new GetLong();
	
	private static final GetValue<Integer> getInteger = new GetInteger( );
	
	
	@SuppressWarnings( "rawtypes" )
	private static final GetValue getValue(String str){
		if("java/lang/Integer".equals( str )){
			return getInteger;
		}else if("java/lang/Long".equals( str )){
			return getLong;
		}else{
			return getString;
		}
	}
	
	
	private GetValue<K>  keyValue;
	
	private GetValue< V > valueValue;
	
	@SuppressWarnings( "unchecked" )
	public AbstractValue(String keyType , String valueType){
		keyValue = getValue( keyType );
		valueValue = getValue( valueType );
	}
	
	public K getKey(ByteBuffer byteBuffer){
		return keyValue.getValue( byteBuffer );
	}
	
	public V getValue(ByteBuffer byteBuffer){
		return valueValue.getValue( byteBuffer );
	}
	
	interface GetValue<G>{
		
		G getValue(ByteBuffer buffer);
		
	}
	
	static class GetString implements GetValue< String >{

		@Override
		public String getValue ( ByteBuffer buffer ) {
			return new String( buffer.array( ) , 0 , buffer.position( ) ) ;
		}		
	}
	
	static class GetLong implements GetValue< Long >{

		@Override
		public Long getValue ( ByteBuffer buffer ) {
			return Long.valueOf( new String( buffer.array( ) , 0 , buffer.position( ) ));
		}
		
	}
	
	static class GetInteger implements GetValue< Integer >{
		@Override
		public Integer getValue ( ByteBuffer buffer ) {
			return Integer.valueOf( new String( buffer.array( ) , 0 , buffer.position( ) ));
		}		
	}
}
