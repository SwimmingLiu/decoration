package com.lamp.decoration.foundation.network.redis.protocol;

import java.nio.ByteBuffer ;
import java.util.ArrayList;
import java.util.List ;

import com.lamp.decoration.foundation.network.redis.create.KeyCreate;
import com.lamp.decoration.foundation.network.redis.serialize.JsonDeToSerialize;


public class DataConversion {
	
	
	private static final int LIST_SIZE = 10;
	
	private static final ThreadLocal< List<DataConversion> >   LIST_DATACONVERSION_LOCAL = new ThreadLocal<>( );
	public static final List<DataConversion> getListDataConversion(){
		List< DataConversion > list = LIST_DATACONVERSION_LOCAL.get( );
		if( list == null){
			list = new ArrayList<>( );
			for(int i = 0 ; i < LIST_SIZE ; i++){
				list.add( new DataConversion( ) );
			}
			LIST_DATACONVERSION_LOCAL.set( list );
		}
		return list;
	}	
	
	private static final ThreadLocal< ByteBuffer > BUFFER_LOCAL = new ThreadLocal<>( );
	
	
	public static final ByteBuffer getBuffer(){
		ByteBuffer buffer = BUFFER_LOCAL.get( );
		if( buffer == null){
			buffer = ByteBuffer.allocate( 1024 <<4 );
			BUFFER_LOCAL.set( buffer );
		}
		buffer.clear( );
		return buffer; 
	}
	
	
	private Object object;
	
	private KeyCreate< Object > keyCreate;
	

	private boolean isWrite ;
	
	public ByteBuffer getWriteByteBuffer(){
		if( !this.isWrite )
			return null;
		ByteBuffer byteBuffer;
		if(keyCreate == null){
			byteBuffer = JsonDeToSerialize.SERIALIZE_DEFAULT.execute( object );
		}else{
			byteBuffer = getBuffer();
			if( object instanceof String){
				keyCreate.getKeySuffixBuffer( (String)object  , byteBuffer );
			}else if( object instanceof Long){
				keyCreate.getKeySuffixBuffer( (Long)object  , byteBuffer );
			}else{
				keyCreate.getKeySuffixBuffer( object  , byteBuffer );
			}
			keyCreate = null;
		}
		object = null;
		this.isWrite = false;
		return byteBuffer;
	}
	// 
	public  DataConversion setObjectAndKeyCreate( Object object ) {
		this.object = object;
		this.isWrite = true;
		return this;
	}
	
	@SuppressWarnings( "unchecked" )
	public <T> DataConversion setObjectAndKeyCreate( Object object ,KeyCreate< T > keyCreate) {
		this.object = object;
		this.keyCreate = (KeyCreate< Object >)keyCreate;
		this.isWrite = true;
		return this;
	}
	
}
