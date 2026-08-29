package com.lamp.foundation.base.function.crud.entity.update.column;

import com.lamp.foundation.api.extension.persistence.Column;
import com.lamp.foundation.api.extension.persistence.Entity;
import com.lamp.foundation.api.extension.persistence.Once;
import com.lamp.foundation.api.extension.validation.constraints.base.Size;
import com.lamp.foundation.base.function.crud.entity.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity(comment = "测试表", value = "test_key")
@Once(onecName = "TestKey", dbName = "test_key")
@EqualsAndHashCode(callSuper = true)
public class AddColumnEntity extends BaseEntity {

    @Size(max = 100)
    @Column(comment = "用户名", unique = true)
    private String name;

    @Column(comment = "组织id")
    private Long orgId;

    @Size(max = 100)
    @Column(comment = "组织名")
    private String orgName;

    @Size(min = 0, max = 100)
    @Column(comment = "年龄", defaultValue = "0")
    private Integer age;

    @Column(comment = "地址")
    private Long address;

}
