package com.lhc.datastructure.tree.segmenttree;

/**
 * 静态线段树
 * 线段树一定是平衡二叉树，平衡二叉树最大深度和最小深度的值相差1
 * 第H层节点个数：2^(H-1)
 * 假如有N个元素，用静态数组装线段树，最多需要4N的空间
 */
public class SegmentTree<E> {

    private E[] data;
    private E[] tree;
    private Merger<E> merger;

    public SegmentTree(E[] arr, Merger<E> merger) {
        this.merger = merger;
        data = (E[]) new Object[arr.length];
        for (int i = 0; i < arr.length; i++) {
            data[i] = arr[i];
        }
        //假如有N个元素，最坏的情况下需要4N的空间来存放N个元素形成的线段树
        tree = (E[]) new Object[data.length * 4];
        buildSegment(0, 0, data.length - 1);
    }

    private void buildSegment(int treeIndex, int l, int r) {
        if (l == r) {
            tree[treeIndex] = data[l];
            return;
        }

        int leftTreeIndex = leftChild(treeIndex);
        int rightTreeIndex = rightChild(treeIndex);
        int mid = l + (r - l) / 2;//这样写可以防止(l + r)过大造成整型溢出

        buildSegment(leftTreeIndex, l, mid);
        buildSegment(rightTreeIndex, mid + 1, r);

        tree[treeIndex] = merger.merge(tree[leftTreeIndex], tree[rightTreeIndex]);
    }

    private int parent(int index) {
        if (index == 0) {
            throw new IllegalArgumentException("index-0 has no parent");
        }

        return (index - 1) / 2;
    }

    private int leftChild(int index) {
        return 2 * index + 1;
    }

    private int rightChild(int index) {
        return 2 * index + 2;
    }

    public int getSize() {
        return data.length;
    }

    public E get(int index) {
        if (index < 0 || index >= data.length) {
            throw new IndexOutOfBoundsException();
        }
        return data[index];
    }

    public E query(int queryL, int queryR) {
        if (queryL < 0 || queryL >= data.length || queryR < 0 || queryR >= data.length) {
            throw new IndexOutOfBoundsException();
        }

        return query(0, 0, data.length - 1, queryL, queryR);
    }

    private E query(int treeIndex, int l, int r, int queryL, int queryR) {
        if (l == queryL && r == queryR) {
            return tree[treeIndex];
        }

        int leftTreeIndex = leftChild(treeIndex);
        int rightTreeIndex = rightChild(treeIndex);
        int mid = l + (r - l) / 2;//这样写可以防止(l + r)过大造成整型溢出
        if (queryR <= mid) {
            return query(leftTreeIndex, l, mid, queryL, queryR);
        } else if (queryL > mid) {
            return query(rightTreeIndex, mid + 1, r, queryL, queryR);
        } else {
            E left = query(leftTreeIndex, l, mid, queryL, mid);
            E right = query(rightTreeIndex, mid + 1, r, mid + 1, queryR);
            return merger.merge(left, right);
        }
    }

    public void set(int index, E e) {
        if (index < 0 || index >= data.length) {
            throw new IndexOutOfBoundsException();
        }
        data[index] = e;
        set(0, 0, data.length - 1, index, e);
    }

    private void set(int treeIndex, int l, int r, int index, E e) {
        if (l == index) {
            tree[treeIndex] = e;
            return;
        }

        int mid = l + (r - l) / 2;
        int leftTreeIndex = leftChild(treeIndex);
        int rightTreeIndex = rightChild(treeIndex);
        if (index <= mid) {
            set(leftTreeIndex, l, mid, index, e);
        } else {
            set(rightTreeIndex, mid + 1, r, index, e);
        }
        tree[treeIndex] = merger.merge(tree[leftTreeIndex], tree[rightTreeIndex]);
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        res.append('[');
        for (int i = 0; i < tree.length; i++) {
            if (tree[i] != null)
                res.append(tree[i]);
            else
                res.append("null");

            if (i != tree.length - 1)
                res.append(", ");
        }
        res.append(']');
        return res.toString();
    }

    public static void main(String[] args) {
        Integer[] nums = {-2, 0, 3, -5, 2, -1};

        SegmentTree<Integer> segTree = new SegmentTree<>(nums,
                (a, b) -> a + b);
        System.out.println(segTree);

        System.out.println(segTree.query(0, 2));
        System.out.println(segTree.query(2, 5));
        System.out.println(segTree.query(0, 5));
    }
}