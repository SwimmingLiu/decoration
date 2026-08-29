package com.lamp.decoration.foundation.network.redis.entity;


import com.lamp.decoration.foundation.network.redis.commands.CombinationElement;
import com.lamp.decoration.foundation.network.redis.create.KeyCreate;
import com.lamp.decoration.foundation.network.redis.net.Connection;

public class DefaultAsynResult<T , V> extends AsynResult< V >{
	
	private CombinationElement ce;
	
	private KeyCreate<T> keyCreate;
	
	private KeyCreate<T> keyCreateTwo;
	
	private Connection cnn;
	




	public DefaultAsynResult(CombinationElement ce, KeyCreate< T > keyCreate, KeyCreate< T > keyCreateTwo, Connection cnn) {
		super( ) ;
		this.ce = ce ;
		this.keyCreate = keyCreate ;
		this.keyCreateTwo = keyCreateTwo ;
		this.cnn = cnn ;
	}

	public CombinationElement getCe ( ) {
		return ce ;
	}

	public void setCe ( CombinationElement ce ) {
		this.ce = ce ;
	}

	public KeyCreate< T > getKeyCreate ( ) {
		return keyCreate ;
	}

	public void setKeyCreate ( KeyCreate< T > keyCreate ) {
		this.keyCreate = keyCreate ;
	}

	public KeyCreate< T > getKeyCreateTwo ( ) {
		return keyCreateTwo ;
	}

	public void setKeyCreateTwo ( KeyCreate< T > keyCreateTwo ) {
		this.keyCreateTwo = keyCreateTwo ;
	}


	public Connection getCnn ( ) {
		return cnn ;
	}


	public void setCnn ( Connection cnn ) {
		this.cnn = cnn ;
	}
	
	
	
	
	
}
