package com.lamp.foundation.base.function.crud.load.databases.mysql;

import org.junit.Before;
import org.junit.Test;

import com.lamp.foundation.api.extension.databases.metadata.TableInfo;
import com.lamp.foundation.api.extension.jdbc.JDBCConfig;
import com.lamp.foundation.base.function.crud.entity.update.column.AddColumnEntity;
import com.lamp.foundation.base.function.crud.entity.update.table.RenameTableEntity;
import com.lamp.foundation.base.function.crud.load.java.ClassMetadataModel;

public class MySQLMetadataLoadTest {


    MySQLMetadataLoad mySQLMetadataLoad = new MySQLMetadataLoad();

    ClassMetadataModel classMetadataModel = new ClassMetadataModel();

    @Before
    public void init() throws Exception {
        JDBCConfig jdbcConfig = new JDBCConfig();
        jdbcConfig.setType("mysql");
        jdbcConfig.setAddress("127.0.0.1:3306");
        jdbcConfig.setDatabases("test_db");
        jdbcConfig.setUsername("root");
        jdbcConfig.setPassword("Ab123123@");

        mySQLMetadataLoad.setJdbcConfig(jdbcConfig);
        mySQLMetadataLoad.init();
    }

    @Test
    public void test_table_create() {
        classMetadataModel.setClazz(AddColumnEntity.class);
        TableInfo tableInfo = classMetadataModel.getTableInfo();

        mySQLMetadataLoad.createTable(tableInfo);
    }

    @Test
    public void test_table_rename() {
        classMetadataModel.setClazz(RenameTableEntity.class);
        TableInfo tableInfo = classMetadataModel.getTableInfo();
        mySQLMetadataLoad.renameTable(tableInfo);
    }

    @Test
    public void test_table_delete() {
        classMetadataModel.setClazz(AddColumnEntity.class);
        TableInfo tableInfo = classMetadataModel.getTableInfo();
        mySQLMetadataLoad.dropTable(tableInfo);

    }


    @Test
    public void test_full_snapshot() {

    }

}
