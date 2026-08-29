package com.lamp.foundation.base.extension.persistence;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.google.common.base.CaseFormat;
import com.lamp.foundation.api.extension.persistence.Entity;
import com.lamp.foundation.api.extension.persistence.Table;

public class PersistenceUtils {

    public static final List<String> SUFFIX = new ArrayList<>();

    static {
        SUFFIX.add("Entity");
        SUFFIX.add("DO");
    }

    public static String getTableName(Method method) {
        String tableName = getTableName(method.getDeclaringClass());
        if (Objects.nonNull(tableName)) {
            return tableName;
        }
        Parameter[] parameters = method.getParameters();
        return parameters.length != 1 ? null : getTableName(parameters[0].getType());
    }

    public static String getTableName(Table table) {
        if (Objects.isNull(table)) {
            return null;
        }
        Class<?> clazz = table.ref();

        if (!Objects.equals(clazz, Object.class)) {
            return table.value();
        }
        return getTableName(clazz);
    }

    public static String getTableName(Class<?> clazz) {
        Table table = clazz.getAnnotation(Table.class);
        if (Objects.nonNull(table)) {
            return getTableName(clazz.getAnnotation(Table.class));
        }
        Entity entity = clazz.getAnnotation(Entity.class);
        if (Objects.nonNull(entity)) {
            if (Objects.nonNull(entity.value())) {
                return entity.value();
            }
        }
        String tableName = clazz.getName();
        for (String suffix : SUFFIX) {
            if (tableName.endsWith(suffix)) {
                String name = tableName.substring(0, tableName.length() - suffix.length());
                return CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, name);
            }
        }
        return null;
    }


}
