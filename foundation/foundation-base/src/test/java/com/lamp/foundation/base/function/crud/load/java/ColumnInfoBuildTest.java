package com.lamp.foundation.base.function.crud.load.java;

import java.util.List;

import org.junit.Test;

import com.lamp.foundation.api.extension.databases.metadata.ColumnInfo;
import com.lamp.foundation.base.function.crud.entity.SimpleChildRootAndParentNotRootEntity;
import com.lamp.foundation.base.function.crud.entity.SimpleExtendsEntity;
import com.lamp.foundation.base.function.crud.entity.UserEntity;

public class ColumnInfoBuildTest {

    private final ColumnInfoBuild columnInfoBuild = new ColumnInfoBuild();

    public void test_simple() {

    }

    @Test
    public void test_extends_simple() {
        List<ColumnInfo> list = this.handler(SimpleExtendsEntity.class);
        list.size();
    }


    @Test
    public void test_simple_child_root_and_parent_not_root_entity(){
        List<ColumnInfo> list = this.handler(SimpleChildRootAndParentNotRootEntity.class);
        list.size();
    }


    @Test
    public void test_eneity(){
        List<ColumnInfo> list = this.handler(UserEntity.class);
        list.size();
    }

    public List<ColumnInfo> handler(Class<?> clazz) {
        columnInfoBuild.setClazz(clazz);
        return columnInfoBuild.handler();
    }
}
