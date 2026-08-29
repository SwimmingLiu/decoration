/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package com.lamp.decoration.servlet.example.provider.mapper;


import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import com.lamp.foundation.api.extension.persistence.Table;
import com.lamp.decoration.servlet.example.service.TestEntity;


/**
 * cluster table operation
 */
@Mapper
@Table("test")
public interface TestMapper {


    @Insert("""
        (id , name)values(#{id},#{name})
        """)
    int insert(TestEntity entity);


    @Insert("""
         test(id , name)values(#{id},#{name})
        """)
    int insertbyTable(TestEntity entity);

}
