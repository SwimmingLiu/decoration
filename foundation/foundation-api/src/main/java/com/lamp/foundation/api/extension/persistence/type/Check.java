package com.lamp.foundation.api.extension.persistence.type;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * @author hahaha
 */
@Target({METHOD, FIELD})
@Retention(RUNTIME)
public @interface Check {
}
