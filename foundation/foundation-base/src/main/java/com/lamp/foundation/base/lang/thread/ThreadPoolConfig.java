/*
 *Copyright (c) [Year] [name of copyright holder]
 *[Software Name] is licensed under Mulan PubL v2.
 *You can use this software according to the terms and conditions of the Mulan PubL v2.
 *You may obtain a copy of Mulan PubL v2 at:
 *         http://license.coscl.org.cn/MulanPubL-2.0
 *THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
 *EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
 *MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 *See the Mulan PubL v2 for more details.
 */
package com.lamp.foundation.base.lang.thread;

import java.lang.reflect.Method;

public class ThreadPoolConfig {

	private Class<?> clazz;
	
	private Method method;
	
	private Class<?>  parameterClazz;
	
	private String threadPoolName;
	
	private int poolSun;
	
	private String threadName;
	
	private int traffic;
	
	private int timeOut;
	
	/**
	 * 抛弃
	 * 异常
	 * 等待
	 * run
	 */
	private int dealTrafficOut;

	public Class<?> getClazz() {
		return clazz;
	}

	public void setClazz(Class<?> clazz) {
		this.clazz = clazz;
	}

	public Method getMethod() {
		return method;
	}

	public void setMethod(Method method) {
		this.method = method;
	}

	public Class<?> getParameterClazz() {
		return parameterClazz;
	}

	public void setParameterClazz(Class<?> parameterClazz) {
		this.parameterClazz = parameterClazz;
	}

	public String getThreadPoolName() {
		return threadPoolName;
	}

	public void setThreadPoolName(String threadPoolName) {
		this.threadPoolName = threadPoolName;
	}

	public int getPoolSun() {
		return poolSun;
	}

	public void setPoolSun(int poolSun) {
		this.poolSun = poolSun;
	}

	public String getThreadName() {
		return threadName;
	}

	public void setThreadName(String threadName) {
		this.threadName = threadName;
	}

	public int getTraffic() {
		return traffic;
	}

	public void setTraffic(int traffic) {
		this.traffic = traffic;
	}

	public int getTimeOut() {
		return timeOut;
	}

	public void setTimeOut(int timeOut) {
		this.timeOut = timeOut;
	}

	public int getDealTrafficOut() {
		return dealTrafficOut;
	}

	public void setDealTrafficOut(int dealTrafficOut) {
		this.dealTrafficOut = dealTrafficOut;
	}

	@Override
	public String toString() {
		return "ThreadPoolConfig [clazz=" + clazz + ", method=" + method + ", parameterClazz=" + parameterClazz
				+ ", threadPoolName=" + threadPoolName + ", poolSun=" + poolSun + ", threadName=" + threadName
				+ ", traffic=" + traffic + ", timeOut=" + timeOut + ", dealTrafficOut=" + dealTrafficOut + "]";
	}
	
	
	
	
}
