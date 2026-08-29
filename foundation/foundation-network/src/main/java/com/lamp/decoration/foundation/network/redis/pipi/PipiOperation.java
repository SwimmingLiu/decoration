package com.lamp.decoration.foundation.network.redis.pipi;

public interface PipiOperation< K , T > {

	
	public PipiRedisCommands< K , T > pipoOpen();
	
	public void pipoClose();
}
