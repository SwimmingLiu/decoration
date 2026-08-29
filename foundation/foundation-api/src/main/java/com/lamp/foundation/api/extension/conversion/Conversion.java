package com.lamp.foundation.api.extension.conversion;

/**
 * @author hahaha
 */
@FunctionalInterface
public interface Conversion<T, V> {


    T convert(V value);
}
