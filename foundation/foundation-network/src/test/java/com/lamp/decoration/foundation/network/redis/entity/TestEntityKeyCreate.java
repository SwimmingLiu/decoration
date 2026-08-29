package com.lamp.decoration.foundation.network.redis.entity;

import java.nio.ByteBuffer;

import com.lamp.ledis.create.AbstractKeyCreate;
import com.lamp.ledis.create.KeyConfigure;

public class TestEntityKeyCreate extends AbstractKeyCreate<TestEntity> {


	public TestEntityKeyCreate(KeyConfigure<TestEntity> keyConfigure) {
		super(keyConfigure);
		// TODO Auto-generated constructor stub
	}


	@Override
	public String getKeySuffix(TestEntity t) {
		return getKey(t.getId());
	}


	@Override
	public void getKeySuffixBuffer ( TestEntity t , ByteBuffer byteBuffer ) {
		this.getKey( t.getId( ) , byteBuffer );
	}


	@Override
	public String getKeyPrefix ( ) {
		// TODO 自动生成的方法存根
		return null ;
	}


	@Override
	public String getKey ( String t ) {
		// TODO 自动生成的方法存根
		return null ;
	}


	@Override
	public ByteBuffer getKeySuffixBuffer(TestEntity t) {
		// TODO Auto-generated method stub
		return null;
	}
}
