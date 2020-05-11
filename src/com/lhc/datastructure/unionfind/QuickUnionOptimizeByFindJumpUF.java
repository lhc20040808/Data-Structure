package com.lhc.datastructure.unionfind;

import java.util.Arrays;

/**
 * 第5个版本的并查集
 * 查找节点的同时进行路径压缩,每次将节点指向父节点的父节点
 * 同时维护一个并不完全准的rank列表。
 * 只是作为一个节点优化的参考
 */
public class QuickUnionOptimizeByFindJumpUF implements IUnionFind {

    /**
     * 存放数据对应集合的编号
     */
    private int[] parent;
    /**
     * nodeRank[i]表示根节点为i的树的深度
     */
    private int[] nodeRank;


    public QuickUnionOptimizeByFindJumpUF(int size) {
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
     * 查找根节点的同时进行路径压缩
     * 如果对同一个节点多次调用find，能让树的深度变成2
     *
     * @param p
     * @return
     */
    public int find(int p) {
        if (p < 0 || p >= parent.length) {
            throw new IndexOutOfBoundsException();
        }

        while (p != parent[p]) {
            parent[p] = parent[parent[p]];//当前节点指向父节点的父节点
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
        QuickUnionOptimizeByFindJumpUF quickUnionUF = new QuickUnionOptimizeByFindJumpUF(10);
        System.out.println(quickUnionUF);
        quickUnionUF.unionElements(0, 1);
        quickUnionUF.unionElements(1, 2);
        System.out.println(quickUnionUF);
        quickUnionUF.unionElements(2, 3);
        System.out.println(quickUnionUF);
        quickUnionUF.unionElements(3, 4);
        System.out.println(quickUnionUF);
        System.out.println(quickUnionUF.getRank());
    }
}