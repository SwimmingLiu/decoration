package com.lamp.decoration.foundation.network.redis.commands;

public interface PubSubCommands {

	String psubscribe(String pattern );
	
	long publish(String pattern,String message);
	
	String pubsub(String channel);
	
	String pubsub(String channel , String message);

	String pubsubscribe(String pattern);
	
	String subscribe(String pattern);
	
	String unsubscribe(String pattern);
}
