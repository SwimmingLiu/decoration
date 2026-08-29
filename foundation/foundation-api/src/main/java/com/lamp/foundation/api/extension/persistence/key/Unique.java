package com.lamp.foundation.api.extension.persistence.key;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.lamp.foundation.api.extension.persistence.KeyLimit;
import com.lamp.foundation.api.extension.persistence.key.Unique.List;

/**
 * @author hahaha
 */
@Repeatable(List.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Unique {

    String name();

    KeyLimit[] limit();

    @Target({ElementType.TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @interface List {

        Unique[] value() default {};
    }
}
