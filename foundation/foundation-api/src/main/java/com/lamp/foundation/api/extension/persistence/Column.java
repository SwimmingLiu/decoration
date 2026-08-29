package com.lamp.foundation.api.extension.persistence;


import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.lamp.foundation.api.extension.jdbc.type.JDBCTypeMapper;

/**
 * @author hahaha
 */
@Target({METHOD, FIELD})
@Retention(RUNTIME)
public @interface Column {

    String name() default "";

    JDBCTypeMapper jdbcType() default JDBCTypeMapper.NULL;

    Class<?> dbType() default Object.class;

    boolean unique() default false;

    boolean primaryKey() default false;

    String defaultValue() default "";

    /**
     * <pre>
     *  目前 mysql 只 支持 CURRENT_TIMESTAMP‌，防止以后支持更多的，所以只能设置为String
     *  现在把 onUpdate 设置为 true ，以后 onUpdate 支持更多的表达式，那么出现非常 “ 致命” 的兼容性问题
     * </pre>
     */
    String onUpdate() default "";

    boolean increment() default false;

    String comment();


}
