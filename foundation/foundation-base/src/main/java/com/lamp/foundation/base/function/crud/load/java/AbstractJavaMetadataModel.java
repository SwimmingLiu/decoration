package com.lamp.foundation.base.function.crud.load.java;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.lamp.foundation.api.extension.databases.metadata.ColumnInfo;
import com.lamp.foundation.api.extension.databases.metadata.KeyInfo;
import com.lamp.foundation.api.extension.databases.metadata.KeyInfo.KeyLimitWrapper;
import com.lamp.foundation.api.extension.databases.metadata.KeyType;
import com.lamp.foundation.api.extension.databases.metadata.TableInfo;
import com.lamp.foundation.base.function.crud.CrudConfig;

public abstract class AbstractJavaMetadataModel implements JavaMetadataModel {

    private final TableInfo tableInfo = new TableInfo();

    private CrudConfig crudConfig;

    {
        tableInfo.setColumns(new ArrayList<>());
        tableInfo.setKeys(new ArrayList<>());
    }


    abstract List<KeyInfo> buildKeyInfo();

    abstract void buildTableInfo(TableInfo tableInfo);

    abstract List<ColumnInfo> buildColumnInfo();

    protected TableInfo tableInfo() {
        return tableInfo;
    }

    @Override
    public TableInfo getTableInfo() {
        this.buildTableInfo(this.tableInfo);
        this.tableInfo.setColumns(buildColumnInfo());
        this.tableInfo.setKeys(buildKeyInfo());
        this.tableInfo.getColumns().forEach(column -> {
            if (column.isPrimaryKey()) {
                if (Objects.nonNull(tableInfo.getPrimaryKey())) {
                    throw new RuntimeException("PrimaryKey is already set");
                }
                KeyInfo keyInfo = this.columnToKeyInfo(KeyType.PRIMARY, column);
                tableInfo.setPrimaryKey(keyInfo);
                return;
            }
            if (column.isUnique()) {
                KeyInfo keyInfo = this.columnToKeyInfo(KeyType.UNIQUE, column);
                this.tableInfo.getKeys().add(keyInfo);
            }
        });
        KeyInfo primaryKey = tableInfo.getPrimaryKey();
        for (KeyInfo keyInfo : this.tableInfo.getKeys()) {
            if (Objects.equals(KeyType.PRIMARY, keyInfo.getType())) {
                if (Objects.nonNull(primaryKey)) {
                    throw new RuntimeException("PrimaryKey is already set");
                }
                primaryKey = keyInfo;
            }
        }
        if (Objects.nonNull(primaryKey)) {
            this.tableInfo.setPrimaryKey(primaryKey);
        }
        return tableInfo;
    }

    private KeyInfo columnToKeyInfo(KeyType keyType, ColumnInfo column) {
        KeyLimitWrapper keyLimitWrapper = new KeyLimitWrapper();
        keyLimitWrapper.setKey(column.getName());

        KeyInfo keyInfo = new KeyInfo();
        keyInfo.setType(keyType);
        List<KeyLimitWrapper> keyLimitWrappers = new ArrayList<>();
        keyLimitWrappers.add(keyLimitWrapper);
        keyInfo.setLimitKeys(keyLimitWrappers);
        return keyInfo;

    }
}
