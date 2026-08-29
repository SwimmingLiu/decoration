package com.lamp.decoration.foundation.network.redis.protocol;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.Collections ;
import java.util.List;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.TypeReference;
import com.lamp.decoration.foundation.network.redis.create.KeyCreate;


public interface ResultHandle {
	
	public static final ResultHandle   byteResultHandle   = new ByteResultHandle();
	
	public static final ResultHandle   stringResultHandle = new StringResultHandle();
	
	public static final ResultHandle   objcetResultHandle = new ObjcetResultHandle();
	
	public static final ResultHandle   stringToLongHandle = new StringToLongResultHandle();  
	
	
	public static final ResultHandle   typeReferenceListResultHandle = new TypeReferenceListResultHandle();
	
	public static final ResultHandle   typeReferenceListString       = new TypeReferenceListStringResultHandle();
	
	public static final ResultHandle   typeReferenceListInteger       = new TypeReferenceListIntegerResultHandle();
	
	public static final ResultHandle   typeReferenceSetResultHandle  = new TypeReferenceSetResultHandle();
	
	public static final ResultHandle   typeReferenceMapResultHandle  = new TypeReferenceMapResultHandle();
	
	public static final ResultHandle   typeReferenceKeyListResultHandle = new TypeReferenceKeyListResultHandle();
	
	
	public <T>T handle(ByteBuffer buffer  , KeyCreate<T> kc);
	
	public <T>T getNullOjbect(KeyCreate< T > keyCreate);
	

	static class ByteResultHandle implements ResultHandle{
		private static final byte[] object = new byte[0];
		
		
		@Override
		@SuppressWarnings("unchecked")
		public <T>T handle(ByteBuffer buffer,  KeyCreate<T> kc) {
			byte[] by = new byte[buffer.position()];
			buffer.get( by );
			return (T)by;
		}

		@SuppressWarnings( "unchecked" )
		@Override
		public < T > T getNullOjbect (KeyCreate< T > keyCreate ) {
			return (T)object ;
		}		
	}
	
	static class StringToLongResultHandle implements ResultHandle{

		private static final Long  value = new Long( 0 );
		
		@SuppressWarnings("unchecked")
		@Override
		public <T> T handle(ByteBuffer buffer, KeyCreate<T> kc) {
			String str = new String(buffer.array() , 0 ,buffer.position());
			return (T)Long.valueOf( str );
		}

		@SuppressWarnings("unchecked")
		@Override
		public <T> T getNullOjbect(KeyCreate<T> keyCreate) {
			return (T)value;
		}
		
	}
	
	static class StringResultHandle implements ResultHandle{
		private static final String object = "";
		
		@Override
		@SuppressWarnings("unchecked")
		public  <T>T handle(ByteBuffer buffer,  KeyCreate<T> kc) {	
			return (T)new String( buffer.array() , 0 , buffer.position());
		}

		@SuppressWarnings( "unchecked" )
		@Override
		public < T > T getNullOjbect ( KeyCreate< T > keyCreate ) {
			return (T)object ;
		}		
	}	
	static class ObjcetResultHandle implements ResultHandle{
		
		@Override
		@SuppressWarnings("unchecked")
		public <T>T handle(ByteBuffer buffer,  KeyCreate<T> kc) {
			return (T) JSON.parseObject(buffer.array(), 0, buffer.position(), Charset.defaultCharset(), kc.getEntityClass());
		}

		@Override
		public < T > T getNullOjbect ( KeyCreate< T > keyCreate ) {
			return keyCreate.getOject( ) ;
		}	
	}
	
	static class TypeReferenceListResultHandle implements ResultHandle{
		
		@Override
		@SuppressWarnings("unchecked")
		public <T>T handle(ByteBuffer buffer, KeyCreate<T> kc) {
			return (T) JSON.parseObject( new String(buffer.array(), 0, buffer.position()), kc.getTypeReferenceList());
		}
		
		@SuppressWarnings( "unchecked" )
		@Override
		public < T > T getNullOjbect ( KeyCreate< T > keyCreate ) {
			return (T)Collections.EMPTY_LIST ;
		}
	}
	
	static class TypeReferenceListStringResultHandle extends TypeReferenceListResultHandle  {
		private final TypeReference<List<String>> typeReference = new TypeReference<List<String>>(){};
		@Override
		@SuppressWarnings("unchecked")
		public <T>T handle(ByteBuffer buffer, KeyCreate<T> kc) {
			return (T) JSON.parseObject( new String(buffer.array(), 0, buffer.position()), typeReference);
		}
	}
	
	static class TypeReferenceListIntegerResultHandle extends TypeReferenceListResultHandle  {
		private final TypeReference<List<Integer>> typeReference = new TypeReference<List<Integer>>(){};
		@Override
		@SuppressWarnings("unchecked")
		public <T>T handle(ByteBuffer buffer, KeyCreate<T> kc) {
			return (T) JSON.parseObject( new String(buffer.array(), 0, buffer.position()), typeReference);
		}
	}
	
	static class TypeReferenceSetResultHandle implements ResultHandle{	
		
		@Override
		@SuppressWarnings("unchecked")
		public <T>T handle(ByteBuffer buffer, KeyCreate<T> kc) {
			return (T)JSON.parseObject( new String(buffer.array(), 0, buffer.position()), kc.getTypeReferenceSet());
		}
		
		@SuppressWarnings( "unchecked" )
		@Override
		public < T > T getNullOjbect ( KeyCreate< T > keyCreate ) {
			return (T)Collections.EMPTY_SET;
		}
	}
	static class TypeReferenceMapResultHandle implements ResultHandle{
		
		@SuppressWarnings("unchecked")
		public <T>T handle(ByteBuffer buffer, KeyCreate<T> kc) {
			return (T)JSON.parseObject( new String(buffer.array(), 0, buffer.position()), kc.getTypeReferenceMap());
		}
		
		@SuppressWarnings( "unchecked" )
		@Override
		public < T > T getNullOjbect ( KeyCreate< T > keyCreate ) {
			return (T)Collections.EMPTY_MAP ;
		}
	}
	
	static class TypeReferenceKeyListResultHandle implements ResultHandle{
		
		@Override
		@SuppressWarnings("unchecked")
		public <T>T handle(ByteBuffer buffer, KeyCreate<T> kc) {
			return (T) JSON.parseObject( new String(buffer.array(), 0, buffer.position()), kc.getTypeReferenceKeyListResultHandle());
		}
		
		@SuppressWarnings( "unchecked" )
		@Override
		public < T > T getNullOjbect ( KeyCreate< T > keyCreate ) {
			return (T)Collections.EMPTY_LIST ;
		}
	}
}
