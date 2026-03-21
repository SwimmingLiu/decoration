package com.lamp.decoration.core.mybatis;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.ibatis.binding.BindingException;
import org.apache.ibatis.binding.MapperProxyFactory;
import org.apache.ibatis.binding.MapperRegistry;
import org.apache.ibatis.builder.annotation.MapperAnnotationBuilder;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

public class DecorationMapperRegistry extends MapperRegistry {

    private final Configuration config;
    private Map<Class<?>, MapperProxyFactory<?>> knownMappers;

    @SuppressWarnings("unchecked")
    public DecorationMapperRegistry(Configuration config) {
        super(config);
        this.config = config;
        try {
            knownMappers = (Map<Class<?>, MapperProxyFactory<?>>) FieldUtils.readField(this, "knownMappers", true);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public <T> T getMapper(Class<T> type, SqlSession sqlSession) {
        return super.getMapper(type, sqlSession);
    }

    @Override
    public <T> boolean hasMapper(Class<T> type) {
        return super.hasMapper(type);
    }

    @Override
    public <T> void addMapper(Class<T> type) {
        if (type.isInterface()) {
            if (hasMapper(type)) {
                throw new BindingException("Type " + type + " is already known to the MapperRegistry.");
            }
            boolean loadCompleted = false;
            try {
                knownMappers.put(type, new DecorationMapperProxyFactory<>(type));
                // It's important that the type is added before the parser is run
                // otherwise the binding may automatically be attempted by the
                // mapper parser. If the type is already known, it won't try.
                MapperAnnotationBuilder parser = new MapperAnnotationBuilder(config, type);
                parser.parse();
                loadCompleted = true;
            } finally {
                if (!loadCompleted) {
                    knownMappers.remove(type);
                }
            }
        }
    }

    @Override
    public Collection<Class<?>> getMappers() {
        return Collections.unmodifiableCollection(knownMappers.keySet());
    }

    @Override
    public void addMappers(String packageName, Class<?> superType) {
        super.addMappers(packageName, superType);
    }

    @Override
    public void addMappers(String packageName) {
        super.addMappers(packageName);
    }
}
