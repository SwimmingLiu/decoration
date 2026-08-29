package com.lamp.decoration.core.mybatis;

import org.apache.ibatis.session.Configuration;

import org.junit.Before;

public class SupplementTest {


    DecorationMapperRegistry registry;

    @Before
    public void init() {
        Configuration config = new Configuration();
        registry = new DecorationMapperRegistry(config);
    }



}
