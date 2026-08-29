package com.lamp.foundation.base.lang.util.foreach;

import java.util.List;

/**
 * @author hahaha
 */
@FunctionalInterface
public interface ForeachNode<T, V> {

    V foreach(T data, int index, int length, List<T> allData);

}
