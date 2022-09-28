package datastructures.worklists;

import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.worklists.PriorityWorkList;


import java.util.NoSuchElementException;

/**
 * See cse332/interfaces/worklists/PriorityWorkList.java
 * for method specifications.
 */
public class MinFourHeapComparable<E extends Comparable<E>> extends PriorityWorkList<E> {
    /* Do not change the name of this field; the tests rely on it to work correctly. */
    private E[] data;
    private int size;


    public MinFourHeapComparable() {
        clear();
    }

    @Override
    public boolean hasWork() {
        if (size > 0) {
            return true;
        }
        return false;
    }

    @Override
    public void add(E work) {
        if (work == null) {
            throw new NoSuchElementException();
        }

        // resize heap if necessary, else add
        if ((size == data.length-1) ) {
            E[] resize = (E[]) new Comparable[2 * data.length];
            for (int i = 0; i <= size; i++) {
                resize[i] = data[i];
            }
            data = resize;
        }
        data[size] = work;
        int chiInd = size;
        size++;
        percolateUp(chiInd);
    }

    private void percolateUp(int childInd){
        int parentInd = (childInd - 1)/4;
        while(childInd > 0){
            if(data[parentInd].compareTo(data[childInd]) > 0){
                E temp = data[parentInd];
                data[parentInd] = data[childInd];
                data[childInd] = temp;
                childInd = parentInd;
                parentInd = (childInd - 1)/4;
            }else{
                childInd = 0;
            }
        }
    }

    @Override
    //return the first element
    public E peek() {
        if (!hasWork()){
            throw new NoSuchElementException();
        }
        return this.data[0];
    }

    @Override
    public E next() {
        int parInd;
        E temp = peek();
        size--;
        data[0] = data[size];
        parInd = 0;
        percolateDown(parInd);
        return temp;
    }

    private void percolateDown(int parInd) {
        int childInd = 4*parInd + 1;
        while(childInd < size){
            childInd = minChiInd(childInd);
            if(data[parInd].compareTo(data[childInd]) > 0){
                E temp = data[parInd];
                data[parInd] = data[childInd];
                data[childInd] = temp;
                parInd = childInd;
                childInd = 4 *parInd + 1;
            }else{
                childInd = size;
            }
        }
    }

    private int minChiInd(int chiInd) {
        int count = 1;
        int ind = chiInd;

        if (ind >= size) { //if it is larger than the array then we dont want to check
            return -1;
        }

        E temp = data[chiInd];
        chiInd++;
        E nextChi = data[chiInd];

        while ((count < 4) && (chiInd < size)) {
            if (temp.compareTo(nextChi) > 0) {
                temp = nextChi;
                ind = chiInd;
            }
            chiInd++;
            nextChi = data[chiInd];
            count++;
        }
        return ind;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public void clear() {
        this.data = (E[]) new Comparable[10];
        data[0] = null;
        this.size = 0;
    }
}
