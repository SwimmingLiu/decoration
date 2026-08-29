package com.lamp.decoration.foundation.network.redis.net;

import org.junit.Test;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class NettyTest {

	@Test
	public void testBufbyte() {
		ByteBuf buf = Unpooled.buffer(1024);
		for (int i = 0; i < 100; i++) {
			buf.writeByte(i);
		}
		
		buf.asReadOnly();
	}
	
	
}
