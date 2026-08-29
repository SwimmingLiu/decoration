package com.lamp.foundation.api.extension.persistence;

import com.lamp.foundation.api.extension.databases.metadata.Sort;

public @interface KeyLimit {

    String value();

    Sort sort() default Sort.NOT;

    int limit() default -1;
}
