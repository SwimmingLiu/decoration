package com.lamp.foundation.base.lang.util.foreach;

import java.util.List;

@FunctionalInterface
public interface NotReturnForeachNode<T> {

    void foreach(T data, int index, int length, List<T> allData);
}
