package com.lamp.foundation.api.lang.reference;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import lombok.Getter;

public abstract class TypeReference<T> {

    protected final Type type;

    @Getter
    protected final Class<T> clazz;

    @SuppressWarnings("unchecked")
    protected TypeReference() {
        Type superClass = this.getClass().getGenericSuperclass();
        Type type = ((ParameterizedType) superClass).getActualTypeArguments()[0];
        this.type = type;
        this.clazz = (Class<T>) type;
    }
}
