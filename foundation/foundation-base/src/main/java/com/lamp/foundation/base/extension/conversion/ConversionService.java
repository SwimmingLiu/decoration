package com.lamp.foundation.base.extension.conversion;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import com.lamp.foundation.api.extension.conversion.BidirectionalConversion;
import com.lamp.foundation.api.extension.conversion.Conversion;
import com.lamp.foundation.api.extension.conversion.ConversionFactory;
import com.lamp.foundation.base.lang.reflection.GenericRelationship;
import com.lamp.foundation.base.lang.util.collections.CollectionsUtils;
import com.lamp.foundation.base.lang.util.collections.Tuple.Pair;
import com.lamp.foundation.base.lang.util.collections.Tuple.Triplet;

import lombok.Getter;

/**
 * @author hahaha
 */
public class ConversionService {

    private final Map<Pair<Class<?>, Class<?>>, Conversion<Object, Object>> pairConversionMap = new HashMap<>();

    @Getter
    private final Map<Triplet<Class<?>, Class<?>, Class<?>>, ConversionFactory<Object, Object, Object>> conversionFactoryMap = new HashMap<>();

    private final GenericRelationship genericRelationship = new GenericRelationship(BidirectionalConversion.class);

    private Pair<Class<?>, Class<?>> getKey(Class<?> clazz) {
        return null;
    }

    public void register(Class<?> clazz) {
        try {
            if (clazz.isAssignableFrom(Conversion.class)) {
                this.register((Conversion<?, ?>) clazz.newInstance());
            }
            if (BidirectionalConversion.class.isAssignableFrom(clazz)) {
                this.register((BidirectionalConversion<?, ?>) clazz.newInstance());
            }
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        Class<?>[] clazzs = clazz.getDeclaredClasses();
        if (clazzs.length == 0) {
            return;
        }
        CollectionsUtils.toList(clazzs).forEach(this::register);


    }

    @SuppressWarnings("unchecked")
    public void register(Conversion<?, ?> conversion) {
        this.pairConversionMap.put(this.getKey(conversion.getClass()), (Conversion<Object, Object>) conversion);
    }

    @SuppressWarnings("unchecked")
    public void register(Conversion<?, ?> conversion, Pair<Class<?>, Class<?>> key) {
        this.pairConversionMap.put(key, (Conversion<Object, Object>) conversion);
    }


    public void register(BidirectionalConversion<?, ?> conversion) {
        genericRelationship.handler(conversion.getClass());
        this.register(conversion.to());
        this.register(conversion.from());
    }

    public void register(ConversionFactory<?, ?, ?> factory) {

    }

    @SuppressWarnings("unchecked")
    public <T> T convert(Object source, Class<T> targetClass, Object defaultValue) {
        T result = this.baseConvert(source, targetClass);
        return Objects.isNull(result) ? (T) defaultValue : result;
    }


    public <T> T convert(Object source, Class<T> targetClass) {
        T result = convert(source, targetClass, null);
        if (Objects.isNull(result)) {
            throw new RuntimeException("No corresponding converter found. Can't convert " + source.getClass() + " to " + targetClass);
        }
        return result;
    }


    @SuppressWarnings("unchecked")
    private <T> T baseConvert(Object source, Class<T> targetClass) {
        Pair<Class<?>, Class<?>> key = Pair.of(source.getClass(), targetClass);
        Conversion<Object, Object> conversion = pairConversionMap.get(key);
        if (Objects.isNull(conversion)) {
            return null;
        }
        return (T) conversion.convert(source);
    }
}
