package com.lamp.decoration.foundation.network.redis.create;

import java.nio.ByteBuffer;
import java.util.Set ;

import com.alibaba.fastjson2.TypeReference;
import com.lamp.decoration.foundation.network.redis.protocol.ProtocolUtil;
import com.lamp.decoration.foundation.network.redis.utils.ClassUtils;


public abstract class AbstractKeyCreate< T > implements KeyCreate< T > {


	private Class< T > clazz ;
	
	private byte[] prefix;
	
	private byte separator;
	
	private T object;

	private KeyCreate<T> keyCreate;
	
	private KeyCreate<T> keyCreateValue;
	
	@SuppressWarnings( "rawtypes" )
	private Value value;
	
	private KeyConfigure<T> keyConfigure ;

	public AbstractKeyCreate(KeyConfigure<T> keyConfigure) {
		this.keyConfigure = keyConfigure ;
		this.clazz = keyConfigure.getAtr( ).getClazz( ) ;
		if( keyConfigure.getPrefix( ) != null && !"".equals(  keyConfigure.getPrefix( ) ) && keyConfigure.getPrefix().length() >1){
			this.prefix      = keyConfigure.getPrefix( ).getBytes( );			
		}
		try {
			this.object = clazz.newInstance( );
		} catch ( InstantiationException | IllegalAccessException e ) {
			// TODO 如果没有无参构造函数
			e.printStackTrace();
		}
	}



	public T getOject(){
		return object;
	}
	
	public void getKeySuffixBuffer(String key ,ByteBuffer byteBuffer){
		getKey( key , byteBuffer );
	}
	
	public void getKeySuffixBuffer(Long key ,ByteBuffer byteBuffer){
		getKey( key , byteBuffer );
	}
	
	
	void setByteBufferPrefix(ByteBuffer byteBuffer){
		if(this.prefix != null){		
			byteBuffer.put( prefix );
		}
	}
	
	public void getKey ( int key , ByteBuffer byteBuffer ) {
		setByteBufferPrefix( byteBuffer );
		ProtocolUtil.getChars( key , byteBuffer );
	}

	public void getKey ( long key , ByteBuffer byteBuffer) {
		setByteBufferPrefix( byteBuffer );
		ProtocolUtil.getChars( key , byteBuffer );
	}



	public void getKey ( Integer key , ByteBuffer byteBuffer) {
		 getKey( key.intValue( ) ,  byteBuffer) ;
	}

	public void getKey ( Long key , ByteBuffer byteBuffer) {
		
		 getKey( key.longValue( ) ,  byteBuffer) ;
	}
	
	public void getKey ( String key , ByteBuffer byteBuffer) {
		setByteBufferPrefix( byteBuffer );
		byteBuffer.put( key.getBytes( ) );
	}

	public String getKey ( T t ) {
		return getKeySuffix( t ) ;
	}
	
	public String getKey ( int key ) {
		return getKey( Integer.toString( key ) ) ;
	}

	public String getKey ( long key ) {
		return getKey( Long.toString( key ) ) ;
	}

	public String getKey ( Integer key ) {
		return getKey( key.longValue( ) ) ;
	}

	public String getKey ( Long key ) {
		
		return getKey( key.longValue( ) ) ;
	}

	public Class< T > getEntityClass ( ) {
		return clazz ;
	}

	@Override
	public TypeReference< ? > getTypeReferenceList ( ) {
		return keyConfigure.getAtr( ).getTrList( ) ;
	}

	@Override
	public TypeReference< Set< T > > getTypeReferenceSet ( ) {
		return null ;
	}

	@Override
	public TypeReference< ? > getTypeReferenceMap ( ) {
		return keyConfigure.getAtr( ).getTrMap( ) ;
	}

	public KeyCreate< T > getKeyCreate ( ) {
		return keyCreate ;
	}

	public void setKeyCreate(KeyCreate< T > keyCreate){
		this.keyCreate = keyCreate;
	}
	
	public KeyCreate< T > getKeyCreateValue ( ) {
		return keyCreateValue ;
	}


	public void setKeyCreateValue ( KeyCreate< T > keyCreateValue ) {
		this.keyCreateValue = keyCreateValue ;
	}



	public TypeReference< ? > getTypeReferenceKeyListResultHandle(){
		return ClassUtils.getTypeReferenceList( keyConfigure.getKeyType( ) );
	}
	
	
	@SuppressWarnings( "rawtypes" )
	public void setValue(Value value){
		this.value = value;
	}
	
	
	@SuppressWarnings( "rawtypes" )
	public Value getValue(){
		return this.value;
	}
	

}
