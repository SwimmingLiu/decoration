package com.lamp.foundation.api.extension.databases.metadata;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

@Getter
public class OperationData<T> {

    private final List<T> create = new ArrayList<>();

    private final List<T> update = new ArrayList<>();

    private final List<T> delete = new ArrayList<>();

    private final List<T> rename = new ArrayList<>();

}
