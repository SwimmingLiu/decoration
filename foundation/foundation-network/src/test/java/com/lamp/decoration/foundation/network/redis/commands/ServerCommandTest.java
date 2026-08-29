package com.lamp.decoration.foundation.network.redis.commands;

import java.util.List;

import org.junit.Test;

import com.lamp.ledis.net.ConnectionFactoryTest;



public class ServerCommandTest extends ConnectionFactoryTest{
	
	//TODO ERROR
	@Test
	public void bgrewriteaof(){
		String str = serverc.bgrewriteaof();
		System.out.println(str);
	}
	
	//TODO ERROR
	@Test
	public void bgsave(){					
		String str = serverc.bgsave();
		System.out.println(str);
	}
	
	//TODO ERROR
	@Test
	public void clientGetName(){
		System.out.println(serverc.clientGetName()); 
	}

	@Test
	public void configGet(){
	//	List<String> list = serverc.configGet("slowlog-max-len");
		List<String> list = serverc.configGet("*");
		for(String str: list)
			System.out.println(str);
	}
	//ERROR
	@Test
	public void psync(){
		System.out.println(serverc.psync("?", "-1"));
	}
	
	@Test
	public void slaveof(){
		System.out.println(serverc.slaveof("127.0.0.1","6379"));
	}
	
	//ERROR resolveManyToListNetProtocol 这个协议有问题，凡是用了这个协议的都出问题
	@Test
	public void time(){
		List<String> list = serverc.time();
		for(String str : list){
			System.out.println(str);
		}
	}
	
	@Test
	public void clientKill(){
		System.out.println(serverc.clientKill("127.0.0.1:50315"));
	}
	

	@Test
	public void clientSetName(){
		System.out.println(serverc.clientSetName("hello")); 
	}
	
	
	@Test
	public void configSet(){
		System.out.println(serverc.configSet("slowlog-max-len", "100"));
	}
	
	@Test
	public void dbsize(){
		System.out.println(serverc.dbsize());
	}
	
	@Test
	public void flushall(){
		System.out.println(serverc.flushall());
	}
	
	@Test
	public void flushdb(){
		System.out.println(serverc.flushdb());
	}
	
	@Test
	public void lastsave(){
		System.out.println(serverc.lastSave());
	}
	
	@Test
	public void monitor(){
		System.out.println(serverc.monitor());
	}
	
	@Test
	public void save(){
		System.out.println(serverc.save());
	}
	
	//TODO 好像是成功的，因为执行后客户端会自动断开。但是会抛错。
	@Test
	public void shutdown(){
		String str = serverc.shutdown();
		System.out.println(str);
	}
	
}
