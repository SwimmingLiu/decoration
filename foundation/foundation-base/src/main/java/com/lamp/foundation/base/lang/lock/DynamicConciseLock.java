package com.lamp.foundation.base.lang.lock;

import java.util.function.Function;

@Deprecated
public class DynamicConciseLock<T, R> {

    private Function<T, R> function;

}
