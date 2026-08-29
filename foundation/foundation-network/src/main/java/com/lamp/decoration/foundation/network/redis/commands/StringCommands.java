package com.lamp.decoration.foundation.network.redis.commands;


import java.util.List;


public interface StringCommands<T>{
	
	
	
	T get(T key);
	
	T get(String key);
	
	T get(long key);
	
	T getset(T key);
	
	List<T> mget(List<T> keys);
	
	List<T> mgetstring(List<String> keys);
	
	List<T> mgetnumber(List<? extends Number> keys);

	T set(T key);

	//void set(byte[] key, byte[] value, Expiration expiration, SetOption option);
	
	Boolean setnx(T key);
	
	Boolean setnx(T key, Object o);
	
	Boolean setex(T key ,long  seconds);
	
	Boolean setex(T key, Object o , long  seconds);

	Boolean psetex(byte[] key, long milliseconds, byte[] value);

	Boolean mset(List<T> tuple);

	Boolean msetnx(List<T> tuple);

	Long incr(T key);

	Long incrby(T key ,long value);

	Double incrby(T key , double value);

	Long decr(T key);

	Long decrby(T key,long value);
/*
	Long append(byte[] key, byte[] value);

	byte[] getRange(T t, long begin, long end);
	
	byte[] getRange(String t, long begin, long end);
	
	byte[] getRange(Number t, long begin, long end);

	void setRange(byte[] key, byte[] value, long offset);

	Boolean getBit(byte[] key, long offset);

	Boolean setBit(byte[] key, long offset, boolean value);

	Long bitCount(byte[] key);

	Long bitCount(byte[] key, long begin, long end);
*/

	Long strlen(T key);

}
