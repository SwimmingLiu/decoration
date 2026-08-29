package com.lamp.decoration.foundation.network.redis.commands;

import java.util.List ;

import com.lamp.decoration.foundation.network.redis.entity.SortedSetParameter;
import com.lamp.decoration.foundation.network.redis.entity.ZunionstoreParameter;

public interface SortedSetCommands<T> {
	
	boolean zadd(T key);
	
	long zadd(List<T> list);
	
	long zcard(String key);
	
	long zcount(String key , int min , int max);
	
	long zincrby(T key);
	
	List<T> zrange(String key , SortedSetParameter sortedSetParameter);
	
	List<T> zrangebyscore(String key , SortedSetParameter sortedSetParameter);
	
	long zrank(String key , String member);
	
	long zrem(T key);
	
	long zrem(List<T> list);
	
	long zremrangebyrank(String key , int start , int stop);
	
	long zremrangebyscore(String key , int min , int max);
	
	List<T> zrevrange(String key ,SortedSetParameter sortedSetParameter);
	
	List<T> zrevrangebyscore(String key ,SortedSetParameter sortedSetParameter);
	
	long zrevrank(String key ,String member);
	
	long zscore(String key ,String member);
	
	List<T> zunionstore(String key , ZunionstoreParameter zunionstoreParameter);
	
	List<T> zinterstore(String key , ZunionstoreParameter zunionstoreParameter);
	
	
}
