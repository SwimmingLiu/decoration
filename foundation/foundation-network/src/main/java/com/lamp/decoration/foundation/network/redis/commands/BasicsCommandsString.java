package com.lamp.decoration.foundation.network.redis.commands;

public interface BasicsCommandsString {

	
	boolean del( String key );
	
	boolean exists( String key );
	
	boolean expire( String key , int seconds );
	
	boolean pexpire( String key , int milliseconds );
	
	boolean expireat( String key , long timestamp );
	
	boolean pexpireat( String key , long milliseconds );
	
	boolean persist( String key );
	
	boolean rename( String key , String newkey );
		
	boolean renamenx( String key , String newkey );
		
}
