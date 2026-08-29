package com.lamp.decoration.foundation.network.redis.net;

import java.io.IOException ;
import java.nio.ByteBuffer ;
import java.util.ArrayList ;
import java.util.List ;

import com.lamp.decoration.foundation.network.redis.protocol.ResolveNetProtocol;


public abstract class AbstractConnectionPattern implements ConnectionPattern {

	private static final List<byte[] >  SELECT_BYTE = new ArrayList<byte[] >( );
	
	private static final String SELECT_STR = "*2\r\n$6\r\nselect\r\n$";
	
	private static final byte[] PING_BYTE = "*1\r\n$4\r\nping".getBytes( );
	
	private static final ByteBuffer PING_BUFFER = ByteBuffer.allocate( 16 );
	
	
	private static final byte[]  PING_BYTE_RETURN = PING_BUFFER.array( );
	
	static{
		StringBuffer commands = new StringBuffer( );
		for(int i = 1 ; i<=16 ; i++){
			commands.append( SELECT_STR )
					.append( i>10? "2\r\n":"1\r\n" )
					.append( i+"" );
			SELECT_BYTE.add( commands.toString( ).getBytes( ) );
			commands.delete( 0 , commands.length( ) );
		}
	}
	
	
	
	NetConfigure configure ;
	PoolConfig   poolConfig;
	
	void connectionInit(Connection con) throws IOException{
		if(!con.isConnect( )){
			con.connect( );
			select( con );
		}
	}

	void select(Connection conn ) throws IOException{
		if(poolConfig.getSelect( ) == 1){
			return;
		}
		conn.getOutputStream( ).write( SELECT_BYTE.get( poolConfig.getSelect( ) - 1 ) );
		ResolveNetProtocol.resolveStateNetProtocol.analysis( conn.getInputStream( ) , null );
		
	}
	
	void ping( Connection conn ) throws Exception{
		try{
			conn.getOutputStream( ).write( PING_BYTE );
			long length = ResolveNetProtocol.resolveStringNetProtocol.analysis( conn.getInputStream( ) , PING_BUFFER );
			if( length==4 && 
				PING_BYTE_RETURN[ 0 ] == 'p' && 
				PING_BYTE_RETURN[ 1 ] == 'o' &&
				PING_BYTE_RETURN[ 2 ] == 'n' && 
				PING_BYTE_RETURN[ 3 ] == '3'){
				
			}
		}catch (Exception e) {
			
			throw e;
		}
		
	}
	
	void operation(Connection conn){
		conn.setOperationTime( );
		
	}
	
}
