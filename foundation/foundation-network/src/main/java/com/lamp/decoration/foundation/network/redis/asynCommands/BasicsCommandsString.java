package com.lamp.decoration.foundation.network.redis.asynCommands;


import com.lamp.decoration.foundation.network.redis.entity.AsynResult;

public interface BasicsCommandsString {

	
    AsynResult<Boolean> del( String key );
	
	AsynResult<Boolean> exists( String key );
	
	AsynResult<Boolean> expire( String key , int seconds );
	
	AsynResult<Boolean> pexpire( String key , int milliseconds );
	
	AsynResult<Boolean> expireat( String key , long timestamp );
	
	AsynResult<Boolean> pexpireat( String key , long milliseconds );
	
	AsynResult<Boolean> persist( String key );
	
	AsynResult<Boolean> rename( String key , String newkey );
		
	AsynResult<Boolean> renamenx( String key , String newkey );
		
}
