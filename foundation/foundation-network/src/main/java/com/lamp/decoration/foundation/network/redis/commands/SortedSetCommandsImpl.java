package com.lamp.decoration.foundation.network.redis.commands;

import java.util.List ;

import com.lamp.decoration.foundation.network.redis.create.KeyCreate;
import com.lamp.decoration.foundation.network.redis.entity.SortedSetParameter;
import com.lamp.decoration.foundation.network.redis.entity.ZunionstoreParameter;
import com.lamp.decoration.foundation.network.redis.utils.DataConversionUtils;


public class SortedSetCommandsImpl<T> extends BasicsCommandsImpl< T > implements SortedSetCommands< T > {



	public SortedSetCommandsImpl(KeyCreate< T > keyCreate, String dataSource) {
		super( keyCreate , dataSource ) ;
	}

	@Override
	public boolean zadd ( T t ) {
		return combination( SortedSetCommandsElement.ZADD , DataConversionUtils.getKeyCreateAndValueCreate( t , keyCreate )) ;
	}

	@Override
	public long zadd ( List< T > list ) {
		return combination( SortedSetCommandsElement.ZADD_MORE ,DataConversionUtils.getDataConversionListKey(list.get( 0 ), keyCreate), list , keyCreate.getKeyCreate( )) ;
	}

	@Override
	public long zcard ( String key ) {
		return combination( SortedSetCommandsElement.ZCARD , DataConversionUtils.getDataConversionListKey( key,keyCreate ) ) ;
	}

	@Override
	public long zcount ( String key , int min , int max ) {
		return combination( SortedSetCommandsElement.ZCOUNT , DataConversionUtils.getDataConversionList( key,min,max,keyCreate) ) ;
	}

	@Override
	public long zincrby ( T t ) {
		return combination( SortedSetCommandsElement.ZINCRBY , DataConversionUtils.getKeyCreateAndValueCreate( t , keyCreate )) ;
	}

	@Override
	public List<T>  zrange ( String key , SortedSetParameter sortedSetParameter ) {
		return  combination( SortedSetCommandsElement.ZRANGE , DataConversionUtils.getKeyStringValueList( key ,sortedSetParameter,keyCreate )) ;
	}

	@Override
	public List< T > zrangebyscore ( String key , SortedSetParameter sortedSetParameter ) {
		return  combination( SortedSetCommandsElement.ZRANGEBYSCORE , DataConversionUtils.getKeyStringValueList( key,sortedSetParameter,keyCreate  )) ;
	}

	@Override
	public long zrank ( String key , String member ) {
		return combination( SortedSetCommandsElement.ZRANK , DataConversionUtils.getDataConversionList( key , member)) ;
	}

	@Override
	public long zrem ( T t ) {
		return combination(SortedSetCommandsElement.ZREN , DataConversionUtils.getKeyToMapKey( t , keyCreate )) ;
	}

	@Override
	public long zrem ( List< T > list ) {
		return combination( SortedSetCommandsElement.ZREN_MORE , DataConversionUtils.getDataConversionListKey( list.get( 0 ), keyCreate ) , list,keyCreate.getKeyCreate( )) ; 
	}

	@Override
	public long zremrangebyrank ( String key , int start , int stop ) {
		return combination( SortedSetCommandsElement.ZREMRANGEBYRANK , DataConversionUtils.getDataConversionList( key , start , stop)) ;
	}

	@Override
	public long zremrangebyscore ( String key , int min , int max ) {
		return combination( SortedSetCommandsElement.ZREMRANGEBYSCORE , DataConversionUtils.getDataConversionList( key , min , max)) ;
	}

	@Override
	public List< T > zrevrange ( String key , SortedSetParameter sortedSetParameter ) {
		return  combination( SortedSetCommandsElement.ZREVRANGE , DataConversionUtils.getKeyStringValueList( key , sortedSetParameter , keyCreate )) ;
	}

	@Override
	public List< T > zrevrangebyscore ( String key , SortedSetParameter sortedSetParameter ) {
		return  combination( SortedSetCommandsElement.ZREVRANGEBYSCORE , DataConversionUtils.getKeyStringValueList( key , sortedSetParameter , keyCreate )) ;
	}

	@Override
	public long zrevrank ( String key , String member ) {
		return combination( SortedSetCommandsElement.ZREVRANK , DataConversionUtils.getDataConversionListKey( key , member, keyCreate)) ;
	}

	@Override
	public long zscore ( String key , String member ) {
		return combination( SortedSetCommandsElement.ZSCORE , DataConversionUtils.getDataConversionListKey( key , member,keyCreate)) ;

	}

	@Override
	public List< T > zunionstore ( String key , ZunionstoreParameter zunionstoreParameter ) {
		return  combination( SortedSetCommandsElement.ZUNIONSTORE , DataConversionUtils.getKeyStringValueList( key , zunionstoreParameter , keyCreate )) ;
	}

	@Override
	public List< T > zinterstore ( String key , ZunionstoreParameter zunionstoreParameter ) {
		return  combination( SortedSetCommandsElement.ZINTERSTORE , DataConversionUtils.getKeyStringValueList( key , zunionstoreParameter , keyCreate )) ;
	}

}
