package experiments;

import cse332.types.AlphabeticString;
import datastructures.dictionaries.ChainingHashTable;
import datastructures.dictionaries.MoveToFrontList;
import datastructures.worklists.CircularArrayFIFOQueue;

import javax.swing.text.html.HTMLDocument;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class HashFuncExperiment {
    private static final int INPUT_SIZE = 10000;
    public static void main(String[] args) throws FileNotFoundException {
        int NUM_WARMUP = 7;
        int NUM_TESTS = 200;
        ChainingHashTable<AlphabeticString, AlphabeticString> CHT = new ChainingHashTable<>(MoveToFrontList::new);
        AlphabeticString[] keys = getKeys();
        insert(keys, CHT, NUM_WARMUP, NUM_TESTS);
        find(keys, CHT, NUM_WARMUP, NUM_TESTS);
    }

    private static AlphabeticString[] getKeys() throws FileNotFoundException {
        AlphabeticString[] key = new AlphabeticString[INPUT_SIZE];
        File f = new File("alice.txt");
        Scanner sc = new Scanner(f);
        int elementNum = 0;
        while(sc.hasNext() && elementNum < INPUT_SIZE){
            String word = sc.next();
            key[elementNum] = new AlphabeticString(word);
            elementNum ++;
        }
        return key;
    }

    private static void insert(AlphabeticString[] getKeys, ChainingHashTable<AlphabeticString, AlphabeticString> CHT,
                               int NUM_WARMUP, int NUM_TESTS){
        long totalTimeCHT = 0;
        for (int j = 0; j < NUM_TESTS; j++) {
            long startCHT = System.nanoTime();
            for (int i = 0; i < INPUT_SIZE; i++) {
                CHT.insert(getKeys[i], getKeys[i]);
            }
            long finishCHT = System.nanoTime();
            if (NUM_WARMUP <= j) {
                totalTimeCHT += finishCHT - startCHT;
            }
        }
        long averageTimeCHT = totalTimeCHT / (NUM_TESTS - NUM_WARMUP);
        System.out.println("average runtime for insert cht = " + averageTimeCHT + " with input size " + INPUT_SIZE);
    }

    private static void find(AlphabeticString[] getKeys, ChainingHashTable<AlphabeticString, AlphabeticString> CHT,
                               int NUM_WARMUP, int NUM_TESTS){
        long totalTimeCHT = 0;
        for (int j = 0; j < NUM_TESTS; j++) {
            long startCHT = System.nanoTime();
            for (int i = 0; i < INPUT_SIZE; i++) {
                CHT.find(getKeys[i]);
            }
            long finishCHT = System.nanoTime();
            if (NUM_WARMUP <= j) {
                totalTimeCHT += finishCHT - startCHT;
            }
        }
        long averageTimeCHT = totalTimeCHT / (NUM_TESTS - NUM_WARMUP);
        System.out.println("average runtime for find cht = " + averageTimeCHT + " with input size " + INPUT_SIZE);
    }

}
