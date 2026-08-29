package com.lamp.foundation.base.lang.util.foreach;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import com.lamp.foundation.base.lang.util.TG;
import com.lamp.foundation.base.lang.util.collections.CollectionsUtils;

/**
 * @author hahaha
 */
public class OnlyForeach<T, V> {

    public static <T> void of(List<T> items, NotReturnForeachNode<T> foreachNode) {
        of(items, adaptation(foreachNode));
    }

    public static <T> void of(T[] items, NotReturnForeachNode<T> foreachNode) {
        of(items, adaptation(foreachNode));
    }


    public static <T, V> List<V> of(List<T> items, ForeachNode<T, V> foreachNode) {
        OnlyForeach<T, V> onlyForeach = new OnlyForeach<>();
        onlyForeach.foreachNode = foreachNode;
        onlyForeach.result = true;
        return onlyForeach.foreach(items);
    }

    public static <T, V> List<V> of(T[] items, ForeachNode<T, V> foreachNode) {
        return of(CollectionsUtils.toList(items), foreachNode);

    }

    private ForeachNode<T, V> foreachNode;

    private boolean result = false;

    private static <T, V> ForeachNode<T, V> adaptation(NotReturnForeachNode<T> notReturnForeachNode) {
        AdaptationNotReturnForeachNode<T, V> adaptationNotReturnForeachNode = new AdaptationNotReturnForeachNode<>();
        adaptationNotReturnForeachNode.notReturnForeachNode = notReturnForeachNode;
        return adaptationNotReturnForeachNode;
    }

    public List<V> foreach(T[] items) {
        List<T> list = CollectionsUtils.toList(items);
        return this.foreach(list);
    }

    public List<V> foreach(List<T> items) {
        int length = items.size();
        if (length == 0) {
            return TG.of(Collections.EMPTY_LIST);
        }
        List<V> result = null;
        if (this.result) {
            result = new ArrayList<>(length);
        }
        for (int i = 0; i < length; i++) {
            T t = items.get(i);
            V v = foreachNode.foreach(t, i, length, items);
            if (Objects.nonNull(result)) {
                result.add(v);
            }
        }
        return result;
    }

    static class AdaptationNotReturnForeachNode<T, V> implements ForeachNode<T, V> {

        private NotReturnForeachNode<T> notReturnForeachNode;

        @Override
        public V foreach(T data, int index, int length, List<T> allData) {
            notReturnForeachNode.foreach(data, index, length, allData);
            return null;
        }
    }

}
