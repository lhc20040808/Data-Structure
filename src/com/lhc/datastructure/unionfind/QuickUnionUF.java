package com.lhc.datastructure.unionfind;

import java.util.Arrays;

public class QuickUnionUF implements IUnionFind {

    /**
     * 存放数据对应集合的编号
     */
    private int[] parent;
    /**
     * 第i个元素为根节点时的节点数量
     */
    private int[] nodeSize;

    public QuickUnionUF(int size) {
        this.parent = new int[size];
        this.nodeSize = new int[size];
        for (int i = 0; i < parent.length; i++) {
            parent[i] = i;
            nodeSize[i] = 1;
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
        int pRoot = find(p);
        int qRoot = find(q);

        if (pRoot == qRoot) {
            return;
        }
        //把深度小的树链接到深度大的树上，防止极端情况并查集出现链表的情况
        if (nodeSize[pRoot] < nodeSize[qRoot]) {
            parent[pRoot] = qRoot;
            nodeSize[qRoot] += nodeSize[pRoot];
        } else {
            parent[qRoot] = pRoot;
            nodeSize[pRoot] += nodeSize[qRoot];
        }
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
        QuickUnionUF quickUnionUF = new QuickUnionUF(10);
        System.out.println(quickUnionUF);
        quickUnionUF.unionElements(0, 1);
        quickUnionUF.unionElements(1, 2);
        quickUnionUF.unionElements(2, 3);
        System.out.println(quickUnionUF);
    }
}