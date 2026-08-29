package com.lamp.decoration.foundation.network.redis.commands;

import java.util.List ;

import com.lamp.decoration.foundation.network.redis.create.KeyCreate;
import com.lamp.decoration.foundation.network.redis.entity.BlockListResult;
import com.lamp.decoration.foundation.network.redis.protocol.DataConversion;
import com.lamp.decoration.foundation.network.redis.utils.DataConversionUtils;


public class ListCommandsImpl<T> extends BasicsCommandsImpl<T >implements ListCommands< T > , ListCommandsElement{
	
	public ListCommandsImpl(KeyCreate< T > keyCreate, String dataSource) {
		super( keyCreate , dataSource ) ;
	}

	@Override
	public BlockListResult<T> blpop ( T key ) {
		return blpop( key , 0 ) ;
	}

	@Override
	public BlockListResult<T> blpop ( long key ) {
		return blpop( key , 0 ) ;
	}

	@Override
	public BlockListResult<T> blpop ( String key ) {
		return blpop( key , 0 ) ;
	}

	@Override
	public BlockListResult<T> blpop ( T key , int timeout ) {
		return combination( BLPOP_SINGLE , DataConversionUtils.keyIsKeyCreate( key  ,timeout, this.keyCreate)  ) ;
	}

	@Override
	public BlockListResult<T> blpop ( long key , int timeout ) {
		return combination( BLPOP_SINGLE , DataConversionUtils.keyIsKeyCreate( key , timeout , this.keyCreate)  ) ;
	}

	@Override
	public BlockListResult<T> blpop ( String key , int timeout ) {
		return combination( BLPOP_SINGLE , DataConversionUtils.keyIsKeyCreate( key , timeout , this.keyCreate)  ) ;
	}

	@Override
	public BlockListResult<T> brpop ( T key ) {
		return brpop( key , 2000000 ) ;
	}

	@Override
	public BlockListResult<T> brpop ( long key ) {
		return brpop( key , 2000000 ) ;
	}

	@Override
	public BlockListResult<T> brpop ( String key ) {
		return brpop( key , 2000000 ) ;
	}

	@Override
	public BlockListResult<T> brpop ( T key , int timeout ) {
		return combination( BRPOP_SINGLE , DataConversionUtils.keyIsKeyCreate( key  ,timeout,0, this.keyCreate)  ) ;
	}

	@Override
	public BlockListResult<T> brpop ( long key , int timeout ) {
		return combination( BRPOP_SINGLE , DataConversionUtils.keyIsKeyCreate( key  ,timeout ,0, this.keyCreate)  ) ;
	}

	@Override
	public BlockListResult<T> brpop ( String key , int timeout ) {
		return combination( BRPOP_SINGLE , DataConversionUtils.keyIsKeyCreate( key  ,timeout , this.keyCreate)  ) ;
	}

	@Override
	public BlockListResult<T> brpoplpush ( T source , T destination ) {
		return brpoplpush( source	 , destination,0 ) ;
	}

	@Override
	public BlockListResult<T> brpoplpush ( long source , long destination ) {
		return combination( BRPOPLPUSH , DataConversionUtils.keyIsKeyCreate( source  ,destination , this.keyCreate)  ) ;
	}

	@Override
	public BlockListResult<T> brpoplpush ( String source , String destination ) {
		return combination( BLPOP_SINGLE , DataConversionUtils.keyIsKeyCreate( source  ,destination , this.keyCreate)  ) ;
	}

	@Override
	public BlockListResult<T> brpoplpush ( T source , T destination , int timeout ) {
		List< DataConversion > dataList = DataConversion.getListDataConversion( );
		dataList.get( 0 ).setObjectAndKeyCreate( source , this.keyCreate ) ;
		dataList.get( 1 ).setObjectAndKeyCreate( source , this.keyCreate ) ;
		dataList.get( 2 ).setObjectAndKeyCreate( timeout );
		return combination( BRPOPLPUSH	 , dataList ) ;
	}

	@Override
	public BlockListResult<T> brpoplpush ( long source , long destination , int timeout ) {
		return combination( BRPOPLPUSH , DataConversionUtils.keyIsKeyCreate( source  ,destination ,timeout , this.keyCreate)  ) ;
	}

	@Override
	public BlockListResult<T> brpoplpush ( String source , String destination , int timeout ) {
		return combination( BRPOPLPUSH , DataConversionUtils.keyIsKeyCreate( source  ,destination ,timeout , this.keyCreate)  ) ;
	}

	@Override
	public T lindex ( T key , int index ) {
		return combination( LINDEX , DataConversionUtils.getDataConversionListKey( key , index , keyCreate   , this.keyCreate)) ;
	}

	@Override
	public T lindex ( long key , int index ) {
		return combination( LINDEX , DataConversionUtils.keyIsKeyCreate(  key , index ,keyCreate )  ) ;
	}

	@Override
	public T lindex ( String key , int index ) {
		return combination( LINDEX , DataConversionUtils.keyIsKeyCreate( key , index,keyCreate )  ) ;
	}

	@Override
	public long linsert ( String key , String pivot , String value ) {
		return combination( LINSERT , DataConversionUtils.keyIsKeyCreate( key , pivot , value , keyCreate ) ) ;
	}

	@Override
	public long llen ( T key ) {
		return combination( LLEN , DataConversionUtils.getDataConversionListIsKey( key , keyCreate ) ) ;
	}

	@Override
	public long llen ( String key ) {
		return combination( LLEN , DataConversionUtils.keyIsKeyCreate( key ,keyCreate ) ) ;
	}

	@Override
	public long llen ( long key ) {
		return combination( LLEN , DataConversionUtils.keyIsKeyCreate( key ,keyCreate ) ) ;
	}

	@Override
	public T lpop ( T key ) {
		return combination( LPOP , DataConversionUtils.getDataConversionListKey( key , keyCreate ) ) ;
	}

	@Override
	public T lpop ( String key ) {
		return combination( LPOP , DataConversionUtils.keyIsKeyCreate( key ,keyCreate ) ) ;
	}

	@Override
	public T lpop ( long key ) {
		return combination( LPOP , DataConversionUtils.keyIsKeyCreate( key , keyCreate ) ) ;
	}

	@Override
	public long lpush ( T keyValue ) {
		return combination( LPUSH , DataConversionUtils.getDataConversionListIsKey( keyValue , keyCreate ) ) ;
	}

	@Override
	public long lpush ( List< T > keyValue ) {
		return combination( LPUSH_MORE , DataConversionUtils.getDataConversionListKey( keyValue.get( 0 ) , keyCreate ) , keyValue , null ) ;
	}

	@Override
	public long lpushx ( T keyValue ) {
		return combination( LPUSHX , DataConversionUtils.getDataConversionList( keyValue , keyCreate ) ) ;
	}

	@Override
	public List< T > lrange ( T key , int start , int end ) {
		return combination( LRANGE , DataConversionUtils.getDataConversionListKey( key , start , end , keyCreate ) ) ;
	}

	@Override
	public List< T > lrange ( String key , int start , int end ) {
		return combination( LRANGE , DataConversionUtils.keyIsKeyCreate( key , start , end  ,keyCreate) ) ;
	}

	@Override
	public List< T > lrange ( long key , int start , int end ) {
		return combination( LRANGE , DataConversionUtils.keyIsKeyCreate( key , start , end  ,keyCreate) ) ;
	}

	@Override
	public long lren ( String key , int count , String value ) {
		return combination( LREM, DataConversionUtils.keyIsKeyCreate( key , count , value  ,keyCreate) ) ;
	}

	@Override
	public boolean lset ( T key , int index ) {
		List<DataConversion> dataList = DataConversion.getListDataConversion( );
		dataList.get( 0 ) .setObjectAndKeyCreate( key , keyCreate  );
		dataList.get( 1 ) .setObjectAndKeyCreate( index  );
		dataList.get( 2 ) .setObjectAndKeyCreate( key  );
		return combination( LSET, dataList ) ; 
	}

	@Override
	public boolean ltrim ( T key , int start , int stop ) {
		return combination( LTRIM, DataConversionUtils.keyIsKeyCreate( key , start , stop ,keyCreate ) ) ;
	}

	@Override
	public boolean ltrim ( String key , int start , int end ) {
		return combination( LREM, DataConversionUtils.keyIsKeyCreate( key , start , end ,keyCreate ) ) ;
	}

	@Override
	public boolean ltrim ( long key , int start , int end ) {
		return combination( LTRIM, DataConversionUtils.keyIsKeyCreate( key , start , end ,keyCreate ) ) ;
	}

	@Override
	public T rpop ( T key ) {
		return combination( RPOP, DataConversionUtils.getDataConversionListKey( key , keyCreate )) ;
	}

	@Override
	public T rpop ( long key ) {
		return combination( RPOP, DataConversionUtils.keyIsKeyCreate( key ,keyCreate) ) ;
	}

	@Override
	public T rpop ( String key ) {
		return combination( RPOP, DataConversionUtils.keyIsKeyCreate( key  ,keyCreate) ) ;
	}

	@Override
	public T rpoplpush ( T source , T destination ) {
		List< DataConversion > dataList = DataConversion.getListDataConversion( );
		dataList.get( 0 ).setObjectAndKeyCreate( source , this.keyCreate ) ;
		dataList.get( 1 ).setObjectAndKeyCreate( source , this.keyCreate ) ;
		return combination( RPOPLPUSH , dataList );
	}

	@Override
	public T rpoplpush ( long source , long destination ) {
		return combination( RPOPLPUSH , DataConversionUtils.keyIsKeyCreate( source , destination  , keyCreate)  ) ;
	}

	@Override
	public T rpoplpush ( String source , String destination ) {
		return combination( RPOPLPUSH , DataConversionUtils.keyIsKeyCreate( source , destination  , keyCreate)  )  ;
	}

	@Override
	public long rpush ( T keyValue ) {
		return combination( RPUSH , DataConversionUtils.getDataConversionListIsKey( keyValue , keyCreate ));
	}

	@Override
	public long rpush ( List< T > keyValue ) {
		return combination( RPUSH_MORE , DataConversionUtils.getDataConversionListKey( keyValue.get( 0 ) , keyCreate ) , keyValue , null);
	}

	@Override
	public long rpushx ( T keyValue ) {
		return  combination( RPUSHX , DataConversionUtils.getDataConversionListIsKey( keyValue , keyCreate ));
	}

}
