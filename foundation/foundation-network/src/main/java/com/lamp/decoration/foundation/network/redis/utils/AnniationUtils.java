package com.lamp.decoration.foundation.network.redis.utils;

import java.lang.reflect.Method;

import com.lamp.decoration.foundation.network.redis.annotation.OperationEntity;
import com.lamp.decoration.foundation.network.redis.annotation.OperationsEntity;
import com.lamp.decoration.foundation.network.redis.annotation.operation.HashOperation;
import com.lamp.decoration.foundation.network.redis.annotation.operation.ListOperation;
import com.lamp.decoration.foundation.network.redis.annotation.operation.StringOperation;
import com.lamp.decoration.foundation.network.redis.annotation.operation.SetOperation;
import com.lamp.decoration.foundation.network.redis.annotation.operation.SortedSetOperation;
import com.lamp.decoration.foundation.network.redis.commands.Commands;


public class AnniationUtils {


	public static final OperationsEntity getOperationsEntity(Method m , Class<?> clazz) {
		OperationsEntity entity = new OperationsEntity();
		entity.setString(   getOperationEntity( m.getAnnotation(StringOperation.class    ), clazz));
		entity.setHash(     getOperationEntity( m.getAnnotation(HashOperation.class      ), clazz));
		entity.setList(     getOperationEntity( m.getAnnotation(ListOperation.class      ), clazz));
		entity.setSet(      getOperationEntity( m.getAnnotation(SetOperation.class       ), clazz));
		entity.setSortedSet(getOperationEntity( m.getAnnotation(SortedSetOperation.class ), clazz));
		
		return entity;
	}
	
	
	
	public static final OperationEntity getOperationEntity(StringOperation operation , Class<?> clazz) {
		if(operation == null)
			return null;
		
		OperationEntity entity = new OperationEntity();
		entity.setClazz(clazz);
		entity.setKey( operation.key());
		entity.setPrefix(operation.prefix());
		entity.setSeparator(operation.separator());
		entity.setDataSource( operation.dataSource());
		entity.setSliceKey(operation.sliceKey());
		entity.setValue( operation.value() );
		
		entity.setCommands(Commands.STRING);
				
		return entity;
	}
	
	public static final OperationEntity getOperationEntity(HashOperation operation, Class<?> clazz ) {
		if(operation == null)
			return null;
		
		OperationEntity entity = new OperationEntity();
		entity.setClazz(clazz);
		entity.setKey( operation.key());
		entity.setPrefix(operation.prefix());
		entity.setSeparator(operation.separator());
		entity.setDataSource( operation.dataSource());
		entity.setSliceKey(operation.sliceKey());
		entity.setValue( operation.value() );
		
		entity.setCommands(Commands.HASH);
		
		entity.setMapKey(operation.hashKey());
		
		
		return entity;
	}
	
	
	public static final OperationEntity getOperationEntity(ListOperation operation, Class<?> clazz) {
		if(operation == null)
			return null;
		
		OperationEntity entity = new OperationEntity();
		entity.setClazz(clazz);
		entity.setKey( operation.key());
		entity.setPrefix(operation.prefix());
		entity.setSeparator(operation.separator());
		entity.setDataSource( operation.dataSource());
		entity.setSliceKey(operation.sliceKey());
		entity.setValue( operation.value() );
		
		entity.setCommands(Commands.LIST);
				
		return entity;
	}
	
	
	public static final OperationEntity getOperationEntity(SetOperation operation, Class<?> clazz) {
		if(operation == null)
			return null;
		
		OperationEntity entity = new OperationEntity();
		entity.setClazz(clazz);
		entity.setKey( operation.key());
		entity.setPrefix(operation.prefix());
		entity.setSeparator(operation.separator());
		entity.setDataSource( operation.dataSource());
		entity.setSliceKey(operation.sliceKey());
		entity.setValue( operation.value() );
		
		entity.setCommands(Commands.SET);
				
		return entity;
	}
	
	
	public static final OperationEntity getOperationEntity(SortedSetOperation operation, Class<?> clazz) {
		if(operation == null)
			return null;
		
		OperationEntity entity = new OperationEntity();
		entity.setClazz(clazz);
		entity.setKey( operation.key());
		entity.setPrefix(operation.prefix());
		entity.setSeparator(operation.separator());
		entity.setDataSource( operation.dataSource());
		entity.setSliceKey(operation.sliceKey());
		entity.setValue( operation.value() );
		
		entity.setCommands(Commands.SORTEDSET);
				
		return entity;
	}
	
}
