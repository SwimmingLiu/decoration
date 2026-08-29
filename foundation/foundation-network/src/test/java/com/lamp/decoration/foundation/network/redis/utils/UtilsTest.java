package com.lamp.decoration.foundation.network.redis.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.Thread.State;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.lamp.ledis.entity.TestEntity;

public class UtilsTest {

	@Test
	public void byteToString() {
		String str;
		try {
			str = new String("\57fa\7840\7ed3\6784".getBytes("ISO-8859-1"), "utf-8");
			System.out.println(str);
		} catch (UnsupportedEncodingException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		byte[] by = new byte[] { 0, 49, 0, 13, 10, 103, 101, 116, 0, 36, 48, 13, 10, 13, 10 };
		System.out.println(new String(by));
	}

	@Test
	public void getClazz() {
		try {
			Thread.currentThread().getContextClassLoader().getResources("");
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}

	@Test
	public void mapJson() {
		Map<Integer, TestEntity> map = new HashMap<>();

		for (int i = 0; i < 20; i++) {
			map.put(i, new TestEntity(1, "test"));
		}
		String str = JSON.toJSONString(map);
		System.out.println(str);
	}

	@Test
	public void test() {
		String str = "{0:{\"appId\":0,\"id\":1001,\"name\":\"mset\"},1:{\"appId\":1,\"id\":1001,\"name\":\"mset\"},2:{\"appId\":2,\"id\":1001,\"name\":\"mset\"},3:{\"appId\":3,\"id\":1001,\"name\":\"mset\"},4:{\"appId\":4,\"id\":1001,\"name\":\"mset\"},5:{\"appId\":5,\"id\":1001,\"name\":\"mset\"},6:{\"appId\":6,\"id\":1001,\"name\":\"mset\"},7:{\"appId\":7,\"id\":1001,\"name\":\"mset\"},8:{\"appId\":8,\"id\":1001,\"name\":\"mset\"},9:{\"appId\":9,\"id\":1001,\"name\":\"mset\"},10:{\"appId\":10,\"id\":1001,\"name\":\"mset\"},11:{\"appId\":11,\"id\":1001,\"name\":\"mset\"},12:{\"appId\":12,\"id\":1001,\"name\":\"mset\"},13:{\"appId\":13,\"id\":1001,\"name\":\"mset\"},14:{\"appId\":14,\"id\":1001,\"name\":\"mset\"},15:{\"appId\":15,\"id\":1001,\"name\":\"mset\"},16:{\"appId\":16,\"id\":1001,\"name\":\"mset\"},17:{\"appId\":17,\"id\":1001,\"name\":\"mset\"},18:{\"appId\":18,\"id\":1001,\"name\":\"mset\"},19:{\"appId\":19,\"id\":1001,\"name\":\"mset\"}}";
		Map<Integer, TestEntity> map = JSON.parseObject(str, new TypeReference<Map<Integer, TestEntity>>() {
		});
		System.out.println(map);

	}

	private static final int PROBE_INCREMENT = 0x9e3779b9;
	private static final AtomicInteger probeGenerator = new AtomicInteger();

	@Test
	public void testaa() {
		System.out.println(128 >>> 3);
		for (int i = 0; i < 20; i++) {
			int p = probeGenerator.addAndGet(PROBE_INCREMENT);
			// System.out.println( p ) ;
		}
		for (int i = 0; i < 10; i++) {
			int n = 16 << i;
			// n = n - (n >>> 2);
			int rs = Integer.numberOfLeadingZeros(n) | (1 << (16 - 1));
			System.out.println("n :" + n + "  rs : " + rs);
			int size = (rs << 16) + 2;
			System.out.println("size : " + size);
			System.out.println((size >>> 16) + "   " + ((size + 100) >>> 16));
		}
		System.out.println((1 << (32 - 16)) - 1);
		int n = 16;
		System.out.println(n - (n >>> 2));
		System.out.println(tableSizeFor(16));
	}

	private static final int MAXIMUM_CAPACITY = 1 << 30;

	private static final int tableSizeFor(int c) {
		int n = c - 1;
		n |= n >>> 1;
		n |= n >>> 2;
		n |= n >>> 4;
		n |= n >>> 8;
		n |= n >>> 16;
		return (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
	}

	@Test
	public void map() {
		ConcurrentHashMap<Integer, Integer> map = new ConcurrentHashMap<>();

		for (int i = 0; i < 20; i++) {
			map.put(i, i);
		}
	}

	@Test
	public void testThread() {
		Thread th = new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					Thread.sleep(20000);
				} catch (InterruptedException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}

			}
		});
		long startTime = System.currentTimeMillis();
		th.start();
		int i = 0;
		for (;;) {
			System.out.println(th.getState());
			if (th.getState() == State.TERMINATED) {
				if (i++ > 10) {
					break;
				}
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}
		System.out.println("runtime : " + (System.currentTimeMillis() - startTime));
	}

	@Test
	public void hash() {
		HashMap<Integer, Integer> map = new HashMap<>();
		for (int i = 0; i < 100; i++) {
			map.put(i, i);
		}
		System.out.println(map.toString());
	}
	@Test
	public void testjson() {
		String str = "[ {1:2},{},{2:3}]";
		List<Map> list = JSON.parseArray(str, Map.class);
		System.out.println(list);
	}
	
}
