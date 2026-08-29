package com.lamp.foundation.api.extension.jdbc.type;

import lombok.Getter;

/**
 * @author hahaha
 */

@Getter
public enum MySQLTypeMapper implements TypeMapper {

    BIT("boolean"),

    TINYINT,

    SMALLINT,

    INTEGER("int"),

    BIGINT,

    FLOAT,

    REAL,

    DOUBLE,

    NUMERIC,

    DECIMAL,

    CHAR,

    VARCHAR,

    LONGVARCHAR,

    DATE,

    TIME,

    TIMESTAMP,

    BINARY,

    VARBINARY,

    LONGVARBINARY,

    BLOB,

    CLOB,


    ENUM,

    SET,

    ;


    private final JDBCTypeMapper jdbcTypeMapper;


    private String type;

    MySQLTypeMapper(JDBCTypeMapper jdbcTypeMapper, String type) {
        this.jdbcTypeMapper = jdbcTypeMapper;
        this.type = type;
    }

    MySQLTypeMapper(JDBCTypeMapper jdbcTypeMapper) {
        this.jdbcTypeMapper = jdbcTypeMapper;
    }

    MySQLTypeMapper() {
        String name = this.name();
        this.jdbcTypeMapper = JDBCTypeMapper.valueOf(name);
        this.type = name.toLowerCase();
    }

    MySQLTypeMapper(String type) {
        this.type = type;
        String name = this.name();
        this.jdbcTypeMapper = JDBCTypeMapper.valueOf(name);
    }

}
