package com.lamp.decoration.foundation.network.redis.entity;

import com.lamp.decoration.foundation.network.redis.annotation.Mapper;
import com.lamp.decoration.foundation.network.redis.annotation.Operation;
import com.lamp.decoration.foundation.network.redis.annotation.OperationList;
import com.lamp.decoration.foundation.network.redis.annotation.OperationsObject;
import com.lamp.decoration.foundation.network.redis.annotation.operation.ListOperation;
import com.lamp.decoration.foundation.network.redis.annotation.operation.StringOperation;

@Mapper
public interface OperationInert {

	
	@OperationList( operationsObject = {
			@OperationsObject( name = "test" , operations = @Operation( key = "id" , mapKey = "appId" ) ) } )
	public TestEntity get();
	
	
	@ListOperation(name="testEntity" , key = "id")
	@StringOperation(name="tesEntity" ,key="id")
	public TestEntity tesEntity();
	
	public void test();
	
	public TestEntity test2();
}
