package datastructures.worklists;

import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.worklists.PriorityWorkList;

import java.util.Comparator;
import java.util.NoSuchElementException;

/**
 * See cse332/interfaces/worklists/PriorityWorkList.java
 * for method specifications.
 */
public class MinFourHeap<E> extends PriorityWorkList<E> {
    /* Do not change the name of this field; the tests rely on it to work correctly. */
    private E[] data;
    private int size;
    private Comparator<E> comparator;
    
    public MinFourHeap(Comparator<E> c) {
        this.comparator = c;
        clear();
    }

    @Override
    public void add(E work) {
        if(work == null){
            throw new IllegalArgumentException();
        }
        if ((size == data.length-1) ) {
            E[] c = (E[]) new Object[2 * data.length];
            for (int i = 0; i <= size; i++) {
                c[i] = data[i];
            }
            data = c;
        }
        data[size] = work;
        int chiInd = size;
        size++;
        percolateUp(chiInd);
    }

    private void percolateUp(int childInd){
        int parentInd = (childInd - 1)/4;
        while(childInd > 0){
            if(comparator.compare(data[parentInd], data[childInd])>0){
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
    public E peek() {
        if(!hasWork()){
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
            if(comparator.compare(data[parInd], data[childInd]) > 0){
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
            if (comparator.compare(temp, nextChi) > 0) {
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
        this.data = (E[]) new Object[10];
        data[0] = null;
        this.size = 0;
    }
}
