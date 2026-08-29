package com.lamp.decoration.foundation.network.redis.net.netty;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class NettyProtocolRecord {

	
	private int totalLine;
	
	private int readLine;
	
	private int dataLineLength;
	
	private int surplusLength;
	
	private boolean isEnough;
	
	private ByteBuf surplus = Unpooled.buffer(1024<< 9);
	
	private List<Object> list;
	
	private ByteBuf readStatusOrInt = Unpooled.buffer( 32 );

	
	public boolean isComplete() {
		return totalLine == readLine;
	}
	
	
	public boolean isNotEnough() {
		return this.isEnough;
	}
	
	public void readStatusOrInt() {
		
	}
	
	public boolean readOKCrLf(ByteBuf buf) {
		boolean bo = false;
		int readerLength = buf.writerIndex() - buf.readerIndex();
		ByteBuf tem;
		if( readStatusOrInt.writerIndex() == 0 && readerLength > 4 ) {
			tem = buf;
		}else {
			buf.readBytes(readStatusOrInt, readStatusOrInt.writerIndex(), readerLength);
			if( readStatusOrInt.writerIndex() < 4) {
				
			}
			tem = readStatusOrInt;
		}
		
		if( tem.readByte() == 'O' && tem.readByte() =='K' ){
			bo = true;
		}
		if (tem.readByte() == '\r') {
			if (tem.readByte() != '\n') {
				//异常
			}
		}else{
			//异常
		}
		this.isEnough = true;
		
		return bo;
	}
	
	
	long value = 0;
	boolean isNeg ;
	boolean isStart =false ;
	public long resolveIntNetProtocol(ByteBuf buf) {
		int b ,length = buf.writerIndex() - buf.readerIndex();
		if( !isStart ) {
			b = buf.readByte();
			length--;
			isNeg = b == '-';
			if (!isNeg) {
				value = value * 10 + b - '0';
			}
		}
		if(length == 0) {
			
		}
		for( ; ; ) {
			b = buf.readByte();
			if (b == '\r') {
				if (buf.readByte() != '\n') {

				}
				break;
			} else {
				value = value * 10 +  b - '0';
			}
			if(length-- == 0) {
				
			}
		}
		return (isNeg ? -value : value);
	}
	
	public void resolveIntNetProtocolSring() {
		//第一步，找到长度，等于找到int
		// 第二部，找到字符串
		
	}
	
	
	private interface Protocol<T>{
		
		public boolean isEnough();
		
		public T analysis(ByteBuf buf ,ByteBuf bbb);
	}
}
