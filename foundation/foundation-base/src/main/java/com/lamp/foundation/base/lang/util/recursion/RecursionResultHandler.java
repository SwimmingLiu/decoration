package com.lamp.foundation.base.lang.util.recursion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.lamp.foundation.api.lang.util.BitsetOperation;
import com.lamp.foundation.api.lang.util.BitsetOperation.Bitset;
import com.lamp.foundation.base.lang.util.collections.Tuple.Pair;

import lombok.Getter;
import lombok.ToString;


/**
 * @author hahaha
 */
@Getter
@ToString
public class RecursionResultHandler<T> {


    private List<T> result;

    private List<Pair<T, List<T>>> relationshipList;

    private Map<T, List<T>> relationshipMap;

    public RecursionResultHandler(Bitset bitset) {
        if (DataInfoMark.RESULT.getBitset().match(bitset)) {
            this.result = new ArrayList<>();
        }
        if (DataInfoMark.RELATIONSHIP.getBitset().match(bitset)) {
            this.relationshipList = new ArrayList<>();
        }
        if (DataInfoMark.RELATIONSHIP_MAP.getBitset().match(bitset)) {
            this.relationshipMap = new HashMap<>();
        }
    }

    public void add(T t) {
        if (Objects.nonNull(result)) {
            this.result.add(t);
        }
    }

    public void relationship(T t, List<T> children) {
        if (Objects.nonNull(this.relationshipList)) {
            Pair<T, List<T>> pair = Pair.of(t, children);
            this.relationshipList.add(pair);
        }
        if (Objects.nonNull(this.relationshipMap)) {
            this.relationshipMap.put(t, children);
        }
        this.expand(t, children);
    }

    public void expand(T t, List<T> children) {

    }

    public enum DataInfoMark {

        RESULT,

        RELATIONSHIP,

        RELATIONSHIP_MAP,

        ;

        private final static BitsetOperation BITSET_OPERATION = new BitsetOperation();


        private Bitset bitset;


        public Bitset getBitset() {
            if (bitset == null) {
                bitset = BITSET_OPERATION.create(this.ordinal());
            }
            return bitset;
        }
    }
}
