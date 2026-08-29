package com.lamp.foundation.base.function.crud.entity;

import java.time.LocalDateTime;

import com.lamp.foundation.api.extension.persistence.Column;

import lombok.Data;

@Data
public class SimpleBaseEntity{

    @Column(comment = "id")
    private Long id;

    @Column(comment = "创建时间")
    private LocalDateTime createTime;

    @Column(comment = "修改时间")
    private  LocalDateTime updateTime;

}
