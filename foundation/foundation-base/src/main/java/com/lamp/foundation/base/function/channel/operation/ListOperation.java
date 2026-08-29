package com.lamp.foundation.base.function.channel.operation;


import java.util.Objects;

import com.lamp.foundation.api.function.channel.operation.EqualsResult;

public class ListOperation<T> extends AbstractOperation<T> {


    @Override
    public void doOperation() {
        boolean[] marked = new boolean[old.size()];

        // 1 2 3 4  2 3 4 5
        for (int i = 0; i < fresh.size(); i++) {
            T targetData = fresh.get(i);
            // 没有匹配到，表示当前数据在 old 数据里面不存在
            boolean found = false;
            for (int j = 0; j < old.size(); j++) {
                T sourceData = old.get(j);
                EqualsResult equalsResult = equals.operation(sourceData, targetData);
                if (!Objects.equals(equalsResult, EqualsResult.DIFFERENT)) {
                    found = true;
                    marked[j] = true;
                    this.unionData(sourceData, targetData, equalsResult);
                    this.updateData(sourceData, targetData, equalsResult);
                    break;
                }
            }
            if (!found) {
                this.addData(targetData);
            }
        }
        if (!this.isRemove()) {
            return;
        }
        for (int i = 0; i < old.size(); i++) {
            if (!marked[i]) {
                this.removeData(old.get(i));
            }
        }
    }

}
