package com.lamp.foundation.api.lang.function;

@FunctionalInterface
public interface TwoConsumer<T,V> {

    void accept(T t, V v);

}
