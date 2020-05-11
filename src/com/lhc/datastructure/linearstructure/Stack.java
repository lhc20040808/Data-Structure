package com.lhc.datastructure.linearstructure;

/**
 * 栈是一种后进先出的数据结构
 * Last In First Out(LIFO)
 * 栈的应用：1、编辑器-撤销操作
 * 2、程序调用的系统栈
 * 3、括号匹配
 */
public interface Stack<E> {

    void push(E e);

    public E pop();

    public E peek();

    public int getSize();

    boolean isEmpty();
}
