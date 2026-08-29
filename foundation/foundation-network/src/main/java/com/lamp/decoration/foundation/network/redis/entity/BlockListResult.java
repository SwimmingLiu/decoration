package com.lamp.decoration.foundation.network.redis.entity;

public class BlockListResult<T> {
	
	private String listName;
	
	private T entity;
	
	private double time;	
	
	public BlockListResult(String listName, T entity, double time) {
		super( ) ;
		this.listName = listName ;
		this.entity = entity ;
		this.time = time ;
	}

	public String getListName ( ) {
		return listName ;
	}

	public void setListName ( String listName ) {
		this.listName = listName ;
	}

	public T getEntity ( ) {
		return entity ;
	}

	public void setEntity ( T entity ) {
		this.entity = entity ;
	}

	public double getTime ( ) {
		return time ;
	}

	public void setTime ( double time ) {
		this.time = time ;
	}
}