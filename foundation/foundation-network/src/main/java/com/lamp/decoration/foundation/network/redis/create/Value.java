package com.lamp.decoration.foundation.network.redis.create;

import java.nio.ByteBuffer ;

public interface Value<K,V,T> {

	K getKey(ByteBuffer byteBuffer);
	
	V getValue(ByteBuffer byteBuffer);
	
	T setValue(K k , V v);
}
