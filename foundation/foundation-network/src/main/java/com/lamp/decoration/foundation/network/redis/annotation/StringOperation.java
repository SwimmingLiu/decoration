package com.lamp.decoration.foundation.network.redis.annotation;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target({ TYPE, METHOD })
public @interface StringOperation {

	String name() default "";

	String prefix() default "";
	
	String separator() default "_";
	
	String key();
		
	String dataSource() default "default";
	
	Pattern[] pattern() default Pattern.DEFAULT;
	
	String sliceKey() default "";
	
	String value() default "";
}
