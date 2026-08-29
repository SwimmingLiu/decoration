package com.lamp.foundation.base.lang.util;


import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * @author hahaha
 */
public class WrapperConverter {

    // 基本类型到包装类型的映射
    private static final Map<Class<?>, Class<?>> PRIMITIVE_TO_WRAPPER = new HashMap<>();
    // 包装类型到基本类型的映射
    private static final Map<Class<?>, Class<?>> WRAPPER_TO_PRIMITIVE = new HashMap<>();

    private static final Set<Class<?>> NUMBER_CLASSES = new HashSet<>();

    static {
        PRIMITIVE_TO_WRAPPER.put(boolean.class, Boolean.class);
        PRIMITIVE_TO_WRAPPER.put(byte.class, Byte.class);
        PRIMITIVE_TO_WRAPPER.put(short.class, Short.class);
        PRIMITIVE_TO_WRAPPER.put(int.class, Integer.class);
        PRIMITIVE_TO_WRAPPER.put(long.class, Long.class);
        PRIMITIVE_TO_WRAPPER.put(float.class, Float.class);
        PRIMITIVE_TO_WRAPPER.put(double.class, Double.class);
        PRIMITIVE_TO_WRAPPER.put(char.class, Character.class);

        PRIMITIVE_TO_WRAPPER.put(boolean[].class, Boolean[].class);
        PRIMITIVE_TO_WRAPPER.put(byte[].class, Byte[].class);
        PRIMITIVE_TO_WRAPPER.put(short[].class, Short[].class);
        PRIMITIVE_TO_WRAPPER.put(int[].class, Integer[].class);
        PRIMITIVE_TO_WRAPPER.put(long[].class, Long[].class);
        PRIMITIVE_TO_WRAPPER.put(float[].class, Float[].class);
        PRIMITIVE_TO_WRAPPER.put(double[].class, Double[].class);
        PRIMITIVE_TO_WRAPPER.put(char[].class, Character[].class);

        PRIMITIVE_TO_WRAPPER.forEach((k, v) -> {
            WRAPPER_TO_PRIMITIVE.put(v, k);
            if (Number.class.isAssignableFrom(v)) {
                NUMBER_CLASSES.add(v);
                NUMBER_CLASSES.add(k);
            }
        });
    }

    public static Map<Class<?>,Class<?>> primitiveToWrapper() {
        return PRIMITIVE_TO_WRAPPER;
    }

    public static boolean isBooleanWrapperAndPrimitive(Class<?> clazz) {
        return Objects.equals(clazz, Boolean.TYPE) || Objects.equals(clazz, boolean.class);
    }

    /**
     * 判断是否为基本类型
     */
    public static boolean isPrimitive(Class<?> clazz) {
        return clazz != null && clazz.isPrimitive();
    }

    /**
     * 判断是否为包装类型
     */
    public static boolean isWrapper(Class<?> clazz) {
        return clazz != null && WRAPPER_TO_PRIMITIVE.containsKey(clazz);
    }

    public static boolean isWrapperOrPrimitive(Class<?> clazz) {
        return isWrapper(clazz) || isPrimitive(clazz);
    }


    /**
     * 判断是否为基本类型数组
     */
    public static boolean isPrimitiveArray(Object obj) {
        return obj != null && obj.getClass().isArray() &&
               !obj.getClass().getComponentType().isPrimitive();
    }

    /**
     * 判断是否为包装类型数组
     */
    public static boolean isWrapperArray(Object obj) {
        if (obj == null || !obj.getClass().isArray()) {
            return false;
        }
        Class<?> componentType = obj.getClass().getComponentType();
        return (componentType == Boolean.class ||
                componentType == Byte.class ||
                componentType == Short.class ||
                componentType == Integer.class ||
                componentType == Long.class ||
                componentType == Float.class ||
                componentType == Double.class ||
                componentType == Character.class);
    }

    /**
     * 获取对应的基本类型
     */
    public static Class<?> getPrimitiveType(Class<?> wrapperType) {
        return WRAPPER_TO_PRIMITIVE.get(wrapperType);
    }

    /**
     * 获取对应的包装类型
     */
    public static Class<?> getWrapperType(Class<?> primitiveType) {
        return PRIMITIVE_TO_WRAPPER.get(primitiveType);
    }

    public static Class<?> getCorrespondTo(Class<?> type) {
        return isPrimitive(type) ? PRIMITIVE_TO_WRAPPER.get(type) : WRAPPER_TO_PRIMITIVE.get(type);
    }


    public static byte[] convertToByteArray(Byte[] objects) {
        final byte[] bytes = new byte[objects.length];
        for (int i = 0; i < objects.length; i++) {
            bytes[i] = objects[i];
        }
        return bytes;
    }

    public static Byte[] convertToObjectArray(byte[] bytes) {
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        final Byte[] objects = new Byte[bytes.length];
        for (int i = 0; i < bytes.length; i++) {
            objects[i] = bytes[i];
        }
        return objects;
    }

    public static boolean isNumberType(Class<?> type) {
        return NUMBER_CLASSES.contains(type);
    }

    /**
     * 将包装类型数组转换为基本类型数组
     */
    public static Object convertWrapperToPrimitiveArray(Object wrapperArray) {
        if (!isWrapperArray(wrapperArray)) {
            return null;
        }

        Class<?> wrapperType = wrapperArray.getClass().getComponentType();
        Class<?> primitiveType = getPrimitiveType(wrapperType);

        if (primitiveType == null) {
            return null;
        }

        int length = Array.getLength(wrapperArray);
        Object primitiveArray = Array.newInstance(primitiveType, length);

        for (int i = 0; i < length; i++) {
            Object element = Array.get(wrapperArray, i);
            if (element != null) {
                Array.set(primitiveArray, i, convertWrapperToPrimitiveValue(element, primitiveType));
            }
        }

        return primitiveArray;
    }

    /**
     * 将基本类型数组转换为包装类型数组
     */
    public static Object convertPrimitiveToWrapperArray(Object primitiveArray) {
        if (primitiveArray == null || !primitiveArray.getClass().isArray()) {
            return null;
        }

        Class<?> primitiveType = primitiveArray.getClass().getComponentType();
        Class<?> wrapperType = getWrapperType(primitiveType);

        if (wrapperType == null) {
            return null;
        }

        int length = Array.getLength(primitiveArray);
        Object wrapperArray = Array.newInstance(wrapperType, length);

        for (int i = 0; i < length; i++) {
            Object element = Array.get(primitiveArray, i);
            if (element != null) {
                Array.set(wrapperArray, i, convertPrimitiveToWrapperValue(element, wrapperType));
            }
        }

        return wrapperArray;
    }

    /**
     * 转换单个包装类型值为基本类型值
     */
    @SuppressWarnings("unchecked")
    private static <T> T convertWrapperToPrimitiveValue(Object wrapperValue, Class<?> primitiveType) {
        if (wrapperValue == null) {
            return null;
        }
        if(PRIMITIVE_TO_WRAPPER.containsKey(primitiveType)){
            return (T)wrapperValue;
        }
        return (T) wrapperValue;
    }

    /**
     * 转换单个基本类型值为包装类型值
     */
    private static Object convertPrimitiveToWrapperValue(Object primitiveValue, Class<?> wrapperType) {
        if (primitiveValue == null) {
            return null;
        }

        if (wrapperType == Boolean.class) {
            return primitiveValue instanceof Boolean ? primitiveValue : Boolean.valueOf(primitiveValue.toString());
        } else if (wrapperType == Byte.class) {
            return primitiveValue instanceof Byte ? primitiveValue : Byte.valueOf(primitiveValue.toString());
        } else if (wrapperType == Short.class) {
            return primitiveValue instanceof Short ? primitiveValue : Short.valueOf(primitiveValue.toString());
        } else if (wrapperType == Integer.class) {
            return primitiveValue instanceof Integer ? primitiveValue : Integer.valueOf(primitiveValue.toString());
        } else if (wrapperType == Long.class) {
            return primitiveValue instanceof Long ? primitiveValue : Long.valueOf(primitiveValue.toString());
        } else if (wrapperType == Float.class) {
            return primitiveValue instanceof Float ? primitiveValue : Float.valueOf(primitiveValue.toString());
        } else if (wrapperType == Double.class) {
            return primitiveValue instanceof Double ? primitiveValue : Double.valueOf(primitiveValue.toString());
        } else if (wrapperType == Character.class) {
            return primitiveValue instanceof Character ? primitiveValue : Character.valueOf(primitiveValue.toString().charAt(0));
        } else if (wrapperType == BigDecimal.class) {
            return new BigDecimal(primitiveValue.toString());
        }
        return primitiveValue;
    }


    public static Object convertStringToWrapperValue(String string, Class<?> wrapperType) {
        if (string == null) {
            return null;
        }

        if (wrapperType == Boolean.class || wrapperType == boolean.class) {
            return Boolean.valueOf(string);
        } else if (wrapperType == Byte.class || wrapperType == byte.class) {
            return Byte.valueOf(string);
        } else if (wrapperType == Short.class || wrapperType == short.class) {
            return Short.valueOf(string);
        } else if (wrapperType == Integer.class || wrapperType == int.class) {
            return Integer.valueOf(string);
        } else if (wrapperType == Long.class || wrapperType == long.class) {
            return Long.valueOf(string);
        } else if (wrapperType == Float.class || wrapperType == float.class) {
            return Float.valueOf(string);
        } else if (wrapperType == Double.class || wrapperType == double.class) {
            return Double.valueOf(string);
        } else if (wrapperType == Character.class || wrapperType == char.class) {
            return string.charAt(0);
        } else if (wrapperType == BigDecimal.class) {
            return new BigDecimal(string);
        }
        return string;
    }
}


