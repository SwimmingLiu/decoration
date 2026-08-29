package com.lamp.decoration.foundation.network.redis.commands;

import java.util.List ;
import java.util.Map ;



public interface HashCommands<K,T> extends BasicsCommands<T>{

	
	boolean hdel(T key);
	
	boolean hdel(String key, K field);
	
	boolean hdel(Long key, K field);
	
	boolean hdel(List<T> key);
	
	
	boolean hexists(T key);
	
	boolean hexists(String key, String field);
	
	boolean hexists(long key, long field);
	
	
	T hget(T key);
	
	T hget(String key, String field);
	
	T hget(long key, long field);
	
	
	Map<K,T> hgetall(T key);
	
	Map<K,T> hgetall(String key);
	
	Map<K,T> hgetall(long key);
	
	long hincrby(T key, long increment);
	
	long hincrby(String key, String field, long increment);
	
	long hincrby(Long key, Long field, long increment);
	
	List<K> hkeys(T key);
	
	List<K> hkeys(String key);
	
	List<K> hkeys(long key);
	
	
	List<T> hvals(T key);
	
	List<T> hvals(String key);
	
	List<T> hvals(long key);
	
	
	long hlen(T key);
	
	long hlen(String key);
	
	long hlen(long key);
	
	
	List<T> hmget(List<T> t);
	
	List<T> hmget(String key, List<T> t);
	
	List<T> hmget(Number key, List<T> t);
	
	boolean hmset(List<T> t);
	
	boolean hmset(String key, List< T > t);
	
	boolean hmset(Number key, List< T > t);
	
	boolean hset(T key);
	
	boolean hset(String key, T t);
	
	boolean hset(Number key, T t);
	
	boolean hsetnx(T key);
	
	boolean hsetnx(String key, T t);
	
	boolean hsetnx(Number key, T t);
}
