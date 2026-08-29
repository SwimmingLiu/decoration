package com.lamp.decoration.foundation.network.redis.entity;

import com.lamp.ledis.annotation.Operation;
import com.lamp.ledis.annotation.OperationList;
import com.lamp.ledis.annotation.OperationsObject;

/**
 * @author muqi
 *
 */
//@Mapper
@OperationList( operationsObject = {
		@OperationsObject( name = "test" , operations = @Operation( key = "id" , mapKey = "appId" ) ) } )
public class TestEntity {

	private int id ;

	private int appId ;

	private String name ;
	
	private Integer wages;

	public TestEntity() {
		System.out.println(" 初始化了 testEntity");
	}

	public TestEntity(int id, String name) {
		super( ) ;
		this.id = id ;
		this.name = name ;
		System.out.println(" 初始化了 testEntity");
	}

	public TestEntity(int id, int appId, String name) {
		super( ) ;
		this.id = id ;
		this.appId = appId ;
		this.name = name ;
		System.out.println(" 初始化了 testEntity");
	}
	
	public TestEntity(String name, Integer wages) {
		this.name = name ;
		this.wages = wages ;
		System.out.println(" 初始化了 testEntity");
	}
	

	public int getId ( ) {
		return id ;
	}


	public void setId ( int id ) {
		this.id = id ;
	}

	public String getName ( ) {
		return name ;
	}

	public void setName ( String name ) {
		this.name = name ;
	}

	public int getAppId ( ) {
		return appId ;
	}

	public void setAppId ( int appId ) {
		this.appId = appId ;
	}

	
	
	public Integer getWages ( ) {
		return wages ;
	}

	public void setWages ( Integer wages ) {
		this.wages = wages ;
	}

	@Override
	public String toString ( ) {
		return "TestEntity [id=" + id + ", appId=" + appId + ", name=" + name + ", wages=" + wages + "]\n\r" ;
	}

}
