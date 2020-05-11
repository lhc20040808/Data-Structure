package com.lhc.datastructure.unionfind;

public interface IUnionFind {

    int getSize();

    boolean isConnected(int p, int q);

    boolean unionElements(int p, int q);
}
