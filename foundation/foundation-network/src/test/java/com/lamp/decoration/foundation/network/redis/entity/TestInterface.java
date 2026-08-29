package com.lamp.decoration.foundation.network.redis.entity;

import com.lamp.ledis.annotation.Mapper;
import com.lamp.ledis.annotation.Operation;
import com.lamp.ledis.annotation.OperationList;
import com.lamp.ledis.annotation.OperationsObject;

@Mapper
public interface TestInterface {


	@OperationList( operationsObject = {
			@OperationsObject( name = "test2" , operations = @Operation( key = "id" , mapKey = "appId" ) ) } )
	public TestEntity keyId();
}
