package com.lamp.foundation.api.extension.sort;

import com.lamp.foundation.api.extension.sort.Sort.SortType;

import lombok.Data;

@Data
public class SortWrapper {

    private SortType type;

    private String field;
}
