package com.lamp.foundation.base.extension.jdbc;

import javax.sql.DataSource;

import com.alibaba.druid.pool.DruidDataSource;
import com.lamp.foundation.api.extension.jdbc.JDBCConfig;
import com.lamp.foundation.api.extension.jdbc.JDBCOperation;

import lombok.Getter;
import lombok.Setter;

public class MetadataJDBCModel {

    @Setter
    private JDBCConfig jdbcConfig;

    @Getter
    private DataSource databaseSource;

    @Getter
    private DataSource metabaseSource;

    @Getter
    private JDBCOperation databaseOperation;

    @Getter
    private JDBCOperation metabaseOperation;

    @Getter
    protected String databaseName;


    public void init() {
        this.databaseName = this.jdbcConfig.getDatabases();
        this.databaseSource = this.buildDataSource(this.jdbcConfig.getDatabases());
        this.databaseOperation = new JDBCOperationImpl(this.databaseSource);
        this.metabaseSource = this.buildDataSource("information_schema");
        this.metabaseOperation = new JDBCOperationImpl(metabaseSource);
    }

    private DruidDataSource buildDataSource(String databaseName) {
        DruidDataSource dataSource = new DruidDataSource();
        String string = "jdbc:%s://%s/%s?useUnicode=true&characterEncoding=utf-8";
        String url = String.format(string, this.jdbcConfig.getType(), this.jdbcConfig.getAddress(), databaseName);
        dataSource.setUrl(url);
        dataSource.setUsername(this.jdbcConfig.getUsername());
        dataSource.setPassword(this.jdbcConfig.getPassword());
        dataSource.setInitialSize(5);
        dataSource.setMinIdle(5);
        dataSource.setMaxActive(20);
        return dataSource;
    }

}
