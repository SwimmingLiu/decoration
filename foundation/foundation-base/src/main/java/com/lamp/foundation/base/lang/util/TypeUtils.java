package com.lamp.foundation.base.lang.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * @author hahaha
 */
public class TypeUtils {

    private static final Set<Class<?>> BASE_TYPES = new HashSet<>();

    static {
        WrapperConverter.primitiveToWrapper().forEach((k, v) -> {
            BASE_TYPES.add(k);
            BASE_TYPES.add(v);
        });

        BASE_TYPES.add(String.class);
        BASE_TYPES.add(String[].class);

        BASE_TYPES.add(LocalDateTime.class);
        BASE_TYPES.add(LocalDate.class);
        BASE_TYPES.add(LocalTime.class);
    }

    public static boolean isBaseType(Class<?> type) {
        return BASE_TYPES.contains(type);
    }

    public static void supplement(Set<Class<?>> types) {
        types.addAll(BASE_TYPES);
    }


    @SuppressWarnings("unchecked")
    public static <T> T byString(String value, Class<T> type) {

        if (String.class.equals(type)) {
            return (T) value;
        }
        if (WrapperConverter.isWrapper(type)) {
            return (T) WrapperConverter.convertStringToWrapperValue(value, type);
        }
        if (Objects.equals(String.class, type)) {

        }
        return null;
    }


}
