package experiments;

import cse332.datastructures.trees.BinarySearchTree;
import datastructures.dictionaries.AVLTree;

public class AVLvsBST {

    public static void main(String[] args) {
        int[] array = {250, 2500, 25000};
        int NUM_TESTS = 50;
        int NUM_WARMUP = 5;
        long avlInsertTime = 0;
        long avlFindTime = 0;
        long bstInsertTime = 0;
        long bstFindTime = 0;

        for (int i = 0; i < array.length; i++) {
            for(int j = 0; j < NUM_TESTS; j++) {
                AVLTree<Integer, Integer> avl = new AVLTree<>();
                long startAVL = System.nanoTime();
                AVLInsert(array[i], avl);
                long finishAVL = System.nanoTime();
                if(NUM_WARMUP <= j) {
                    avlInsertTime += finishAVL - startAVL;
                }

                startAVL = System.nanoTime();
                AVLFind(avl);
                finishAVL = System.nanoTime();
                if(NUM_WARMUP <= j) {
                    avlFindTime += finishAVL - startAVL;
                }
            }
            long averageRunTimeInsertAVL = avlInsertTime / (NUM_TESTS - NUM_WARMUP);
            System.out.println("time for avl insert = " + averageRunTimeInsertAVL + " for size " + array[i]);
            long averageRunTimeFindAVL = avlFindTime / (NUM_TESTS - NUM_WARMUP);
            System.out.println("time for avl find = " + averageRunTimeFindAVL + " for size " + array[i]);
        }

        for(int i = 0; i < array.length; i++){
            for(int j = 0; j < NUM_TESTS; j++) {
                BinarySearchTree<Integer, Integer> bst = new BinarySearchTree<>();
                long startBST = System.nanoTime();
                BSTInsert(array[i], bst);
                long finishBST = System.nanoTime();
                if(NUM_WARMUP <= j) {
                    bstInsertTime += finishBST - startBST;
                }
                startBST = System.nanoTime();
                BSTFind(bst);
                finishBST = System.nanoTime();
                if(NUM_WARMUP <= j) {
                    bstFindTime += finishBST - startBST;
                }
            }
            long averageRunTimeInsertBST = bstInsertTime / (NUM_TESTS - NUM_WARMUP);
            System.out.println("time for bst insert = " + averageRunTimeInsertBST + " for size " + array[i]);
            long averageRunTimeFindBST = bstFindTime / (NUM_TESTS - NUM_WARMUP);
            System.out.println("time for bst find = " + averageRunTimeFindBST + " for size " + array[i]);

        }
    }

    public static void AVLInsert(int size, AVLTree<Integer, Integer> avl) {
        for (int i = 0; i < size; i++) {
            avl.insert(i, i);
        }
    }

    public static void AVLFind(AVLTree<Integer, Integer> avl){
        for(int i = 0; i < 2500; i++){
            avl.find(i);
        }
    }

    public static void BSTInsert(int size, BinarySearchTree<Integer, Integer> bst){
        for(int i = 0; i < size; i++){
            bst.insert(i, i);
        }
    }

    public static void BSTFind(BinarySearchTree<Integer, Integer> bst){
        for(int i = 0; i < 2500; i++){
            bst.find(i);
        }
    }
}