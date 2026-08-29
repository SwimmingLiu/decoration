package com.lamp.decoration.foundation.network.redis.net.netty;

import java.nio.charset.Charset;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class ByteBufManage {

	final static ThreadLocal<ByteBuf> WRITE_BYTE_BUF_THREAD_LOAD = new ThreadLocal<>();
	
	final static ThreadLocal<ByteBuf> READ_BYTE_BUF_THREAD_LOAD = new ThreadLocal<>();
	
	final static int READ_BYTE_BUF_SIZE = 1<<23;//8M
	
	final static int WRITE_BYTE_BUF_SIZE = 1<<22;//4M
	
	public static final ByteBuf getReadByteBuf() {
		return getBuf(READ_BYTE_BUF_THREAD_LOAD, READ_BYTE_BUF_SIZE);
	}
	
	public static final ByteBuf getWriteByteBuf() {
		return getBuf(WRITE_BYTE_BUF_THREAD_LOAD, WRITE_BYTE_BUF_SIZE);
	}
	
	
	private static ByteBuf getBuf(ThreadLocal<ByteBuf> threadLoad , int size) {
		ByteBuf buf = threadLoad.get();
		if(buf == null) {
			buf = Unpooled.directBuffer(size, size);
			threadLoad.set(buf);
		}
		return buf;
	}
	
	public static final String getString() {
		ByteBuf byteBuf = getReadByteBuf();
		CharSequence cs = byteBuf.readCharSequence(byteBuf.writerIndex(), Charset.defaultCharset());
		byteBuf.readerIndex(0).writerIndex(0);
		return (String)cs;
	}
	
	public static final void setByte(byte b) {
		getReadByteBuf().writeByte(b);
	}
	
	public static final void setByteArray(byte[] b) {
		getReadByteBuf().writeBytes(b);
	}
}
