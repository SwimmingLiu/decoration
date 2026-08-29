package com.lamp.foundation.base.lang.util.recursion;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Objects;
import java.util.Stack;

import com.lamp.foundation.api.lang.util.BitsetOperation.Bitset;
import com.lamp.foundation.api.lang.util.Recursion;
import com.lamp.foundation.api.lang.util.Recursion.Match;
import com.lamp.foundation.base.lang.util.collections.Tuple.Pair;
import com.lamp.foundation.base.lang.util.recursion.RecursionResultHandler.DataInfoMark;

public class SimpleRecursion<T> {


    public static <T> List<T> of(List<T> root, Recursion<T> recursion) {
        return of(root, recursion, DataInfoMark.RESULT.getBitset()).execute().getResult();
    }

    public static <T> SimpleRecursion<T> of(List<T> root, Recursion<T> recursion, Bitset bitset) {
        return new SimpleRecursion<>(root, recursion, bitset);
    }

    private final Recursion<T> recursion;

    private final Stack<T> stack = new Stack<>();

    private final RecursionResultHandler<T> recursionResultHandler;

    public SimpleRecursion(List<T> root, Recursion<T> recursion, Bitset bitset) {
        this.stack.addAll(root);
        this.recursion = recursion;
        this.recursionResultHandler = new RecursionResultHandler<>(bitset);
    }


    public RecursionResultHandler<T> execute() {
        for (; ; ) {
            if (stack.isEmpty()) {
                break;
            }
            T t = stack.pop();

            Match match = this.recursion.match(t);
            if (Objects.equals(Match.END, match)) {
                break;
            }

            if (Objects.equals(Match.NOT_THOROUGHLY_AND_ABANDON, match)) {
                continue;
            }
            if (Objects.equals(Match.NOT_THOROUGHLY_ADD, match)) {
                this.recursionResultHandler.add(t);
                continue;
            }
            if (Objects.equals(Match.THOROUGHLY_ADD, match)) {
                this.recursionResultHandler.add(t);
            }
            List<T> list = this.recursion.child(t);
            if (Objects.isNull(list) || list.isEmpty()) {
                continue;
            }
            this.recursionResultHandler.relationship(t, list);
            stack.addAll(list);

        }
        return this.recursionResultHandler;
    }

    static class FieldRecursion implements Recursion<Pair<Object, Field>> {

        @Override
        public Match match(Pair<Object, Field> objectFieldPair) {
            Field field = objectFieldPair.getPair();
            if (Objects.isNull(field)) {
                Object object = objectFieldPair.getUnit();
                if (Objects.isNull(object)) {
                    return Match.END;
                }
            }
            return null;
        }

        @Override
        public List<Pair<Object, Field>> child(Pair<Object, Field> objectFieldPair) {
            Field field = objectFieldPair.getPair();
            Class<?> type = field.getType();
            return List.of();
        }
    }

}
