package com.lamp.decoration.foundation.network.redis.asynCommands;


import com.lamp.decoration.foundation.network.redis.entity.AsynResult;

public interface BasicsCommandsLong {

	
	AsynResult<Boolean> del( Long key );
	
	AsynResult<Boolean> exists( Long key );
	
	
	AsynResult<Boolean> expire( Long key , int seconds );
	
	
	AsynResult<Boolean> pexpire( Long key , int milliseconds );
	
	AsynResult<Boolean> expireat( Long key , long timestamp );
	
	
	AsynResult<Boolean> pexpireat( Long key , long milliseconds );
	
	
	AsynResult<Boolean> persist( String key );
	
	
	AsynResult<Boolean> rename( Long key , Long newkey );

	
	AsynResult<Boolean> renamenx( Long key , Long newkey );
}
