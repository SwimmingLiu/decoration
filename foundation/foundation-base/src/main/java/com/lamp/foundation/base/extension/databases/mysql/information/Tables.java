package com.lamp.foundation.base.extension.databases.mysql.information;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class Tables {

    /** 表的目录名，通常为 'def' */
    private String tableCatalog;

    /** 表所属的数据库名 */
    private String tableSchema;

    /** 表的名称 */
    private String tableName;

    /** 表的类型，如 'BASE TABLE' 或 'VIEW' */
    private String tableType;

    /** 表的存储引擎，如 InnoDB、MyISAM 等 */
    private String engine;

    /** 表的版本 */
    private Integer version;

    /** 表的行格式 */
    private String rowFormat;

    /** 表中的行数估计值 */
    private Long tableRows;

    /** 表中行的平均长度 */
    private Long avgRowLength;

    /** 表数据部分的长度（字节） */
    private Long dataLength;

    /** 表可以包含的最大数据量（字节） */
    private Long maxDataLength;

    /** 表索引部分的长度（字节） */
    private Long indexLength;

    /** 为表分配但未使用的空间量（字节） */
    private Long dataFree;

    /** 下一个 AUTO_INCREMENT 的值 */
    private Long autoIncrement;

    /** 表的创建时间 */
    private LocalDateTime createTime;

    /** 表的最后更新时间 */
    private LocalDateTime updateTime;

    /** 表的最后检查时间 */
    private LocalDateTime checkTime;

    /** 表的默认字符集和排序规则 */
    private String tableCollation;

    /** 表的校验和 */
    private Long checksum;

    /** 创建表时使用的其他选项 */
    private String createOptions;

    /** 表的注释 */
    private String tableComment;

}
