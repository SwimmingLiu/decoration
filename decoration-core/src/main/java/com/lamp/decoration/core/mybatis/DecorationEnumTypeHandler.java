package com.lamp.decoration.core.mybatis;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.EnumTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.lang.reflect.Field;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import com.lamp.foundation.api.enums.ValueMark;


/**
 * <pre>
 *      1. code ( 在大规模的情况下)
 *      2. 中文 ( 在小规模的情况下 提高数据的可读性 )
 *      3. 使用  name
 *
 *      识别方案：提供注解，标注那个字段
 *
 *  </pre>
 */
public class DecorationEnumTypeHandler<E extends Enum<E>> extends BaseTypeHandler<E> {

    private BaseTypeHandler<E> enumTypeHandler;

    public DecorationEnumTypeHandler(Class<E> type) {
        if (type == null) {
            throw new IllegalArgumentException("Type argument cannot be null");
        }
        if (this.buildValueMark(type)) {
            return;
        }
        this.enumTypeHandler = new EnumTypeHandler<>(type);
    }

    private boolean buildValueMark(Class<E> clazz) {
        Field[] fields = clazz.getDeclaredFields();
        if (fields.length > 0) {
            Field code = null, value = null, valueMark = null;
            for (Field field : fields) {
                ValueMark newValueMark = field.getAnnotation(ValueMark.class);
                if (Objects.equals("code", field.getName())) {
                    code = field;
                }
                if (Objects.equals("value", field.getName())) {
                    value = field;
                }
                if (newValueMark != null) {
                    if (valueMark == null) {
                        valueMark = field;
                        continue;
                    }
                    throw new IllegalArgumentException("Value marks must be unique");
                }
            }
            Field markField = null;
            if (Objects.nonNull(valueMark)) {
                markField = valueMark;
            } else if (Objects.nonNull(code)) {
                markField = code;
            } else if (Objects.nonNull(value)) {
                markField = value;
            }
            if (Objects.nonNull(markField)) {
                this.enumTypeHandler = new ValueMarkBaseTypeHandler<>(clazz, markField);
                return true;
            }

        }
        return false;
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, E parameter, JdbcType jdbcType) throws SQLException {
        enumTypeHandler.setNonNullParameter(ps, i, parameter, jdbcType);
    }

    @Override
    public E getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return enumTypeHandler.getNullableResult(rs, columnName);
    }

    @Override
    public E getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return enumTypeHandler.getNullableResult(rs, columnIndex);
    }

    @Override
    public E getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return enumTypeHandler.getNullableResult(cs, columnIndex);
    }

    static class ValueMarkBaseTypeHandler<E extends Enum<E>> extends BaseTypeHandler<E> {

        private final Map<Object, Object> enumToValueMap = new HashMap<>();

        private final Map<Object, Object> valueToEnumMap = new HashMap<>();


        public ValueMarkBaseTypeHandler(Class<E> clazz, Field markField) {
            markField.setAccessible(true);
            E[] constants = clazz.getEnumConstants();
            Map<Object, Object> enumToValueMap = new HashMap<>();
            Map<Object, Object> valueToEnumMap = new HashMap<>();
            for (E constant : constants) {
                try {
                    Object object = markField.get(constant);
                    enumToValueMap.put(constant, object);
                    valueToEnumMap.put(object, constant);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        @Override
        public void setNonNullParameter(PreparedStatement ps, int i, E parameter, JdbcType jdbcType) throws SQLException {
            Object value = enumToValueMap.get(parameter);
            if (jdbcType == null) {
                ps.setObject(i, value);
            } else {
                ps.setObject(i, value, jdbcType.TYPE_CODE); // see r3589
            }
        }

        @SuppressWarnings("unchecked")
        @Override
        public E getNullableResult(ResultSet rs, String columnName) throws SQLException {
            String s = rs.getString(columnName);
            return (E) valueToEnumMap.get(s);
        }

        @SuppressWarnings("unchecked")
        @Override
        public E getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
            String s = rs.getString(columnIndex);
            return (E) valueToEnumMap.get(s);
        }

        @Override
        public E getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
            String s = cs.getString(columnIndex);
            return (E) valueToEnumMap.get(s);
        }
    }
}
