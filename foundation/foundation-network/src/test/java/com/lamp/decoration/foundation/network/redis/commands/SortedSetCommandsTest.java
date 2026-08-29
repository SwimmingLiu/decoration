package com.lamp.decoration.foundation.network.redis.commands;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.lamp.ledis.entity.SortedSetParameter;
import com.lamp.ledis.entity.TestEntity;
import com.lamp.ledis.net.ConnectionFactoryTest;

public class SortedSetCommandsTest extends ConnectionFactoryTest{
	
	
	private String key = "0";

	private TestEntity testEntity = new TestEntity( "laohu" , 5000 );
	private List<TestEntity>  list = new ArrayList<>( );
	
	{
		TestEntity testEntity = new TestEntity( "lao" , 2000 );
		list.add( testEntity );
		testEntity = new TestEntity( "hu" , 1000 );
		list.add( testEntity );
		testEntity = new TestEntity( "da" , 4000 );
		list.add( testEntity );
		testEntity = new TestEntity( "za" , 500 );
		list.add( testEntity );
		testEntity = new TestEntity( "daza" , 100 );
		list.add( testEntity );
	}
	
	@Test
	public void zadd(){
		
		boolean boo = ssc.zadd( testEntity );
		System.out.println(  boo ) ;
		List<TestEntity> teList = ssc.zrange( key , SortedSetParameter.createRange( ) );
		System.out.println(  teList ) ;
		
		long length = ssc.zadd( list );
		System.out.println(length ) ;
		teList = ssc.zrange( key , SortedSetParameter.createRange( ) );
		System.out.println(  teList ) ;
		
	}
	
	@Test
	public void zcard(){
		long length = ssc.zcard( key );
		System.out.println(length);
		List<TestEntity> teList = ssc.zrange( key , SortedSetParameter.createRange( ) );
		System.out.println( length == teList.size( ) ) ;
		
		
	}
	
	@Test
	public void zcount(){
		long length = ssc.zcount( key , 1000 , 5000 );
		System.out.println( length) ;
	}
	
	@Test
	public void zincrby(){
		List<TestEntity> teList = ssc.zrange( key , SortedSetParameter.createRange( ) );
		System.out.println(  teList ) ;
		ssc.zincrby( testEntity );
		teList = ssc.zrange( key , SortedSetParameter.createRange( ) );
		System.out.println(  teList ) ;
	}
	
	@Test
	public void zrande(){
		List<TestEntity> teList = ssc.zrangebyscore( key , SortedSetParameter.createInf( ).setOffset( 0 ).setCount( 1 ) );
		System.out.println( teList ) ;
	}
	
	
	@Test
	public  void zrank(){
		long rank = ssc.zrank( key , "laohu" );
		System.out.println( rank ) ;	
	}
	
	
	
	@Test
	public void zrem(){
		long i = ssc.zrem( testEntity );
		System.out.println( i ) ;
		List<TestEntity> teList = ssc.zrange( key , SortedSetParameter.createRange( ) );
		System.out.println(  teList ) ;
		
		
		i = ssc.zrem( this.list );
		System.out.println( i ) ;
		teList = ssc.zrange( key , SortedSetParameter.createRange( ) );
		System.out.println(  teList ) ;
		
		
	}
	
	
	@Test
	public void zrenrangebyrank(){
		List<TestEntity> teList = ssc.zrange( key , SortedSetParameter.createRange( ) );
		System.out.println(  teList ) ;
		ssc.zremrangebyrank( 0+"" , 0 , 0 );
		teList = ssc.zrange( key , SortedSetParameter.createRange( ) );
		System.out.println(  teList ) ;
		
		teList = ssc.zrange( key , SortedSetParameter.createRange( ) );
		ssc.zremrangebyscore( key , 4000 , 5000 );
		System.out.println(  teList ) ;
	}
	
	@Test
	public void zrevrange(){
		List<TestEntity> list = ssc.zrevrange( key , SortedSetParameter.createRange( ) );
		System.out.println( list ) ;
		
		list = ssc.zrevrangebyscore( key , SortedSetParameter.createInf( ) );
		System.out.println( list ) ;
		
		
	}
	
	@Test
	public void zrevrank(){
		long l = ssc.zrevrank( key , "laohu" );
		System.out.println( l ) ;
	}
	
	@Test
	public void zscore(){
		long l = ssc.zscore(key, "laohu");
		System.out.println(l);
	}
	
}
