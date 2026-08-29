package com.lamp.foundation.base.function.crud.load;

import com.lamp.foundation.api.extension.jdbc.JDBCConfig;
import com.lamp.foundation.api.function.crud.operation.MetadataOperation;
import com.lamp.foundation.base.function.crud.CrudConfig;

import lombok.Setter;

public abstract class AbstractMetadataOperation implements MetadataOperation {


    @Setter
    protected JDBCConfig jdbcConfig;

}
