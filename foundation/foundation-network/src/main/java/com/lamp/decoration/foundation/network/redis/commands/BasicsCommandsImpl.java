package com.lamp.decoration.foundation.network.redis.commands;


import com.lamp.decoration.foundation.network.redis.create.KeyCreate;
import com.lamp.decoration.foundation.network.redis.utils.DataConversionUtils;

public class BasicsCommandsImpl<T > extends AbstractLedis<T > implements BasicsCommands<T > , BasicsCommandsElement{

	public BasicsCommandsImpl(KeyCreate< T > keyCreate, String dataSource) {
		super( keyCreate , dataSource ) ;
	}

	@Override
	public boolean del ( String key ) {
		return combination( DEL , DataConversionUtils.keyIsKeyCreate( key , keyCreate ) ) ;
	}

	@Override
	public boolean exists ( String key ) {
		return combination( EXISTS , DataConversionUtils.keyIsKeyCreate( key , keyCreate ) ) ;
	}

	@Override
	public boolean expire ( String key , int seconds ) {
		return combination( EXPIRE , DataConversionUtils.keyIsKeyCreate( key , keyCreate ) ) ;
	}

	@Override
	public boolean pexpire ( String key , int milliseconds ) {
		return combination( PEXPIRE , DataConversionUtils.keyIsKeyCreate( key , keyCreate )) ;
	}

	@Override
	public boolean expireat ( String key , long timestamp ) {
		return combination( EXPIREAT , DataConversionUtils.getDataConversionList( key , timestamp ) ) ;
	}

	@Override
	public boolean pexpireat ( String key , long milliseconds ) {
		return combination( PEXPIREAT , DataConversionUtils.keyIsKeyCreate( key , keyCreate )) ;
	}

	@Override
	public boolean persist ( String key ) {
		return combination( PERSIST , DataConversionUtils.keyIsKeyCreate( key , keyCreate ) ) ;
	}

	@Override
	public boolean rename ( String key , String newkey ) {
		return combination( RENAME , DataConversionUtils.keyIsKeyCreate( key , newkey , keyCreate ) ) ;
	}

	@Override
	public boolean renamenx ( String key , String newkey ) {
		return combination( RENAMENX , DataConversionUtils.keyIsKeyCreate( key , newkey , keyCreate ) ) ;
	}


}
