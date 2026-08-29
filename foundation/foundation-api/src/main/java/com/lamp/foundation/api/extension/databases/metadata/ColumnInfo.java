package com.lamp.foundation.api.extension.databases.metadata;

import java.lang.reflect.Field;
import java.util.Map;

import com.lamp.foundation.api.extension.databases.check.column.ColumnCheck;
import com.lamp.foundation.api.extension.jdbc.type.JDBCTypeMapper;
import com.lamp.foundation.api.extension.sort.SortWrapper;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hahaha
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ColumnInfo extends DBMatedata {

    private Field field;

    private String tableName;

    private String name;

    private String onceName;

    private ColumnInfo lastColumn;

    private ColumnInfo nextColumn;

    private String columnName;

    private Integer ordinalPosition;

    private SortWrapper sortWrapper;

    private JDBCTypeMapper mapper;

    private boolean nullable;

    private boolean increment;

    private boolean primaryKey;

    private boolean unique;

    private String defaultValue;

    private String onUpdate;

    private String comment;

    private Map<Class<?> , ColumnCheck> columnCheckMap;


    public String getBaseInfo(){
        return " class is " + this.field.getDeclaringClass().getName()
               + " field is " + this.field.getName()
               + " column is " + this.columnName;
    }

}
