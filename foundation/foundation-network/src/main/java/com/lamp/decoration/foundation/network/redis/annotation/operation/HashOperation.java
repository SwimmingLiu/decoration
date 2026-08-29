package com.lamp.decoration.foundation.network.redis.annotation.operation;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.lamp.decoration.foundation.network.redis.annotation.Pattern;


@Retention(RUNTIME)
@Target({ METHOD })
public @interface HashOperation {

	String name() default "";

	String prefix() default "";
	
	String separator() default "_";
	
	String key();
	
	String hashKey();
		
	String dataSource() default "default";
	
	Pattern[] pattern() default Pattern.DEFAULT;
	
	String sliceKey() default "";
	
	String value() default "";
}
