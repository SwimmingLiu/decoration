package com.lamp.foundation.api.extension.jdbc;

import lombok.Data;

/**
 * @author hahaha
 */
@Data
public class JDBCConfig {


    private String type;

    private String address;

    private String databases;

    private String metadataDatabase;

    private String username;

    private String password;

    private String parameter;

}
