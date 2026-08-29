package com.lamp.decoration.foundation.network.redis.asynCommands;


import com.lamp.decoration.foundation.network.redis.entity.AsynResult;

public interface BasicsCommandsObject< T > {
	AsynResult<Boolean> del( T key );
	
	AsynResult<Boolean> exists( T key );
	
	AsynResult<Boolean> expire( T key , int seconds );
	
	AsynResult<Boolean> pexpire( T key , int milliseconds );
	
	AsynResult<Boolean> expireat( T key , long timestamp );
	
	AsynResult<Boolean> pexpireat( T key , long milliseconds );

	AsynResult<Boolean> persist( T key );
	
	AsynResult<Boolean> rename( T key ,T newkey );
	
	AsynResult<Boolean> renamenx( T key ,T newkey );
}
