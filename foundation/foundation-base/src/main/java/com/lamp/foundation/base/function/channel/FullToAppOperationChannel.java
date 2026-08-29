package com.lamp.foundation.base.function.channel;

import org.apache.commons.collections.CollectionUtils;

import java.util.Objects;

import com.lamp.foundation.api.function.channel.model.FullData;
import com.lamp.foundation.api.function.channel.model.UpdateData;
import com.lamp.foundation.api.function.channel.operation.EqualsResult;
import com.lamp.foundation.base.function.channel.operation.FullUnionChannel;
import com.lamp.foundation.base.lang.util.collections.Tuple.Triplet;

import lombok.Getter;
import lombok.Setter;

public class FullToAppOperationChannel<T> {

    @Getter
    protected final FullData<T> fullData = new FullData<>();

    @Setter
    private FullUnionChannel<T> fullUnionChannel;

    @SuppressWarnings("unchecked")
    protected void unionData(T oldData, T newData, EqualsResult equalsResult) {
        if (Objects.isNull(this.fullData.getAllData())) {
            return;
        }
        if (!Objects.equals(EqualsResult.CONSISTENT, equalsResult)) {
            return;
        }
        this.fullData.getAllData().add((T) Triplet.of(newData, oldData, equalsResult));
    }


    protected void addData(T data) {
        if (Objects.isNull(this.fullData.getAddList())) {
            return;
        }
        this.fullData.getAddList().add(data);
    }


    protected void updateData(T old, T update, EqualsResult equalsResult) {
        if (Objects.isNull(this.fullData.getUpdateList()) || equalsResult.equals(EqualsResult.CONSISTENT)) {
            return;
        }
        UpdateData<T> updateData = new UpdateData<>();
        updateData.setOld(old);
        updateData.setUpdate(update);
        updateData.setEqualsResult(equalsResult);
        this.fullData.getUpdateList().add(updateData);
    }

    protected void removeData(T data) {
        if (Objects.isNull(this.fullData.getDeleteList())) {
            return;
        }
        this.fullData.getDeleteList().add(data);
    }

    protected boolean isRemove() {
        return Objects.nonNull(this.fullData.getDeleteList());
    }

    public void execute() {
        if (CollectionUtils.isNotEmpty(fullData.getAllData())) {
            fullUnionChannel.insert(fullData.getAddList());
        }
        if (CollectionUtils.isNotEmpty(fullData.getUpdateList())) {
            fullUnionChannel.update(fullData.getUpdateList());
        }
        if (CollectionUtils.isNotEmpty(fullData.getDeleteList())) {
            fullUnionChannel.delete(fullData.getDeleteList());
        }
        if (CollectionUtils.isNotEmpty(fullData.getAddList())) {
            fullUnionChannel.data(fullData.getAddList());
        }
    }

}
