package com.lamp.foundation.base.function.crud.load.databases.mysql;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.lamp.foundation.api.extension.databases.metadata.ColumnInfo;
import com.lamp.foundation.api.extension.databases.metadata.KeyInfo;
import com.lamp.foundation.api.extension.databases.metadata.KeyInfo.KeyLimitWrapper;
import com.lamp.foundation.api.extension.databases.metadata.KeyType;
import com.lamp.foundation.api.extension.databases.metadata.Sort;
import com.lamp.foundation.api.extension.databases.metadata.TableInfo;
import com.lamp.foundation.base.extension.databases.mysql.information.Columns;
import com.lamp.foundation.base.extension.databases.mysql.information.Indexs;
import com.lamp.foundation.base.extension.databases.mysql.information.Tables;

public class TableInfoBuild {

    private final Map<String, TableInfo> tableInfoMap = new HashMap<>();

    private final List<Tables> tablesList;

    private final List<Columns> columnsList;

    private final List<Indexs> indexsList;


    public TableInfoBuild(List<Tables> tablesList, List<Columns> columnsList, List<Indexs> indexsList) {
        this.tablesList = tablesList;
        this.columnsList = columnsList;
        this.indexsList = indexsList;
    }


    public List<TableInfo> build() {
        this.buildTables();
        this.buildColumns();
        this.buildIndex();
        tableInfoMap.values().forEach(this::organize);
        return new ArrayList<>(tableInfoMap.values());
    }

    public void organize(TableInfo tableInfo) {
        List<KeyInfo> keyInfoList = tableInfo.getKeys();

        keyInfoList.forEach((keyInfo) -> {
            if (Objects.equals(keyInfo.getType(), KeyType.PRIMARY)) {
                tableInfo.setPrimaryKey(keyInfo);
            }
            keyInfo.getLimitKeys().sort(Comparator.comparingInt(KeyLimitWrapper::getIndexSchema));
        });
        keyInfoList.sort(Comparator.comparing(KeyInfo::getType));
        tableInfo.getColumns().sort(Comparator.comparingInt(ColumnInfo::getOrdinalPosition));
    }

    public void buildTables() {
        tablesList.forEach(table -> {
            TableInfo tableInfo = new TableInfo();

            tableInfo.setColumns(new ArrayList<>());
            tableInfo.setKeys(new ArrayList<>());
            tableInfoMap.put(table.getTableName(), tableInfo);

            tableInfo.setComment(table.getTableComment());
        });
    }

    public void buildColumns() {
        columnsList.forEach(column -> {
            ColumnInfo columnInfo = new ColumnInfo();
            columnInfo.setColumnName(column.getColumnName());
            columnInfo.setNullable(!Objects.equals("NO", column.getIsNullable()));
            columnInfo.setDefaultValue(column.getColumnDefault());
            columnInfo.setOrdinalPosition(column.getOrdinalPosition());

            if (Objects.nonNull(column.getExtra())) {
                if (column.getExtra().contains("CURRENT_TIMESTAMP") || column.getExtra().contains("current_timestamp")) {
                    columnInfo.setOnUpdate("current_timestamp");
                }
            }
            String columnType = column.getColumnType();
            if (Objects.nonNull(columnType)) {
                if (columnType.endsWith(")")) {
                    String context = columnType.substring(columnType.indexOf('(')+1, columnType.length() - 2);
                    if(Objects.equals(column.getDataType(), "varchar") || Objects.equals(column.getDataType(), "text")) {

                    }
                    int length = Integer.parseInt(columnType.substring(columnType.indexOf('(')+1, columnType.length() - 2));
                }
            }
            if (Objects.nonNull(column.getColumnKey())) {
                if (Objects.equals(column.getColumnKey(), "PRI")) {
                    columnInfo.setPrimaryKey(true);
                }
            }

            columnInfo.setComment(column.getColumnComment());

            tableInfoMap.get(column.getTableName()).getColumns().add(columnInfo);
        });
    }

    public void buildIndex() {
        Map<String, Map<String, KeyInfo>> tableNameAndKeyMap = new HashMap<>();
        indexsList.forEach(index -> {

            Map<String, KeyInfo> stringKeyInfoMap = tableNameAndKeyMap.computeIfAbsent(index.getTableName(), k -> new HashMap<>());
            KeyInfo keyInfo = stringKeyInfoMap.computeIfAbsent(index.getIndexName(), k -> {
                KeyInfo newKeyInfo = new KeyInfo();
                newKeyInfo.setKeyName(index.getIndexName());
                tableInfoMap.get(index.getTableName()).getKeys().add(newKeyInfo);
                newKeyInfo.setLimitKeys(new ArrayList<>());

                String indexName = index.getIndexName();
                newKeyInfo.setKeyName(indexName);
                if (Objects.equals(indexName, "PRIMARY")) {
                    newKeyInfo.setType(KeyType.PRIMARY);
                } else {
                    if (Objects.equals(index.getIndexType(), "BTREE")) {
                        newKeyInfo.setType(index.getNonUnique() == 0 ? KeyType.KEY : KeyType.UNIQUE);
                    }
                    if (Objects.equals(index.getIndexType(), "FULLTEXT")) {
                        newKeyInfo.setType(KeyType.FULLTEXT);
                    }
                }
                return newKeyInfo;
            });

            KeyLimitWrapper keyLimitWrapper = new KeyLimitWrapper();
            keyLimitWrapper.setKey(index.getColumnName());
            keyLimitWrapper.setIndexSchema(index.getSeqInIndex());
            keyLimitWrapper.setLimit(index.getSubPart());
            Sort sort = Objects.isNull(index.getCollation()) ? Sort.NOT : Objects.equals(index.getCollation(), "A") ? Sort.ASC : Sort.DESC;
            keyLimitWrapper.setSort(sort);

            keyInfo.getLimitKeys().add(keyLimitWrapper);

        });
    }
}
