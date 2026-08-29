package com.lamp.decoration.foundation.network.redis.serialize;

public interface Deserialize {

	public <T> T execute(byte[] by , Class<T> clazz) ;
}
