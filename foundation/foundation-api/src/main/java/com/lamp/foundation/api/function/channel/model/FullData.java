package com.lamp.foundation.api.function.channel.model;

import java.util.Collection;
import java.util.List;

import lombok.Data;

/**
 * @author hahaha
 */
@Data
public class FullData<T> {

    @SuppressWarnings("unchecked")
    public static <T> FullData<T> ofByCollection(Collection<Object> allData, List<Object> addData, List<UpdateData<Object>> updateData,
        List<Object> deleteData) {
        return of((Collection<T>) allData, (List<T>) addData, (List<UpdateData<T>>) (T) updateData, (List<T>) deleteData);
    }

    public static <T> FullData<T> of(List<T> addData, List<UpdateData<T>> updateData, List<T> removeData) {
        return of(null, addData, updateData, removeData);
    }

    public static <T> FullData<T> of(Collection<T> allData, List<T> addData, List<UpdateData<T>> updateData, List<T> removeData) {
        FullData<T> fullData = new FullData<>();
        fullData.setAllData(allData);
        fullData.setAddList(addData);
        fullData.setUpdateList(updateData);
        fullData.setDeleteList(removeData);
        return fullData;
    }

    private Collection<T> allData;

    private List<T> addList;

    private List<UpdateData<T>> updateList;

    private List<T> deleteList;

}
