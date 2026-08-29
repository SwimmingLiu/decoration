package com.lamp.foundation.api.http.request;

import com.lamp.foundation.api.http.response.ReturnMode;
import com.lamp.foundation.api.extension.serialize.Serialize;

import java.lang.reflect.Method;


public class RequestWrapper {

	private RequestInfo requestInfo;
	
	
	public RequestWrapper(RequestInfo requestInfo) {
		this.requestInfo = requestInfo;
	}
	
	public Method method() {
		return requestInfo.getMethod();
	}
	
	public Object proxy(){
		return requestInfo.getProxy();
	}
	
	public boolean tls() {
		return requestInfo.isTls();
	}
	
	public ReturnMode returnMode() {
		return requestInfo.getReturnMode();
	}
	
	public Serialize serialize() {
		return requestInfo.getSerialize();
	}
}
