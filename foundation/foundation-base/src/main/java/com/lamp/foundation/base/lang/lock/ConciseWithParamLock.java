package com.lamp.foundation.base.lang.lock;

import java.util.Objects;
import java.util.function.Function;

public class ConciseWithParamLock<T, R> {

    public static <T, R> ConciseWithParamLock<T, R> of(Function<T, R> function) {
        return new ConciseWithParamLock<>(function);
    }

    private volatile R data;

    private R notVolatileData;

    private final Function<T, R> function;


    public ConciseWithParamLock(Function<T, R> function) {
        this.function = function;
    }

    public R get(T r) {
        if (Objects.nonNull(notVolatileData)) {
            return notVolatileData;
        }
        if (Objects.nonNull(data)) {
            return data;
        }
        synchronized (this) {
            if (Objects.isNull(data)) {
                data = function.apply(r);
                notVolatileData = data;
            }
        }
        return data;
    }
}
