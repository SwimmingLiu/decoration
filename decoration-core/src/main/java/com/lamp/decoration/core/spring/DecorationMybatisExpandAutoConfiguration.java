package com.lamp.decoration.core.spring;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;

import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import com.lamp.decoration.core.mybatis.DecorationEnumTypeHandler;
import com.lamp.decoration.core.mybatis.DecorationMapperRegistry;
import com.lamp.decoration.core.mybatis.DecorationXMLLanguageDriver;

@org.springframework.context.annotation.Configuration
@EnableConfigurationProperties(DecorationProperties.class)
@AutoConfigureAfter(MybatisAutoConfiguration.class)
public class DecorationMybatisExpandAutoConfiguration {

    private static final Log logger = LogFactory.getLog(DecorationAutoConfiguration.class);


    @Bean
    @ConditionalOnProperty(name = "decoration.mybatisExpandConfig.enabled", havingValue = "true")
    public Object mybatisExpand(SqlSessionFactory sqlSessionFactory) {
        Configuration configuration = sqlSessionFactory.getConfiguration();
        DecorationXMLLanguageDriver decorationxmllanguagedriver = new DecorationXMLLanguageDriver();
        decorationxmllanguagedriver.init();

        configuration.getLanguageRegistry().register(decorationxmllanguagedriver);
        configuration.getTypeHandlerRegistry().setDefaultEnumTypeHandler(DecorationEnumTypeHandler.class);
        DecorationMapperRegistry decorationMapperRegistry = new DecorationMapperRegistry(configuration);
        try {
            FieldUtils.writeField(configuration.getLanguageRegistry(), "defaultDriverClass", DecorationXMLLanguageDriver.class, true);
            FieldUtils.writeDeclaredField(configuration, "mapperRegistry", decorationMapperRegistry, true);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        logger.info("decoration mybatis expand success");
        return decorationMapperRegistry;
    }
}
