package com.lamp.foundation.base.function.crud.entity;

import com.lamp.foundation.api.extension.persistence.Column;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class SimpleExtendsEntity extends SimpleBaseEntity {

    @Column(comment = "名字")
    private String name;

}
