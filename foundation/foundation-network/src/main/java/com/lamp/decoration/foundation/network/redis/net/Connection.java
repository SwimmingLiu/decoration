package com.lamp.decoration.foundation.network.redis.net;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;

import com.lamp.decoration.foundation.network.redis.utils.LedisInputStream;


public interface Connection {

	
	public LedisInputStream getInputStream();
	
	public OutputStream getOutputStream();
	
	public void close() throws IOException;
	
	public void setOperationTime();
	
	public long getOperationTime();
	
	ConnectionState getConnectionState();
	
	boolean setConnectionState(ConnectionState expect, ConnectionState update);
	
	public ByteBuffer getBuffer();
	
	public boolean isConnect();
	
	public void connect();
	
}
