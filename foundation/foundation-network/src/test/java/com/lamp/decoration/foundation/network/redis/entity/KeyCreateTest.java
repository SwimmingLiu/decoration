package com.lamp.decoration.foundation.network.redis.entity;

import org.junit.Test;

import com.lamp.decoration.foundation.network.redis.utils.KeyCreateUtils;


public class KeyCreateTest {
	
	TestEntity testEntity = new TestEntity(1 , "hello , laohu");
	
	@Test
	public void testType(){
		TestEntityKeyCreate tkc = new TestEntityKeyCreate(new KeyConfigure<>( null , null , null , null , null ));
		tkc.getKeySuffix( testEntity );
		tkc.getKeySuffixBuffer( testEntity );
	}
	
	@Test
	public void tsestUtils(){
		KeyCreateUtils kcu =  KeyCreateUtils.getInstance( );
		try {
			//             com.lamp.ledis.entity
			kcu.createKeyCreate("com.lamp.ledis.entity.TestEntity", "name", "1",null,null);
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings( "unchecked" )
	@Test
	public void testValue(){
		try {
			Value< Integer , String , TestEntity> value =  KeyCreateUtils
															.getInstance( )
															.value( TestEntity.class , "id" , "name" );
			TestEntity te = value.setValue( 2 , "hello value" );
			System.out.println( te.toString( ) ) ;
		} catch ( Exception e ) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
}
