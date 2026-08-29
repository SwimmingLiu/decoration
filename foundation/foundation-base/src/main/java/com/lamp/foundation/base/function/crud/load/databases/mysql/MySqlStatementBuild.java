package com.lamp.foundation.base.function.crud.load.databases.mysql;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.lamp.foundation.api.extension.databases.check.column.ColumnCheck;
import com.lamp.foundation.api.extension.databases.check.column.DigitsColumnCheck;
import com.lamp.foundation.api.extension.databases.check.column.RationalColumnCheck;
import com.lamp.foundation.api.extension.databases.check.column.SizeColumnCheck;
import com.lamp.foundation.api.extension.databases.metadata.ColumnInfo;
import com.lamp.foundation.api.extension.databases.metadata.DatabasesFunction;
import com.lamp.foundation.api.extension.databases.metadata.KeyInfo;
import com.lamp.foundation.api.extension.databases.metadata.KeyInfo.KeyLimitWrapper;
import com.lamp.foundation.api.extension.databases.metadata.KeyType;
import com.lamp.foundation.api.extension.databases.metadata.OperationType;
import com.lamp.foundation.api.extension.databases.metadata.Sort;
import com.lamp.foundation.api.extension.databases.metadata.TableInfo;
import com.lamp.foundation.api.extension.jdbc.type.ColumnCheckMapper;
import com.lamp.foundation.api.extension.jdbc.type.DataTypeMapper;
import com.lamp.foundation.api.extension.jdbc.type.MySQLTypeMapper;
import com.lamp.foundation.api.extension.jdbc.type.TypeMapper;
import com.lamp.foundation.base.lang.util.WrapperConverter;
import com.lamp.foundation.base.lang.util.foreach.ForeachSplice;

/**
 * @author hahaha
 */
public class MySqlStatementBuild {


    public static String build(ColumnInfo columnInfo, OperationType operationType) {
        ColumnStatementBuild columnStatementBuild = new ColumnStatementBuild();
        columnStatementBuild.build(columnInfo, operationType);
        return columnStatementBuild.stringBuilder.toString();
    }

    public static String build(TableInfo tableInfo) {
        TableStatmentBuild tableStatmentBuild = new TableStatmentBuild();
        tableStatmentBuild.build(tableInfo);
        return tableStatmentBuild.sql.toString();
    }

    static class ColumnBuildStatement {

        public void build(ColumnInfo column, StringBuilder stringBuilder) {
            DataTypeMapper dataTypeMapper = column.getMapper().getDataTypeMapper();
            String type = dataTypeMapper.getJdbcType().name();
            TypeMapper typeMapper = MySQLTypeMapper.valueOf(type);

            int typeLength = typeMapper.getType().length();

            stringBuilder.append(typeMapper.getType());
            Map<Class<?>, ColumnCheck> columnCheckMap = column.getColumnCheckMap();

            if (ColumnCheckMapper.getInstance().match(SizeColumnCheck.class, column.getMapper())) {
                SizeColumnCheck sizeColumnCheck = (SizeColumnCheck) columnCheckMap.get(SizeColumnCheck.class);
                if (Objects.isNull(sizeColumnCheck)) {
                    throw new RuntimeException("");
                }
                int tmp = stringBuilder.length();
                stringBuilder.append("(").append(sizeColumnCheck.getMax()).append(")");
                typeLength += stringBuilder.length() - tmp;
            }
            if (ColumnCheckMapper.getInstance().match(DigitsColumnCheck.class, column.getMapper())) {
                DigitsColumnCheck digitsColumnCheck = (DigitsColumnCheck) columnCheckMap.get(SizeColumnCheck.class);
                if (Objects.isNull(digitsColumnCheck)) {
                    throw new RuntimeException("");
                }
                int tmp = stringBuilder.length();
                stringBuilder.append("(").append(digitsColumnCheck.getInteger()).append(",").append(digitsColumnCheck.getFraction()).append(")");
                typeLength += stringBuilder.length() - tmp;
            }

            if (ColumnCheckMapper.getInstance().match(RationalColumnCheck.class, column.getMapper())) {
                if (!columnCheckMap.containsKey(RationalColumnCheck.class)) {
                    int tmp = stringBuilder.length();
                    stringBuilder.append(" unsigned ");
                    typeLength += stringBuilder.length() - tmp;
                }
            }
            if (column.isIncrement()) {
                int tmp = stringBuilder.length();
                stringBuilder.append(" auto_increment ");
                typeLength += stringBuilder.length() - tmp;
            }
            this.supplement(stringBuilder, 24, typeLength);
            if (!column.isNullable()) {
                stringBuilder.append(" not null ");
            } else {
                this.supplement(stringBuilder, 10, 0);
            }
            if (StringUtils.isNotBlank(column.getDefaultValue())) {
                String defaultValue = column.getDefaultValue().trim();
                stringBuilder.append(" default ");

                if (defaultValue.startsWith("__")) {
                    DatabasesFunction databasesFunction = MySQLGeneralFunction.valueOf(defaultValue.substring(2).toUpperCase());
                    stringBuilder.append(databasesFunction.functionName());
                } else if (defaultValue.startsWith("(") && defaultValue.endsWith(")")
                           || Objects.equals(defaultValue, "''")) {
                    stringBuilder.append(defaultValue);
                } else if (WrapperConverter.isNumberType(dataTypeMapper.getMainJavaType())) {
                    try {
                        WrapperConverter.convertStringToWrapperValue(defaultValue, column.getField().getType());
                    } catch (Exception e) {
                        String message = String.format("%s can't convert value %s , \n  %s", column.getBaseInfo(), defaultValue, e.getMessage());
                        throw new RuntimeException(message);
                    }
                    stringBuilder.append(defaultValue);

                } else if (Objects.equals(defaultValue, "")) {
                    stringBuilder.append("''");
                } else {
                    stringBuilder.append("'").append(column.getDefaultValue()).append("'");
                }
                if (StringUtils.isNotBlank(column.getOnUpdate())) {
                    stringBuilder.append(" on update current_timestamp ");
                }

            }
            stringBuilder.append(" comment '").append(column.getComment()).append("'");
        }

        protected void supplement(StringBuilder stringBuilder, int columnLength, String name) {
            this.supplement(stringBuilder, columnLength, name.length());
        }

        @SuppressWarnings("StringRepeatCanBeUsed")
        protected void supplement(StringBuilder stringBuilder, int length, int currentLength) {
            if (length < currentLength) {
                return;
            }
            int supplement = length - currentLength;
            for (int i = 0; i < supplement; i++) {
                stringBuilder.append(' ');
            }
        }
    }

    static class TableStatmentBuild extends ColumnBuildStatement {

        private TableInfo tableInfo;

        private final StringBuilder sql = new StringBuilder("create table ");

        private final String tab = "    ";

        private final String lineBreak = ",\n";

        private int columnNameLength = 0;

        public String build(TableInfo tableInfo) {
            this.tableInfo = tableInfo;
            sql.append(tableInfo.getName()).append(" (");
            this.columnBuild();
            this.keyBuild();
            sql.append("\n)\n");
            return sql.toString();
        }

        private void columnBuild() {
            List<ColumnInfo> columns = tableInfo.getColumns();
            for (ColumnInfo column : columns) {
                if (columnNameLength < column.getName().length()) {
                    columnNameLength = column.getName().length();
                }
            }
            columnNameLength += 4;
            this.sql.append('\n').append(this.tab);
            ForeachSplice.of(columns, this.lineBreak + this.tab, this.sql, this::columnBuild);
        }

        private void columnBuild(StringBuilder stringBuilder, ColumnInfo column) {
            stringBuilder.append(this.tab).append(column.getName());
            this.supplement(stringBuilder, columnNameLength, column.getName());
            this.build(column, stringBuilder);
        }

        private void keyBuild() {
            if (CollectionUtils.isEmpty(tableInfo.getKeys()) && Objects.isNull(tableInfo.getPrimaryKey())) {
                return;
            }
            this.sql.append(this.lineBreak).append(this.tab);
            if (Objects.nonNull(tableInfo.getPrimaryKey())) {
                this.keyBuild(this.sql, this.tableInfo.getPrimaryKey());
                if (CollectionUtils.isNotEmpty(tableInfo.getKeys())) {
                    this.sql.append(this.lineBreak).append(this.tab);

                }
            }
            ForeachSplice.of(tableInfo.getKeys(), this.lineBreak, this.sql, this::keyBuild);

        }

        private void keyBuild(StringBuilder stringBuilder, KeyInfo keyInfo) {
            stringBuilder.append(this.tab);
            if (Objects.equals(keyInfo.getType(), KeyType.PRIMARY)) {
                stringBuilder.append("primary key");
                stringBuilder.append("(");
                ForeachSplice.of(keyInfo.getLimitKeys(), ", ", stringBuilder, this::keyBuild);
                stringBuilder.append(")");
            } else {
                if (Objects.equals(keyInfo.getType(), KeyType.UNIQUE)) {
                    stringBuilder.append("unique key");
                } else if (Objects.equals(keyInfo.getType(), KeyType.KEY)) {
                    stringBuilder.append("key");
                }
                stringBuilder.append(" `");
                if (Objects.nonNull(keyInfo.getKeyName())) {
                    stringBuilder.append(keyInfo.getKeyName());
                } else {
                    ForeachSplice.of(keyInfo.getLimitKeys(), "_", stringBuilder, (s, t) -> stringBuilder.append(t.getKey()));
                }
                stringBuilder.append("` (");
                ForeachSplice.of(keyInfo.getLimitKeys(), ", ", stringBuilder, this::keyBuild);
                stringBuilder.append(")");
            }

        }

        private void keyBuild(StringBuilder stringBuilder, KeyLimitWrapper keyLimitWrapper) {
            stringBuilder.append(keyLimitWrapper.getKey());
            if (Objects.nonNull(keyLimitWrapper.getLimit()) && keyLimitWrapper.getLimit() > 0) {
                stringBuilder.append("(").append(keyLimitWrapper.getLimit()).append(")");
            }
            if (Objects.equals(keyLimitWrapper.getSort(), Sort.DESC)) {
                stringBuilder.append(" desc");
            }
        }


    }


    static class ColumnStatementBuild extends ColumnBuildStatement {

        public final StringBuilder stringBuilder = new StringBuilder(" alter table ");

        public ColumnInfo columnInfo;

        public void build(ColumnInfo columnInfo, OperationType operationType) {
            this.columnInfo = columnInfo;
            stringBuilder.append(this.columnInfo.getTableName());
            if (Objects.equals(operationType, OperationType.CREATE)) {
                stringBuilder.append(" add ");
            } else if (Objects.equals(operationType, OperationType.RENAME)) {
                stringBuilder.append(" change ");
                stringBuilder.append(this.columnInfo.getName()).append(" ").append(columnInfo.getName()).append(" ");
            } else if (Objects.equals(operationType, OperationType.UPDATE)) {
                stringBuilder.append(" modify ").append(columnInfo.getName()).append(" ");
            }
            this.build(columnInfo, stringBuilder);
        }
    }

}
