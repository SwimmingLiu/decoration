package com.lamp.foundation.api.extension.jdbc.type;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.math.BigDecimal;
import java.sql.JDBCType;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * <pre>
 *     not null 问题
 *       默认 not null ， 要 null ， 需要标记 Null
 *       NotNull  NotEmpty ， NotBlank 都会设置为 not null
 *
 *     长度问题
 *        Size ，限制 字符串
 *        Digits， 这个是精度
 *
 *     识别对应的类型，读取 check 类型的注释，
 *
 * </pre>
 * @author hahaha
 */
public enum JDBCTypeMapper {


    BIT(JDBCType.BIT, Boolean.class, boolean.class),

    TINYINT(JDBCType.BIT, Byte.class, byte.class),

    SMALLINT(JDBCType.TINYINT, Short.class, short.class),

    INTEGER(JDBCType.INTEGER, Integer.class, int.class),

    BIGINT(JDBCType.BIGINT, Long.class, long.class),

    FLOAT(JDBCType.FLOAT, Float.class, float.class),

    REAL(JDBCType.REAL, Float.class, float.class),

    DOUBLE(JDBCType.DOUBLE, Double.class, double.class),

    NUMERIC(JDBCType.NUMERIC, BigDecimal.class),

    DECIMAL(JDBCType.DECIMAL, BigDecimal.class),

    CHAR(JDBCType.CHAR, String.class, Character.class),

    VARCHAR(JDBCType.VARCHAR, String.class, Character.class),

    LONGVARCHAR(JDBCType.LONGVARCHAR, String.class),

    DATE(JDBCType.DATE, LocalDate.class),

    TIME(JDBCType.TIME, LocalTime.class),

    TIMESTAMP(JDBCType.TIMESTAMP, LocalDateTime.class),

    BINARY(JDBCType.BINARY, Byte[].class, byte[].class, InputStream.class),

    VARBINARY(JDBCType.VARBINARY, Byte[].class, byte[].class, InputStream.class),

    LONGVARBINARY(JDBCType.LONGVARBINARY, Byte[].class, byte[].class, InputStream.class),

    BLOB(JDBCType.BLOB, InputStream.class, OutputStream.class),

    CLOB(JDBCType.CLOB, Reader.class, Writer.class),


    ENUM(JDBCType.NULL, null),

    SET(JDBCType.NULL, null),

    ARRAY(JDBCType.ARRAY, null),

    DATALINK(JDBCType.DATALINK, null),

    NULL(JDBCType.NULL, null),

    ;
    @lombok.Getter
    private final DataTypeMapper dataTypeMapper;

    JDBCTypeMapper(JDBCType jdbcType, Class<?> javaType, Class<?>... genericTypes) {
        this.dataTypeMapper = DataTypeMapper.fo(jdbcType, javaType, genericTypes);
    }

}
