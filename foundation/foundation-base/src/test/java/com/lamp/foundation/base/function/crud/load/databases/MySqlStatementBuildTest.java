package com.lamp.foundation.base.function.crud.load.databases;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.lamp.foundation.api.extension.databases.check.column.ColumnCheck;
import com.lamp.foundation.api.extension.databases.check.column.SizeColumnCheck;
import com.lamp.foundation.api.extension.databases.metadata.ColumnInfo;
import com.lamp.foundation.api.extension.databases.metadata.OperationType;
import com.lamp.foundation.api.extension.jdbc.type.JDBCTypeMapper;
import com.lamp.foundation.base.function.crud.load.databases.mysql.MySqlStatementBuild;

public class MySqlStatementBuildTest {

    private MySqlStatementBuild mySqlStatementBuild = new MySqlStatementBuild();

    private ColumnInfo columnInfo = new ColumnInfo();

    @Before
    public void setUp() throws Exception {
        columnInfo.setTableName("test_table");
        columnInfo.setColumnName("test_column");
        columnInfo.setMapper(JDBCTypeMapper.VARCHAR);
        columnInfo.setDefaultValue("test_default");
        columnInfo.setComment("test_comment");
        columnInfo.setNullable(false);
        SizeColumnCheck sizeColumnCheck = new SizeColumnCheck();
        sizeColumnCheck.setMin(1);
        sizeColumnCheck.setMax(1024);
        Map<Class<?>, ColumnCheck> columnCheckMap = new HashMap<>();
        columnCheckMap.put(SizeColumnCheck.class, sizeColumnCheck);
        columnInfo.setColumnCheckMap(columnCheckMap);

        ColumnInfo lastColumn = new ColumnInfo();
        lastColumn.setColumnName("last_column");

        columnInfo.setLastColumn(lastColumn);
    }

    @Test
    public void test_columnInfo_add() {
        MySqlStatementBuild.build(columnInfo, OperationType.CREATE);
    }
}
