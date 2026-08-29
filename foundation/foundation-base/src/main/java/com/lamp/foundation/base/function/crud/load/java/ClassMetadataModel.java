package com.lamp.foundation.base.function.crud.load.java;

import org.apache.commons.lang3.ClassUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import com.lamp.foundation.api.extension.databases.metadata.ColumnInfo;
import com.lamp.foundation.api.extension.databases.metadata.KeyInfo;
import com.lamp.foundation.api.extension.databases.metadata.KeyInfo.KeyLimitWrapper;
import com.lamp.foundation.api.extension.databases.metadata.KeyType;
import com.lamp.foundation.api.extension.databases.metadata.TableInfo;
import com.lamp.foundation.api.extension.persistence.Entity;
import com.lamp.foundation.api.extension.persistence.KeyLimit;
import com.lamp.foundation.api.extension.persistence.Once;
import com.lamp.foundation.api.extension.persistence.key.Key;
import com.lamp.foundation.api.extension.persistence.key.Primary;
import com.lamp.foundation.api.extension.persistence.key.Unique;

import lombok.Setter;

@Setter
public class ClassMetadataModel extends AbstractJavaMetadataModel {

    private Class<?> clazz;


    @Override
    protected void buildTableInfo(TableInfo tableInfo) {
        Entity entity = clazz.getAnnotation(Entity.class);
        if (Objects.nonNull(entity) && Objects.nonNull(entity.value())) {
            tableInfo.setName(entity.value());
        } else {
            tableInfo.setName(clazz.getSimpleName());
        }

        Once once = clazz.getAnnotation(Once.class);
        if (Objects.nonNull(once)) {
            tableInfo.setOnecName(once.onecName());
            tableInfo.setDbName(once.dbName());
        }
        tableInfo.setKeys(new ArrayList<>());
    }

    @Override
    List<ColumnInfo> buildColumnInfo() {
        ColumnInfoBuild columnInfoBuild = new ColumnInfoBuild();
        columnInfoBuild.setClazz(clazz);
        return columnInfoBuild.handler();
    }

    @Override
    List<KeyInfo> buildKeyInfo() {
        List<KeyInfo> keyInfos = new ArrayList<>();
        this.buildKeyInfo(this.clazz, keyInfos);
        ClassUtils.getAllSuperclasses(clazz).forEach(superClass -> {
            this.buildKeyInfo(superClass, keyInfos);
        });
        return keyInfos;
    }

    private void buildKeyInfo(Class<?> clazz, List<KeyInfo> keyInfos) {
        if (Objects.equals(clazz, Object.class)) {
            return;
        }
        Unique[] uniques = clazz.getAnnotationsByType(Unique.class);
        for (Unique unique : uniques) {
            keyInfos.add(buildKeyInfo(KeyType.UNIQUE, unique.name(), unique.limit()));
        }
        Key[] keys = clazz.getAnnotationsByType(Key.class);
        for (Key key1 : keys) {
            keyInfos.add(buildKeyInfo(KeyType.KEY, key1.name(), key1.limit()));
        }
        Primary primary = clazz.getAnnotation(Primary.class);
        if (Objects.nonNull(primary)) {
            this.tableInfo().setPrimaryKey(buildKeyInfo(KeyType.PRIMARY, null, primary.limit()));
        }
    }

    private KeyInfo buildKeyInfo(KeyType type, String name, KeyLimit[] limits) {
        KeyInfo keyInfo = new KeyInfo();
        keyInfo.setType(type);
        keyInfo.setKeyName(name);
        List<KeyLimitWrapper> keyLimitWrappers = new ArrayList<>();
        keyInfo.setLimitKeys(keyLimitWrappers);

        for (KeyLimit keyLimit : limits) {
            KeyLimitWrapper keyLimitWrapper = new KeyLimitWrapper();
            keyLimitWrapper.setKey(keyLimit.value());
            keyLimitWrapper.setLimit(keyLimit.limit());
            keyLimitWrapper.setSort(keyLimit.sort());
            keyLimitWrappers.add(keyLimitWrapper);
        }
        return keyInfo;
    }
}
