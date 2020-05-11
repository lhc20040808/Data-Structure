package com.lhc.datastructure.unionfind;

/**
 * 第1个版本的并查集
 * 能快速查找的并查集
 * find方法时间复杂度0(1)
 * 但是合并操作为O(n)
 */
public class QuickFindUF implements IUnionFind {
    /**
     * 存放数据对应集合的编号
     */
    private int[] ids;

    public QuickFindUF(int size) {
        this.ids = new int[size];
        for (int i = 0; i < ids.length; i++) {
            ids[i] = i;
        }
    }

    @Override
    public int getSize() {
        return ids.length;
    }

    @Override
    public boolean isConnected(int p, int q) {
        return find(p) == find(q);
    }

    @Override
    public void unionElements(int p, int q) {
        int pGroup = find(p);
        int qGroup = find(q);
        if (pGroup == qGroup) {
            return;
        }
        for (int i = 0; i < ids.length; i++) {
            if (ids[i] == pGroup) {
                ids[i] = qGroup;
            }
        }
    }

    /**
     * 查找p元素对应的集合
     *
     * @param p
     * @return
     */
    private int find(int p) {
        if (p < 0 || p >= ids.length) {
            throw new IndexOutOfBoundsException();
        }

        return ids[p];
    }
}