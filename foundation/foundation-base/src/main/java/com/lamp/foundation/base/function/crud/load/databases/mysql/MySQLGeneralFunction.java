package com.lamp.foundation.base.function.crud.load.databases.mysql;

import com.lamp.foundation.api.extension.databases.metadata.DatabasesFunction;
import com.lamp.foundation.api.extension.databases.metadata.GeneralFunction;

/**
 * @author hahaha
 */

public enum MySQLGeneralFunction implements DatabasesFunction {


    CURRENT_TIMESTAMP(GeneralFunction.CURRENT_TIMESTAMP, "current_timestamp"),

    ;

    private final String generalFunction;

    private final String name;

    MySQLGeneralFunction(String generalFunction, String name) {
        this.generalFunction = generalFunction;
        this.name = name;
    }


    @Override
    public String functionName() {
        return name;
    }

    public String generalFunction() {
        return generalFunction;
    }
}
