package com.lamp.decoration.foundation.network.redis.commands;

import org.junit.Test;

import com.lamp.ledis.net.ConnectionFactoryTest;

public class ConnectionCommandsTest extends ConnectionFactoryTest{

	
	
	@Test
	public void auth(){
		boolean str = cc.auth( "123456" );
		System.out.println( str ) ;
	}
	
	@Test
	public void echo(){
		String message = cc.echo( "hello laohu" );
		System.out.println( message ) ;
	}
	
	@Test
	public void ping(){
		String ping = cc.ping( "1" );
		System.out.println( ping ) ;
	}
	
	@Test
	public void select(){
		boolean b = cc.select( 3 );
		System.out.println( b ) ;
	}
}
