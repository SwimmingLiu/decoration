package com.lamp.decoration.foundation.network.redis.spring;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.lamp.decoration.foundation.network.redis.commands.RedisCommands;
import com.lamp.decoration.foundation.network.redis.entity.TestEntity;


@Service
public class SpringInitTest{
	
	
	@Resource(name="test")
	private RedisCommands<Long, TestEntity> teRC;
	
	public SpringInitTest() {
		System.out.println( " 慧慧啊，我被实例化了啊啊啊");
	}
	
	
	@PostConstruct
	public void init(){
		TestEntity te = teRC.get(2);
		System.out.println( te);
	}
	
	
	
}
