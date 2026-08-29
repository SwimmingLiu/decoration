package com.lamp.decoration.foundation.network.redis.entity;

import java.nio.ByteBuffer;

import com.lamp.ledis.create.Value;

public class TestValue implements Value<Integer , String , TestEntity>{



	@Override
	public TestEntity setValue( Integer k , String v ) {
		TestEntity te = new TestEntity( );
		te.setName( v );
		te.setId( k );
		return te;
	}

	@Override
	public Integer getKey ( ByteBuffer byteBuffer ) {
		// TODO 自动生成的方法存根
		return null ;
	}

	@Override
	public String getValue ( ByteBuffer byteBuffer ) {
		// TODO 自动生成的方法存根
		return null ;
	}
}
