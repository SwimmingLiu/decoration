package com.lamp.foundation.api.extension.jdbc.type;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import lombok.Getter;

/**
 * @author hahaha
 */

@Getter
public enum DefaultMapper {

    Boolean(Boolean.class, JDBCTypeMapper.BIT),

    Byte(Byte.class, JDBCTypeMapper.TINYINT),

    Short(Short.class, JDBCTypeMapper.SMALLINT),

    Integer(Integer.class, JDBCTypeMapper.INTEGER),

    Long(Long.class, JDBCTypeMapper.BIGINT),

    Float(Float.class, JDBCTypeMapper.FLOAT),

    Double(Double.class, JDBCTypeMapper.DOUBLE),

    String(String.class, JDBCTypeMapper.VARCHAR),

    Date(LocalDate.class, JDBCTypeMapper.DATE),

    Time(LocalTime.class, JDBCTypeMapper.TIME),

    DateTime(LocalDateTime.class, JDBCTypeMapper.TIMESTAMP),

    ;


    private final Class<?> clazz;

    private final JDBCTypeMapper jdbcTypeMapper;

    DefaultMapper(Class<?> clazz, JDBCTypeMapper jdbcTypeMapper) {
        this.clazz = clazz;
        this.jdbcTypeMapper = jdbcTypeMapper;
    }


}

