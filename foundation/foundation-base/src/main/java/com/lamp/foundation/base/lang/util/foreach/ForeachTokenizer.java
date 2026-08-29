package com.lamp.foundation.base.lang.util.foreach;

/**
 * @author hahaha
 */
@FunctionalInterface
public interface ForeachTokenizer<T> {


    void foreach(StringBuilder stringBuilder, T data);
}
