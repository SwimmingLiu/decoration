package com.lamp.foundation.base.lang.util.foreach;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

/**
 * @author hahaha
 */
public class ForeachSplice<T> {

    public static <T> ForeachSplice<T> of() {
        return new ForeachSplice<>();
    }

    public static <T> ForeachSplice<T> of(List<T> data, String tokenizer, Function<? super T, Object> valuesMapper) {
        return of(data, tokenizer, null, valuesMapper);
    }

    public static <T> ForeachSplice<T> of(List<T> data, String tokenizer, StringBuilder stringBuilder, Function<? super T, Object> valuesMapper) {
        return of(data, tokenizer, stringBuilder, toForeachTokenizer(valuesMapper));
    }

    public static <T> ForeachSplice<T> of(List<T> data, String tokenizer, ForeachTokenizer<T> foreachTokenizer) {
        return of(data, tokenizer, null, foreachTokenizer);
    }

    public static <T> ForeachSplice<T> of(List<T> data, String tokenizer, StringBuilder stringBuilder, ForeachTokenizer<T> foreachTokenizer) {
        ForeachSplice<T> foreachSplice = new ForeachSplice<>();
        foreachSplice.data(data).stringBuilder(stringBuilder).tokenizer(tokenizer).foreachTokenizer(foreachTokenizer);
        foreachSplice.execute();
        return foreachSplice;
    }

    private static <T> ForeachTokenizer<T> toForeachTokenizer(Function<? super T, Object> valuesMapper) {
        return (s, v) -> {
            Object object = valuesMapper.apply(v);
            if (Objects.isNull(object)) {
                return;
            }
            if (object instanceof String) {
                s.append((String) object);
            } else {
                s.append(object);
            }
        };
    }

    private List<T> data;

    private String tokenizer;

    private List<Pair<String, Integer>> tokenizerList;

    private StringBuilder stringBuilder;

    private ForeachTokenizer<T> foreachTokenizer;

    private String header;

    private String footer;

    private void check() {
        if (Objects.isNull(this.data)) {
            throw new RuntimeException("There is no iterable data");
        }
        if (Objects.isNull(this.tokenizer) && CollectionUtils.isEmpty(this.tokenizerList)) {
            throw new RuntimeException("There is no split data");
        }
        if (Objects.isNull(this.foreachTokenizer)) {
            throw new RuntimeException("There is no foreach tokenizer");
        }
    }

    public StringBuilder execute() {
        this.check();
        if (Objects.isNull(this.stringBuilder)) {
            this.stringBuilder = new StringBuilder();
        }
        if (Objects.nonNull(this.header)) {
            this.stringBuilder.append(this.header);
        }
        for (int index = 0; index < data.size(); index++) {
            this.foreachTokenizer.foreach(this.stringBuilder, data.get(index));
            if (index != this.data.size() - 1) {
                this.stringBuilder.append(this.tokenizer);
            }
        }
        if (Objects.nonNull(this.footer)) {
            this.stringBuilder.append(this.footer);
        }
        return this.stringBuilder;
    }


    public ForeachSplice<T> data(List<T> data) {
        this.data = data;
        return this;
    }

    public ForeachSplice<T> tokenizer(String tokenizer) {
        this.tokenizer = tokenizer;
        return this;
    }

    public ForeachSplice<T> tokenizer(String split, int count) {
        if (Objects.isNull(this.tokenizerList)) {
            this.tokenizerList = new ArrayList<>();
        }
        Pair<String, Integer> pair = Pair.of(split, count);
        this.tokenizerList.add(pair);
        return this;
    }

    public ForeachSplice<T> header(String header) {
        this.header = header;
        return this;
    }

    public ForeachSplice<T> footer(String footer) {
        this.footer = footer;
        return this;
    }

    @SuppressWarnings("UnusedReturnValue")
    public ForeachSplice<T> foreachTokenizer(ForeachTokenizer<T> foreachTokenizer) {
        this.foreachTokenizer = foreachTokenizer;
        return this;
    }

    public ForeachSplice<T> valueMapper(Function<? super T, Object> valuesMapper) {
        this.foreachTokenizer = toForeachTokenizer(valuesMapper);
        return this;
    }

    public ForeachSplice<T> stringBuilder(StringBuilder stringBuilder) {
        this.stringBuilder = stringBuilder;
        return this;
    }

}
