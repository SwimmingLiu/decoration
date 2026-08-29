package com.lamp.decoration.foundation.network.redis.protocol;

import java.nio.ByteBuffer;
import java.util.List;

import com.lamp.decoration.foundation.network.redis.create.KeyCreate;


public interface GenerateNetProtocol {
	
	public void setByteBuffer(ByteBuffer byteBuffer);
	
	public byte[] parameter(String str);
	
	public byte[] parameter(long number);
	
	public byte[] parameter( Object t, KeyCreate<Object> keyCreate);
	
	public byte[] parameterList(  List<String> list );
	
	public byte[] parameterListLong(  List<Long>  list );
	
	public byte[] parameterList(  List<Object> list ,KeyCreate<Object> ceyCreate);
	
	public byte[] parameterSet(Object t);
	
	public byte[] parameterListSet(  List<Object> list ,KeyCreate<Object> keyCreate);
	
}