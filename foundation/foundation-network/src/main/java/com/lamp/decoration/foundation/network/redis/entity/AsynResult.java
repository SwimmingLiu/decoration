package com.lamp.decoration.foundation.network.redis.entity;

public class AsynResult<V> {
	

	protected Exception exception = null;
	
	private V object;
	
	private Boolean isException;
	
	private volatile boolean set = false;

	public Exception getException ( ) {
		return exception ;
	}

	public void setException ( Exception exception ) {
		this.exception = exception ;
	}

	public V getObject ( ) {
		return object ;
	}

	public void setObject ( V object ) {
		this.object = object ;
	}

	public Boolean getIsException ( ) {
		return isException ;
	}

	public void setIsException ( Boolean isException ) {
		this.isException = isException ;
	}

	public boolean isSet ( ) {
		return set ;
	}

	public void setSet ( boolean set ) {
		this.set = set ;
	}
	
	

}
