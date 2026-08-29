package com.lamp.decoration.foundation.network.redis.asynCommands;

import java.util.List ;

import com.lamp.decoration.foundation.network.redis.entity.AsynResult;
import com.lamp.decoration.foundation.network.redis.entity.SortedSetParameter;
import com.lamp.decoration.foundation.network.redis.entity.ZunionstoreParameter;


public interface SortedSetCommands<T> {
	
	AsynResult<Boolean> zadd(T key);
	
	AsynResult<Long> zadd(List<T> list);
	
	AsynResult<Long> zcard(String key);
	
	AsynResult<Long> zcount(String key , int min , int max);
	
	AsynResult<Long> zincrby(T key);
	
	AsynResult<List<T>> zrange(String key , SortedSetParameter sortedSetParameter);
	
	AsynResult<List<T>> zrangebyscore(String key , SortedSetParameter sortedSetParameter);
	
	AsynResult<Long> zrank(String key , String member);
	
	AsynResult<Long> zrem(T key);
	
	AsynResult<Long> zrem(List<T> list);
	
	AsynResult<Long> zremrangebyrank(String key , int start , int stop);
	
	AsynResult<Long> zremrangebyscore(String key , int min , int max);
	
	AsynResult<List<T>> zrevrange(String key ,SortedSetParameter sortedSetParameter);
	
	AsynResult<List<T>> zrevrangebyscore(String key ,SortedSetParameter sortedSetParameter);
	
	AsynResult<Long> zrevrank(String key ,String member);
	
	AsynResult<Long> zscore(String key ,String member);
	
	AsynResult<List<T>> zunionstore(String key , ZunionstoreParameter zunionstoreParameter);
	
	AsynResult<List<T>> zinterstore(String key , ZunionstoreParameter zunionstoreParameter);
	
	
}
