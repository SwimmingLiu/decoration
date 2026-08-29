package com.lamp.foundation.base.extension.databases.mysql.information;

import lombok.Data;

/**
 *  statistics 表
 */
@Data
public class Indexs {

    /**
     * 包含索引的表所属的目录名称
     */
    private String tableCatalog;

    /**
     * 包含索引的表所属的数据库名称
     */
    private String tableSchema;

    /**
     * 包含索引的表的名称
     */
    private String tableName;

    /**
     * 索引是否允许重复值，0表示不允许，1表示允许
     */
    private Integer nonUnique;

    /**
     * 索引所属的数据库名称
     */
    private String indexSchema;

    /**
     * 索引的名称
     */
    private String indexName;

    /**
     * 索引中列的序列号
     */
    private Integer seqInIndex;

    /**
     * 索引中列的名称
     */
    private String columnName;

    /**
     * 列在索引中的排序方式
     */
    private String collation;

    /**
     * 索引中唯一值的估计数量
     */
    private Long cardinality;

    /**
     * 索引前缀的长度
     */
    private Integer subPart;

    /**
     * 指示键的打包方式
     */
    private String packed;

    /**
     * 列是否可能包含NULL值
     */
    private String nullable;

    /**
     * 索引的类型
     */
    private String indexType;

    /**
     * 有关索引的注释信息
     */
    private String comment;

    /**
     * 索引的注释
     */
    private String indexComment;

    /**
     * 索引是否可见
     */
    private String isVisible;

    /**
     * 表达式索引的表达式内容
     */
    private String expression;

}
