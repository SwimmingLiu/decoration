package com.lamp.foundation.api.extension.databases.check.column;

import lombok.Data;

/**
 * @author hahaha
 */
@Data
public class DigitsColumnCheck implements ColumnCheck {

    private Integer integer;

    private Integer fraction;

}
