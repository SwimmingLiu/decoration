package com.lamp.foundation.api.extension.sort;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.util.Objects;

import com.lamp.foundation.api.lang.util.Node;

import lombok.Getter;

/**
 * <pre>
 *  sort 主要解决，有父 entity 的解决方案
 *  如果没有父类，就按照默认循序，强制性 ?????,目前强制性
 * </pre>
 *
 * @author hahaha
 */
@Target({METHOD, FIELD})
@Retention(RUNTIME)
public @interface Sort {

    SortType type() default SortType.AFTER;

    String field() default "";

    enum SortType {

        /**
         * 顶格，一个类只有一个
         */
        ROOT_FIRST(null),

        /**
         * 当前类第一，如何父类有 ROOT_FIRST ， 那么变量就排第二，
         */
        FIRST(ROOT_FIRST),

        /**
         * 向上，自动排序，默认是这个
         */
        UPWARD(FIRST),

        /**
         * 向下，自动排序
         */
        DOWN(UPWARD),
        /**
         * 当前类倒数一，如何父类有 ROOT_LAST ， 那么变量就排倒数第二
         */
        LAST(DOWN),

        /**
         * 一个类最后一个
         */
        ROOT_LAST(LAST),

        /**
         * 再某个字段之前
         */
        BEFORE(null),

        /**
         * 在某个字段之后
         */
        AFTER(null),

        ;


        static {
            ROOT_LAST.getNode();
        }

        @Getter
        private SortType next;

        private SortType prev;

        private Node<SortType> sortTypeNode;

        SortType(SortType sortType) {
            this.prev = sortType;
        }

        public Node<SortType> getNode() {
            if (Objects.nonNull(this.sortTypeNode)) {
                return this.sortTypeNode;
            }
            sortTypeNode = new Node<>();
            if(Objects.nonNull(this.prev)) {
                sortTypeNode.setPrev(prev.getNode());
                prev.getNode().setNext(sortTypeNode);
                prev.next = this;
            }
            sortTypeNode.setElement(this);
            return sortTypeNode;
        }

    }

}
