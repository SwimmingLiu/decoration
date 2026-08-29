package com.lamp.foundation.api.extension.persistence;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.lamp.foundation.api.extension.persistence.JoinTable.List;


/**
 * 不仅可以 join table ，还可以 join object，命名有些歧义
 *
 * @author hahaha
 */
@Repeatable(List.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface JoinTable {

    Class<?> entity();

    String prefix();


    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    @interface List {

        JoinTable[] value();
    }
}
