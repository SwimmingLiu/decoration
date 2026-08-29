package com.lamp.foundation.api.extension.persistence;


import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * @author tiger
 */
@Target(TYPE)
@Retention(RUNTIME)
public @interface Table {

    String value() default "";

    Class<?> ref() default Object.class;

}
