package com.lamp.decoration.foundation.network.redis.commands;

import java.util.List ;

public interface SetCommands {

	
	long sadd(String key , String member);
	
	long sadd(String key , List<String> member);
	
	long scard(String key);
	
	List<String> sdiff(List<String> key);
	
	long sdiffstore(String destination , List<String> key);
	
	List<String> sinter(List<String> key);
	
	long sinterstore(String destination , List<String> key);
	
	boolean sismember(String key , String member);
	
	List<String> smemebers(String key);
	
	boolean smove(String source , String destination , String member);
	
	String spop(String key);
	
	String srandmember(String key);
	
	List<String> srandmember(String key , int count);
	
	boolean  srem(String key , String member);
	
	long srem(String key , List<String> member);
	
	List<String> sunion(List<String> key);
	
	long sunionstore(String destination , List<String> key );

	
}
