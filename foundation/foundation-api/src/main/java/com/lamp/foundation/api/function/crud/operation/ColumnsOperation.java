package com.lamp.foundation.api.function.crud.operation;

import com.lamp.foundation.api.extension.databases.metadata.ColumnInfo;

public interface ColumnsOperation {

    String addColumn(ColumnInfo columnInfo);

    String updateColumn(ColumnInfo columnInfo);

    String dropColumn(ColumnInfo columnInfo);

    String renameColumn(ColumnInfo columnInfo);

}
