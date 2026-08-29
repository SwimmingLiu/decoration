package com.lamp.decoration.core.mybatis;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtConstructor;
import javassist.CtMethod;
import javassist.Modifier;
import javassist.NotFoundException;

import org.apache.ibatis.builder.annotation.MapperAnnotationBuilder;
import org.apache.ibatis.session.Configuration;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;

public class DecorationMapperAnnotationBuilder {

    private static final ThreadLocal<PassthroughData> THREAD_LOCAL_MAP = ThreadLocal.withInitial(PassthroughData::new);


    private static final Constructor<?> DECORATION_AGENT_CONSTRUCTOR = buildSubclass();

    public static void setMethod(Method method) {
        THREAD_LOCAL_MAP.get().method = method;
    }

    public static void setClass(Class<?> clazz) {
        THREAD_LOCAL_MAP.get().clazz = clazz;
    }

    public static Method getMethod() {
        return THREAD_LOCAL_MAP.get().method;
    }

    public static Class<?> getClazz() {
        PassthroughData passthroughData = THREAD_LOCAL_MAP.get();
        if (Objects.nonNull(passthroughData.clazz)) {
            return passthroughData.clazz;
        }
        Method method = DecorationMapperAnnotationBuilder.getMethod();
        return method.getDeclaringClass();
    }

    public static MapperAnnotationBuilder create(Configuration configuration, Class<?> mapperInterface) {
        try {
            return (MapperAnnotationBuilder) DECORATION_AGENT_CONSTRUCTOR.newInstance(configuration, mapperInterface);
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static Constructor<?> buildSubclass() {
        try {
            ClassPool pool = ClassPool.getDefault();
            //pool.importPackage("org.apache.ibatis.session.Configuration");
            pool.importPackage("org.apache.ibatis.builder.annotation.MapperAnnotationBuilder");
            pool.importPackage("com.lamp.decoration.core.mybatis.DecorationMapperAnnotationBuilder");

            CtClass innerClass = pool.makeClass(MapperAnnotationBuilder.class.getName() + "$DecorationAgent");
            innerClass.setSuperclass(pool.get("org.apache.ibatis.builder.annotation.MapperAnnotationBuilder"));

            CtClass configuration = pool.get("org.apache.ibatis.session.Configuration");
            CtClass clazz = pool.get("java.lang.Class");
            // 添加构造方法到静态内部类
            CtConstructor innerConstructor = new CtConstructor(new CtClass[] {configuration, clazz}, innerClass);
            innerConstructor.setBody("{ super($1,$2); }");
            innerConstructor.setModifiers(Modifier.PUBLIC);
            innerClass.addConstructor(innerConstructor);

            String parseStatementCode = "void parseStatement(java.lang.reflect.Method arg0) {" +
                                        "      DecorationMapperAnnotationBuilder.setMethod(arg0);" +
                                        "      super.parseStatement(arg0);" +
                                        "}";
            innerClass.addMethod(CtMethod.make(parseStatementCode, innerClass));
            Class<?> decorationAgentClazz =
                pool.toClass(innerClass, MapperAnnotationBuilder.class, DecorationMapperAnnotationBuilder.class.getClassLoader(),
                    MapperAnnotationBuilder.class.getProtectionDomain());
            return decorationAgentClazz.getDeclaredConstructor(Configuration.class, Class.class);
        } catch (NotFoundException | CannotCompileException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }


    static class PassthroughData {

        private Method method;

        private Class<?> clazz;

    }


}
