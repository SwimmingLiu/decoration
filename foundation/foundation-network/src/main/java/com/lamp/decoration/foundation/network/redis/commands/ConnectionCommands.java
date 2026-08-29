package com.lamp.decoration.foundation.network.redis.commands;

public interface ConnectionCommands {
	
	boolean auth( String auth );
	
	String echo( String message );
	
	String ping( String message );
	
	boolean quit();
	
	boolean select( int index );
}
