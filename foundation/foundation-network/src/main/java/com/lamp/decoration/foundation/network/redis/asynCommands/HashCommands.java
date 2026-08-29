package com.lamp.decoration.foundation.network.redis.asynCommands;

import java.util.List ;
import java.util.Map ;

import com.lamp.decoration.foundation.network.redis.entity.AsynResult;


public interface HashCommands<K,T> extends BasicsCommands<T>{

	
	AsynResult<Boolean> hdel(T key);
	
	AsynResult<Boolean> hdel(String key, K field);
	
	AsynResult<Boolean> hdel(Long key, K field);
	
	AsynResult<Boolean> hdel(List<T> key);
	
	
	AsynResult<Boolean> hexists(T key);
	
	AsynResult<Boolean> hexists(String key, String field);
	
	AsynResult<Boolean> hexists(long key, long field);
	
	
	AsynResult<T> hget(T key);
	
	AsynResult<T> hget(String key, String field);
	
	AsynResult<T> hget(long key, long field);
	
	
	AsynResult<Map<K,T>> hgetall(T key);
	
	AsynResult<Map<K,T>> hgetall(String key);
	
	AsynResult<Map<K,T>> hgetall(long key);
	
	AsynResult<Long> hincrby(T key, long increment);
	
	AsynResult<Long> hincrby(String key, String field, long increment);
	
	AsynResult<Long> hincrby(Long key, Long field, long increment);
	
	AsynResult<List<K>>  hkeys(T key);
	
	AsynResult<List<K>>  hkeys(String key);
	
	AsynResult<List<K>>  hkeys(long key);
	
	
	AsynResult<List<T>>  hvals(T key);
	
	AsynResult<List<T>>  hvals(String key);
	
	AsynResult<List<T>>  hvals(long key);
	
	
	AsynResult<Long> hlen(T key);
	
	AsynResult<Long> hlen(String key);
	
	AsynResult<Long> hlen(long key);
	
	
	AsynResult<List<T>>  hmget(List<T> t);
	
	AsynResult<List<T>>  hmget(String key, List<T> t);
	
	AsynResult<List<T>>  hmget(Number key, List<T> t);
	
	AsynResult<Boolean> hmset(List<T> t);
	
	AsynResult<Boolean> hmset(String key, List< T > t);
	
	AsynResult<Boolean> hmset(Number key, List< T > t);
	
	AsynResult<Boolean> hset(T key);
	
	AsynResult<Boolean> hset(String key, T t);
	
	AsynResult<Boolean> hset(Number key, T t);
	
	AsynResult<Boolean> hsetnx(T key);
	
	AsynResult<Boolean> hsetnx(String key, T t);
	
	AsynResult<Boolean> hsetnx(Number key, T t);
}
