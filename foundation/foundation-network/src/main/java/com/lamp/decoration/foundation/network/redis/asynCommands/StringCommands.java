package com.lamp.decoration.foundation.network.redis.asynCommands;


import java.util.List;

import com.lamp.decoration.foundation.network.redis.entity.AsynResult;


public interface StringCommands<T>{
	
	
	
    AsynResult<T> get(T key);
	
    AsynResult<T> get(String key);
	
    AsynResult<T> get(long key);
	
    AsynResult<T> getset(T key);
	
    AsynResult<List<T>> mget(List<T> keys);
	
    AsynResult<List<T>> mgetstring(List<String> keys);
	
    AsynResult<List<T>> mgetnumber(List<? extends Number> keys);

    AsynResult<T> set(T key);

	//void set(byte[] key, byte[] value, Expiration expiration, SetOption option);
	
	AsynResult<Boolean> setnx(T key);
	
	AsynResult<Boolean> setnx(T key, Object o);
	
	AsynResult<Boolean> setex(T key ,long  seconds);
	
	AsynResult<Boolean> setex(T key, Object o , long  seconds);

	AsynResult<Boolean> psetex(byte[] key, long milliseconds, byte[] value);

	AsynResult<Boolean> mset(List<T> tuple);

	AsynResult<Boolean> msetnx(List<T> tuple);

	AsynResult<Long> incr(T key);

	AsynResult<Long> incrby(T key ,long value);

	AsynResult<Double> incrby(T key , double value);

	AsynResult<Long> decr(T key);

	AsynResult<Long> decrby(T key,long value);
/*
	AsynResult<Long> append(byte[] key, byte[] value);

	byte[] getRange(T t, long begin, long end);
	
	byte[] getRange(String t, long begin, long end);
	
	byte[] getRange(Number t, long begin, long end);

	void setRange(byte[] key, byte[] value, long offset);

	AsynResult<Boolean> getBit(byte[] key, long offset);

	AsynResult<Boolean> setBit(byte[] key, long offset, boolean value);

	AsynResult<Long> bitCount(byte[] key);

	AsynResult<Long> bitCount(byte[] key, long begin, long end);
*/

	AsynResult<Long> strlen(T key);

}
