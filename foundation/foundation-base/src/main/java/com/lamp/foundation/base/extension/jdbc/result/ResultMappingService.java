package com.lamp.foundation.base.extension.jdbc.result;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.lamp.foundation.base.extension.jdbc.type.TypeHandlerService;

import lombok.Setter;

public class ResultMappingService {

    private Map<Class<?>, ClassResultMapping> classResultMappingMap = new ConcurrentHashMap<>();

    @Setter
    private TypeHandlerService typeHandlerService;

    public ClassResultMapping getClassResultMapping(Class<?> clazz) {
        return classResultMappingMap.computeIfAbsent(clazz, key -> {
            ClassResultMapping classResultMapping = new ClassResultMapping();
            classResultMapping.setClazz(clazz);
            classResultMapping.setTypeHandlerService(typeHandlerService);
            return classResultMapping;
        });
    }

}
