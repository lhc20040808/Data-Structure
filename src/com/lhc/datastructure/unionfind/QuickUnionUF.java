package com.lhc.datastructure.unionfind;

import java.util.Arrays;

public class QuickUnionUF implements IUnionFind {

    /**
     * 存放数据对应集合的编号
     */
    private int[] parent;
    /**
     * nodeSize[i]表示根节点为i的树的节点数量
     */
    private int[] nodeSize;
    /**
     * nodeRank[i]表示根节点为i的树的深度
     */
    private int[] nodeRank;


    public QuickUnionUF(int size) {
        this.parent = new int[size];
        this.nodeSize = new int[size];
        this.nodeRank = new int[size];
        for (int i = 0; i < parent.length; i++) {
            parent[i] = i;
            nodeSize[i] = 1;
            nodeRank[i] = 1;
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
        unionElementsOptimizeByRank(p, q);
    }

    public void unionElementsOptimizeByRank(int p, int q) {
        int pRoot = find(p);
        int qRoot = find(q);

        if (pRoot == qRoot) {
            return;
        }

        //把深度低的树链接到深度高的树的根节点上
        //因为深度低的树的深度+1 <= 深度高的树，所以深度不同时不用更新树的深度
        if (nodeRank[pRoot] < nodeRank[qRoot]) {
            parent[pRoot] = qRoot;
        } else if (nodeRank[pRoot] > nodeRank[qRoot]) {
            parent[qRoot] = pRoot;
        } else {
            parent[pRoot] = qRoot;
            nodeRank[qRoot]++;
        }
    }

    /**
     * 基于Size优化并查集
     * 能在一定程度上解决深度问题，但有时候仍然会将深度高的树链接给深度低的树，导致树的深度进一步加深
     *
     * @param p
     * @param q
     */
    public void unionElementsOptimizeBySize(int p, int q) {
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
     * 普通的合并方法，极端情况下会产生单链
     *
     * @param p
     * @param q
     */
    public void unionElementsCommon(int p, int q) {
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

    public String getRank() {
        return Arrays.toString(nodeRank);
    }

    public static void main(String[] args) {
        QuickUnionUF quickUnionUF = new QuickUnionUF(10);
        System.out.println(quickUnionUF);
        quickUnionUF.unionElements(0, 1);
        quickUnionUF.unionElements(1, 2);
        quickUnionUF.unionElements(2, 3);
        System.out.println(quickUnionUF);
        System.out.println(quickUnionUF.getRank());
    }
}