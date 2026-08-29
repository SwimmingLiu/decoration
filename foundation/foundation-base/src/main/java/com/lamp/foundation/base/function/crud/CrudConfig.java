package com.lamp.foundation.base.function.crud;


import lombok.Data;

@Data
public class CrudConfig {

    /**
     * model to class
     */
    private String defaultColumnClass;

    private String idClass;

    private String dbType;

    private String dbAddress;

    private String databases;

    private String metadataDatabase;

    private String username;

    private String password;

    private String parameter;

}
