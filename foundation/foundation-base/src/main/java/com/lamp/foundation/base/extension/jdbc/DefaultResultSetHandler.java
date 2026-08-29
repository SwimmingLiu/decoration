package com.lamp.foundation.base.extension.jdbc;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import com.lamp.foundation.api.extension.jdbc.OnceMetadataResultSetHandler;
import com.lamp.foundation.api.extension.jdbc.OnceMetadataResultSetHandler.OnecExecuteResultSetHandler;
import com.lamp.foundation.api.extension.jdbc.OnceResultSetHandler;
import com.lamp.foundation.base.extension.jdbc.result.ClassResultMapping;
import com.lamp.foundation.base.extension.jdbc.result.FieldMapping;

import lombok.Setter;

public class DefaultResultSetHandler extends AbstractResultSetHandler {

    private static final Map<Class<?>, OnceMetadataResultSetHandler> BEAN_HANDLERS = new HashMap<>();

    private static final Map<Class<?>, Map<String, Method>> METHOD_MAP = new ConcurrentHashMap<>();


    static {
        BEAN_HANDLERS.put(Long.class, new OnecExecuteResultSetHandler(OnceResultSetHandler.LONG_RESULT_SET_HANDLER));
        BEAN_HANDLERS.put(Integer.class, new OnecExecuteResultSetHandler(OnceResultSetHandler.INT_RESULT_SET_HANDLER));
        BEAN_HANDLERS.put(String.class, new OnecExecuteResultSetHandler(OnceResultSetHandler.STRING_RESULT_SET_HANDLER));
    }


    public static DefaultResultSetHandler newInstance(Class<?> clazz, boolean onceResultSet, OnceResultSetHandler onceResultSetHandler) {
        DefaultResultSetHandler resultSetHandler = new DefaultResultSetHandler();
        resultSetHandler.setClazz(clazz);
        resultSetHandler.setOnec(onceResultSet);
        resultSetHandler.setOnceResultSetHandler(onceResultSetHandler);
        return resultSetHandler;
    }

    @Setter
    private Class<?> clazz;

    @Setter
    private boolean onec;

    @Setter
    private JDBCService jdbcService;

    private OnceMetadataResultSetHandler onceResultSetHandler;


    @Override
    public Object handler(ResultSet rs) throws SQLException {
        List<String> nameList = this.metadata(rs);
        OnceMetadataResultSetHandler onceResultSetHandler = this.getOnceResultSetHandler();
        if (onec) {
            if(rs.next()) {
                return onceResultSetHandler.handler(rs, nameList, 1);
            }
            return null;
        } else {
            List<Object> list = new ArrayList<>();
            int index = 1;
            while (rs.next()) {
                list.add(onceResultSetHandler.handler(rs, nameList, index++));
            }
            return list;
        }
    }

    private OnceMetadataResultSetHandler getOnceResultSetHandler() {
        if (Objects.nonNull(onceResultSetHandler)) {
            return onceResultSetHandler;
        }
        if (clazz.isPrimitive() || Objects.equals(String.class, clazz)) {
            return BEAN_HANDLERS.get(clazz);
        }
        if (Objects.equals(clazz, Map.class)) {
            return OnceMetadataResultSetHandler.MAP_ONCE_METADATA_RESULT_SET_HANDLER;
        }
        return new HumpOnceMetadataResultSetHandler();
    }


    class HumpOnceMetadataResultSetHandler implements OnceMetadataResultSetHandler {

        @Override
        public Object handler(ResultSet resultSet, List<String> columnName, int index) throws SQLException {
            try {
                Constructor<?> constructor = clazz.getDeclaredConstructor();
                Object object = constructor.newInstance();
                ClassResultMapping classResultMapping = jdbcService.getResultMappingService().getClassResultMapping(clazz);
                Map<String, FieldMapping> fieldMappingMap = classResultMapping.getStringFieldMappingMap();
                for (int i = 0; i < columnName.size(); i++) {
                    String key = columnName.get(i);
                    FieldMapping fieldMapping = fieldMappingMap.get(key);
                    if (Objects.isNull(fieldMapping)) {
                        fieldMapping = fieldMappingMap.get(key.toLowerCase());
                    }
                    if (Objects.isNull(fieldMapping)) {
                        throw new RuntimeException(" database field '" + key + "' not found");
                    }
                    Object value = fieldMapping.getTypeHandler().getResult(resultSet, i + 1, null);
                    if (Objects.isNull(value)) {
                        continue;
                    }
                    fieldMapping.getMethod().invoke(object, value);
                }
                return object;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        }

        public Map<String, Method> getMethodMap() {
            return METHOD_MAP.computeIfAbsent(clazz, (k) -> new ConcurrentHashMap<>());
        }
    }


    public void setOnceResultSetHandler(OnceResultSetHandler onceResultSetHandler) {
        if (Objects.isNull(onceResultSetHandler)) {
            return;
        }
        this.onceResultSetHandler = new OnecExecuteResultSetHandler(onceResultSetHandler);
    }


}
