package com.lamp.decoration.core;

import java.io.File;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.SuperMethodCall;
import net.bytebuddy.matcher.ElementMatchers;

public class ByteBuddyInnerClassExample {

    public static void main(String[] args) throws Exception {
        // 创建内部类，继承外部类
        DynamicType.Unloaded<?> dynamicType = new ByteBuddy()
            .subclass(OuterClass.class)
            .name("InnerClass")
            .method(ElementMatchers.named("publicMethod"))
            .intercept(SuperMethodCall.INSTANCE)
            .make();

        // 保存生成的类文件
        dynamicType.saveIn(new File("."));

        // 加载并实例化
        Class<?> innerClass = dynamicType.load(ByteBuddyInnerClassExample.class.getClassLoader()).getLoaded();
        Object instance = innerClass.getDeclaredConstructor(OuterClass.class).newInstance(new OuterClass());

        System.out.println("内部类继承外部类并调用父类方法成功");
    }

    public static class OuterClass {

        private String privateField = "privateValue";

        private void privateMethod() {
            System.out.println("调用父类私有方法");
        }

        public String publicMethod() {
            return "publicMethod";
        }
    }
}

