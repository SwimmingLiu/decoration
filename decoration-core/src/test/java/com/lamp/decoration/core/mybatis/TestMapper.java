package com.lamp.decoration.core.mybatis;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.lamp.foundation.api.extension.persistence.Table;

@Table("test")
public interface TestMapper {

    @SQL(" and delete = 1")
    String test = null;


    @Insert("(id)values(#{id})")
    void insert(TestEntity testEntity);

    @Select("where id = #{id}")
    void select(TestEntity testEntity);

    @Update("set id= 2 where id = 1")
    void update(TestEntity testEntity);
}
