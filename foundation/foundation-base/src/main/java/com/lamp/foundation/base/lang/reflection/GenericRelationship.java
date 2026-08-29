package com.lamp.foundation.base.lang.reflection;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.lamp.foundation.api.lang.util.Recursion;
import com.lamp.foundation.base.lang.util.collections.CollectionsUtils;
import com.lamp.foundation.base.lang.util.foreach.OnlyForeach;
import com.lamp.foundation.base.lang.util.recursion.SimpleRecursion;

/**
 * @author hahaha
 */
public class GenericRelationship {

    @SuppressWarnings("PatternVariableCanBeUsed")
    public static Type getGenericSuperclass(Class<?> clazz) {
        Type superClass = clazz.getGenericSuperclass();
        if (superClass instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) superClass;
            Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
            return actualTypeArguments[0];
        }
        return null;
    }

    private final Class<?> clazz;

    private final boolean interfaced;

    /**
     * 应该是双key. current class + super or in
     */
    private final Map<Type, ClassGeneric> mapGeneric = new HashMap<>();

    private final Map<Class<?>, ClassGeneric> currentGenericMap = new HashMap<>();

    public GenericRelationship(Class<?> clazz) {
        this.clazz = clazz;
        this.interfaced = clazz.isInterface();
    }

    public void handler(Class<?> clazz) {
        if (!this.clazz.isAssignableFrom(clazz)) {
            throw new ClassCastException(clazz + " is not assignable from " + this.clazz);
        }
        ClassGeneric classGeneric = mapGeneric.get(clazz);
        if (!this.interfaced || clazz.isInterface()) {
            this.superClass(clazz);
        } else {
            this.all(clazz);
        }

    }

    private void all(Class<?> clazz) {
        List<Type> typeList = SimpleRecursion.of(CollectionsUtils.toList(clazz), new GenericRecursion());

    }

    @SuppressWarnings("PatternVariableCanBeUsed")
    private void superClass(Class<?> clazz) {
        ClassGeneric classGeneric = null;
        Type genericSuperclass = clazz;
        for (; ; ) {
            if (Objects.equals(this.clazz, genericSuperclass)) {
                return;
            }
            classGeneric = this.builderClassGeneric(genericSuperclass, classGeneric);
            if (genericSuperclass instanceof Class) {
                clazz = (Class<?>) genericSuperclass;
                genericSuperclass = clazz.getGenericSuperclass();
                continue;
            }
            if (genericSuperclass instanceof ParameterizedType) {
                ParameterizedType parameterizedType = (ParameterizedType) genericSuperclass;
                Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();

                Type finalGenericSuperclass = genericSuperclass;
                ClassGeneric finalClassGeneric = classGeneric;
                OnlyForeach.of(actualTypeArguments, (data, index, length, allData) -> {
                    this.buidler(data, finalClassGeneric, finalGenericSuperclass, index);
                });
                genericSuperclass = parameterizedType.getRawType();
            }

        }
    }


    private ClassGeneric builderClassGeneric(Type type, ClassGeneric superClass) {
        ClassGeneric classGeneric = new ClassGeneric();
        classGeneric.type = type;
        classGeneric.superClass = superClass;
        mapGeneric.put(type, classGeneric);
        return classGeneric;
    }


    private void buidler(Type type, ClassGeneric classGeneric, Type genericSuperclass, int index) {
        GenericInfo genericInfo = new GenericInfo();

    }

    public static class ClassGeneric {

        /**
         * 是否定义类型，通过 class.getTypeParameters() 创建的对象
         */
        private boolean statement = false;

        private Type type;

        /**
         * <pre>
         *     1. 当前类自己定义的
         *     2. 从父类 ，父接口，接口 承接下来的
         *
         *     public class Test<AA,A,B,C,E,F,G></> extends A<A,B,C,Sring> implements B<E,F,G,Long>{}
         *
         *     <AA,A,B,C,E,F,G> 是当前类的 泛型
         *
         * </pre>
         */
        private List<GenericInfo> currentList;

        /**
         * <pre>
         *      public class Test<AA,A,B,C,E,F,G></> extends A<A,B,C,Sring> implements B<E,F,G,Long>{}
         *      Sring，Long，是具体的泛型
         * </pre>
         */
        private List<GenericInfo> specificList;

        private ClassGeneric superClass;

        private Map<Class<?>, ClassGeneric> interfaceGenericMap;

    }

    static class GenericInfo {

        private int index;

        /**
         * 是否是自己的 定义的
         */
        private boolean self;

        /**
         * 是否是定义，非具体的类型
         */
        private boolean definition;

        /**
         * 泛型的名字
         */
        private String express;

        /**
         * 继承 or 实现 之后， 具体的类型
         */
        private Class<?> type;

    }

    class GenericRecursion implements Recursion<Type> {

        private ClassGeneric superClass;


        @SuppressWarnings("PatternVariableCanBeUsed")
        @Override
        public Match match(Type type) {

            if (type instanceof Class) {
                Class<?> aClass = (Class<?>) type;
                if (Objects.equals(aClass, Object.class)) {
                    return Match.NOT_THOROUGHLY_AND_ABANDON;
                }
                if (!clazz.isAssignableFrom(aClass)) {
                    return Match.NOT_THOROUGHLY_AND_ABANDON;
                }
                if (Objects.equals(aClass.getSuperclass(), Object.class)) {
                    return Match.NOT_THOROUGHLY_ADD;
                }
                return Match.THOROUGHLY_ADD;
            }
            return Match.THOROUGHLY_ADD;
        }

        @SuppressWarnings("PatternVariableCanBeUsed")
        @Override
        public List<Type> child(Type type) {
            Class<?> rawClass = null;
            if (type instanceof Class) {
                rawClass = (Class<?>) type;
            } else if (type instanceof ParameterizedType) {
                ParameterizedType pt = (ParameterizedType) type;
                rawClass = (Class<?>) pt.getRawType();
            }
            if (Objects.isNull(rawClass)) {
                return Collections.emptyList();
            }

            if (!clazz.isAssignableFrom(rawClass)) {
                return Collections.emptyList();
            }

            ClassGeneric classGeneric = this.buildCurrentGenericInfo(rawClass);
            superClass = classGeneric;
            List<Type> child = new ArrayList<>();
            Type superType = rawClass.getGenericSuperclass();
            if (Objects.nonNull(superType)) {
                child.add(superType);
                this.buildCurrentGenericInfo(superType, classGeneric);
            }
            Type[] types = rawClass.getGenericInterfaces();
            for (Type t : types) {
                if (t instanceof ParameterizedType) {
                    ParameterizedType pt = (ParameterizedType) t;
                    rawClass = (Class<?>) pt.getRawType();
                    if (clazz.isAssignableFrom(rawClass)) {
                        child.add(t);
                    }
                }
            }
            return child;
        }

        public ClassGeneric buildCurrentGenericInfo(Class<?> clazz) {
            ClassGeneric classGeneric = currentGenericMap.get(clazz);
            if (Objects.nonNull(classGeneric)) {
                return classGeneric;
            }
            classGeneric = new ClassGeneric();
            currentGenericMap.put(clazz, classGeneric);
            classGeneric.type = clazz;
            TypeVariable<? extends Class<?>>[] typeParameters = clazz.getTypeParameters();
            if (typeParameters.length != 0) {
                classGeneric.currentList = OnlyForeach.of(typeParameters, this::builderGenericInfo);
            }
            return classGeneric;
        }

        @SuppressWarnings("PatternVariableCanBeUsed")
        public void buildCurrentGenericInfo(Type type, ClassGeneric currentGenericInfo) {
            if (!(type instanceof ParameterizedType)) {
                return;
            }
            ParameterizedType pt = (ParameterizedType) type;
            Class<?> clazz = (Class<?>) pt.getRawType();
            this.buildCurrentGenericInfo(clazz);
            ClassGeneric classGeneric = new ClassGeneric();
            classGeneric.type = clazz;
            classGeneric.statement = true;
            Type[] types = pt.getActualTypeArguments();
            if (types.length != 0) {
                classGeneric.currentList = OnlyForeach.of(types, this::builderGenericInfoByType);
            }
            if (clazz.isInterface()) {
                if (Objects.isNull(currentGenericInfo.interfaceGenericMap)) {
                    currentGenericInfo.interfaceGenericMap = new HashMap<>(4);
                }
                currentGenericInfo.interfaceGenericMap.put(clazz, classGeneric);
            } else {
                currentGenericInfo.superClass = classGeneric;
            }
        }

        private GenericInfo builderGenericInfo(TypeVariable<? extends Class<?>> data, int index, int length,
            List<TypeVariable<? extends Class<?>>> allData) {
            GenericInfo genericInfo = new GenericInfo();
            genericInfo.index = index;
            genericInfo.express = data.getName();

            genericInfo.definition = true;

            return genericInfo;
        }

        private GenericInfo builderGenericInfoByType(Type data, int index, int length,
            List<Type> allData) {
            GenericInfo genericInfo = new GenericInfo();
            genericInfo.index = index;

            return genericInfo;
        }

    }

}
