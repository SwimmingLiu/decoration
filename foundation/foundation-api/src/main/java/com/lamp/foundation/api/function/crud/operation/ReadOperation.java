package com.lamp.foundation.api.function.crud.operation;

import java.util.List;

import com.lamp.foundation.api.extension.databases.metadata.TableInfo;

public interface ReadOperation {

    List<TableInfo> readMetadataByDatabase();

    TableInfo readMetadataByTable(String tableName);


    String readFullContent();

}
