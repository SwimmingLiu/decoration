package com.lamp.foundation.api.extension.databases.metadata;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hahaha
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class TableInfo extends DBMatedata{

    private String name;

    private String onecName;

    private String dbName;

    private String clazz;

    private String className;

    private KeyInfo primaryKey;

    private List<KeyInfo> keys;

    private List<ColumnInfo> columns;

    private String comment;

    private String address;

    private String version;

    private LocalDateTime updateTime;


}
