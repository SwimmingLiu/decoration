package com.lamp.foundation.api.function.channel.model;

import java.util.Collection;

import lombok.Data;

@Data
@SuppressWarnings("unchecked")
public class RegisterRequest<T> {

    private Collection<T> all;

    private Collection<T> insert;

    private Collection<T> update;

    private Collection<T> delete;


    public void setAllObject(Collection<Object> all) {
        this.all = (Collection<T>)all;
    }

    public void setInsertObject(Collection<Object> insert) {
        this.insert = (Collection<T>)insert;
    }

    public void setUpdateObject(Collection<Object> update) {
        this.update = (Collection<T>)update;
    }

    public void setDeleteObject(Collection<Object> delete) {
        this.delete = (Collection<T>)delete;
    }


}
