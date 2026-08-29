package com.lamp.decoration.foundation.network.redis.net;

import java.io.IOException ;
import java.lang.Thread.State ;
import java.util.Iterator ;
import java.util.Map.Entry ;
import java.util.concurrent.ConcurrentHashMap ;
import java.util.concurrent.LinkedBlockingQueue ;

public class LocalConnectionPattern extends AbstractConnectionPattern{
	/**
	 * 不能使用connection,无法替换。如果使用就得上锁或者自旋。不使用可以csa
	 */
	private static final ThreadLocal<Connection>  tl = new ThreadLocal<>();
	
	private final LinkedBlockingQueue<Connection> lbq = new LinkedBlockingQueue<Connection>();
	
	private final ConcurrentHashMap< Thread , Connection >   chmap = new ConcurrentHashMap<>( );
	
	public LocalConnectionPattern(NetConfigure configure, PoolConfig poolConfig) {
		super( ) ;
		this.configure = configure ;
		this.poolConfig = poolConfig ;
	}


	/**
	 * 1. 池子使用安全队列，性能不需要考虑。这个概率有点低。
	 * 2. 连接在ping，获取端会做判断，自旋等待ping执行玩。
	 * 3. 怎么防止链接溢出
	 */
	@Override
	public Connection getConnection() throws Exception {
		// TODO 自动生成的方法存根
		Connection con = tl.get();
		try{
			if( con == null){
				con = getConnectionDirect();
			}else{
				int i = 10;
				for( ; ; ){
					if( con.setConnectionState( ConnectionState.USER_RETURN , ConnectionState.USER_OBTAIN ) ){
						break;
					}
					if( i-- == 0){
						exceptionHandle( con );
						con = getConnectionDirect();
						break;						
					}
				}
			}	
			operation(con );
			return con;
		}catch (Exception e) {
			exceptionHandle( con );
			throw e;
		}
	}
	
	private Connection getConnectionDirect() throws IOException{
		Connection con = null;
		Thread thread = Thread.currentThread( );
		try {
			ConnectionState state = null;
			//看池子里面有没有
			for(;;){//防止队列里面的数据，少于线程获得数。poll去除为null
				if( lbq.isEmpty( )){
					con = new BIOConnection( configure );
					state = ConnectionState.NEW;
				}else{
					//从池子里面取
					con = lbq.poll( );
					if( con != null ){
						state = ConnectionState.POOLING;
					}else{
						continue;
					}
				}
				if( con != null){
					con.setConnectionState( state , ConnectionState.USER_OBTAIN );
					break;
				}
			}		
			connectionInit( con );	
			tl.set( con ) ;
			chmap.put( thread , con ) ;
			return con;
		} catch ( IOException e ) {
			chmap.remove( thread );
			exceptionHandle( con );
			throw e;
		}
	}

	public void exceptionHandle(Connection con){
		if( con != null)
			lbq.add( con );
		//TODO tl.set操作，线程结束才会失败。目前无法预测是否有溢出的可能。
		tl.set( null ) ;
	}
	
	
	public void ping(){
		Iterator< Entry< Thread , Connection > > it = chmap.entrySet( ).iterator( );
		Entry< Thread , Connection > e;
		Thread thread;
		Connection con;
		long current = System.currentTimeMillis( );
		while( it.hasNext( )){
			e = it.next( );
			thread = e.getKey( );
			con    = e.getValue( );
			if( thread.getState( ) == State.TERMINATED){
				it.remove( );
				lbq.add( con );
			}
			if( current - con.getOperationTime( ) > 180000 ){
				try {
					//TODO 这里需要优化
					//ping( con );
				} catch ( Exception e1 ) {
					it.remove( );
					exceptionHandle(con);
					e1.printStackTrace();
				}
			}
		}
	}
	@Override
	public void setConnection(Connection conn) {

	}

	
	
}
