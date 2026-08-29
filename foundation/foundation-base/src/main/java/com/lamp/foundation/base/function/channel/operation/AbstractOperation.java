package com.lamp.foundation.base.function.channel.operation;

import java.util.ArrayList;
import java.util.List;

import com.lamp.foundation.api.function.channel.operation.CollectionOperation;
import com.lamp.foundation.api.function.channel.operation.Equals;
import com.lamp.foundation.base.function.channel.FullToAppOperationChannel;

/**
 * @author hahaha
 */
public abstract class AbstractOperation<T> extends FullToAppOperationChannel<T> implements CollectionOperation<T> {

    protected List<T> old;

    protected List<T> fresh;

    protected Equals<T> equals;

    private int operation = CollectionOperation.UPDATE;

    abstract void doOperation();

    @Override
    public void operation() {
        this.init();
        this.doOperation();
    }


    public AbstractOperation<T> operation(int operation) {
        this.operation = operation;
        return this;
    }

    public AbstractOperation<T> equal(Equals<T> equals) {
        this.equals = equals;
        return this;
    }


    public AbstractOperation<T> old(T source) {
        this.old = new ArrayList<>();
        this.old.add(source);
        return this;
    }


    public AbstractOperation<T> old(List<T> source) {
        this.old = source;
        return this;
    }

    public AbstractOperation<T> fresh(T source) {
        this.fresh = new ArrayList<>();
        this.fresh.add(source);
        return this;
    }

    public AbstractOperation<T> fresh(List<T> target) {
        this.fresh = target;
        return this;
    }

    public AbstractOperation<T> channel(FullUnionChannel<T> fullUnionChannel) {
        this.setFullUnionChannel(fullUnionChannel);
        return this;
    }


    protected void init() {
        assert equals != null;
        assert old != null;
        assert fresh != null;

        if ((operation & CollectionOperation.UNION) != 0) {
            this.fullData.setAllData(new ArrayList<>());
        }
        if ((operation & CollectionOperation.DIFFERENCE) != 0) {
            this.fullData.setAddList(new ArrayList<>());
        }
        if ((operation & CollectionOperation.DIFFERENCE_UPDATE) != 0) {
            this.fullData.setUpdateList(new ArrayList<>());
        }
        if ((operation & CollectionOperation.DIFFERENCE_REMOVE) != 0) {
            this.fullData.setDeleteList(new ArrayList<>());
        }
    }

}
