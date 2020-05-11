package com.lhc.datastructure.unionfind;

import java.util.Arrays;

/**
 * 第4个版本的并查集
 * 基于Rank优化并查集，将深度低的树链接到深度高的树上
 */
public class QuickUnionOptimizeByRankUF implements IUnionFind {

    /**
     * 存放数据对应集合的编号
     */
    private int[] parent;
    /**
     * nodeRank[i]表示根节点为i的树的深度
     */
    private int[] nodeRank;


    public QuickUnionOptimizeByRankUF(int size) {
        this.parent = new int[size];
        this.nodeRank = new int[size];
        for (int i = 0; i < parent.length; i++) {
            parent[i] = i;
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

    private void unionElementsOptimizeByRank(int p, int q) {
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