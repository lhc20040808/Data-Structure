package com.lhc.datastructure.tree;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * 二分搜索树
 * 左孩子比父亲节点小，右孩子比父亲节点大
 * @param <E>
 */
public class BST<E extends Comparable<E>> {

    private class Node implements TreePrintUtil.TreeNode {

        E val;
        Node left;
        Node right;
        int size;
        int depth;

        public Node(E val) {
            this.val = val;
            this.left = null;
            this.right = null;
            this.size = 1;
        }

        public Node(E val, int depth) {
            this.val = val;
            this.left = null;
            this.right = null;
            this.size = 1;
            this.depth = depth;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "val=" + val +
                    ", size=" + size +
//                    ", depth=" + depth +
                    '}';
        }

        @Override
        public String getPrintInfo() {
            return val.toString();
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

    public void add(E e) {
        root = addR(root, e, 1);
    }

    /**
     * 递归-增加子节点
     *
     * @param node
     * @param e
     * @return
     */
    private Node addR(Node node, E e, int depth) {
        if (node == null) {
            return new Node(e, depth);
        }
        node.size++;
        if (e.compareTo(node.val) < 0) {
            node.left = addR(node.left, e, ++depth);
        } else if (e.compareTo(node.val) > 0) {
            node.right = addR(node.right, e, ++depth);
        }
        return node;
    }

    /**
     * 非递归-增加子节点
     *
     * @param e
     */
    private void nonRecursiveAdd(E e) {
        if (root == null) {
            root = new Node(e);
            return;
        }

        Node node = root;
        for (; ; ) {
            if (e.compareTo(node.val) < 0) {
                if (node.left == null) {
                    node.left = new Node(e);
                    return;
                } else {
                    node.size++;
                    node = node.left;
                }
            } else if (e.compareTo(node.val) > 0) {
                if (node.right == null) {
                    node.right = new Node(e);
                    return;
                } else {
                    node.size++;
                    node = node.right;
                }
            }
        }
    }

    public boolean contains(E e) {
        return recursiveContains(root, e);
    }

    /**
     * 递归-查询元素
     *
     * @param node
     * @param e
     * @return
     */
    private boolean recursiveContains(Node node, E e) {
        if (node == null) {
            return false;
        }

        if (e.compareTo(node.val) == 0) {
            return true;
        }

        return e.compareTo(node.val) < 0 ? recursiveContains(node.left, e) : recursiveContains(node.right, e);
    }

    /**
     * 非递归-查询元素
     *
     * @param e
     * @return
     */
    private boolean nonRecursiveContains(E e) {
        Node node = root;
        for (; ; ) {
            if (node == null) {
                return false;
            }

            if (e.compareTo(node.val) == 0) {
                return true;
            }

            if (e.compareTo(node.val) < 0) {
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

        System.out.println(node.val);
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
            System.out.println(node.val);
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
            System.out.println(node.val);
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
            System.out.println(node.val);
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
        System.out.println(node.val);
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
            System.out.println(s2.pop().val);
        }
    }

    public E minimum() {
        E val = minimumR(root).val;
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

    public E removeMin() {
        E e = minimumR(root).val;
        root = removeMinR(root);
        System.out.println("remove " + e);
        return e;
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

    public E maximum() {
        E val = maximumR(root).val;
        System.out.println(val);
        return val;
    }

    private Node maximumR(Node node) {
        if (node.right == null) {
            return node;
        }
        return maximumR(node.right);
    }

    public E removeMax() {
        E e = maximumR(root).val;
        root = removeMaxR(root);
        System.out.println("remove " + e);
        return e;
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

    public void removeElement(E e) {
        removeElementR(root, e);
    }

    private Node removeElementR(Node node, E e) {
        if (node == null) {
            return null;
        }

        if (e.compareTo(node.val) < 0) {
            node.size--;
            node.left = removeElementR(node.left, e);
            return node;
        } else if (e.compareTo(node.val) > 0) {
            node.size--;
            node.right = removeElementR(node.right, e);
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

    /**
     * 比e小的最大的数
     *
     * @param e
     */
    public void floor(E e) {
        //TODO
    }

    /**
     * 比e大的最小的数
     *
     * @param e
     */
    public void ceil(E e) {
        //TODO
    }

    /**
     * 返回e的排名
     *
     * @param e
     * @return
     */
    public int rank(E e) {
        //TODO
        return 0;
    }


    /**
     * 排第rank位的元素是什么
     *
     * @param rank
     * @return
     */
    public E select(int rank) {
        return null;
    }

    /////////////////
    //      5      //
    //    /   \    //
    //   3    6    //
    //  / \    \   //
    // 2  4     8  //
    /////////////////
    public static void main(String[] args) {
        BST bst = new BST();
        int[] nums = {5, 3, 6, 8, 4, 2, 7, 10, 9, 11};
        for (int num : nums)
            bst.add(num);

//        bst.preOrder();
//        bst.inOrder();
//        bst.postOrder();
//        bst.levelOrder();
//        bst.minimum();
//        bst.removeMin();
//        bst.maximum();
//        bst.removeMax();
//        bst.inOrder();
        TreePrintUtil.pirnt(bst.root);
//        bst.removeElement(8);
//        bst.removeMin();

//        bst.inOrder();
    }
}
