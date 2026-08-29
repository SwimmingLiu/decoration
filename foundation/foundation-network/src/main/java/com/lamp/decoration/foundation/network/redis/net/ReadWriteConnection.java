package com.lamp.decoration.foundation.network.redis.net;

import java.util.ArrayList ;
import java.util.List ;
import java.util.concurrent.atomic.AtomicLong ;

public class ReadWriteConnection {

	/*private ConnectionPattern write;
	
	private List<Connection> read;
	
	private int readLength;
	
	private AtomicLong readFrequency;
	
	public ReadWriteConnection(NetConfigure configure ,PoolConfig poolConfig , List<PoolConfig> poolConfigList){
		read = new ArrayList<>( poolConfigList.size( ) );
		if( poolConfig.getConnectionMode( ) == 0){
			write  = new NoLockConnectionPattern(configure, poolConfig);
			for(PoolConfig pc : poolConfigList){
				//read.add( new NoLockConnectionPattern(configure, poolConfig) );
			}
		}else{
			write  =  new LocalConnectionPattern( configure , poolConfig );
			for(PoolConfig pc : poolConfigList){
				
			}
		}
	}
	
	
	private void createNoLock(){
		
	}
	
	private Connection getReadConnection(){
		return read.get( (int)(readFrequency.incrementAndGet( )%readLength) );
	}*/
	
}
