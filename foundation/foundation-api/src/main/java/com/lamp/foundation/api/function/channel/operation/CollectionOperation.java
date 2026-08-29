package com.lamp.foundation.api.function.channel.operation;

public interface CollectionOperation<T> {

    /**
     * 并集
     */
    int UNION = 1;

    /**
     * 交集‌
     */
    int INTERSECTION = 1 << 1;


    /**
     * A 差集, 即 需要添加的数据
     */
    int DIFFERENCE = 1 << 2;

    /**
     * A 差集, 即 需要 修改的的数据
     */
    int DIFFERENCE_UPDATE = 1 << 3;

    /**
     * B 差集 ， 即 需要删除的数据
     */
    int DIFFERENCE_REMOVE = 1 << 4;

    int UPDATE = DIFFERENCE | DIFFERENCE_UPDATE | DIFFERENCE_REMOVE;

    void operation();

}
