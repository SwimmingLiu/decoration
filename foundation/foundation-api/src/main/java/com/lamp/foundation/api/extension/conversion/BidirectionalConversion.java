package com.lamp.foundation.api.extension.conversion;

/**
 * @author hahaha
 */
public interface BidirectionalConversion<T, V> {


    Conversion<T, V> to();

    Conversion<V, T> from();

}
