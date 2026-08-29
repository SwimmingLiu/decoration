package com.lamp.foundation.base.extension.databases.mysql.information;

import lombok.Data;

@Data
public class Columns {

    /** 表的目录名，通常为 'def' */
    private String tableCatalog;

    /** 表所属的数据库名 */
    private String tableSchema;

    /** 表的名称 */
    private String tableName;

    /** 列的名称 */
    private String columnName;

    /** 列在表中的位置（从1开始计数） */
    private Integer ordinalPosition;

    /** 列的默认值 */
    private String columnDefault;

    /** 列是否可以为 NULL */
    private String isNullable;

    /** 列的数据类型 */
    private String dataType;

    /** 对于字符类型的数据，表示列的最大字符长度 */
    private Long characterMaximumLength;

    /** 对于字符类型的数据，表示列的最大字节数 */
    private Long characterOctetLength;

    /** 对于数值类型的数据，表示精度 */
    private Integer numericPrecision;

    /** 对于数值类型的数据，表示小数位数 */
    private Integer numericScale;

    /** 对于日期时间类型的数据，表示精度 */
    private Integer datetimePrecision;

    /** 列的字符集名称 */
    private String characterSetName;

    /** 列的排序规则名称 */
    private String collationName;

    /** 列的完整类型信息 */
    private String columnType;

    /** 列的关键属性 */
    private String columnKey;

    /** 额外的属性信息 */
    private String extra;

    /** 操作列的权限 */
    private String privileges;

    /** 列的注释信息 */
    private String columnComment;

    /** 对于生成列，该字段包含其表达式 */
    private String generationExpression;

    /** 空间参考系统ID */
    private Long srsId;


}
