package com.lamp.foundation.api.lang.util;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public interface Recursion<T> {

    FileRecursion FILE_RECURSION = new FileRecursion();

    Match match(T t);

    List<T> child(T t);


    enum Match {

        /**
         * 深入，表示当前节点还有子节点
         */
        THOROUGHLY,

        /**
         * 深入且加入 ，表示当前节点还有子节点，并且把node ，进入返回
         */
        THOROUGHLY_ADD,

        /**
         * 不需要深入，且会加入返回结果
         */
        NOT_THOROUGHLY_ADD,

        /**
         * 不需要深入，且会抛弃当前节点
         */
        NOT_THOROUGHLY_AND_ABANDON,

        /**
         * 结束递归
         */
        END,

        ;

    }

    class FileRecursion implements Recursion<File> {

        @Override
        public Match match(File t) {
            if (!t.exists()) {
                return Match.NOT_THOROUGHLY_AND_ABANDON;
            }
            return t.isDirectory() ? Match.THOROUGHLY : Match.NOT_THOROUGHLY_ADD;
        }

        @Override
        public List<File> child(File t) {
            File[] files = t.listFiles();
            if (Objects.isNull(files)) {
                return Collections.emptyList();
            }
            return Arrays.asList(files);
        }
    }

}
