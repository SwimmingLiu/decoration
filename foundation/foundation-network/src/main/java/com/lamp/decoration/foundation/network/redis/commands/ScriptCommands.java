package com.lamp.decoration.foundation.network.redis.commands;

public interface ScriptCommands {
	
	public String  scriptLoad(String script);
	
	public Boolean scriptExists(String script);

	public String evalsha(String script);
}
