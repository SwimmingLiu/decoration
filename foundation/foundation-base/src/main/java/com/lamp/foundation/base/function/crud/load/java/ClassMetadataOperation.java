package com.lamp.foundation.base.function.crud.load.java;

import java.util.ArrayList;
import java.util.List;

import com.lamp.foundation.api.extension.databases.metadata.TableInfo;
import com.lamp.foundation.api.function.crud.operation.ReadOperation;

import lombok.Setter;

/**
 * @author hahaha
 */
public class ClassMetadataOperation extends JavaWriteOperation implements ReadOperation {

    private final ClassMetadataModel classMetadataModel = new ClassMetadataModel();

    @Setter
    private String packageName;

    @Setter
    private List<Class<?>> classes = new ArrayList<>();


    @Override
    public List<TableInfo> readMetadataByDatabase() {
        List<TableInfo> tableInfoList = new ArrayList<>();
        this.classes.forEach(clazz -> {
            this.classMetadataModel.setClazz(clazz);
            tableInfoList.add(this.classMetadataModel.getTableInfo());
        });
        return tableInfoList;
    }

    @Override
    public TableInfo readMetadataByTable(String tableName) {
        return null;
    }

    @Override
    public String readFullContent() {
        return "";
    }


}
