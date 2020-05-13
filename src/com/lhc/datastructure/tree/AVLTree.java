package com.lhc.datastructure.tree;

import java.util.ArrayList;

/**
 * 二分搜索树
 * 左孩子比父亲节点小，右孩子比父亲节点大
 *
 * @param <K>
 */
public class AVLTree<K extends Comparable<K>, V> {

    private class Node implements TreePrintUtil.TreeNode {
        K key;
        V value;
        Node left;
        Node right;
        int height;

        public Node(K key, V val) {
            this.key = key;
            this.value = val;
            this.left = null;
            this.right = null;
            this.height = 1;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "val=" + key +
                    '}';
        }

        @Override
        public String getPrintInfo() {
            return key.toString();
        }

        @Override
        public TreePrintUtil.TreeNode getLeftChild() {
            return left;
        }

        @Override
        public TreePrintUtil.TreeNode getRightChild() {
            return right;
        }
    }

    private Node root;
    private int size;

    public AVLTree() {
        this.root = null;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return root == null;
    }

    public void add(K k, V v) {
        root = add(root, k, v);
    }

    /**
     * 递归-增加子节点
     *
     * @param node
     * @param k
     * @return
     */
    private Node add(Node node, K k, V v) {
        if (node == null) {
            return new Node(k, v);
        }
        if (k.compareTo(node.key) < 0) {
            node.left = add(node.left, k, v);
        } else if (k.compareTo(node.key) > 0) {
            node.right = add(node.right, k, v);
        }

        int beforeHeight = node.height;
        node.height = 1 + Math.max(getHeight(node.left), getHeight(node.right));

        //添加节点后，节点高度没发生变化，说明不需要旋转
        if(beforeHeight == node.height){
            return node;
        }

        int balanceFactor = getBalanceFactor(node);

        if (balanceFactor > 1 && getBalanceFactor(node.left) >= 0) {
            //LL情况，进行一次右旋
            return rightRotate(node);
        }

        if (balanceFactor < -1 && getBalanceFactor(node.right) <= 0) {
            //RR情况，进行一次左旋
            return leftRotate(node);
        }

        if (balanceFactor > 1 && getBalanceFactor(node.left) < 0) {
            //LR情况，先做一次左旋，再做一次右旋
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        if (balanceFactor < -1 && getBalanceFactor(node.right) > 0) {
            //RL情况，先做一次右旋，再做一次左旋
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        return node;
    }

    // 对节点y进行向右旋转操作，返回旋转后新的根节点x
    //        y                              x
    //       / \                           /   \
    //      x   T4     向右旋转 (y)        z     y
    //     / \       - - - - - - - ->    / \   / \
    //    z   T3                       T1  T2 T3 T4
    //   / \
    // T1   T2
    private Node rightRotate(Node y) {
        Node x = y.left;
        Node T3 = x.right;

        x.right = y;
        y.left = T3;

        updateHeight(y);
        updateHeight(x);
        return x;
    }

    // 对节点y进行向左旋转操作，返回旋转后新的根节点x
    //    y                             x
    //  /  \                          /   \
    // T1   x      向左旋转 (y)       y     z
    //     / \   - - - - - - - ->   / \   / \
    //   T2  z                     T1 T2 T3 T4
    //      / \
    //     T3 T4
    private Node leftRotate(Node y) {
        Node x = y.right;
        Node T2 = x.left;

        x.left = y;
        y.right = T2;

        updateHeight(y);
        updateHeight(x);
        return x;
    }

    private void updateHeight(Node node) {
        if (node == null) {
            return;
        }

        node.height = 1 + Math.max(getHeight(node.left), getHeight(node.right));
    }

    private int getHeight(Node node) {
        if (node == null) {
            return 0;
        } else {
            return node.height;
        }
    }

    private int getBalanceFactor(Node node) {
        if (node == null) {
            return 0;
        } else {
            return getHeight(node.left) - getHeight(node.right);
        }
    }


    /**
     * 返回键为key的节点
     *
     * @param key
     * @return
     */
    public Node getNode(K key) {
        return getNode(root, key);
    }

    private Node getNode(Node node, K key) {
        if (node == null) {
            return null;
        }

        if (key.compareTo(node.key) < 0) {
            return getNode(node.left, key);
        } else if (key.compareTo(node.key) > 0) {
            return getNode(node.right, key);
        } else {
            return node;
        }
    }

    public V get(K key) {
        Node node = getNode(key);
        return node == null ? null : node.value;
    }

    public void set(K key, V val) {
        Node node = getNode(key);
        if (node == null) {
            throw new IllegalArgumentException(key + "do not exist");
        }

        node.value = val;
    }

    public boolean contains(K k) {
        return getNode(k) != null;
    }

    public K minimum() {
        K val = minimumR(root).key;
        System.out.println(val);
        return val;
    }

    public Node minimumR(Node node) {
        if (node.left == null) {
            return node;
        }
        return minimumR(node.left);
    }

    public K removeMin() {
        K k = minimumR(root).key;
        root = removeMinR(root);
        System.out.println("remove " + k);
        return k;
    }

    /**
     * @param node 返回删除节点后新的二分搜索树的根
     * @return
     */
    private Node removeMinR(Node node) {
        if (node.left == null) {
            Node rightNode = node.right;
            node.right = null;
            return rightNode;
        }

        node.left = removeMinR(node.left);
        return node;
    }

    public K maximum() {
        K val = maximumR(root).key;
        System.out.println(val);
        return val;
    }

    private Node maximumR(Node node) {
        if (node.right == null) {
            return node;
        }
        return maximumR(node.right);
    }

    public K removeMax() {
        K k = maximumR(root).key;
        root = removeMaxR(root);
        System.out.println("remove " + k);
        return k;
    }

    private Node removeMaxR(Node node) {
        if (node.right == null) {
            Node leftNode = node.left;
            node.left = null;
            return leftNode;
        }
        node.right = removeMaxR(node.right);
        return node;
    }

    public void remove(K k) {
        remove(root, k);
    }

    private Node remove(Node node, K k) {
        if (node == null) {
            return null;
        }

        Node retNode = null;
        if (k.compareTo(node.key) < 0) {
            node.left = remove(node.left, k);
            retNode = node;
        } else if (k.compareTo(node.key) > 0) {
            node.right = remove(node.right, k);
            retNode = node;
        } else {
            if (node.left == null) {
                Node rightNode = node.right;
                node.right = null;
                retNode = rightNode;
            } else if (node.right == null) {
                Node leftNode = node.left;
                node.left = null;
                retNode = leftNode;
            } else {
                Node successor = minimumR(node.right);
                successor.right = remove(node.right, successor.key);
                successor.left = node.left;
                node.left = node.right = null;
                retNode = successor;
            }
        }

        if (retNode == null) {
            return null;
        }

        updateHeight(retNode);

        int balanceFactor = getBalanceFactor(retNode);

        if (balanceFactor > 1 && getBalanceFactor(retNode.left) >= 0) {
            //LL情况，进行一次右旋
            return rightRotate(retNode);
        }

        if (balanceFactor < -1 && getBalanceFactor(retNode.right) <= 0) {
            //RR情况，进行一次左旋
            return leftRotate(retNode);
        }

        if (balanceFactor > 1 && getBalanceFactor(retNode.left) < 0) {
            //LR情况，先做一次左旋，再做一次右旋
            retNode.left = leftRotate(retNode.left);
            return rightRotate(retNode);
        }

        if (balanceFactor < -1 && getBalanceFactor(retNode.right) > 0) {
            //RL情况，先做一次右旋，再做一次左旋
            retNode.right = rightRotate(retNode.right);
            return leftRotate(retNode);
        }

        return retNode;
    }

    public boolean isBalance() {
        return isBalance(root);
    }

    /**
     * 判断以Node为根的二叉树是否是一棵平衡二叉树，递归算法
     *
     * @param node
     * @return
     */
    private boolean isBalance(Node node) {
        if (node == null) {
            return true;
        }
        int balanceFactor = getBalanceFactor(node);
        if (Math.abs(balanceFactor) > 1) {
            return false;
        }
        return isBalance(node.left) && isBalance(node.right);
    }

    /**
     * 判断是否是一颗二分搜索树
     * 中序遍历，前一个数值比后一个数值小
     *
     * @return
     */
    public boolean isBST() {
        ArrayList<K> list = new ArrayList<>();
        inOrder(root, list);
        for (int i = 1; i < list.size(); i++) {
            if (list.get(i - 1).compareTo(list.get(i)) > 0) {
                return false;
            }
        }
        return true;
    }

    private void inOrder(Node node, ArrayList<K> list) {
        if (node == null) {
            return;
        }

        inOrder(node.left, list);
        list.add(node.key);
        inOrder(node.right, list);
    }
}
