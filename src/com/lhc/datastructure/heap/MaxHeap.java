package com.lhc.datastructure.heap;

import com.lhc.datastructure.linearstructure.Array;

/**
 * 二叉堆是一个完全二叉树
 * 堆中某个节点的值总是不大于父亲节点的值
 */
public class MaxHeap<E extends Comparable<E>> {

    private Array<E> array;

    public MaxHeap() {
        array = new Array<>();
    }

    public MaxHeap(int capacity) {
        array = new Array<>(capacity);
    }

    public MaxHeap(E[] arr) {
        array = new Array<>(arr);
        for (int i = parent(arr.length - 1); i >= 0; i--) {
            shiftDown(i);
        }
    }

    public int getSize() {
        return array.getSize();
    }

    public boolean isEmpty() {
        return array.isEmpty();
    }

    /**
     * 返回完全二叉树的数组表示中，一个索引所表示的元素的父亲节点的索引
     */
    private int parent(int index) {
        if (index == 0) {
            throw new IllegalArgumentException("index-0 has no parent");
        }
        return (index - 1) / 2;
    }

    /**
     * 返回完全二叉树的数组表示中，一个索引所表示的元素的左孩子节点的索引
     */
    private int leftChild(int index) {
        return index * 2 + 1;
    }

    /**
     * 返回完全二叉树的数组表示中，一个索引所表示的元素的右孩子节点的索引
     */
    private int rightChild(int index) {
        return index * 2 + 2;
    }

    public void add(E e) {
        array.addLast(e);
        shiftUp(getSize() - 1);
    }

    private void shiftUp(int index) {
        while (index > 0 && array.get(index).compareTo(array.get(parent(index))) > 0) {
            array.swap(index, parent(index));
            index = parent(index);
        }
    }

    /**
     * 查看堆中最大的元素
     */
    public E findMax() {
        if (getSize() == 0) {
            return null;
        } else {
            return array.getFirst();
        }
    }

    /**
     * 取出堆中最大元素
     */
    public E extractMax() {
        E ret = findMax();
        array.swap(0, getSize() - 1);
        array.removeLast();
        shiftDown(0);
        return ret;
    }

    private void shiftDown(int index) {
        while (leftChild(index) > getSize()) {
            int j = leftChild(index);
            if (j + 1 < getSize() && array.get(j).compareTo(array.get(j + 1)) < 0) {
                j++;
            }

            if (array.get(index).compareTo(array.get(j)) >= 0) {
                break;
            }

            array.swap(index, j);
            index = j;
        }
    }

    /**
     * 取出堆中的最大元素，并且替换成元素e
     */
    public E replace(E e) {
        E ret = findMax();
        array.set(0, e);
        shiftDown(0);
        return ret;
    }

    public static void main(String[] args) {

    }
}