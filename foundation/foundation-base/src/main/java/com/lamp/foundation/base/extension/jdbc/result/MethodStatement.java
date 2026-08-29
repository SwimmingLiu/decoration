package com.lamp.foundation.base.extension.jdbc.result;

import java.lang.reflect.Method;

import com.lamp.foundation.base.extension.jdbc.parameter.ParameterMap;

public class MethodStatement {

    private String id;

    private Method method;

    private Class<?> clazz;

    private Class<?> returnType;

    private ClassResultMapping classResultMapping;

    private ParameterMap parameterMap;

    private String sql;

}
