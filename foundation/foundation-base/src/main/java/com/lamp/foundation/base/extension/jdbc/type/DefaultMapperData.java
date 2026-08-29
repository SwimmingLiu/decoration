package com.lamp.foundation.base.extension.jdbc.type;

import java.util.HashMap;
import java.util.Map;

import com.lamp.foundation.api.extension.jdbc.type.DefaultMapper;
import com.lamp.foundation.api.extension.jdbc.type.JDBCTypeMapper;
import com.lamp.foundation.base.lang.util.WrapperConverter;

public class DefaultMapperData {

    private static final Map<Class<?>, JDBCTypeMapper> CLASS_JDBC_TYPE_MAPPER_MAP = new HashMap<>();

    static {
        for(DefaultMapper mapper : DefaultMapper.values()){
            CLASS_JDBC_TYPE_MAPPER_MAP.put(mapper.getClazz(), mapper.getJdbcTypeMapper());
            if(WrapperConverter.isWrapperOrPrimitive(mapper.getClazz())){
                CLASS_JDBC_TYPE_MAPPER_MAP.put(WrapperConverter.getCorrespondTo(mapper.getClazz()), mapper.getJdbcTypeMapper());
            }
        }
    }

    public static JDBCTypeMapper getMapper(Class<?> clazz) {
        return CLASS_JDBC_TYPE_MAPPER_MAP.get(clazz);
    }
}
