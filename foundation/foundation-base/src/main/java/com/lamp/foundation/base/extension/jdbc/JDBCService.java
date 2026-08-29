package com.lamp.foundation.base.extension.jdbc;

import com.lamp.foundation.base.extension.jdbc.result.ResultMappingService;
import com.lamp.foundation.base.extension.jdbc.type.TypeHandlerService;

import lombok.Data;

@Data
public class JDBCService {

    private TypeHandlerService typeHandlerService = new TypeHandlerService();

    private ResultMappingService resultMappingService = new ResultMappingService();


    {
        resultMappingService.setTypeHandlerService(typeHandlerService);
    }

}
