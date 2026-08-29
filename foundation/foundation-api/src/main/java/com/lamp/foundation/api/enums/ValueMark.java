package com.lamp.foundation.api.enums;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 不能标记在，方法 上，枚举重要的是不可变性。方法具有可变性。所以次枚举不能标记在方法上，只能标记在变量上
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValueMark {

}
