/*
 *Copyright (c) [Year] [name of copyright holder]
 *[Software Name] is licensed under Mulan PubL v2.
 *You can use this software according to the terms and conditions of the Mulan PubL v2.
 *You may obtain a copy of Mulan PubL v2 at:
 *         http://license.coscl.org.cn/MulanPubL-2.0
 *THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
 *EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
 *MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 *See the Mulan PubL v2 for more details.
 */

package com.lamp.decoration.core.spring.validation.validato;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author laohu
 */
public class ConstantConstraintValidator implements ConstraintValidator<ConstantRange, Object> {

    private ConstantRange range;

    private Set<Object> valueSet;

    private boolean contains;

    @Override
    public void initialize(ConstantRange constraintAnnotation) {
        this.range = constraintAnnotation;
        this.contains = constraintAnnotation.contains();
        this.valueSet(range.clazz());

    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }
        return contains == this.valueSet(value).contains(value);
    }

    private Set<Object> valueSet(Object value) {
        if (Objects.nonNull(valueSet)) {
            return valueSet;
        }
         /*
          注解没案发使用  基础枚举对象，只能在运行的时候进读取
         */
        synchronized (this) {
            if (Objects.nonNull(valueSet)) {
                return valueSet;
            }
            this.valueSet = this.valueSet(value.getClass());
            return this.valueSet;
        }
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private Set<Object> valueSet(Class<?> clazz) {
        Set<Object> valueSet = new HashSet<>();
        if (Objects.equals(clazz, String.class) || clazz.isPrimitive()) {
            Collections.addAll(valueSet, range.constant());
        }
        if (!clazz.isEnum()) {
            throw new IllegalArgumentException(String.format("%s is not an enum", clazz.getName()));
        }
        for (String e : range.constant()) {
            valueSet.add(Enum.valueOf((Class<Enum>) clazz, e));
        }
        return valueSet;
    }
}
