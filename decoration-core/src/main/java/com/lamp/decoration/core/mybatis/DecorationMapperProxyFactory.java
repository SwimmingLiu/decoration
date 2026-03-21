package com.lamp.decoration.core.mybatis;

import org.apache.ibatis.binding.MapperProxy;
import org.apache.ibatis.binding.MapperProxyFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class DecorationMapperProxyFactory<T> extends MapperProxyFactory<T> {

    public DecorationMapperProxyFactory(Class<T> mapperInterface) {
        super(mapperInterface);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected T newInstance(MapperProxy<T> mapperProxy) {
        DecorationMapperProxy<T> decorationMapperProxy = new DecorationMapperProxy<>(mapperProxy);
        return (T) Proxy.newProxyInstance(super.getMapperInterface().getClassLoader(), new Class[] {super.getMapperInterface()},
            decorationMapperProxy);
    }


    static class DecorationMapperProxy<T> implements InvocationHandler {

        private final MapperProxy<T> mapperProxy;

        public DecorationMapperProxy(MapperProxy<T> mapperProxy) {
            this.mapperProxy = mapperProxy;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            return this.mapperProxy.invoke(proxy, method, args);
        }
    }

}
