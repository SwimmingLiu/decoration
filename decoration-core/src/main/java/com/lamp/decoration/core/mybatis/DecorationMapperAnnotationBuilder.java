package com.lamp.decoration.core.mybatis;

import org.apache.ibatis.builder.annotation.MapperAnnotationBuilder;
import org.apache.ibatis.session.Configuration;

public class DecorationMapperAnnotationBuilder extends MapperAnnotationBuilder {

    public DecorationMapperAnnotationBuilder(Configuration configuration, Class<?> type) {
        super(configuration, type);
    }


}
