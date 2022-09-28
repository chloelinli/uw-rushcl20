package experiments;


import cse332.datastructures.trees.BinarySearchTree;
import cse332.interfaces.misc.BString;
import cse332.interfaces.misc.DeletelessDictionary;
import cse332.types.AlphabeticString;
import datastructures.dictionaries.AVLTree;
import datastructures.dictionaries.ChainingHashTable;
import datastructures.dictionaries.HashTrieMap;
import datastructures.dictionaries.MoveToFrontList;
import net.reduls.sanmoku.dic.Char;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class GeneralPurposeDictionaryExperiment {

    public static final int INPUT_SIZE = 20000;



    public static void main(String[] args) throws FileNotFoundException {
        int NUM_TESTS = 300;
        int NUM_WARMUP = 5;
        BinarySearchTree<AlphabeticString, AlphabeticString> BST = new BinarySearchTree<>();
        AVLTree<AlphabeticString, AlphabeticString> AVL = new AVLTree<>();
        ChainingHashTable<AlphabeticString, AlphabeticString> CHT = new ChainingHashTable<>(MoveToFrontList::new);
        HashTrieMap<Character, AlphabeticString, AlphabeticString> HTM = new HashTrieMap<>(AlphabeticString.class);

        AlphabeticString[] keys = getKeys();


        insertBST(keys, BST, NUM_TESTS, NUM_WARMUP);
        insertAVL(keys, AVL, NUM_TESTS, NUM_WARMUP);
        insertCHT(keys, CHT, NUM_TESTS, NUM_WARMUP);
        insertHTM(keys, HTM, NUM_TESTS, NUM_WARMUP);
        findBST(keys, BST, NUM_TESTS, NUM_WARMUP);
        findAVL(keys, AVL, NUM_TESTS, NUM_WARMUP);
        findCHT(keys, CHT, NUM_TESTS, NUM_WARMUP);
        findHTM(keys, HTM, NUM_TESTS, NUM_WARMUP);


    }

    private static AlphabeticString[] getKeys() throws FileNotFoundException {
            AlphabeticString[] keys = new AlphabeticString[INPUT_SIZE];
            File f = new File("alice.txt");
            Scanner sc = new Scanner(f);
            int numberOfElement = 0;
            while (sc.hasNext() && numberOfElement < INPUT_SIZE) {
                String word = sc.next();
                keys[numberOfElement] = new AlphabeticString(word);
                numberOfElement++;
            }
        return keys;
    }

    private static void insertBST(AlphabeticString[] getKeys, BinarySearchTree<AlphabeticString, AlphabeticString> BST,
                                  int NUM_TESTS, int NUM_WARMUP){
        long totalTimeBST = 0;
            for (int j = 0; j < NUM_TESTS; j++) {
                long startBST = System.nanoTime();
                for (int i = 0; i < INPUT_SIZE; i++) {
                    BST.insert(getKeys[i], getKeys[i]);
                }
                long finishBST = System.nanoTime();
                if (NUM_WARMUP <= j) {
                    totalTimeBST += finishBST - startBST;
                }
        }
        long averageTimeBST = totalTimeBST / (NUM_TESTS - NUM_WARMUP);
        System.out.println("average runtime for insert bst = " + averageTimeBST + " with input size " + INPUT_SIZE);
    }

    private static void insertAVL(AlphabeticString[] getKeys, AVLTree<AlphabeticString, AlphabeticString> AVL,
                                  int NUM_TESTS, int NUM_WARMUP){
        long totalTimeAVL = 0;
        for(int i = 0; i < NUM_TESTS; i++) {
            long startAVL = System.nanoTime();
            for (int j = 0; j < INPUT_SIZE; j++) {
                AVL.insert(getKeys[j], getKeys[j]);
            }
            long finishAVL = System.nanoTime();
            if(NUM_WARMUP <= i) {
                totalTimeAVL += finishAVL - startAVL;
            }
        }
        long averageTimeAVL = totalTimeAVL / (NUM_TESTS - NUM_WARMUP);
        System.out.println("average runtime for insert avl = " + averageTimeAVL + " with input size " + INPUT_SIZE);
    }

    private static void insertCHT(AlphabeticString[] getKeys, ChainingHashTable<AlphabeticString, AlphabeticString> CHT,
                                  int NUM_TESTS, int NUM_WARMUP){
        long totalTimeCHT = 0;
        for(int i = 0; i < NUM_TESTS; i++) {
            long startCHT = System.nanoTime();
            for (int j = 0; j < INPUT_SIZE; j++) {
                CHT.insert(getKeys[j], getKeys[j]);
            }
            long finishCHT = System.nanoTime();
            if(NUM_WARMUP <= i) {
                totalTimeCHT += finishCHT - startCHT;
            }
        }
        long averageTimeCHT = totalTimeCHT / (NUM_TESTS - NUM_WARMUP);
        System.out.println("average runtime for insert cht = " + averageTimeCHT + " with input size " + INPUT_SIZE);
    }

    private static void insertHTM(AlphabeticString[] getKeys, HashTrieMap<Character, AlphabeticString, AlphabeticString> HTM,
                                  int NUM_TESTS, int NUM_WARMUP){
        long totalTimeHTM = 0;
        for(int i = 0; i < NUM_TESTS; i++) {
            long startHTM = System.nanoTime();
            for (int j = 0; j < INPUT_SIZE; j++) {
                HTM.insert(getKeys[j], getKeys[j]);
            }
            long finishHTM = System.nanoTime();
            if(NUM_WARMUP <= i) {
                totalTimeHTM += finishHTM - startHTM;
            }
        }
        long averageTimeHTM = totalTimeHTM / (NUM_TESTS - NUM_WARMUP);
        System.out.println("average runtime for insert htm = " + averageTimeHTM + " with input size " + INPUT_SIZE);
    }

    private static void findBST(AlphabeticString[] getKeys, BinarySearchTree<AlphabeticString, AlphabeticString> BST,
                                  int NUM_TESTS, int NUM_WARMUP){
        long totalTimeBST = 0;
        for(int i = 0; i < NUM_TESTS; i++) {
            long startBST = System.nanoTime();
            for (int j = 0; j < INPUT_SIZE; j++) {
                BST.find(getKeys[j]);
            }
            long finishBST = System.nanoTime();
            if(NUM_WARMUP <= i) {
                totalTimeBST += finishBST - startBST;
            }
        }
        long averageTimeBST = totalTimeBST / (NUM_TESTS - NUM_WARMUP);
        System.out.println("average runtime for find bst = " + averageTimeBST + " with input size " + INPUT_SIZE);
    }

    private static void findAVL(AlphabeticString[] getKeys, AVLTree<AlphabeticString, AlphabeticString> AVL,
                                  int NUM_TESTS, int NUM_WARMUP){
        long totalTimeAVL = 0;
        for(int i = 0; i < NUM_TESTS; i++) {
            long startAVL = System.nanoTime();
            for (int j = 0; j < INPUT_SIZE; j++) {
                AVL.find(getKeys[j]);
            }
            long finishAVL = System.nanoTime();
            if(NUM_WARMUP <= i) {
                totalTimeAVL += finishAVL - startAVL;
            }
        }
        long averageTimeAVL = totalTimeAVL / (NUM_TESTS - NUM_WARMUP);
        System.out.println("average runtime for find avl = " + averageTimeAVL + " with input size " + INPUT_SIZE);
    }

    private static void findCHT(AlphabeticString[] getKeys, ChainingHashTable<AlphabeticString, AlphabeticString> CHT,
                                  int NUM_TESTS, int NUM_WARMUP) {
        long totalTimeCHT = 0;
        for(int i = 0; i < NUM_TESTS; i++) {
            long startCHT = System.nanoTime();
            for (int j = 0; j < INPUT_SIZE; j++) {
                CHT.find(getKeys[j]);
            }
            long finishCHT = System.nanoTime();
            if(NUM_WARMUP <= i) {
                totalTimeCHT += finishCHT - startCHT;
            }
        }
        long averageTimeCHT = totalTimeCHT / (NUM_TESTS - NUM_WARMUP);
        System.out.println("average runtime for find cht = " + averageTimeCHT + " with input size " + INPUT_SIZE);
    }

    private static void findHTM(AlphabeticString[] getKeys, HashTrieMap<Character, AlphabeticString, AlphabeticString> HTM,
                                  int NUM_TESTS, int NUM_WARMUP){
                long totalTimeHTM = 0;
        for(int i = 0; i < NUM_TESTS; i++) {
            long startHTM = System.nanoTime();
            for (int j = 0; j < INPUT_SIZE; j++) {
                HTM.find(getKeys[j]);
            }
            long finishHTM = System.nanoTime();
            if(NUM_WARMUP <= i) {
                totalTimeHTM += finishHTM - startHTM;
            }
        }
        long averageTimeHTM = totalTimeHTM / (NUM_TESTS - NUM_WARMUP);
        System.out.println("average runtime for find htm = " + averageTimeHTM + " with input size " + INPUT_SIZE);
    }
}

