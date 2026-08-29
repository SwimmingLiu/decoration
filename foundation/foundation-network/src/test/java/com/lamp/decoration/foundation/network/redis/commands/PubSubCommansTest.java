package com.lamp.decoration.foundation.network.redis.commands;

import org.junit.Test;

import com.lamp.ledis.net.ConnectionFactoryTest;

public class PubSubCommansTest extends ConnectionFactoryTest{
	
	//订阅一个或多个符合给定模式的频道
	@Test
	public void psubscribe(){
	//TODO 这个地方返回的数据值有问题，没有按照redis客户端返回integer数据多返回了一个类型
		String str = psc.psubscribe("news.*");
		System.out.println(str);
		String str1 = psc.psubscribe("news.it.*");
		System.out.println(str1);
	}
	
	//将信息 message 发送到指定的频道 channel
	@Test
	public void publish(){
		System.out.println(psc.publish("news.*", "xx"));
		System.out.println(psc.publish("news.it.*", "it news"));

	}
	@Test
	public void pubsub(){
		//TODO  不能输出孔的数组
		//列出当前的活跃频道
		System.out.println("------ " + psc.pubsub("CHANNELS"));
		System.out.println("------ " + psc.pubsub("NUMSUB"));
	}
	public void pubsubscribe(){
		
	}
	public void subscribe(){
		String str = psc.subscribe("msg");
		System.out.println(str);
	}
	public void unsubscribe(){
		psc.unsubscribe(null);
	}

}
