package com.lamp.foundation.api.function.channel;

import com.lamp.foundation.api.function.channel.model.FullData;

@FunctionalInterface
public interface FullChannel<T> {

    void full(FullData<T> fullData);

}
