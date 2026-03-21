package com.lamp.decoration.core.databases.mybatis;

import static java.lang.reflect.Proxy.newProxyInstance;

import org.apache.ibatis.session.SqlSessionFactory;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Objects;

import org.springframework.util.ReflectionUtils;

/**
 * 用于解决 分页参数问题
 */
public class DecorationSqlSessionProxy {

    private Class<?> sqlSessionTemplateClass;

    private Object sqlSessionTemplate;

    private Object sqlSessionProxy;

    public Class<?> getSqlSessionTemplateClass() throws ClassNotFoundException {
        sqlSessionTemplateClass = Class.forName("org.mybatis.spring.SqlSessionTemplate");
        return sqlSessionTemplateClass;
    }

    public void proxyHandler(Object sqlSessionTemplate) throws IllegalAccessException, ClassNotFoundException {
        Field sqlSessionProxyField = ReflectionUtils.findField(this.sqlSessionTemplateClass, "sqlSessionProxy");
        if (Objects.isNull(sqlSessionProxyField)) {
            throw new IllegalAccessException("SqlSessionTemplate is does not exist");
        }
        sqlSessionProxyField.setAccessible(true);
        this.sqlSessionProxy = sqlSessionProxyField.get(sqlSessionTemplate);
        Object newSqlSessionProxy = newProxyInstance(SqlSessionFactory.class.getClassLoader(),
            this.sqlSessionProxy.getClass().getInterfaces(), new SqlSessionInterceptor());
        sqlSessionProxyField.set(sqlSessionTemplate, newSqlSessionProxy);
        this.sqlSessionTemplate = sqlSessionTemplate;
    }

    private class SqlSessionInterceptor implements InvocationHandler {

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                return  method.invoke(sqlSessionTemplate, args);
        }
    }
}
