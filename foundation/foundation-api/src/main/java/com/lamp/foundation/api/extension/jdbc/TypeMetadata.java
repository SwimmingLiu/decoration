package com.lamp.foundation.api.extension.jdbc;

import java.lang.reflect.Field;

import lombok.Data;

@Data
public class TypeMetadata {

    private int jdbcType;

    private String javaType;

    private Class<?> javaClass;

    private Field field;

}
