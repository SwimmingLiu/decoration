package com.lamp.decoration.foundation.network.redis.spring;


import com.lamp.decoration.foundation.network.redis.annotation.OperationsEntity;
import com.lamp.decoration.foundation.network.redis.commands.CommandsObjectFactory;
import com.lamp.decoration.foundation.network.redis.commands.RedisCommands;

/**
 * 还是不延迟加载了，如果asm异常怎么办
 * 这种异常应该在 初始化的时候就应该检测出来
 * 是否设定一个值，来处理
 * @author laohu
 *
 * @param <T> 11111
 */
public class MapperFactoryBean<T> {

	
	private OperationsEntity operationsEntity;
	
	public void init(){
		operationsEntity.getName( );
	}
	
	@SuppressWarnings ( "unchecked" )
	public T getObject ( ) throws Exception {
		return ( T ) CommandsObjectFactory.getInstance( ).getRedisCommands(String.valueOf(operationsEntity)) ;
	}

	public Class< ? > getObjectType ( ) {
		return RedisCommands.class ;
	}

	public boolean isSingleton ( ) {
		return true ;
	}

	public void setOperationsEntity ( OperationsEntity operationsEntity ) {
		this.operationsEntity = operationsEntity ;
	}

	 
}
