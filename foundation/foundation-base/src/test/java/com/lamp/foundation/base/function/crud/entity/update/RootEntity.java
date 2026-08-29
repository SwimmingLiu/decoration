package com.lamp.foundation.base.function.crud.entity.update;

import com.lamp.foundation.api.extension.persistence.Column;
import com.lamp.foundation.api.extension.persistence.Entity;
import com.lamp.foundation.api.extension.persistence.Once;
import com.lamp.foundation.base.function.crud.entity.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Once(onecName = "TestKey", dbName = "test_key")
@Entity(comment = "测试", value = "test_key")
public class RootEntity extends BaseEntity {

    @Column(comment = "用户名", unique = true)
    private String name;

    @Column(comment = "组织id")
    private Long orgId;

    @Column(comment = "组织名")
    private String orgName;

    @Column(comment = "年龄")
    private Integer age;

}
