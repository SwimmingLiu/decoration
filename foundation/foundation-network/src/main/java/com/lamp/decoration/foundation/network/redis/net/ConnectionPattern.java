package com.lamp.decoration.foundation.network.redis.net;

public interface ConnectionPattern {
	
	public Connection getConnection() throws Exception ;
	
	public void setConnection(Connection conn)  ;
	
	void ping();
}
