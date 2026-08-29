package com.lamp.decoration.foundation.network.redis.commands;

import java.util.List ;

import com.lamp.decoration.foundation.network.redis.entity.BlockListResult;


public interface ListCommands<T> extends BasicsCommands< T > {

	
	BlockListResult<T> blpop(T key );
	
	BlockListResult<T> blpop(long key );
	
	BlockListResult<T> blpop(String key );
	
	BlockListResult<T> blpop(T key , int timeout);
	
	BlockListResult<T> blpop(long key , int timeout);
	
	BlockListResult<T> blpop(String key , int timeout);
	
	
	BlockListResult<T> brpop(T key );
	
	BlockListResult<T> brpop(long key );
	
	BlockListResult<T> brpop(String key );
	
	BlockListResult<T> brpop(T key , int timeout);
	
	BlockListResult<T> brpop(long key , int timeout);
	
	BlockListResult<T> brpop(String key , int timeout);
	
	
	BlockListResult<T> brpoplpush(T source , T destination );
	
	BlockListResult<T> brpoplpush(long source ,long destination );
	
	BlockListResult<T> brpoplpush(String source ,String destination);
	
	BlockListResult<T> brpoplpush(T source , T destination ,int timeout);
	
	BlockListResult<T> brpoplpush(long source ,long destination ,int timeout);
	
	BlockListResult<T> brpoplpush(String source ,String destination, int timeout);
	
	
	
	T lindex(T key  , int index);
	
	T lindex(long key ,int index);
	
	T lindex(String key ,int index);
	
	long linsert(String key , String pivot , String value);
	
	long llen(T key);
	
	long llen(String key);
	
	long llen(long key);
	
	T lpop(T key);
	
	T lpop(String key);
	
	T lpop(long key);
	
	long lpush(T keyValue);
	
	long lpush(List<T> keyValue);
	
	long lpushx(T keyValue);
	
	List<T> lrange(T key , int start , int end);
	
	List<T> lrange(String key , int start , int end);
	
	List<T> lrange(long key , int start , int end);
	
	long  lren(String key , int count , String value );
	
	boolean lset(T key , int index );
	
	boolean ltrim(T key , int start , int end );
	
	boolean ltrim(String key , int start , int end );
	
	boolean ltrim(long key , int start , int end );
	
	T rpop(T key );
	
	T rpop(long key );
	
	T rpop(String key );
	
	
	T rpoplpush(T source , T destination );
	
	T rpoplpush(long source ,long destination );
	
	T rpoplpush(String source ,String destination );
	
	long rpush(T keyValue);
	
	long rpush(List<T> keyValue);
	
	long rpushx(T keyValue);
}
