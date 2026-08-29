package com.lamp.foundation.base.function.crud.entity;

import com.lamp.foundation.api.extension.persistence.Column;

public class UserEntity extends BaseEntity {

    @Column(comment = "名字")
    private String name;

    @Column(comment = "密码")
    private String password;

    @Column(comment = "手机号")
    private String phone;

    @Column(comment = "邮箱")
    private String email;

    @Column(comment = "地址")
    private String address;

    @Column(comment = "性别")
    private int sex;

}
