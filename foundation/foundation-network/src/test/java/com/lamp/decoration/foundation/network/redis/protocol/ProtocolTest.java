package com.lamp.decoration.foundation.network.redis.protocol;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import org.junit.Test;

import com.lamp.decoration.foundation.network.redis.net.BIOConnection;
import com.lamp.decoration.foundation.network.redis.net.NetConfigure;
import com.lamp.decoration.foundation.network.redis.utils.LedisInputStream;


public class ProtocolTest {

	@Test
	public void ResolveStateNetProtocolTest(){
		ResolveNetProtocol<Boolean> psnp = new ResolveNetProtocol.ResolveStateNetProtocol();
		try{
			String  data = "+OK\r\n";
			LedisInputStream is = new LedisInputStream(  new ByteArrayInputStream( data.getBytes()) );
			boolean b =  psnp.analysis( is ,null);
			System.out.println( b );
		}catch (Exception e) {
			
		}
	}
	
	@Test
	public void ResolveExceedinglyNetProtocolTest(){
		ResolveNetProtocol<Boolean> psnp = new ResolveNetProtocol.ResolveStateNetProtocol();
		try{
			String  data = "-ERR unknown command 'foobar'\r\n";
			LedisInputStream  is = new LedisInputStream(  new ByteArrayInputStream( data.getBytes()) );
			boolean b =  psnp.analysis( is , null );
			System.out.println( b );
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void ResolveIntToLongNetProtocolTest(){
		ResolveNetProtocol<Long> psnp = new ResolveNetProtocol.ResolveIntNetProtocol();
		try{
			String  data = ":1000\r\n";
			LedisInputStream  is = new LedisInputStream(  new ByteArrayInputStream( data.getBytes()) );
			long b =  psnp.analysis( is , null );
			System.out.println( b );
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Test
	public void ResolveIntToBooleanNetProtocolTest(){
		ResolveNetProtocol<Boolean> psnp = new ResolveNetProtocol.ResolveIntToBooleanNetProtocol();
		try{
			String  data = ":1\r\n";
			LedisInputStream  is = new LedisInputStream(  new ByteArrayInputStream( data.getBytes()) );
			boolean b =  psnp.analysis( is , null );
			System.out.println( b );
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void protocolTest() throws IOException{
	    try {
		NetConfigure mc = new NetConfigure();
		BIOConnection bio = new BIOConnection(mc);
		OutputStream out = bio.getOutputStream();
		InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("pro.txt");
		BufferedReader bufr = new BufferedReader(new InputStreamReader(in));
		String line=null;
		byte[] s = "\r\n".getBytes();
		while((line=bufr.readLine())!=null){
		    out.write(line.getBytes());
		    out.write(s);
		}
		out.flush();
		Thread.sleep(1000);
		bio.getInputStream().readbyte();
	    } catch (InterruptedException e) {
		// TODO 自动生成的 catch 块
		e.printStackTrace();
	    }
	}
	
	@Test
	public void test(){
	    try {
		String de = "*3\r\n$3\r\nSET\r\n$5\r\nmykey\r\n$7\r\nmyvalue\r\n";
		NetConfigure mc = new NetConfigure();
		BIOConnection bio = new BIOConnection(mc);
		OutputStream out = bio.getOutputStream();
		out.write(de.getBytes());
		out.flush();
		bio.getInputStream().readbyte();
	    } catch (IOException e) {
		// TODO 自动生成的 catch 块
		e.printStackTrace();
	    }
	}
}
