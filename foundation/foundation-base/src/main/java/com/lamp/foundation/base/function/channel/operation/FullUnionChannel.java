package com.lamp.foundation.base.function.channel.operation;

import com.lamp.foundation.api.function.channel.AllOperationChannel;
import com.lamp.foundation.api.function.channel.operation.EqualsResult;
import com.lamp.foundation.base.lang.util.collections.Tuple.Triplet;

public interface FullUnionChannel<T> extends AllOperationChannel<T> {


    @SuppressWarnings("unchecked")
    @Override
    default void data(T data) {
        this.union((Triplet<T, T, EqualsResult>) data);
    }

    void union(Triplet<T, T, EqualsResult> data);
}
