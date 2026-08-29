package com.lamp.foundation.api.function.channel.model;

import com.lamp.foundation.api.function.channel.operation.EqualsResult;

import lombok.Data;

@Data
public class UpdateData<T> {

    private EqualsResult equalsResult;

    private T old;

    private T update;

}