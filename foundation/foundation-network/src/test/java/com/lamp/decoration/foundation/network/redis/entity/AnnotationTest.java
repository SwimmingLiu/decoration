package com.lamp.decoration.foundation.network.redis.entity;

import org.junit.Test;

import com.lamp.ledis.annotation.LedisAanntationCollection;

public class AnnotationTest {
	
	Class<?> clazz = OperationInert.class;
	
	@Test
	public void test() {
		LedisAanntationCollection ledisAanntationCollection = new LedisAanntationCollection();
		
		ledisAanntationCollection.addOperation(clazz);
	}

}
