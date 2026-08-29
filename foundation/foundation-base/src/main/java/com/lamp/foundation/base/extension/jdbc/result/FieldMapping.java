package com.lamp.foundation.base.extension.jdbc.result;

import java.lang.reflect.Method;

import com.lamp.foundation.api.extension.jdbc.TypeHandler;

import lombok.Data;

@Data
public class FieldMapping {

    private String columnName;

    private String fieldName;

    private Method method;

    private Class<?> type;

    private TypeHandler<?> typeHandler;

    private boolean autoMapping = false;


}
