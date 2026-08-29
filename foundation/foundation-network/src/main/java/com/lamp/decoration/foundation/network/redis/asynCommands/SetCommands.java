package com.lamp.decoration.foundation.network.redis.asynCommands;

import java.util.List ;

import com.lamp.decoration.foundation.network.redis.entity.AsynResult;


public interface SetCommands {

	
    AsynResult<Long> sadd(String key , String member);
	
    AsynResult<Long> sadd(String key , List<String> member);
	
    AsynResult<Long> scard(String key);
	
    AsynResult<List<String>> sdiff(List<String> key);
	
	AsynResult<Long> sdiffstore(String destination , List<String> key);
	
	AsynResult<List<String>> sinter(List<String> key);
	
	AsynResult<Long> sinterstore(String destination , List<String> key);
	
	AsynResult<Boolean> sismember(String key , String member);
	
	AsynResult<List<String>> smemebers(String key);
	
	AsynResult<Boolean> smove(String source , String destination , String member);
	
	AsynResult<String> spop(String key);
	
	AsynResult<String> srandmember(String key);
	
	AsynResult<List<String>> srandmember(String key , int count);
	
	AsynResult<Boolean>  srem(String key , String member);
	
	AsynResult<Long> srem(String key , List<String> member);
	
	AsynResult<List<String>> sunion(List<String> key);
	
	AsynResult<Long> sunionstore(String destination , List<String> key );

	
}
