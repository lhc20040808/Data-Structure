package com.lhc.datastructure.unionfind;

import java.util.Arrays;

public class QuickUnionCommonUF implements IUnionFind {

    /**
     * 存放数据对应集合的编号
     */
    private int[] parent;

    public QuickUnionCommonUF(int size) {
        this.parent = new int[size];
        for (int i = 0; i < parent.length; i++) {
            parent[i] = i;
        }
    }

    @Override
    public int getSize() {
        return parent.length;
    }

    @Override
    public boolean isConnected(int p, int q) {
        return find(p) == find(q);
    }

    @Override
    public void unionElements(int p, int q) {
        unionElementsCommon(p, q);
    }

    /**
     * 普通的合并方法，极端情况下会产生单链
     *
     * @param p
     * @param q
     */
    private void unionElementsCommon(int p, int q) {
        int pRoot = find(p);
        int qRoot = find(q);

        if (pRoot == qRoot) {
            return;
        }
        parent[pRoot] = qRoot;
    }

    /**
     * 时间复杂度0(h)
     *
     * @param p
     * @return
     */
    private int find(int p) {
        if (p < 0 || p >= parent.length) {
            throw new IndexOutOfBoundsException();
        }

        while (p != parent[p]) {
            p = parent[p];
        }
        return p;
    }

    @Override
    public String toString() {
        return "QuickUnionUF{" +
                "parent=" + Arrays.toString(parent) +
                '}';
    }

    public static void main(String[] args) {

    }
}