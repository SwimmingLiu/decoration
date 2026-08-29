package com.lamp.decoration.foundation.network.redis.commands;

public interface BasicsCommandsObject< T > {
	boolean del( T key );
	
	boolean exists( T key );
	
	boolean expire( T key , int seconds );
	
	boolean pexpire( T key , int milliseconds );
	
	boolean expireat( T key , long timestamp );
	
	boolean pexpireat( T key , long milliseconds );

	boolean persist( T key );
	
	boolean rename( T key ,T newkey );
	
	boolean renamenx( T key ,T newkey );
}
