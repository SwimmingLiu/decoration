package com.lamp.decoration.foundation.network.redis.annotation;

public @interface OperationsName {
	
	
	String operations();
	
	String string();
	
	String hash();
	
	String list();
	
	String set();
	
	String sortedSet();
	
	String pubSub();
}
