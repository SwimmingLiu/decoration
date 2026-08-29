package com.lamp.decoration.foundation.network.redis.commands;

import java.util.List;

import com.lamp.decoration.foundation.network.redis.create.KeyCreate;
import com.lamp.decoration.foundation.network.redis.protocol.DataConversion;


public class StringCommandsImpl< T > extends AbstractLedis< T > implements StringCommands< T > {
	
	public StringCommandsImpl( KeyCreate< T > ledis ,String dataSource) {
		super( ledis ,dataSource);
	}

	@Override
	public T get( T key ) {
		List<DataConversion> list = DataConversion.getListDataConversion( );
		list.get( 0 ) .setObjectAndKeyCreate( key , keyCreate );
		return combination(StringCommandsElement.GET_ELEMENT, list );
	}

	@Override
	public T get( String key ) {
		List<DataConversion> list = DataConversion.getListDataConversion( );
		list.get( 0 ) .setObjectAndKeyCreate( key  ,keyCreate);
		return combination(StringCommandsElement.GET_ELEMENT, list );
	}

	@Override
	public T get( long key ) {
		List<DataConversion> list = DataConversion.getListDataConversion( );
		list.get( 0 ) .setObjectAndKeyCreate( key  ,keyCreate);
		return combination(StringCommandsElement.GET_ELEMENT, list );
	}

	@Override
	public T getset( T key ) {
		List<DataConversion> list = DataConversion.getListDataConversion( );
		list.get( 0 ) .setObjectAndKeyCreate( key , keyCreate );
		list.get( 1 ) .setObjectAndKeyCreate( key  );
		return combination(StringCommandsElement.GETSET_ELEMENT, list );
	}

	@Override
	public List< T > mget( List< T > keys ) {
		return combination( StringCommandsElement.MGET_ELEMENT ,  DataConversion.getListDataConversion( ) , keys );
	}

	@Override
	public List< T > mgetstring( List< String > keys ) {
		return combinationString( StringCommandsElement.MGET_ELEMENT ,  DataConversion.getListDataConversion( ) , keys );
	}
	@Override
	public List< T > mgetnumber( List< ? extends Number > keys ) {
		return combinationLong( StringCommandsElement.MGET_ELEMENT ,  DataConversion.getListDataConversion( ) , keys );
	}

	@Override
	public T set( T key ) {
		List<DataConversion> list = DataConversion.getListDataConversion( );
		list.get( 0 ) .setObjectAndKeyCreate( key , keyCreate );
		list.get( 1 ).setObjectAndKeyCreate( key );
		return combination(StringCommandsElement.SET_ELEMENT, list );
	}

	@Override
	public Boolean setnx( T key ) {
		List<DataConversion> list = DataConversion.getListDataConversion( );
		list.get( 0 ) .setObjectAndKeyCreate( key , keyCreate );
		list.get( 1 ).setObjectAndKeyCreate( key );
		return combination(StringCommandsElement.SETNX_ELEMENT, list );
	}

	@Override
	public Boolean setnx( T key , Object o ) {
		List<DataConversion> list = DataConversion.getListDataConversion( );
		list.get( 0 ) .setObjectAndKeyCreate( key , keyCreate );
		list.get( 1 ).setObjectAndKeyCreate( o );
		return combination(StringCommandsElement.SETNX_ELEMENT, list );
	}

	@Override
	public Boolean setex( T key, long  seconds ) {
		List<DataConversion> list = DataConversion.getListDataConversion( );
		list.get( 0 ) .setObjectAndKeyCreate( key , keyCreate );
		list.get( 1 ).setObjectAndKeyCreate( seconds );
		list.get( 2 ).setObjectAndKeyCreate( key );
		return combination(StringCommandsElement.SETEX_ELEMENT, list );
	}

	@Override
	public Boolean setex( T key , Object o , long  seconds) {
		List<DataConversion> list = DataConversion.getListDataConversion( );
		list.get( 0 ) .setObjectAndKeyCreate( key , keyCreate );
		list.get( 1 ).setObjectAndKeyCreate( seconds );
		list.get( 2 ).setObjectAndKeyCreate( o );
		return combination(StringCommandsElement.SETEX_ELEMENT, list );
	}

	@Override
	public Boolean psetex( byte[ ] key , long milliseconds , byte[ ] value ) {
		// TODO 自动生成的方法存根
		return false;

	}

	@Override
	public Boolean mset( List< T > tuple ) {
		 return combination( StringCommandsElement.MSET_ELEMENT ,  DataConversion.getListDataConversion( ) , tuple );

	}

	@Override
	public Boolean msetnx( List< T > tuple ) {
		return combination( StringCommandsElement.MSETNX_ELEMENT ,  DataConversion.getListDataConversion( ) , tuple , this.keyCreate);
	}

	@Override
	public Long incr(T key ) {
		List<DataConversion> list = DataConversion.getListDataConversion( );
		list.get( 0 ) .setObjectAndKeyCreate( key , keyCreate );
		return combination(StringCommandsElement.INCR_ELEMENT, list );
	}

	@Override
	public Long incrby(T key, long value ) {
		List<DataConversion> list = DataConversion.getListDataConversion( );
		list.get( 0 ) .setObjectAndKeyCreate( key , keyCreate );
		list.get( 1 ).setObjectAndKeyCreate( value );
		return combination(StringCommandsElement.INCRBY_ELEMENT, list );
	}

	@Override
	public Double incrby(T key, double value ) {
		// TODO 自动生成的方法存根
		return null;
	}

	@Override
	public Long decr( T key ) {
		List<DataConversion> list = DataConversion.getListDataConversion( );
		list.get( 0 ) .setObjectAndKeyCreate( key , keyCreate );
		return combination(StringCommandsElement.DECR_ELEMENT, list );
	}

	@Override
	public Long decrby(T key, long value ) {
		List<DataConversion> list = DataConversion.getListDataConversion( );
		list.get( 0 ) .setObjectAndKeyCreate( key , keyCreate );
		list.get( 1 ).setObjectAndKeyCreate( value );
		return combination(StringCommandsElement.DECRBY_ELEMENT, list );
	}

	@Override
	public Long strlen( T key ) {
		List<DataConversion> list = DataConversion.getListDataConversion( );
		list.get( 0 ) .setObjectAndKeyCreate( key , keyCreate );
		return combination(StringCommandsElement.STRLEN_ELEMENT, list );
	}

}
