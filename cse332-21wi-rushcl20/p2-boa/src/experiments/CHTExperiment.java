package experiments;

import datastructures.dictionaries.MoveToFrontList;
import cse332.datastructures.trees.BinarySearchTree;
import datastructures.dictionaries.AVLTree;
import datastructures.dictionaries.ChainingHashTable;


public class CHTExperiment {
    public static void main(String[] args) {
        int NUM_WARMUP = 7;
        int trials = 50;
        double mtflTotTime = 0.0, bstTotTime = 0.0, avlTotTime = 0.0;
        double mtflAvgTime = 0.0, bstAvgTime = 0.0, avlAvgTime = 0.0;

        for (int i = 1; i <= 5; i++) {
            mtflTotTime = 0.0;
            bstTotTime = 0.0;
            avlTotTime = 0.0;

            for (int j = 0; j < trials; j++) {
                long start = System.currentTimeMillis();
                ChainingHashTable mtfl = new ChainingHashTable(MoveToFrontList::new);
                insertCHT(mtfl, (i*500));
                findCHT(mtfl, (i*500));
                long end = System.currentTimeMillis();
                if (NUM_WARMUP <= j) {
                    mtflTotTime += (end - start);
                }

                start = System.currentTimeMillis();
                ChainingHashTable bst = new ChainingHashTable(BinarySearchTree::new);
                insertCHT(bst, (i*500));
                findCHT(bst, (i*500));
                end = System.currentTimeMillis();
                if (NUM_WARMUP <= j) {
                    bstTotTime += (end - start);
                }

                start = System.currentTimeMillis();
                ChainingHashTable avl = new ChainingHashTable(AVLTree::new);
                insertCHT(avl, (i*500));
                findCHT(avl, (i*500));
                end = System.currentTimeMillis();
                if (NUM_WARMUP <= j) {
                    avlTotTime += (end - start);
                }
            }
            mtflAvgTime = mtflTotTime / (trials - NUM_WARMUP);
            bstAvgTime = bstTotTime / (trials - NUM_WARMUP);
            avlAvgTime = avlTotTime / (trials - NUM_WARMUP);

            System.out.println("Average time for MTFL with " + (i*500) + " input: " + mtflAvgTime + " ms");
            System.out.println("Average time for BST with " + (i*500) + " input: " + bstAvgTime + " ms");
            System.out.println("Average time for AVL with " + (i*500) + " input: " + avlAvgTime + " ms");
            System.out.println();
        }
    }
    public static void insertCHT(ChainingHashTable chain, int input) {
        for (int i = 0; i < input; i++) {
            chain.insert(i, i);
        }
    }

    public static void findCHT(ChainingHashTable chain, int input) {
        for (int i = 0; i < input-75; i++) {
            chain.find(i);
        }
    }
}
