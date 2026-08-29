package com.lamp.decoration.foundation.network.redis.create;

import java.nio.ByteBuffer ;

import com.alibaba.fastjson2.TypeReference;


public interface KeyCreate<T> {
	
	
	public String getKeyPrefix();
	
	//TODO
	public String getKeySuffix(T t);
	
	
	public ByteBuffer getKeySuffixBuffer(T t);
	
	public void getKeySuffixBuffer(T t ,ByteBuffer byteBuffer);
	
	public void getKeySuffixBuffer(String t ,ByteBuffer byteBuffer);
	
	public void getKeySuffixBuffer(Long t ,ByteBuffer byteBuffer);
	
	public String getKey(T t);	
	
	public String getKey(String t);
	
	public String getKey(long num);
	
	public Class< ? > getEntityClass();
	
	public T  getOject();
	
	public TypeReference< ? > getTypeReferenceList();
	
	public TypeReference< ? >  getTypeReferenceSet();
	
	public TypeReference< ? >  getTypeReferenceMap();
	
	public TypeReference< ? > getTypeReferenceKeyListResultHandle();
	
	public KeyCreate<T> getKeyCreate();
	
	public KeyCreate<T> getKeyCreateValue();
	
	public Value getValue();
	

}
