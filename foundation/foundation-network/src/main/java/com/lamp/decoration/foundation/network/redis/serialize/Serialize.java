package com.lamp.decoration.foundation.network.redis.serialize;

import java.nio.ByteBuffer ;

public interface Serialize {

	
	public byte[]     executeByte(Object o);
	
	public String     executeString(Object o);
	
	public ByteBuffer execute(Object o);
}
