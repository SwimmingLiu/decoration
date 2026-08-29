package com.lamp.decoration.foundation.network.redis.commands;

public interface RedisCommands<K , T> extends KeyCommands , ListCommands< T > , HashCommands< K , T > , StringCommands< T >
												, SetCommands , SortedSetCommands<T> , BasicsCommands< T >{

	
	
}
