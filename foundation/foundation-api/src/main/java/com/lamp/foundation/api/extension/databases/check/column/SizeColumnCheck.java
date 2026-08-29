package com.lamp.foundation.api.extension.databases.check.column;

import lombok.Data;

/**
 * @author hahaha
 */
@Data
public class SizeColumnCheck implements ColumnCheck {


    private Integer min;

    private Integer max;

}
