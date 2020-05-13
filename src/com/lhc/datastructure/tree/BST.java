package com.lhc.datastructure.tree;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * 二分搜索树
 * 左孩子比父亲节点小，右孩子比父亲节点大
 *
 * @param <K>
 */
public class BST<K extends Comparable<K>, V> {

    private class Node implements TreePrintUtil.TreeNode {

        K key;
        V val;
        Node left;
        Node right;
        int size;
        int depth;

        public Node(K key, V val) {
            this.key = key;
            this.val = val;
            this.left = null;
            this.right = null;
            this.size = 1;
        }

        public Node(K key, V val, int depth) {
            this.key = key;
            this.val = val;
            this.left = null;
            this.right = null;
            this.size = 1;
            this.depth = depth;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "val=" + key +
                    ", size=" + size +
//                    ", depth=" + depth +
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

    public BST() {
        this.root = null;
    }

    public int size() {
        if (root == null) {
            return 0;
        } else {
            return root.size;
        }
    }

    public boolean isEmpty() {
        return root == null;
    }

    public void add(K k, V v) {
        root = addR(root, k, v, 1);
    }


    public boolean isBST() {
        ArrayList<K> list = new ArrayList<>();
        inOrderCollect(root, list);
        for (int i = 1; i < list.size(); i++) {
            if (list.get(i - 1).compareTo(list.get(i)) > 0) {
                return false;
            }
        }
        return true;
    }

    private void inOrderCollect(Node node, ArrayList<K> list) {
        if (node == null) {
            return;
        }

        inOrderCollect(node.left, list);
        list.add(node.key);
        inOrderCollect(node.right, list);
    }

    /**
     * 递归-增加子节点
     *
     * @param node
     * @param k
     * @return
     */
    private Node addR(Node node, K k, V v, int depth) {
        if (node == null) {
            return new Node(k, v, depth);
        }
        node.size++;
        if (k.compareTo(node.key) < 0) {
            node.left = addR(node.left, k, v, ++depth);
        } else if (k.compareTo(node.key) > 0) {
            node.right = addR(node.right, k, v, ++depth);
        }
        return node;
    }

    /**
     * 非递归-增加子节点
     *
     * @param k
     */
    private void nonRecursiveAdd(K k, V v) {
        if (root == null) {
            root = new Node(k, v);
            return;
        }

        Node node = root;
        for (; ; ) {
            if (k.compareTo(node.key) < 0) {
                if (node.left == null) {
                    node.left = new Node(k, v);
                    return;
                } else {
                    node.size++;
                    node = node.left;
                }
            } else if (k.compareTo(node.key) > 0) {
                if (node.right == null) {
                    node.right = new Node(k, v);
                    return;
                } else {
                    node.size++;
                    node = node.right;
                }
            }
        }
    }

    public boolean contains(K k) {
        return recursiveContains(root, k);
    }

    /**
     * 递归-查询元素
     *
     * @param node
     * @param k
     * @return
     */
    private boolean recursiveContains(Node node, K k) {
        if (node == null) {
            return false;
        }

        if (k.compareTo(node.key) == 0) {
            return true;
        }

        return k.compareTo(node.key) < 0 ? recursiveContains(node.left, k) : recursiveContains(node.right, k);
    }

    /**
     * 非递归-查询元素
     *
     * @param k
     * @return
     */
    private boolean nonRecursiveContains(K k) {
        Node node = root;
        for (; ; ) {
            if (node == null) {
                return false;
            }

            if (k.compareTo(node.key) == 0) {
                return true;
            }

            if (k.compareTo(node.key) < 0) {
                node = node.left;
            } else {
                node = node.right;
            }
        }
    }

    //前序遍历
    public void preOrder() {
        System.out.println("---递归前序遍历---");
        preOrderR(root);
        System.out.println("---非递归前序遍历---");
        preOrderNR(root);
    }

    /**
     * 递归-前序遍历（根节点→左孩子→右孩子）
     *
     * @param node
     */
    private void preOrderR(Node node) {
        if (node == null)
            return;

        System.out.println(node.key);
        preOrderR(node.left);
        preOrderR(node.right);
    }

    /**
     * 非递归-前序遍历
     *
     * @param node
     */
    private void preOrderNR(Node node) {
        if (node == null) {
            return;
        }

        Stack<Node> stack = new Stack<>();
        stack.push(node);
        while (!stack.isEmpty()) {
            node = stack.pop();
            System.out.println(node.key);
            if (node.right != null)
                stack.push(node.right);
            if (node.left != null)
                stack.push(node.left);
        }
    }

    public void inOrder() {
        System.out.println("---递归中序遍历---");
        inOrderR(root);
//        System.out.println("---非递归中序遍历---");
//        inOrderNR(root);
    }

    /**
     * 递归-中序遍历
     *
     * @param node
     */
    private void inOrderR(Node node) {
        if (node == null)
            return;

        inOrderR(node.left);
        System.out.println(node);
        inOrderR(node.right);
    }

    private void inOrderNR(Node node) {
        Stack<Node> stack = new Stack<>();
        while (true) {
            while (node != null) {
                stack.push(node);
                node = node.left;
            }
            if (stack.isEmpty()) {
                break;
            }
            node = stack.pop();
            System.out.println(node.key);
            node = node.right;
        }
    }

    public void postOrder() {
        System.out.println("---递归后序遍历---");
        postOrderR(root);
        System.out.println("---非递归后序遍历---");
        postOrderNR(root);
    }

    /**
     * 层序遍历
     */
    public void levelOrder() {
        Node node = root;
        if (node == null) {
            return;
        }
        System.out.println("---层序遍历---");
        Queue<Node> queue = new LinkedList<>();
        queue.add(node);
        while (!queue.isEmpty()) {
            node = queue.poll();
            System.out.println(node.key);
            if (node.left != null) {
                queue.add(node.left);
            }
            if (node.right != null) {
                queue.add(node.right);
            }
        }
    }

    private void postOrderR(Node node) {
        if (node == null)
            return;

        postOrderR(node.left);
        postOrderR(node.right);
        System.out.println(node.key);
    }

    private void postOrderNR(Node node) {
        if (node == null) {
            return;
        }

        Stack<Node> s1 = new Stack<>();
        Stack<Node> s2 = new Stack<>();

        s1.push(node);

        Node curNode;
        while (!s1.isEmpty()) {

            curNode = s1.pop();
            // 中、右、左顺序压入栈中
            s2.push(curNode);

            // 压入s1为先左后右，保证中、右、左顺序压入s2中
            if (curNode.left != null) {
                s1.push(curNode.left);
            }
            if (curNode.right != null) {
                s1.push(curNode.right);
            }
        }

        while (!s2.isEmpty()) {
            System.out.println(s2.pop().key);
        }
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

    public Node minimumNR(Node node) {
        if (node == null) {
            return null;
        }

        while (node.left != null) {
            node = node.left;
        }

        return node;
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

        node.size--;
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
        node.size--;
        node.right = removeMaxR(node.right);
        return node;
    }

    public void removeElement(K k) {
        removeElementR(root, k);
    }

    private Node removeElementR(Node node, K k) {
        if (node == null) {
            return null;
        }

        if (k.compareTo(node.key) < 0) {
            node.size--;
            node.left = removeElementR(node.left, k);
            return node;
        } else if (k.compareTo(node.key) > 0) {
            node.size--;
            node.right = removeElementR(node.right, k);
            return node;
        } else {
            if (node.left == null) {
                Node rightNode = node.right;
                node.right = null;
                return rightNode;
            }

            if (node.right == null) {
                Node leftNode = node.left;
                node.left = null;
                return leftNode;
            }

            Node successor = minimumR(node.right);
            successor.size = node.size - 1;
            successor.depth = node.depth;
            successor.right = removeMinR(node.right);
            successor.left = node.left;
            node.left = node.right = null;
            return successor;
        }
    }

    public void set(K key, V val) {
        Node node = find(key);
        if (node == null) {
            throw new IllegalArgumentException("node do not exist");
        }
        node.val = val;
    }

    public Node find(K key) {
        return find(root, key);
    }

    private Node find(Node node, K key) {
        if (node == null) {
            return null;
        }

        if (key.compareTo(node.key) < 0) {
            return find(node.left, key);
        } else if (key.compareTo(node.key) > 0) {
            return find(node.right, key);
        } else {
            return node;
        }
    }

    public V get(K key) {
        Node node = find(key);
        return node == null ? null : node.val;
    }
}
