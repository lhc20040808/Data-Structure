package com.lhc.datastructure.unionfind;

import java.util.Arrays;

/**
 * 第3个版本的并查集
 * 基于Size优化并查集，将数量少的树链接给数量多的树
 */
public class QuickUnionOptimizeBySizeUF implements IUnionFind {

    /**
     * 存放数据对应集合的编号
     */
    private int[] parent;
    /**
     * nodeSize[i]表示根节点为i的树的节点数量
     */
    private int[] nodeSize;


    public QuickUnionOptimizeBySizeUF(int size) {
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
        unionElementsOptimizeBySize(p, q);
    }

    /**
     * 基于Size优化并查集，将数量少的树链接给数量多的树
     * 能在一定程度上解决深度问题，但有时候仍然会将深度高的树链接给深度低的树，导致树的深度进一步加深
     *
     * @param p
     * @param q
     */
    private void unionElementsOptimizeBySize(int p, int q) {
        int pRoot = find(p);
        int qRoot = find(q);

        if (pRoot == qRoot) {
            return;
        }
        //把数量少的树链接到数量多的树上，防止极端情况并查集出现链表的情况
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

    }
}