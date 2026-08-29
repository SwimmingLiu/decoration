package com.lamp.decoration.foundation.network.redis.commands;


import com.lamp.decoration.foundation.network.redis.create.KeyCreate;
import com.lamp.decoration.foundation.network.redis.protocol.DataConversion;
import com.lamp.decoration.foundation.network.redis.utils.DataConversionUtils;

public class ConnectionCommandsImpl extends AbstractLedis< String > implements ConnectionCommands , ConnectionCommandsElement{


	public ConnectionCommandsImpl(KeyCreate< String > keyCreate, String dataSource) {
		super( keyCreate , dataSource ) ;
	}

	@Override
	public boolean auth ( String auth ) {
		return combination( AUTH , DataConversionUtils.getDataConversionList( auth ) ) ;
	}

	@Override
	public String echo ( String message ) {
		return combination( ECHO , DataConversionUtils.getDataConversionList( message ) ) ;
	}

	@Override
	public String ping (String message ) {
		return combination( PING , DataConversionUtils.getDataConversionList( message ) ) ;
	}

	@Override
	public boolean quit ( ) {
		return combination( QUIT , DataConversion.getListDataConversion( ) ) ;
	}

	@Override
	public boolean select ( int index ) {
		return combination( SELECT ,  DataConversionUtils.getDataConversionList( index )) ;
	}

	
}
