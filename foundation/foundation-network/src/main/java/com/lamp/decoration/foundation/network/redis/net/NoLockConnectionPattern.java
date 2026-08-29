package com.lamp.decoration.foundation.network.redis.net;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class NoLockConnectionPattern implements ConnectionPattern{

	/**
	 * 暂时不想写那么复杂
	 */
	private final LinkedBlockingQueue<Connection> lbq = new LinkedBlockingQueue<Connection>();
	
	private final Connection[] connection ;
	
	private int maxWaitMillis;
	
	public NoLockConnectionPattern(NetConfigure configure , PoolConfig poolConfig ){
		this.maxWaitMillis = poolConfig.getMaxWaitMillis( );
		connection = new Connection[ poolConfig.getMaxTotal() ];
		for(int i = 0 ; i < poolConfig.getMaxTotal() ; i++){
			lbq.add( new BIOConnection(configure));
		}
	}
	
	@Override
	public Connection getConnection() throws Exception {
		return lbq.poll(this.maxWaitMillis, TimeUnit.MINUTES);
	}
	@Override
	public void setConnection(Connection conn) {
		if( conn == null ) return;
		lbq.add( conn );
	}

	@Override
	public void ping ( ) {
		// TODO 自动生成的方法存根
		
	}
}
