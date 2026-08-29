package com.lamp.foundation.api.extension.databases.manufacturer;

import com.lamp.foundation.api.function.crud.operation.MetadataOperation;

/**
 * @author hahaha
 */
public interface DatabaseManufacturer {

    String name();

    Object typeMapper();

    Object generalFunction();

    MetadataOperation operation();
}
