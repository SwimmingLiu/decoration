package com.lamp.foundation.base.function.crud.entity;

import com.lamp.foundation.api.extension.persistence.Column;
import com.lamp.foundation.api.extension.sort.Sort;
import com.lamp.foundation.api.extension.sort.Sort.SortType;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class SimpleChildRootAndParentNotRootEntity extends SimpleBaseEntity {

    @Sort(type = SortType.ROOT_FIRST)
    @Column(comment = "sid")
    private Long sId;


    @Column(comment = "test")
    private String test;


    @Sort(type = SortType.ROOT_LAST)
    private Long sParentId;

}
