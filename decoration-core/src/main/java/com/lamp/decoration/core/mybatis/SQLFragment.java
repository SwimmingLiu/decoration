package com.lamp.decoration.core.mybatis;

import org.apache.ibatis.scripting.xmltags.SqlNode;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import com.lamp.foundation.base.extension.persistence.PersistenceUtils;

/**
 * <pre>
 *     1. 没有点，就是当前类的变量，
 *     2. 首字母有大写
 *     3. 首字母大小，只有一个点，获得当前包下的内容
 *     4. 小写开头，有点，招全路径
 * </pre>
 */
public class SQLFragment {

    private final Map<String, FragmentInfo> decorationFragment = new HashMap<>();

    private final Map<Class<?>, ClassFragment> currentFragment = new HashMap<>();

    private final Map<String, Map<String, FragmentInfo>> currentPackageFragment = new HashMap<>();

    private final Map<String, FragmentInfo> globalFragment = new HashMap<>();


    public void register(Class<?> clazz) {
        String tableName = PersistenceUtils.getTableName(clazz);
        if (Objects.nonNull(tableName)) {
            FragmentInfo fragmentInfo = new FragmentInfo();
            fragmentInfo.setContent(tableName);
            this.decorationFragment.put(null, fragmentInfo);
        }
        this.currentFragment.computeIfAbsent(clazz, this::analysis);

    }

    private ClassFragment analysis(Class<?> clazz) {
        Map<String, FragmentInfo> currentPackageFragmentMap =
            this.currentPackageFragment.computeIfAbsent(clazz.getPackage().getName(), key -> new HashMap<>());

        ClassFragment classFragment = new ClassFragment();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            SQL sql = field.getAnnotation(SQL.class);
            if (Objects.isNull(sql)) {
                continue;
            }
            FragmentInfo fragmentInfo = new FragmentInfo();
            fragmentInfo.setContent(sql.value());
            classFragment.currentFragment.put(field.getName(), fragmentInfo);
            this.globalFragment.put(clazz.getName() + "." + field.getName(), fragmentInfo);
            currentPackageFragmentMap.put(clazz.getSimpleName() + "." + field.getName(), fragmentInfo);
        }
        Class<?>[] interfaces = clazz.getInterfaces();
        for (Class<?> anInterface : interfaces) {
            this.currentFragment.computeIfAbsent(anInterface, this::analysis);
        }
        return classFragment;
    }


    public FragmentInfo getFragmentInfo(String fragmentName) {
        if (fragmentName.indexOf('.') == -1) {
            if (Character.isLowerCase(fragmentName.charAt(0))) {
                ClassFragment classFragment = this.currentFragment.get(DecorationMapperAnnotationBuilder.getClazz());
                return classFragment.currentFragment.get(fragmentName);
            } else {

            }
        }
        if (Character.isLowerCase(fragmentName.charAt(0))) {
            return this.globalFragment.get(fragmentName);
        } else {
            Method method = DecorationMapperAnnotationBuilder.getMethod();
            Class<?> clazz = method.getDeclaringClass();
            return this.currentPackageFragment.get(clazz.getPackage().getName()).get(fragmentName);
        }
    }


    static class ClassFragment {

        private final Map<String, FragmentInfo> currentFragment = new HashMap<>();

        private final Map<String, ClassFragment> currentPackageFragment = new HashMap<>();

    }

    public static class FragmentInfo {

        private boolean string;

        private String content;

        private SqlNode sqlNode;

        public boolean isString() {
            return string;
        }

        public void setString(boolean string) {
            this.string = string;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public SqlNode getSqlNode() {
            return sqlNode;
        }

        public void setSqlNode(SqlNode sqlNode) {
            this.sqlNode = sqlNode;
        }
    }

}
