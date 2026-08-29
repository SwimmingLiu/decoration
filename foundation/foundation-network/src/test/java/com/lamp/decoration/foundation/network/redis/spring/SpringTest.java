package com.lamp.decoration.foundation.network.redis.spring;

import java.io.IOException;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import com.lamp.ledis.commands.RedisCommands;
import com.lamp.ledis.entity.TestEntity;

public class SpringTest {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	public void springStart() throws IOException {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[] { "spring.xml" });
		context.start();
		RedisCommands<Long, TestEntity> sc = (RedisCommands)context.getBean("test");
		
	}
	
	@Test
	public void test() {
		ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
		try {
			resourcePatternResolver.getResources("com.**.ledis");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
