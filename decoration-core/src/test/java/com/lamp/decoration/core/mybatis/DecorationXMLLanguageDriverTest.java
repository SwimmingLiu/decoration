package com.lamp.decoration.core.mybatis;

import org.apache.commons.lang3.reflect.MethodUtils;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.session.Configuration;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

public class DecorationXMLLanguageDriverTest {

    DecorationXMLLanguageDriver driver = new DecorationXMLLanguageDriver();

    Configuration configuration = new Configuration();

    SQLFragment sqlFragment = new SQLFragment();

    @Before
    public void test_init() {
        sqlFragment.register(TestMapper.class);
        this.driver.setSqlFragment(sqlFragment);
        driver.init();


    }

    @Test
    public void test() {
        String test = "select *  from cluster where id = <if  test= 'type == 1'> 1 </if><if  test= 'type == 2'> 2 </if>";
        SqlSource sqlSource = driver.createSqlSource(this.configuration, test, null);
        Map<String, String> parameterMap = new HashMap<>();
        parameterMap.put("type", "1");
        BoundSql boundSql = sqlSource.getBoundSql(parameterMap);
        String sql = boundSql.getSql();
        System.out.println(sql);
    }

    @Test
    public void test_include() {

        DecorationMapperAnnotationBuilder.setClass(TestMapper.class);

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("select *  from cluster ");
        stringBuilder.append("  where id = <if  test= 'type == 1'> 1 </if>");
        stringBuilder.append("  and id = <if  test= 'type == 2'> 2 </if>");
        stringBuilder.append("  <ref name='test' /> ");
        SqlSource sqlSource = driver.createSqlSource(this.configuration, stringBuilder.toString(), null);

        Map<String, String> parameterMap = new HashMap<>();
        parameterMap.put("type", "1");
        BoundSql boundSql = sqlSource.getBoundSql(parameterMap);
        String sql = boundSql.getSql();
        System.out.println(sql);
    }


    @Test
    public void test_table_supplement() {
        String sql = "select * from <t/>";
        this.supplement(sql, "select");
    }

    @Test
    public void test_insert_supplement() {
        String string = "()values()";
        this.supplement(string, "insert");

        string = "insert ()values()";
        this.supplement(string, "insert");

        string = "INSERT ()values()";
        this.supplement(string, "insert");
    }

    @Test
    public void test_select_supplement() {
        String string = "where id =#{id}";
        this.supplement(string, "select");

        string = "from test where id2 =#{id2}";
        this.supplement(string, "select");
    }

    @Test
    public void test_update_supplement() {
        String string = "set id =#{id} where id = 1";
        this.supplement(string, "update");

    }

    private void supplement(String script, String type) {
        this.supplement(script, type, null, null);
    }

    private void supplement(String script, String type, Object parameter, String assertString) {
        DecorationMapperAnnotationBuilder.setMethod(MethodUtils.getMatchingMethod(TestMapper.class, type, TestEntity.class));
        SqlSource sqlSource = driver.createSqlSource(this.configuration, script, null);
        BoundSql boundSql = sqlSource.getBoundSql(parameter);
        String sql = boundSql.getSql();
        System.out.println(sql);
    }
}
