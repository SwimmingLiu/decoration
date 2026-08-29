package com.lamp.decoration.foundation.network.redis.net.netty;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayDeque;
import java.util.List;


import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;

import com.lamp.decoration.foundation.network.redis.commands.CombinationElement;
import com.lamp.decoration.foundation.network.redis.protocol.EecutionMode;

public class NettyRedisEncoder extends ChannelDuplexHandler  {
	
	private final static int INITIAL_CAPACITY = 1024*1024*2;
	
	private final static int MAX_CAPACITY = INITIAL_CAPACITY*4;

	private final ArrayDeque<ResponseFuture> arrayDeque = new ArrayDeque<>();

	private ResponseFuture firstObject;

	private EncoderOutputStream outputStream = new EncoderOutputStream();

	@Override
	public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
		ResponseFuture responseFuture = (ResponseFuture)msg;
		arrayDeque.add(responseFuture);
		outputStream.ctx = ctx;
		outputStream.byteBuf = ctx.alloc().ioBuffer(INITIAL_CAPACITY, INITIAL_CAPACITY);
		CombinationElement ce = responseFuture.getCombinationElement();
		EecutionMode ecutionMode = ce.getAgreementPretreatment( ).getExecutioMode( ) ;
		if(ecutionMode == EecutionMode.DEFAULT) {
			ce.getAgreementPretreatment( ).perteatmentOut( outputStream , 0 ) ;
			AgreementPretreatment.ListReferenceAgreementPretreatment( outputStream , responseFuture.getDataList() , ce.getAgreementPretreatment( ).getLength( ) ) ;
		}else {
			List<Object> objectList = responseFuture.getObjectList();
			ce.getAgreementPretreatment( ).perteatmentOut( outputStream ,ecutionMode.getMultiple( ) * objectList.size( ) + ecutionMode.getBase( ) ) ;
			if ( ecutionMode == EecutionMode.STRING_MGET || ecutionMode == EecutionMode.MAP_MGET ) {
				AgreementPretreatment.ListReferenceAgreementPretreatment( outputStream , responseFuture.getDataList() , objectList , 1 ,responseFuture.getCodeKeyCreate() ) ;
			} else if ( ecutionMode == EecutionMode.STRING_MSET || ecutionMode == EecutionMode.MAP_MSET ) {
				AgreementPretreatment.HashReferenceAgreementPretreatment( outputStream , responseFuture.getDataList() , objectList , responseFuture.getCodeKeyCreate() ) ;
			} else if ( ecutionMode == EecutionMode.MAP_MSET ) {

			}
		}
		ctx.flush();
		
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		if(firstObject == null) {
			firstObject = arrayDeque.getLast();
		}
        if (msg instanceof ByteBuf) {
        	for( ; ; ) {
        		
        	}
        }
        firstObject = null;
    }
	
	static class EncoderOutputStream extends OutputStream{

		ChannelHandlerContext ctx;
		
		ByteBuf byteBuf;
		
		public void write(byte b[]) throws IOException {
			if( byteBuf.writableBytes() < b.length ) {
				ctx.write(byteBuf);
				byteBuf = ctx.alloc().ioBuffer(INITIAL_CAPACITY, MAX_CAPACITY);
				if(INITIAL_CAPACITY < b.length ) {
					ctx.write( ctx.alloc().ioBuffer(b.length, b.length).setBytes(0, b));
				}
				
			}
			byteBuf.writeBytes(b);
	    }
		
		
		@Override
		public void write(int b) throws IOException {
			if( byteBuf.writableBytes() == 0 ) {
				ctx.write(byteBuf);
				byteBuf = ctx.alloc().ioBuffer(INITIAL_CAPACITY, MAX_CAPACITY);
			}
			byteBuf.writeByte(b);
			
		}
		
	}
	

}
