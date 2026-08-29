package com.lamp.decoration.core.mybatis;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author hahaha
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface SQL {

    String value();

    String name() default "";
}
