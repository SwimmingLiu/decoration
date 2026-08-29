package com.lamp.decoration.core.mybatis;

import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.wrapper.ObjectWrapper;
import org.apache.ibatis.reflection.wrapper.ObjectWrapperFactory;

/**
 * MetaClass,BeanWrapper
 * <p>
 * Configuration.setObjectWrapperFactory
 * <p>
 *     解决 mybatis的 驼峰命名法，无法识别 bean 无 is前缀问题
 * </p>
 */
public class DecorationObjectWrapperFactory implements ObjectWrapperFactory {

    @Override
    public boolean hasWrapperFor(Object object) {
        return true;
    }

    @Override
    public ObjectWrapper getWrapperFor(MetaObject metaObject, Object object) {
        return null;
    }
}
