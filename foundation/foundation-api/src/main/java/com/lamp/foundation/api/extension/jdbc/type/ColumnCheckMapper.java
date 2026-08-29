package com.lamp.foundation.api.extension.jdbc.type;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import com.lamp.foundation.api.extension.databases.check.column.DigitsColumnCheck;
import com.lamp.foundation.api.extension.databases.check.column.RationalColumnCheck;
import com.lamp.foundation.api.extension.databases.check.column.SizeColumnCheck;

/**
 * @author hahaha
 */
public class ColumnCheckMapper {


    private static final ColumnCheckMapper INSTANCE = new ColumnCheckMapper();

    private ColumnCheckMapper() {
    }

    public static ColumnCheckMapper getInstance() {
        return INSTANCE;
    }


    private Map<Class<?>, Set<JDBCTypeMapper>> mappers = new HashMap<>();

    {
        Set<JDBCTypeMapper> set = new HashSet<>();
        set.add(JDBCTypeMapper.TINYINT);
        set.add(JDBCTypeMapper.SMALLINT);
        set.add(JDBCTypeMapper.INTEGER);
        set.add(JDBCTypeMapper.BIGINT);
        set.add(JDBCTypeMapper.DOUBLE);
        set.add(JDBCTypeMapper.FLOAT);
        set.add(JDBCTypeMapper.NUMERIC);
        set.add(JDBCTypeMapper.DECIMAL);
        mappers.put(RationalColumnCheck.class, set);

        set = new HashSet<>();
        set.add(JDBCTypeMapper.CHAR);
        set.add(JDBCTypeMapper.VARCHAR);
        set.add(JDBCTypeMapper.VARBINARY);

        mappers.put(SizeColumnCheck.class, set);

        set = new HashSet<>();
        set.add(JDBCTypeMapper.NUMERIC);
        set.add(JDBCTypeMapper.DECIMAL);

        mappers.put(DigitsColumnCheck.class, set);

    }

    public boolean match(Class<?> check, JDBCTypeMapper typeMapper) {
        Set<JDBCTypeMapper> checkSet = this.mappers.get(check);
        if (Objects.isNull(checkSet)) {
            return false;
        }
        return checkSet.contains(typeMapper);
    }

}
