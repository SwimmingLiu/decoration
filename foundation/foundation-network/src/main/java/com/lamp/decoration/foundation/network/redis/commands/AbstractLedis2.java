
package com.lamp.decoration.foundation.network.redis.commands;

import java.util.List ;


import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;

import com.lamp.decoration.foundation.network.redis.create.AmsTypeReference;
import com.lamp.decoration.foundation.network.redis.create.KeyConfigure;
import com.lamp.decoration.foundation.network.redis.create.KeyCreate;
import com.lamp.decoration.foundation.network.redis.create.StringKeyCreate;
import com.lamp.decoration.foundation.network.redis.net.netty.ResponseFuture;
import com.lamp.decoration.foundation.network.redis.protocol.DataConversion;

public abstract class AbstractLedis2< T > {

	@SuppressWarnings("unchecked")
	protected final  static KeyCreate<String> STRING_KEYCREATE = new StringKeyCreate(new KeyConfigure<>( null, null, null, null, AmsTypeReference.stringAsmType));
	
	protected KeyCreate< T > keyCreate ;

	

	public AbstractLedis2(KeyCreate< T > keyCreate, String dataSource) {
		this.keyCreate = keyCreate ;
		
	}
	
	
	
	@SuppressWarnings({ "unchecked", "hiding" })
	public   < T > T combination( CombinationElement ce , List <DataConversion> dataList ){
			return defaultCombination( ce , dataList );
	} 
	
	@SuppressWarnings({ "unchecked", "hiding" })
	public   < T > T defaultCombination( CombinationElement ce , List < DataConversion > dataList ){
		return null;
	}
	
	
	
	@SuppressWarnings({ "unchecked" })
	public   < V >V combination( CombinationElement ce,List<DataConversion> dataList ,List<T> objectList , KeyCreate<T> keyCreat){
			return defaultCombination( ce , dataList , (List<Object>)objectList , (KeyCreate<Object>)keyCreat );
	} 

	public final < V > V combination ( CombinationElement ce , List< DataConversion > dataList , List< T > objectList ) {
		return combination( ce , dataList , objectList , this.keyCreate ) ;
	}

	@SuppressWarnings( "unchecked" )
	public final < V > V combinationString ( CombinationElement ce , List< DataConversion > dataList , List< String > objectList ) {
		return combination( ce , dataList , ( List< T > ) objectList , this.keyCreate ) ;
	}

	@SuppressWarnings( { "unchecked" } )
	public final < V > V combinationLong ( CombinationElement ce , List< DataConversion > dataList , List< ? extends Number > objectList ) {
		return combination( ce , dataList , ( List< T > ) objectList , null ) ;
	}
	
	
	@SuppressWarnings("unchecked")
	public  < V >V  defaultCombination( CombinationElement ce,List<DataConversion> dataList ,List<Object> objectList , KeyCreate<Object> keyCreate ){
		
		final ResponseFuture responseFuture = new ResponseFuture(null , null, ce , dataList, this.keyCreate, objectList , keyCreate);
		
		return null;
	}
}
