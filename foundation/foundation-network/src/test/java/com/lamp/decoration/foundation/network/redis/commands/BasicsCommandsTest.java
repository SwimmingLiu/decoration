package com.lamp.decoration.foundation.network.redis.commands;

import org.junit.Test;

import com.lamp.decoration.foundation.network.redis.net.ConnectionFactoryTest;


public class BasicsCommandsTest extends ConnectionFactoryTest {

	
	@Test
	public void del ( ) {
		boolean boo = bc.del( "2" );
		System.out.println( boo ) ;
	}

	@Test
	public void exists ( ) {
		boolean boo = bc.exists( "3" );
		System.out.println( boo ) ;
		boo = bc.exists( "2" );
		System.out.println( boo ) ;
	}

	@Test
	public void expire ( ) {
		boolean boo = bc.expire( "3" , 10 );
		System.out.println( boo ) ;
		try {
			Thread.sleep( 11000 );
		} catch ( InterruptedException e ) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		boo = bc.exists( "3" );
		System.out.println( boo ) ;
	}

	@Test
	public void pexpire ( ) {
		boolean boo = bc.pexpire( "5" , 10000 );
		System.out.println( boo ) ;
		try {
			Thread.sleep( 11000 );
		} catch ( InterruptedException e ) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		boo = bc.exists( "5" );
		System.out.println( boo ) ;
	}

	@Test
	public void expireat ( ) {
		boolean boo = bc.expireat( "6" , System.currentTimeMillis( )/1000+10 );
		System.out.println( boo ) ;
		try {
			Thread.sleep( 11000 );
		} catch ( InterruptedException e ) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		boo = bc.exists( "6" );
		System.out.println( boo ) ;
	}

	@Test
	public void pexpireat ( ) {
		boolean boo = bc.pexpireat( "7" , System.currentTimeMillis( )+10000 );
		System.out.println( boo ) ;
		try {
			Thread.sleep( 11000 );
		} catch ( InterruptedException e ) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		
		boo = bc.exists( "7" );
		System.out.println( boo ) ;
	}

	@Test
	public void persist ( ) {
		boolean boo = bc.pexpireat( "8" , System.currentTimeMillis( )+10000 );
		System.out.println( boo ) ;
		boo = bc.persist( "8"  );
		System.out.println( boo ) ;
		
	}

	@Test
	public void rename ( ) {
		boolean boo = bc.rename( "8" , "1" );
		System.out.println( boo ) ;
		boo = bc.exists( "8" );
		System.out.println( boo ) ;
		boo = bc.exists( "1" );
		System.out.println( boo ) ;
	}

	@Test
	public void renamenx ( ) {
		boolean boo = bc.renamenx( "1" , "8" );
		System.out.println( boo ) ;
		boo = bc.exists( "8" );
		System.out.println( boo ) ;
		boo = bc.exists( "1" );
		System.out.println( boo ) ;
	}
}
