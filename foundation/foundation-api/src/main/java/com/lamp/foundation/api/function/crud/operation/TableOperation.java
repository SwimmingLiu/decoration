package com.lamp.foundation.api.function.crud.operation;

import com.lamp.foundation.api.extension.databases.metadata.TableInfo;

public interface TableOperation {



    String createTable(TableInfo tableInfo);

    String renameTable(TableInfo tableInfo);

    String dropTable(TableInfo tableInfo);


}
