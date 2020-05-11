package com.lhc.datastructure.unionfind;

public interface IUnionFind {

    int getSize();

    boolean isConnected(int p, int q);

    void unionElements(int p, int q);
}
