package com.lamp.foundation.api.function.crud.operation;

import com.lamp.foundation.api.extension.databases.metadata.OperationData;
import com.lamp.foundation.api.extension.databases.metadata.TableInfo;

import lombok.Data;

@Data
public class FullOperationData {

    private TableInfo tableInfo;

    private String createContent;

    private String dropContent;

    private String renameContent;

    private OperationData<String> key;

    private OperationData<String> column;
}
