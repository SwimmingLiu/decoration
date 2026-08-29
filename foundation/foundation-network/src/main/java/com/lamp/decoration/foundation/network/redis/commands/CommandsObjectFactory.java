package com.lamp.decoration.foundation.network.redis.commands;

import java.util.Map ;
import java.util.concurrent.ConcurrentHashMap ;

import com.lamp.decoration.foundation.network.redis.annotation.OperationsEntity;


public final class CommandsObjectFactory {

	private final static CommandsObjectFactory commandsObjectFactory = new CommandsObjectFactory();
	
	private CommandsObjectFactory(){
		
	}
	
	public static final CommandsObjectFactory getInstance(){
		return commandsObjectFactory;
	}
	
	@SuppressWarnings( "rawtypes" )
	private final Map<String,RedisCommands> redisCommandsMap = new ConcurrentHashMap<>();
	
	
	@SuppressWarnings( "rawtypes" )
	public RedisCommands getRedisCommands( OperationsEntity operationsEntity ){
		if( !redisCommandsMap.containsKey( operationsEntity.getName() ) ){
			try {
				redisCommandsMap.put( operationsEntity.getName() , new RedisCommandsImpl<>( operationsEntity ) );
			} catch ( Exception e ) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}
		return redisCommandsMap.get( operationsEntity.getName() );
	}
	
	@SuppressWarnings ( { "rawtypes" } )
	public RedisCommands getRedisCommands( String name ){
		return  redisCommandsMap.get( name );
	}
}
