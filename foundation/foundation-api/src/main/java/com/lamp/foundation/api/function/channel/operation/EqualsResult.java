package com.lamp.foundation.api.function.channel.operation;

public enum EqualsResult {

    /**
     * 完全一样
     */
    CONSISTENT,

    /**
     * 只有主键一样
     */
    PRIMARY_KEY,

    /**
     * 主键被修改了
     */
    PRIMARY_KEY_CHANGE,

    /**
     * 主键被修改了，同时内容也不一样了
     */
    PRIMARY_KEY_CHANGE_AND_DIFFERENT,

    /**
     * 不一样，
     */
    DIFFERENT,

}
