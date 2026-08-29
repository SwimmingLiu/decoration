package com.lamp.decoration.foundation.network.redis.commands;

import org.junit.Test;

import com.lamp.ledis.net.ConnectionFactoryTest;

public class ScriptCommandsTest extends ConnectionFactoryTest{

	@Test
	public void getLoad(){
		String s = sco.scriptLoad("return 'hello moto'");
		System.out.println(s);
	
	/*	String var = sco.evalsha(s);
		System.out.println(var);*/
		
/*		boolean flag =  sco.scriptExists("232fd51614574cf0867b83d384a5e898cfd24e5a");
		System.out.println(flag);*/
	}
}
