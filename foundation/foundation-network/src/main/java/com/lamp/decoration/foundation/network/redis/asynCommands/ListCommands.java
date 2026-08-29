package com.lamp.decoration.foundation.network.redis.asynCommands;

import java.util.List ;

import com.lamp.decoration.foundation.network.redis.entity.AsynResult;
import com.lamp.decoration.foundation.network.redis.entity.BlockListResult;


public interface ListCommands<T> extends BasicsCommands< T > {

	
	AsynResult<BlockListResult<T>> blpop(T key );
	
	AsynResult<BlockListResult<T>> blpop(long key );
	
	AsynResult<BlockListResult<T>> blpop(String key );
	
	AsynResult<BlockListResult<T>> blpop(T key , int timeout);
	
	AsynResult<BlockListResult<T>> blpop(long key , int timeout);
	
	AsynResult<BlockListResult<T>> blpop(String key , int timeout);
	
	
	AsynResult<BlockListResult<T>> brpop(T key );
	
	AsynResult<BlockListResult<T>> brpop(long key );
	
	AsynResult<BlockListResult<T>> brpop(String key );
	
	AsynResult<BlockListResult<T>> brpop(T key , int timeout);
	
	AsynResult<BlockListResult<T>> brpop(long key , int timeout);
	
	AsynResult<BlockListResult<T>> brpop(String key , int timeout);
	
	
	AsynResult<BlockListResult<T>> brpoplpush(T source , T destination );
	
	AsynResult<BlockListResult<T>> brpoplpush(long source ,long destination );
	
	AsynResult<BlockListResult<T>> brpoplpush(String source ,String destination);
	
	AsynResult<BlockListResult<T>> brpoplpush(T source , T destination ,int timeout);
	
	AsynResult<BlockListResult<T>> brpoplpush(long source ,long destination ,int timeout);
	
	AsynResult<BlockListResult<T>> brpoplpush(String source ,String destination, int timeout);
	
	
	
	AsynResult<T> lindex(T key  , int index);
	
	AsynResult<T> lindex(long key ,int index);
	
	AsynResult<T> lindex(String key ,int index);
	
	AsynResult<Long> linsert(String key , String pivot , String value);
	
	AsynResult<Long> llen(T key);
	
	AsynResult<Long> llen(String key);
	
	AsynResult<Long> llen(long key);
	
	AsynResult<T> lpop(T key);
	
	AsynResult<T> lpop(String key);
	
	AsynResult<T> lpop(long key);
	
	AsynResult<Long> lpush(T keyValue);
	
	AsynResult<Long> lpush(List<T> keyValue);
	
	AsynResult<Long> lpushx(T keyValue);
	
	AsynResult<List<T>>  lrange(T key , int start , int end);
	
	AsynResult<List<T>>  lrange(String key , int start , int end);
	
	AsynResult<List<T>>  lrange(long key , int start , int end);
	
	AsynResult<Long>  lren(String key , int count , String value );
	
	AsynResult<Boolean> lset(T key , int index );
	
	AsynResult<Boolean> ltrim(T key , int start , int end );
	
	AsynResult<Boolean> ltrim(String key , int start , int end );
	
	AsynResult<Boolean> ltrim(long key , int start , int end );
	
	AsynResult<T> rpop(T key );
	
	AsynResult<T> rpop(long key );
	
	AsynResult<T> rpop(String key );
	
	
	AsynResult<T> rpoplpush(T source , T destination );
	
	AsynResult<T> rpoplpush(long source ,long destination );
	
	AsynResult<T> rpoplpush(String source ,String destination );
	
	AsynResult<Long> rpush(T keyValue);
	
	AsynResult<Long> rpush(List<T> keyValue);
	
	AsynResult<Long> rpushx(T keyValue);
}
