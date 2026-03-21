package com.lamp.decoration.core.mybatis;

import org.apache.ibatis.scripting.xmltags.SqlNode;

import java.util.HashMap;
import java.util.Map;

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

    private final Map<String, Map<String, ClassFragment>> currentPackageFragment = new HashMap<>();

    private final Map<String, ClassFragment> globalFragment = new HashMap<>();

    private void register(Class<?> clazz) {
        ClassFragment classFragment = this.currentFragment.computeIfAbsent(clazz, key -> new ClassFragment());

        Map<String, ClassFragment> currentPackageFragmentMap =
            this.currentPackageFragment.computeIfAbsent(clazz.getPackage().getName(), key -> new HashMap<>());
        currentPackageFragmentMap.put(clazz.getSimpleName(), classFragment);
        this.globalFragment.put(clazz.getName(), classFragment);
    }


    public FragmentInfo getFragmentInfo(String fragmentName) {

        return decorationFragment.get(fragmentName);
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
