package com.lamp.foundation.api.extension.persistence.key;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import com.lamp.foundation.api.extension.persistence.KeyLimit;

@Retention(RetentionPolicy.RUNTIME)
public @interface Primary {

    String[] keys();

    KeyLimit[] limit() default {};
}
