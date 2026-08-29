package com.lamp.foundation.api.http.interceptor;

public @interface  InterceptorLabel {

	String manufacturer();
	
	String version() ;
	
	boolean defaultVersion() default false;
}
