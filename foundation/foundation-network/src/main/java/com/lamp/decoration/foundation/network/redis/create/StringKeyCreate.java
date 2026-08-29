package com.lamp.decoration.foundation.network.redis.create;

import java.nio.ByteBuffer;

public class StringKeyCreate extends AbstractKeyCreate{

	private String key;

	
	
	public StringKeyCreate(KeyConfigure keyConfigure) {
		super(keyConfigure);
	}

	@Override
	public String getKeyPrefix() {
		return getKey(key);
	}

	@Override
	public ByteBuffer getKeySuffixBuffer(Object t) {
		return null;
	}

	@Override
	public void getKeySuffixBuffer(Object t, ByteBuffer byteBuffer) {
		
	}

	@Override
	public String getKey(String t) {
		return null;
	}

	@Override
	public String getKeySuffix(Object t) {
		// TODO Auto-generated method stub
		return null;
	}

}
