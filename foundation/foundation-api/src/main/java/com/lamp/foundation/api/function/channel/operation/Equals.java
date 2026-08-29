package com.lamp.foundation.api.function.channel.operation;

import java.util.Objects;

@FunctionalInterface
public interface Equals<T> {

    Equals<Object> OBJECT_EQUALS = new ObjectEquals<>();


    EqualsResult operation(T old, T newData);


    class ObjectEquals<T> implements Equals<T> {

        @Override
        public EqualsResult operation(T o1, T o2) {
            if (Objects.isNull(o1) || Objects.isNull(o2)) {
                return EqualsResult.DIFFERENT;
            }
            return o1.equals(o2) ? EqualsResult.CONSISTENT : EqualsResult.DIFFERENT;
        }
    }
}
