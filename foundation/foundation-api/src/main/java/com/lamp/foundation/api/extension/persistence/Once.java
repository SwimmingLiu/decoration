package com.lamp.foundation.api.extension.persistence;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * 此注解是自动生成，请不要修改，新增，删除注解
 * @author hahaha
 */
@Target({TYPE, FIELD})
@Retention(RUNTIME)
public @interface Once {

    /**
     * java 方面曾经的名字
     */
    String onecName();


    /**
     * 数据库 曾经的名字
     */
    String dbName();
}
