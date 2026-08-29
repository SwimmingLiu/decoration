package com.lamp.decoration.foundation.network.redis.commands;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.lamp.ledis.entity.TestEntity;
import com.lamp.ledis.net.ConnectionFactoryTest;

public class StringCommandsTest extends ConnectionFactoryTest {


	TestEntity testEntity = new TestEntity( 2 , "hello , laohu" ) ;

	List< TestEntity > list ;
	List< TestEntity > listMsetNX = new ArrayList<>( ) ;
	List< String > listString ;
	List< Integer > listIntger ;
	{
		list = new ArrayList<>( ) ;
		listString = new ArrayList<>( ) ;
		listIntger = new ArrayList<>( ) ;
		for ( int i = 4 ; i < 14 ; i++ ) {
			list.add( new TestEntity( i , "hello , laohu" + i ) ) ;
			listString.add( i + "" ) ;
			listIntger.add( i ) ;
		}
		
		for ( int i = 14 ; i < 20 ; i++ ) {
			listMsetNX.add( new TestEntity( i , "hello , laohu" + i ) ) ;
		}
	}
	TestEntity te ;



	@Test
	public void set ( ) {
		sc.set( testEntity ) ;
	}

	@Test
	public void get ( ) {
		te = sc.get( testEntity ) ;
		System.out.println( te ) ;
		te = sc.get( testEntity.getId( ) ) ;
		System.out.println( te ) ;
		te = sc.get( testEntity.getId( ) + "" ) ;
		System.out.println( te ) ;
	}

	@Test
	public void getset ( ) {
		testEntity.setName( "hello laohu" ) ;
		te = sc.getset( testEntity ) ;
		System.out.println( te ) ;
	}

	@Test
	public void mget ( ) {
		List< TestEntity > cd = sc.mget( list ) ;
		System.out.println( JSON.toJSONString( cd ) ) ;
		cd = sc.mgetstring( listString ) ;
		System.out.println( JSON.toJSONString( cd ) ) ;
		cd = sc.mgetnumber( listIntger ) ;
		System.out.println( JSON.toJSONString( cd ) ) ;
		
	}
	@Test
	public void mset ( ) {
		sc.mset( list ) ;
	}

	
	@Test
	public void setnx(){
		TestEntity testEntity = new TestEntity( 10001 , "hello , setNX" ) ;
		sc.set( testEntity ) ;
		
		boolean bo = sc.setnx(testEntity);
		System.out.println(  bo ) ;
		testEntity.setId( 10002 );
		bo = sc.setnx(testEntity);
		System.out.println(  bo ) ;
	}
	
	@Test
	public void msetnx(){
		boolean bo = false;
		bo = sc.msetnx( list );
		System.out.println(  bo ) ;
		bo = sc.msetnx( listMsetNX );
		System.out.println( bo ) ;
	}
	
	@Test
	public void setex(){	
		boolean bo = sc.setex(testEntity , 60);
		System.out.println(  bo ) ;
	}
	
	@Test
	public void incrAndIncrByOrDecrAndDecrBy() {
		TestEntity testEntity = new TestEntity( 100011 , "hello , incrAndIncrBy" ) ;
		long incr = -2;
		incr = sc.incr( testEntity );
		System.out.println( incr ) ;
		
		incr = sc.incrby( testEntity , 50 );
		System.out.println( incr ) ;
		
		incr = sc.decr( testEntity );
		System.out.println( incr ) ;
		
		incr = sc.decrby( testEntity , 10 );
		System.out.println( incr ) ;		
	}

	@Test
	public void strlen(){
		sc.set( testEntity );
		long i = sc.strlen( testEntity );
		System.out.println(  i ) ;
	}
}
