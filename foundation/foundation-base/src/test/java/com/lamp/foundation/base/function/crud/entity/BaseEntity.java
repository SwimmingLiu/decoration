package com.lamp.foundation.base.function.crud.entity;

import java.time.LocalDateTime;

import com.lamp.foundation.api.extension.databases.metadata.GeneralFunction;
import com.lamp.foundation.api.extension.persistence.Column;
import com.lamp.foundation.api.extension.persistence.Entity;
import com.lamp.foundation.api.extension.persistence.KeyLimit;
import com.lamp.foundation.api.extension.persistence.Once;
import com.lamp.foundation.api.extension.persistence.key.Unique;
import com.lamp.foundation.api.extension.sort.Sort;
import com.lamp.foundation.api.extension.sort.Sort.SortType;

import lombok.Data;

@Unique(name = "test_key", limit = {
    @KeyLimit("key"),
    @KeyLimit("key"),
})
@Data
@Entity(comment = "测试表", value = "test_key")
@Once(onecName = "TestKey", dbName = "test_key")
public class BaseEntity {

    @Sort(type = SortType.ROOT_FIRST)
    @Column(comment = "id", increment = true, primaryKey = true)
    private Long id;

    @Sort(type = SortType.DOWN)
    @Column(defaultValue = GeneralFunction.CURRENT_TIMESTAMP, comment = "创建时间")
    private LocalDateTime createTime;

    @Sort(type = SortType.DOWN)
    @Column(defaultValue = GeneralFunction.CURRENT_TIMESTAMP, onUpdate = GeneralFunction.CURRENT_TIMESTAMP, comment = "更新时间")
    private LocalDateTime updateTime;

    @Sort(type = SortType.ROOT_LAST)
    @Column(defaultValue = "1", comment = "逻辑删除,0 删除")
    private int deleted;
}
