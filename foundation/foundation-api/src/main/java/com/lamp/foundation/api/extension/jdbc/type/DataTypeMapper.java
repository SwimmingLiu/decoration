package com.lamp.foundation.api.extension.jdbc.type;

import java.sql.JDBCType;

import lombok.Data;

@Data
public class DataTypeMapper {


    public static DataTypeMapper fo(JDBCType jdbcType, Class<?> javaType, Class<?>... genericTypes) {
        DataTypeMapper mapper = new DataTypeMapper();
        mapper.jdbcType = jdbcType;
        mapper.mainJavaType = javaType;
        mapper.javaType = genericTypes;
        return mapper;

    }

    /**
     * @see java.sql.JDBCType
     */
    private JDBCType jdbcType;

    private Class<?> mainJavaType;

    private Class<?>[] javaType;

}
