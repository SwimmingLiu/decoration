package com.lamp.decoration.foundation.network.redis.net.netty;

import java.nio.charset.Charset;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import com.alibaba.fastjson.JSON;
import com.lamp.ledis.net.netty.RedisNettyProtocol.IntRedisNettyProtocol;
import com.lamp.ledis.net.netty.RedisNettyProtocol.ListRedisNetProtocol;
import com.lamp.ledis.net.netty.RedisNettyProtocol.SetRedisNetProtocol;
import com.lamp.ledis.net.netty.RedisNettyProtocol.StateRedisNettyProtocol;
import com.lamp.ledis.net.netty.RedisNettyProtocol.StringRedisNettyProtocol;

public class RedisNettyProtocolTest {
	
	
	ByteBuf buf = Unpooled.buffer();
	
	@Test
	public void exceptionRedisNettyProtocol() {
		StateRedisNettyProtocol protocol = new  RedisNettyProtocol.StateRedisNettyProtocol();
		String exception = "-WRONGTYPE Operation against a key holding the wrong kind of value\r\n";
		buf.writeCharSequence(exception, Charset.defaultCharset());
		protocol.distinguish(buf);
		Assert.assertTrue(protocol.isException());
		Assert.assertEquals(exception.substring(1,exception.length()-2), protocol.getExceptionContent());
		protocol.getData();
		
		exception = "-ERR unknown command 'foobar'\r";
		buf.writeCharSequence(exception, Charset.defaultCharset());
		protocol.distinguish(buf);
		buf.writeByte('\n');
		protocol.distinguish(buf);
		Assert.assertTrue(protocol.isException());
		Assert.assertEquals(exception.substring(1,exception.length()-1), protocol.getExceptionContent());
		
	}
	
	@Test
	public void stateRedisNettyProtocolTest() {
		StateRedisNettyProtocol protocol = new  RedisNettyProtocol.StateRedisNettyProtocol();
		buf.writeByte('*');
		buf.writeByte('O');
		protocol.distinguish(buf);
		buf.writeByte('K');
		buf.writeByte('\r');
		buf.writeByte('\n');
		protocol.distinguish(buf);
		Boolean isData = protocol.getData();
		Assert.assertTrue(isData);
	}
	
	
	@Test
	public void intRedisNettyProtocolTest() {
		IntRedisNettyProtocol protocol = new RedisNettyProtocol.IntRedisNettyProtocol();
		buf.writeCharSequence(":123", Charset.defaultCharset());
		protocol.distinguish(buf);
		buf.writeCharSequence("456\r\n", Charset.defaultCharset());
		protocol.distinguish(buf);
		Long data = protocol.getData();
		Assert.assertEquals(data, Long.valueOf(123456L));
		
		buf.writeCharSequence(":-123", Charset.defaultCharset());
		protocol.distinguish(buf);
		buf.writeCharSequence("456\r\n", Charset.defaultCharset());
		protocol.distinguish(buf);
		data = protocol.getData();
		Assert.assertEquals(data, Long.valueOf(-123456L));
	}
	
	
	@Test
	public void stringRedisNettyProtocolTest() {
		StringRedisNettyProtocol protocol = new RedisNettyProtocol.StringRedisNettyProtocol();
		buf.writeCharSequence("$10\r\n", Charset.defaultCharset());
		protocol.distinguish(buf);
		
		buf.writeCharSequence("123456", Charset.defaultCharset());
		protocol.distinguish(buf);
		buf.writeCharSequence("7890\r\n", Charset.defaultCharset());
		protocol.distinguish(buf);
		CharSequence data = protocol.getData().readCharSequence(10, Charset.defaultCharset());
		Assert.assertEquals("1234567890", data);
	}
	
	@Test
	public void setRedisNetProtocolTest() {
		SetRedisNetProtocol protocol = new RedisNettyProtocol.SetRedisNetProtocol();
		buf.writeCharSequence("*10\r\n", Charset.defaultCharset());
		buf.writeCharSequence("$10\r\n", Charset.defaultCharset());
		protocol.distinguish(buf);
		
		buf.writeCharSequence("123456", Charset.defaultCharset());
		protocol.distinguish(buf);
		buf.writeCharSequence("7890\r\n", Charset.defaultCharset());
		protocol.distinguish(buf);
		for(int i = 0 ; i<9; i++) {
			buf.writeCharSequence("$10\r\n1234567890\r\n", Charset.defaultCharset());
		}
		protocol.distinguish(buf);
		Set<String> data = protocol.getData();
		Set<String> set = new HashSet<>();
		for(int i = 0 ; i<10;i++) {
			set.add("1234567890");
		}
		Assert.assertEquals(data, set);
	}
	
	@Test
	public void listRedisNetProtocolTest() {
		ListRedisNetProtocol protocol = new RedisNettyProtocol.ListRedisNetProtocol();
		buf.writeCharSequence("*10\r\n", Charset.defaultCharset());
		buf.writeCharSequence("$10\r\n", Charset.defaultCharset());
		protocol.distinguish(buf);
		
		buf.writeCharSequence("123456", Charset.defaultCharset());
		protocol.distinguish(buf);
		buf.writeCharSequence("7890\r\n", Charset.defaultCharset());
		protocol.distinguish(buf);
		for(int i = 0 ; i<9; i++) {
			buf.writeCharSequence("$10\r\n1234567890\r\n", Charset.defaultCharset());
		}
		protocol.distinguish(buf);
		protocol.getData();
		ByteBuf newbuf = ByteBufManage.getReadByteBuf();
		CharSequence s = newbuf.getCharSequence(0, newbuf.writerIndex(), Charset.defaultCharset());
		List<String> list = JSON.parseArray((String)s, String.class);
		list.get(1);
	}
	
}
