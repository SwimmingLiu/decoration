package com.lamp.foundation.base.lang.lock;

import java.util.Objects;
import java.util.function.Supplier;

/**
 * @author hahaha
 */
public class ConciseLock<T> {

    public static <T> ConciseLock<T> of(Supplier<T> supplier) {
        return new ConciseLock<>(supplier);
    }


    private volatile T data;

    private T notVolatileData;

    private final Supplier<T> supplier;

    public ConciseLock(Supplier<T> supplier) {
        this.supplier = supplier;
    }

    public T get() {
        if (Objects.nonNull(notVolatileData)) {
            return notVolatileData;
        }
        if (Objects.nonNull(data)) {
            return data;
        }
        synchronized (this) {
            if (Objects.isNull(data)) {
                data = supplier.get();
                notVolatileData = data;
            }
        }
        return data;
    }

}
