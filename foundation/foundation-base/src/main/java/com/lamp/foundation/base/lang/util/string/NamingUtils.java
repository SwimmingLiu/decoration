package com.lamp.foundation.base.lang.util.string;

import java.lang.reflect.Field;

import com.lamp.foundation.api.root.Utils;
import com.lamp.foundation.base.lang.util.WrapperConverter;

public class NamingUtils implements Utils {


    public static String toUpperCamelCase(String name) {
        return toCamelCase(name, "_", true);

    }

    /**
     * 转大驼峰
     */
    public static String toUpperCamelCase(String name, String separator) {
        return toCamelCase(name, separator, true);

    }

    public static String toLowerCamelCase(String name) {
        return toCamelCase(name, "_", false);
    }

    public static String toLowerCamelCase(String name, String separator) {
        return toCamelCase(name, separator, false);
    }

    public static String toCamelCase(String name, String separator, boolean lowerCase) {
        String newName = name.toLowerCase();
        String[] names = newName.split(separator);
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < names.length; i++) {
            String s = names[i];
            if (lowerCase && i == 0) {
                result.append(s);
                continue;
            }
            result.append(s.substring(0, 1).toUpperCase()).append(s.substring(1));
        }
        return result.toString();
    }

    public static String camelCaseToLowerCamelCase(String name) {
        return camelCaseToSnakeCase(name, "_");
    }

    public static String camelCaseToSnakeCase(String f, String separator) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < f.length(); i++) {
            char c = f.charAt(i);
            if (Character.isUpperCase(c) && i != 0) {
                result.append(separator);
                result.append(Character.toLowerCase(c));
            } else {
                result.append(c);
            }
        }
        return result.toString();
    }

    public static String buildGetMethodName(Field field) {
        Class<?> clazz = field.getType();
        if (WrapperConverter.isBooleanWrapperAndPrimitive(clazz)) {
            return "is" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);
        }
        return buildSetMethodName(field.getName());
    }

    public static String buildSetMethodName(String fieldName) {
        return "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
    }

    public static String buildGetMethodName(String fieldName) {
        return "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
    }

}
