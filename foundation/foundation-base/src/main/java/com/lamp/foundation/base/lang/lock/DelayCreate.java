package com.lamp.foundation.base.lang.lock;

import java.util.Objects;
import java.util.function.Supplier;

public class DelayCreate<T> {

    public static <T> DelayCreate<T> of(Supplier<T> supplier) {
        return new DelayCreate<>(supplier);
    }

    private final Supplier<T> supplier;

    private T value;

    private boolean initialized = false;

    public DelayCreate(Supplier<T> supplier) {
        this.supplier = supplier;
    }

    public T get() {
        if (Objects.nonNull(value) || !initialized) {
            return value;
        }
        this.value = supplier.get();
        this.initialized = true;
        return value;
    }
}
