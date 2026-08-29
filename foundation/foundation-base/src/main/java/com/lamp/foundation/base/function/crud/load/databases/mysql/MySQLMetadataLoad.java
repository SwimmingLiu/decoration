package com.lamp.foundation.base.function.crud.load.databases.mysql;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.lamp.foundation.api.extension.databases.metadata.ColumnInfo;
import com.lamp.foundation.api.extension.databases.metadata.KeyInfo;
import com.lamp.foundation.api.extension.databases.metadata.OperationType;
import com.lamp.foundation.api.extension.databases.metadata.TableInfo;
import com.lamp.foundation.api.function.crud.operation.FullOperationData;
import com.lamp.foundation.api.lang.function.TwoConsumer;
import com.lamp.foundation.base.extension.databases.mysql.information.Columns;
import com.lamp.foundation.base.extension.databases.mysql.information.Indexs;
import com.lamp.foundation.base.extension.databases.mysql.information.Tables;
import com.lamp.foundation.base.function.crud.load.databases.AbstractDBMetadataLoad;


public class MySQLMetadataLoad extends AbstractDBMetadataLoad {


    @Override
    public List<TableInfo> readMetadataByDatabase() {
        return this.readMetadata(null);
    }

    @Override
    public TableInfo readMetadataByTable(String tableName) {
        List<TableInfo> tableInfoList = this.readMetadata(tableName);
        return tableInfoList.isEmpty() ? null : tableInfoList.get(0);
    }


    private List<TableInfo> readMetadata(String tableName) {
        String tableSql = "";
        if (Objects.nonNull(tableName)) {
            tableSql = " and  table_name = '" + tableName + "'";
        }
        try {
            String tablesSql = "select * from tables where table_schema = '" + this.metadataJDBCModel.getDatabaseName() + "'" + tableSql;
            List<Tables> tablesList = this.metadataJDBCModel.getMetabaseOperation().queryByList(tablesSql, Tables.class, null);

            String columnsSql = "select * from columns where  table_schema = '" + this.metadataJDBCModel.getDatabaseName() + "'" + tableSql;
            List<Columns> columnsList = this.metadataJDBCModel.getMetabaseOperation().queryByList(columnsSql, Columns.class, null);

            String indexSql = "select * from statistics where table_schema = '" + this.metadataJDBCModel.getDatabaseName() + "'" + tableSql;
            List<Indexs> indexsList = this.metadataJDBCModel.getMetabaseOperation().queryByList(indexSql, Indexs.class, null);
            return new TableInfoBuild(tablesList, columnsList, indexsList).build();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String createTable(TableInfo tableInfo) {
        return MySqlStatementBuild.build(tableInfo);
    }


    @Override
    public String dropTable(TableInfo tableInfo) {
        return "drop table if exists " + tableInfo.getName();
    }


    @Override
    public String renameTable(TableInfo tableInfo) {
        return "rename table `" + tableInfo.getName() + "` to `" + tableInfo.getName() + "`";
    }

    @Override
    public String addColumn(ColumnInfo columnInfo) {
        return MySqlStatementBuild.build(columnInfo, OperationType.CREATE);
    }

    public String addColumn(List<ColumnInfo> columnInfoList) {
        TwoConsumer<ColumnInfo, StringBuilder> addColumnsConsumer = (value, s) -> {
            s.append("alter table  ")
                .append(value.getTableName())
                .append(" add column ")
                .append(value.getColumnName());

            if (!value.isNullable()) {
                s.append(" not null ");
            }
            if (Objects.nonNull(value.getDefaultValue())) {
                s.append(" default ").append(value.getDefaultValue());
                if (value.getOnUpdate() != null) {
                    s.append(" on update ").append(value.getOnUpdate());
                }
            }
            s.append(" comment ").append(value.getComment());
            if (Objects.nonNull(value.getNextColumn())) {
                s.append(" after ").append(value.getNextColumn().getColumnName());
            }
            s.append(";\n");
        };
        return null;

    }

    @Override
    public String updateColumn(ColumnInfo columnInfo) {
        return null;
    }


    @Override
    public String dropColumn(ColumnInfo columnInfo) {
        return "alter table " + columnInfo.getTableName() + " drop " + columnInfo.getName();
    }

    @Override
    public String renameColumn(ColumnInfo columnInfo) {
        return "";
    }

    @Override
    public String createIndex(KeyInfo keyInfo) {
        return null;
    }

    @Override
    public String dropIndex(KeyInfo keyInfo) {
        return "drop index `" + keyInfo.getKeyName() + "`";
    }


    @Override
    public String readFullContent() {
        String allCreateTableSql = "select concat('show create table ', table_name, ';') from information_schema.tables "
                                   + "where table_schema = '" + this.jdbcConfig.getDatabases() + "'";
        try {
            List<String> sql = this.metadataJDBCModel.getMetabaseOperation().queryByList(allCreateTableSql, String.class, null);
            StringBuilder result = new StringBuilder();
            sql.forEach(item -> {
                try {
                    Map<String, Object> createSql = this.metadataJDBCModel.getMetabaseOperation().query(item, null);
                    result.append((String) createSql.get("Create Table")).append(";\n");
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            });
            return result.toString();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void init() {
        this.metadataJDBCModel.setJdbcConfig(jdbcConfig);
        this.metadataJDBCModel.init();
    }

    @Override
    public void write(FullOperationData fullOperationData) {
        List<String> fullCreateTableSqlList = new ArrayList<>();
        fullCreateTableSqlList.add(fullOperationData.getRenameContent());
        fullCreateTableSqlList.add(fullOperationData.getCreateContent());
        fullCreateTableSqlList.add(fullOperationData.getDropContent());
        if (Objects.nonNull(fullOperationData.getKey())) {
            fullCreateTableSqlList.addAll(fullOperationData.getKey().getCreate());
            fullCreateTableSqlList.addAll(fullOperationData.getKey().getDelete());
        }
        if (Objects.nonNull(fullOperationData.getColumn())) {
            fullCreateTableSqlList.addAll(fullOperationData.getColumn().getCreate());
            fullCreateTableSqlList.addAll(fullOperationData.getColumn().getDelete());
            fullCreateTableSqlList.addAll(fullOperationData.getColumn().getUpdate());
            fullCreateTableSqlList.addAll(fullOperationData.getColumn().getRename());
        }
        this.write(fullCreateTableSqlList);
    }

    @Override
    public void write(List<String> content) {
        try {
            this.metadataJDBCModel.getDatabaseOperation().setAutoCommit(false);
            content.forEach((item) -> {
                if (Objects.isNull(item)) {
                    return;
                }
                try {
                    this.metadataJDBCModel.getDatabaseOperation().update(item, null);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            });
        } catch (SQLException e) {
            try {
                this.metadataJDBCModel.getDatabaseOperation().rollback();
            } catch (SQLException e1) {
                throw new RuntimeException(e1);
            }
            throw new RuntimeException(e);
        }
    }
}
