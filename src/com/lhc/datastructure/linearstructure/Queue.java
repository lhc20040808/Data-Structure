package com.lhc.datastructure.linearstructure;

/**
 * First In First Out 先进先出
 */
public interface Queue<E> {

    void enqueue(E e);

    E dequeue();

    E getFront();

    int getSize();

    boolean isEmpty();
}
