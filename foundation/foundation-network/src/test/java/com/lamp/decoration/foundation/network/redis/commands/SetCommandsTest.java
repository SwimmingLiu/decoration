package com.lamp.decoration.foundation.network.redis.commands;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.lamp.decoration.foundation.network.redis.net.ConnectionFactoryTest;


public class SetCommandsTest extends ConnectionFactoryTest {

	List< String > list = new ArrayList< String >( ) ;

	List< String > settList = new ArrayList< String >( ) ;

	List< String > keyList = new ArrayList<>( ) ;

	String key = "set" ;

	String setKey = "sett" ;

	{

		for ( int i = 16 ; i < 40 ; i++ ) {
			list.add( i + "" ) ;
			settList.add( ( i - 15 ) + "" ) ;
		}

		keyList.add( key ) ;
		keyList.add( setKey ) ;
	}

	@Test
	public void sadd ( ) {
		long i = setc.sadd( key , "15" ) ;
		System.out.println( i ) ;
		List< String > list = setc.smemebers( key ) ;
		System.out.println( list ) ;
	}

	@Test
	public void saddList ( ) {
		long i = setc.sadd( key , list ) ;
		System.out.println( i ) ;
		List< String > list = setc.smemebers( key ) ;
		System.out.println( list ) ;

		i = setc.sadd( setKey , settList ) ;
		System.out.println( i ) ;
		list = setc.smemebers( setKey ) ;
		System.out.println( list ) ;
	}

	@Test
	public void scard ( ) {
		long i = setc.scard( key ) ;
		System.out.println( i ) ;
	}

	@Test
	public void sdiff ( ) {
		List< String > list = setc.sdiff( keyList ) ;
		System.out.println( list ) ;
		long l = setc.sdiffstore( "sdiff" , keyList ) ;
		System.out.println( l ) ;
	}

	@Test
	public void sinter ( ) {
		List< String > list = setc.sinter( keyList ) ;
		System.out.println( list ) ;
		long l = setc.sinterstore( "sinter" , keyList ) ;
		System.out.println( l ) ;
	}

	@Test
	public void sismember ( ) {
		boolean bo = setc.sismember( key , 18 + "" ) ;
		System.out.println( bo ) ;
		bo = setc.sismember( key , 1 + "" ) ;
		System.out.println( bo ) ;

	}

	@Test
	public void smove ( ) {
		List< String > list = setc.smemebers( key ) ;
		System.out.println( list ) ;
		list = setc.smemebers( setKey ) ;
		System.out.println( list ) ;
		setc.smove( setKey , key , 1 + "" ) ;
		list = setc.smemebers( key ) ;
		System.out.println( list ) ;
		list = setc.smemebers( setKey ) ;
		System.out.println( list ) ;
	}

	
	@Test
	public void spop(){
		List< String > list = setc.smemebers( key ) ;
		System.out.println( list ) ;
		String str = setc.spop( key );
		System.out.println(  str ) ;
		list = setc.smemebers( key ) ;
		System.out.println( list ) ;
	}
	
	@Test
	public void srandmember(){
		String str = setc.srandmember( key );
		System.out.println(  str ) ;
		
		List<String> strList = setc.srandmember( key , 5 );
		System.out.println(  strList.toString( ) ) ;
	}
	
	@Test
	public void srem(){
		List< String > list = setc.smemebers( key ) ;
		System.out.println( list ) ;
		boolean boo = setc.srem( key , "1" );
		System.out.println( boo ) ;
		list = setc.smemebers( key ) ;
		System.out.println( list ) ;
		
		List<String> keyList = new ArrayList<>( );
		keyList.add( "16" );
		keyList.add( "17" );
		keyList.add( "18" );
		long l = setc.srem( key , keyList );
		System.out.println(  l ) ;
		list = setc.smemebers( key ) ;
		System.out.println( list ) ;
		
	}
	
	
	@Test
	public void sunion(){
		List<String> list = setc.sunion( keyList );
		System.out.println( list ) ;
		
		long l = setc.sunionstore("sunion", keyList );
		System.out.println( l ) ;
		list = setc.smemebers( "sunion" ) ;
		System.out.println( list ) ;
	}
	
}
