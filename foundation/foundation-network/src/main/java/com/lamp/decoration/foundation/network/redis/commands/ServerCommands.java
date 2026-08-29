package com.lamp.decoration.foundation.network.redis.commands;

import java.util.List;

public interface ServerCommands {

		String bgrewriteaof();
		
		String bgsave();
		
		String clientGetName();
		
		boolean clientKill(String name); 
		
		boolean clientSetName(String name);
		
		List<String> configGet(String name);
		
		boolean configSet(String name, String config);
		
		long dbsize();
		
		boolean flushall();
		
		boolean flushdb();
		
		long lastSave();
		
		boolean monitor();
		
		String psync(String pattern, String param);
		
		boolean save();
		
		String shutdown();
		
		boolean slaveof(String host, String post);
		
		List<String> time();
}
