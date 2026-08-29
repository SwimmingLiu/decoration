package com.lamp.foundation.api.lang.util;

import lombok.Data;

@Data
public class Node<E> {

    private E element;

    private Node<E> next;

    private Node<E> prev;

    public Node() {

    }

    public Node(Node<E> prev, Node<E> next, E element) {
        this.next = next;
        this.prev = prev;
        this.element = element;
    }


    public interface  GetNode<E> {


        Node<E> getNode();
    }

}
