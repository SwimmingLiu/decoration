package com.lamp.decoration.foundation.network.redis.net;

import java.util.Iterator ;
import java.util.List ;
import java.util.Map ;
import java.util.concurrent.ConcurrentHashMap ;

public class ConnectionFactory {

	private final  static ConnectionFactory cf = new ConnectionFactory();
	
	private final Map<String , ConnectionPattern> connectionPatternMap = new ConcurrentHashMap< String , ConnectionPattern >();
	

	private ConnectionPattern connectionPattern;
	
	
	private ConnectionCheck connectionCheck =new ConnectionCheck( );
	
	private volatile boolean connectionCheckStart = false;
	
	private ConnectionFactory(){
		
	}
	
	public static final ConnectionFactory getInstance(){
		return cf;
	}
	
	public void init(){
		init( new NetConfigure() , new PoolConfig());
	}
	
	
	public void init(NetConfigure configure ,PoolConfig poolConfig ){
		if(connectionPattern == null){
			connectionPattern = new NoLockConnectionPattern(configure, poolConfig);      
			start();                                                                                                                                                   
		}
	}
	
	public void init(NetAndPoolConfig  config , List<NetAndPoolConfig> netConfigureList ){
		if(connectionPattern == null){
			connectionPattern = createConnectionPattern( config.getNetConfigure( ) , config.getPoolConfig( ) );
			for(NetAndPoolConfig netAndPoolConfig : netConfigureList){
				connectionPatternMap.put( netAndPoolConfig.getName( ) , createConnectionPattern( netAndPoolConfig.getNetConfigure( ) , netAndPoolConfig.getPoolConfig( ) ) );
			}
			start();
		}
	}
	
	private ConnectionPattern createConnectionPattern( NetConfigure configure ,PoolConfig poolConfig ){
		if( poolConfig.getConnectionMode( ) == 0){			
			return  new LocalConnectionPattern( configure , poolConfig );
		}else{
			return	new NoLockConnectionPattern(configure, poolConfig);
		}
		
	}
	
	private void start(){
		if( !this.connectionCheckStart){
			connectionCheck.start( );
			this.connectionCheckStart =true;
		}
	}
	public void initCluster(){
		
	}
	
	public Connection getConnection(){
		
		try {
			return connectionPattern.getConnection();
		} catch (Exception e) {			
			e.printStackTrace();
			return null;
		}
	}
	
	
	public ConnectionPattern getConnectionPattern(){
		return this.connectionPattern;
	}
	
	
	public ConnectionPattern getConnectionPattern( String data){
		return this.connectionPatternMap.get( data );
	}
	
	public void setConnection( Connection conn){
		connectionPattern.setConnection( conn );
	}
	
	
	
	class ConnectionCheck extends Thread{

		@Override
		public void run ( ) {
			while( true){
				try{
					while(true){
						ConnectionFactory.this.connectionPattern.ping( );
						Iterator< ConnectionPattern > it = ConnectionFactory.this.connectionPatternMap.values( ).iterator( );
						while ( it.hasNext( ) ) {
							it.next( ).ping( );
						}
					}
				}catch(Exception e){
					
				}
			}
			
		}
		
	}
}
