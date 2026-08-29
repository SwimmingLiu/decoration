package com.lamp.decoration.core.mybatis;


import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.builder.annotation.MapperAnnotationBuilder;
import org.apache.ibatis.session.Configuration;


import org.junit.Test;

public class DecorationMapperAnnotationBuilderTest {


    private final Configuration configuration = new Configuration();

    @Test
    public void test() {
        MapperAnnotationBuilder builder = DecorationMapperAnnotationBuilder.create(configuration,TestMapper.class);
        builder.parse();
    }

    @Mapper
    interface TestMapper {

        @Insert("insert into  test ")
        void insert();

    }

}
