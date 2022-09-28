package datastructures.worklists;

import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.worklists.LIFOWorkList;

import java.util.NoSuchElementException;

/**
 * See cse332/interfaces/worklists/LIFOWorkList.java
 * for method specifications.
 */
public class ArrayStack<E> extends LIFOWorkList<E> {
    private static final int DEFSIZE = 10;
    int size;
    E[] as;
    int front;

    public ArrayStack() {
        clear();
    }

    @Override
    public void add(E work) {
        //add elements to array
        //resize if necessary (helper function?)
        if (size != 0 && size == as.length) { // resize
            addRoom();
        }
        size++;
        as[front] = work;
        front++;
    }

    @SuppressWarnings("unchecked")
    private void addRoom(){
        E[] update = (E[])new Object[2 * as.length];
        for(int i = 0; i < as.length; i++){
            update[i] = as[i];
        }
        as = update;
    }

    @Override
    public E peek() {
        //return element data at front
        if(size == 0){
            throw new NoSuchElementException();
        }
        return as[front - 1];
    }

    @Override
    public E next() {
        //return element and remove element
        if(size == 0){
            throw new NoSuchElementException();
        }
        front--;
        size--;
        return as[front];
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void clear() {
        as = (E[])new Object[DEFSIZE];
        size = 0;
        front = 0;
    }
}
