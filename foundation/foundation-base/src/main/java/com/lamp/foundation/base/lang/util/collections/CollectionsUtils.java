package com.lamp.foundation.base.lang.util.collections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Objects;

@SuppressWarnings("NullableProblems")
public class CollectionsUtils {

    public static <T> void add(List<T> list, T[] array) {
        for (T t : array) {
            list.add(t);
        }
    }

    public static <K, V> Map<K, V> create(Map<K, V> map) {
        return Objects.nonNull(map) ? map : new HashMap<>();
    }

    public static <E> List<E> create(List<E> list) {
        return Objects.nonNull(list) ? list : new ArrayList<>();
    }

    public static <T> Iterator<T> iterator(T[] array) {
        return new Iterator<>() {
            int i = 0;

            @Override
            public boolean hasNext() {
                return i < array.length;
            }

            @Override
            public T next() {
                return array[i++];
            }
        };
    }

    public static <T> List<T> toList(T t) {
        List<T> list = new ArrayList<>(1);
        list.add(t);
        return list;
    }

    public static <T> List<T> toList(T[] array) {
        return new List<>() {

            @Override
            public int size() {
                return array.length;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public boolean contains(Object o) {
                return false;
            }

            @Override
            public Iterator<T> iterator() {
                return CollectionsUtils.iterator(array);
            }

            @Override
            public Object[] toArray() {
                return array;
            }

            @SuppressWarnings("unchecked")
            @Override
            public <T> T[] toArray(T[] a) {
                return (T[]) array;
            }

            @Override
            public boolean add(T o) {
                throw new UnsupportedOperationException();
            }

            @Override
            public boolean remove(Object o) {
                throw new UnsupportedOperationException();
            }

            @Override
            public boolean containsAll(Collection<?> c) {
                throw new UnsupportedOperationException();
            }

            @Override
            public boolean addAll(Collection<? extends T> c) {
                throw new UnsupportedOperationException();
            }

            @Override
            public boolean addAll(int index, Collection<? extends T> c) {
                throw new UnsupportedOperationException();
            }

            @Override
            public boolean removeAll(Collection<?> c) {
                throw new UnsupportedOperationException();
            }

            @Override
            public boolean retainAll(Collection<?> c) {
                throw new UnsupportedOperationException();
            }

            @Override
            public void clear() {
                throw new UnsupportedOperationException();
            }

            @Override
            public T get(int index) {
                if (index >= 0 && index < array.length) {
                    return array[index];
                }
                throw new IndexOutOfBoundsException();
            }

            @Override
            public T set(int index, Object element) {
                throw new UnsupportedOperationException();
            }

            @Override
            public void add(int index, Object element) {
                throw new UnsupportedOperationException();
            }

            @Override
            public T remove(int index) {
                throw new UnsupportedOperationException();
            }

            @Override
            public int indexOf(Object o) {
                throw new UnsupportedOperationException();
            }

            @Override
            public int lastIndexOf(Object o) {
                throw new UnsupportedOperationException();
            }

            @Override
            public ListIterator<T> listIterator() {
                throw new UnsupportedOperationException();
            }

            @Override
            public ListIterator<T> listIterator(int index) {
                throw new UnsupportedOperationException();
            }

            @Override
            public List<T> subList(int fromIndex, int toIndex) {
                throw new UnsupportedOperationException();
            }
        };
    }

}
