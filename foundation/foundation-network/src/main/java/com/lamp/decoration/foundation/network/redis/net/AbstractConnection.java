package com.lamp.decoration.foundation.network.redis.net;

import java.nio.ByteBuffer;
import java.util.concurrent.atomic.AtomicReference ;

public abstract class AbstractConnection implements Connection{

	NetConfigure configure;
	private ByteBuffer buffer;
	
	private long operationTime;
	
	
	private AtomicReference< ConnectionState > connectionState = new AtomicReference<>( ConnectionState.NEW );
	
	private final long startTime = System.currentTimeMillis();
	
	AbstractConnection(NetConfigure configure){
		this.configure = configure;
		//this.buffer    = ByteBuffer.allocate( 8192 );
	}
	
	public void setOperationTime(){
		this.operationTime = System.currentTimeMillis();
	}
	public long getOperationTime(){
		return this.operationTime;
	}

	public ConnectionState getConnectionState(){
		return this.connectionState.get( );
	}
	
	public boolean setConnectionState(ConnectionState expect, ConnectionState update){
		return connectionState.compareAndSet( expect , update );
	}
	
	public ByteBuffer getBuffer(){
		return this.buffer;
	}
	
	public long  getStartTime(){
		return startTime;
	}
}