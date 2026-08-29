package com.lamp.decoration.core.mybatis;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.ibatis.binding.BindingException;
import org.apache.ibatis.binding.MapperProxyFactory;
import org.apache.ibatis.binding.MapperRegistry;
import org.apache.ibatis.builder.annotation.MapperAnnotationBuilder;
import org.apache.ibatis.session.Configuration;

import java.util.Map;

public class DecorationMapperRegistry extends MapperRegistry {

    private final Configuration config;

    private final Map<Class<?>, MapperProxyFactory<?>> agentKnownMappers;

    @SuppressWarnings("unchecked")
    public DecorationMapperRegistry(Configuration config) {
        super(config);
        this.config = config;
        try {
            agentKnownMappers = (Map<Class<?>, MapperProxyFactory<?>>) FieldUtils.readField(this, "knownMappers", true);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public <T> void addMapper(Class<T> type) {
        if (type.isInterface()) {
            if (hasMapper(type)) {
                throw new BindingException("Type " + type + " is already known to the MapperRegistry.");
            }
            boolean loadCompleted = false;
            try {
                agentKnownMappers.put(type, new MapperProxyFactory<>(type));
                MapperAnnotationBuilder parser = DecorationMapperAnnotationBuilder.create(config, type);
                parser.parse();
                loadCompleted = true;
            } finally {
                if (!loadCompleted) {
                    agentKnownMappers.remove(type);
                }
            }
        }
    }

}
