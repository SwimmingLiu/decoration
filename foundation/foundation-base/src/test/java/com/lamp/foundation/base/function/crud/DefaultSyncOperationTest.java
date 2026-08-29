package com.lamp.foundation.base.function.crud;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.lamp.foundation.api.extension.jdbc.JDBCConfig;
import com.lamp.foundation.api.function.crud.operation.MetadataOperation;
import com.lamp.foundation.api.lang.util.BitsetOperation;
import com.lamp.foundation.api.lang.util.BitsetOperation.Bitset;
import com.lamp.foundation.base.function.crud.entity.update.RootEntity;
import com.lamp.foundation.base.function.crud.entity.update.column.AddColumnEntity;
import com.lamp.foundation.base.function.crud.entity.update.column.DeleteColumnEntity;
import com.lamp.foundation.base.function.crud.entity.update.column.UpdateColumnEntity;
import com.lamp.foundation.base.function.crud.load.databases.mysql.MySQLMetadataLoad;
import com.lamp.foundation.base.function.crud.load.java.ClassMetadataOperation;

public class DefaultSyncOperationTest {

    private final DefaultSyncOperation defaultSyncOperation = new DefaultSyncOperation();

    private final JDBCConfig jdbcConfig = new JDBCConfig();

    private final BitsetOperation bitsetOperation = BitsetOperation.of();

    private final Bitset cache = bitsetOperation.increment();

    private final Bitset mysql = bitsetOperation.increment();

    private final Bitset all = bitsetOperation.create(cache, mysql);

    private final Bitset current = cache;

    private ClassMetadataOperation readOperation;

    private List<Class<?>> classList = new ArrayList<>();

    private final MySQLMetadataLoad mySQLMetadataLoad = new MySQLMetadataLoad();

    @Before
    public void init() {
        this.jdbcConfig.setType("mysql");
        this.jdbcConfig.setAddress("127.0.0.1:3306");
        this.jdbcConfig.setDatabases("lantern");
        this.jdbcConfig.setUsername("root");
        this.jdbcConfig.setPassword("Ab123123@");
        readOperation = new ClassMetadataOperation();
        readOperation.setClasses(classList);

        mySQLMetadataLoad.setJdbcConfig(this.jdbcConfig);
        mySQLMetadataLoad.init();

    }

    @Test
    public void test_java_to_db_init() {
        this.test(RootEntity.class);
    }

    @Test
    public void test_java_to_db_add() {
        this.test(AddColumnEntity.class);
    }

    @Test
    public void test_java_to_db_update() {
        this.test(UpdateColumnEntity.class);
    }

    @Test
    public void test_java_to_db_delete() {
        this.test(DeleteColumnEntity.class);
    }


    @Test
    public void test_checkByPsiClass_db_init() {
        this.test(RootEntity.class);
    }

    private void test_psi_class(Class<?> clazz) {
        classList.add(clazz);
        defaultSyncOperation.setFormMetadata(readOperation);
        defaultSyncOperation.setDatabaseOperation(mySQLMetadataLoad);
        defaultSyncOperation.checkByPsiClass(readOperation, true, false);
    }


    private void test(Class<?> clazz) {
        classList.add(clazz);
        MetadataOperation metadataOperation = null;
        if (this.current.match(this.cache)) {
            ClassMetadataOperation toClassMeta = new ClassMetadataOperation();
            toClassMeta.init();
            classList = new ArrayList<>();
            classList.add(RootEntity.class);
            toClassMeta.setClasses(classList);

        }
        if (this.current.match(this.mysql)) {
            metadataOperation = mySQLMetadataLoad;
        }
        defaultSyncOperation.setFormMetadata(readOperation);
        defaultSyncOperation.setDatabaseOperation(metadataOperation);
        defaultSyncOperation.syncAll(false, null);
    }

}
