package com.lamp.foundation.api.function.channel;

import java.util.Collection;

public interface AllOperationChannel<T> extends SingleOperationChannel<T> {


    void data(T data);


    default void data(Collection<T> data) {
        data.forEach(this::data);
    }

}
