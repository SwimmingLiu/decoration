package com.lamp.foundation.base.lang.reflection;


import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;


public class InterfaceGenericResolver {

    /**
     * 获取类实现的所有接口及其泛型信息（包括继承的接口）
     *
     * @param clazz 目标类
     * @return Map<Type, Type [ ]>，Key为接口类型，Value为该接口的泛型参数数组
     */
    public static Map<Type, Type[]> getAllInterfaceGenerics(Class<?> clazz) {
        Map<Type, Type[]> result = new LinkedHashMap<>();
        Set<Type> visited = new HashSet<>();
        resolveInterfaces(clazz, result, visited);
        return result;
    }

    public static void main(String[] args) {
        Map<Type, Type[]> generics = getAllInterfaceGenerics(MyClass3.class);

        System.out.println("MyClass 实现的接口及泛型信息：");
        for (Map.Entry<Type, Type[]> entry : generics.entrySet()) {
            System.out.println("接口: " + entry.getKey());
            Type[] argsTypes = entry.getValue();
            if (argsTypes.length > 0) {
                System.out.print("  泛型参数: ");
                for (Type t : argsTypes) {
                    System.out.print(t.getTypeName() + " ");
                }
                System.out.println();
            } else {
                System.out.println("  无泛型参数或非参数化类型");
            }
        }
    }

    private static void resolveInterfaces(Type type, Map<Type, Type[]> result, Set<Type> visited) {
        if (type == null || visited.contains(type)) {
            return;
        }
        visited.add(type);

        Class<?> rawClass = null;
        Type[] genericInterfaces = null;

        // 判断 type 是 Class 还是 ParameterizedType
        if (type instanceof Class) {
            rawClass = (Class<?>) type;
            genericInterfaces = rawClass.getGenericInterfaces();
        } else if (type instanceof ParameterizedType) {
            ParameterizedType pt = (ParameterizedType) type;
            rawClass = (Class<?>) pt.getRawType();
            genericInterfaces = pt.getActualTypeArguments(); // 这里注意：对于接口本身，我们需要的是它继承的父接口，而不是它的参数

            // 记录当前接口及其泛型参数
            // 注意：如果 type 是 ParameterizedType，说明它是被具体化的接口，如 ChildInterface<String, Integer>
            // 我们通常关心的是接口定义上的泛型如何被填充的
            result.putIfAbsent(pt, pt.getActualTypeArguments());

            // 继续处理该接口继承的父接口
            Type[] parentInterfaces = rawClass.getGenericInterfaces();
            for (Type parent : parentInterfaces) {
                // 需要将父接口的泛型变量映射到当前的实际类型中，这非常复杂
                // 简化处理：直接递归获取父接口的原始定义，若需精确映射需使用 TypeVariable 解析
                // 此处演示获取结构，若需精确解析泛型传递关系，需更复杂的逻辑
                resolveSimpleInterfaceHierarchy(rawClass, result, visited);
            }
            return;
        }

        if (rawClass != null) {
            // 记录直接实现的接口
            Type[] directInterfaces = rawClass.getGenericInterfaces();
            for (Type iface : directInterfaces) {
                if (iface instanceof ParameterizedType) {
                    ParameterizedType pt = (ParameterizedType) iface;
                    result.putIfAbsent(pt, pt.getActualTypeArguments());
                    // 递归处理接口的父接口
                    resolveSimpleInterfaceHierarchy((Class<?>) pt.getRawType(), result, visited);
                } else if (iface instanceof Class) {
                    // 非泛型接口或裸类型
                    result.putIfAbsent(iface, new Type[] {iface});
                    resolveSimpleInterfaceHierarchy((Class<?>) iface, result, visited);
                }
            }
        }
    }

    private static void resolveSimpleInterfaceHierarchy(Class<?> iface, Map<Type, Type[]> result, Set<Type> visited) {
        if (visited.contains(iface)) {
            return;
        }
        visited.add(iface);

        Type[] parentInterfaces = iface.getGenericInterfaces();
        for (Type parent : parentInterfaces) {
            if (parent instanceof ParameterizedType) {
                ParameterizedType pt = (ParameterizedType) parent;
                // 注意：这里获取的是父接口在定义时的泛型声明，而非子类传递过来的具体类型
                // 若要获取子类传递后的具体类型，需要进行 TypeVariable 替换，此处仅展示结构获取
                result.putIfAbsent(pt, pt.getActualTypeArguments());
                resolveSimpleInterfaceHierarchy((Class<?>) pt.getRawType(), result, visited);
            } else if (parent instanceof Class) {
                result.putIfAbsent(parent, new Type[] {parent});
                resolveSimpleInterfaceHierarchy((Class<?>) parent, result, visited);
            }
        }
    }


    // 定义测试用的泛型接口和继承结构
    interface BaseInterface<T, S, D> {

        void baseMethod(T t);

        void test(S S, D d);
    }

    interface BaseInterface2<T, S, D> {

    }

    interface BaseInterface3<T, S, D, C> extends BaseInterface<Object, S, D>, BaseInterface2<T, S, C> {

    }

    interface ChildInterface<K, V> extends BaseInterface<K, String, String> {

        void childMethod(K k, V v);

    }

    // 测试类，实现了带有具体泛型的子接口
    class MyClass implements ChildInterface<String, Integer> {

        @Override
        public void baseMethod(String s) {
        }

        @Override
        public void test(String S, String string) {

        }

        @Override
        public void childMethod(String s, Integer i) {
        }
    }

    abstract class MyClass2<K, V> implements ChildInterface<K, V> {

    }

    class MyClass3 extends MyClass2<String, Integer> {

        @Override
        public void childMethod(String string, Integer integer) {

        }

        @Override
        public void baseMethod(String string) {

        }

        @Override
        public void test(String S, String string) {

        }
    }
}

