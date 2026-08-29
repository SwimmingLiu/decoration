package com.lamp.foundation.base.extension.databases;

import java.util.HashMap;
import java.util.Map;

import com.lamp.foundation.api.extension.databases.manufacturer.DatabaseManufacturer;

/**
 * @author hahaha
 */
public class DatabaseManufacturerManage {


    private final Map<String, DatabaseManufacturer> manufacturers = new HashMap<>();


    public void registerManufacturer(DatabaseManufacturer manufacturer) {
        manufacturers.put(manufacturer.name(), manufacturer);
    }



}
