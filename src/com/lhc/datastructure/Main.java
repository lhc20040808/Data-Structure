package com.lhc.datastructure;

import com.lhc.datastructure.tree.AVLTree;
import com.lhc.datastructure.utils.FileOperation;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        System.out.println("Pride and Prejudice");

        ArrayList<String> words = new ArrayList<>();
        if (FileOperation.readFile("pride-and-prejudice.txt", words)) {
            System.out.println("Total words: " + words.size());

            long startTime = System.nanoTime();

            // Test AVL
            startTime = System.nanoTime();

            AVLTree<String, Integer> avl = new AVLTree<>();
            for (String word : words) {
                if (avl.contains(word))
                    avl.set(word, avl.get(word) + 1);
                else
                    avl.add(word, 1);
            }

            for (String word : words)
                avl.contains(word);

            long endTime = System.nanoTime();

            System.out.println("AVL: " + (endTime - startTime) / 1000000000.0 + " s");
            System.out.println("是否平衡:" + avl.isBalance());
            System.out.println("是否是二分搜索树:" + avl.isBST());
        }

        System.out.println();
    }
}
