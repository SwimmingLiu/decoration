package com.lamp.decoration.foundation.network.redis.net;

public enum ConnectionState {

	NEW,
	
	POOLING,
	
	USER_OBTAIN,
	
	USER_RETURN,
	
	PING,
	
	TERMINATED;
}
