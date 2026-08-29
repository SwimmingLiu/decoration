package com.lamp.decoration.foundation.network.redis.commands;

public interface BasicsCommandsLong {

	
	boolean del( Long key );
	
	boolean exists( Long key );
	
	
	boolean expire( Long key , int seconds );
	
	
	boolean pexpire( Long key , int milliseconds );
	
	boolean expireat( Long key , long timestamp );
	
	
	boolean pexpireat( Long key , long milliseconds );
	
	
	boolean persist( String key );
	
	
	boolean rename( Long key , Long newkey );

	
	boolean renamenx( Long key , Long newkey );
}
