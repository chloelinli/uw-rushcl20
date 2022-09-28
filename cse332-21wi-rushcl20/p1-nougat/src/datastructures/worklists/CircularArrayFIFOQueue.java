package datastructures.worklists;

import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.worklists.FixedSizeFIFOWorkList;

import java.util.NoSuchElementException;

/**
 * See cse332/interfaces/worklists/FixedSizeFIFOWorkList.java
 * for method specifications.
 */
public class CircularArrayFIFOQueue<E> extends FixedSizeFIFOWorkList<E> {
    E[] ca;
    int size;
    int front;
    int back;
    int cap;

    public CircularArrayFIFOQueue(int capacity) {
        super(capacity);
        cap = capacity;
        clear();
    }

    @Override
    public void add(E work) {
        if(size == cap){
            throw new IllegalStateException();
        } else {
            ca[back] = work;
            back = (back + 1) % cap;
            size++;
        }
    }

    @Override
    public E peek() {
        if(size == 0){
            throw new NoSuchElementException();
        }
        return ca[front];
    }
    
    @Override
    public E peek(int i) {
        if(size == 0 || ca[i] == null){
            throw new NoSuchElementException();
        }
        int temp = (front + i) % cap;
        return ca[temp];
    }
    
    @Override
    public E next() {
        if(size == 0){
            throw new NoSuchElementException();
        }
        E temp = ca[front];
        front = (front + 1) % cap;
        size--;
        return temp;
    }
    
    @Override
    public void update(int i, E value) {
        if(ca[front + i] == null){
            throw new NoSuchElementException();
        }
        ca[i] = value;
    }
    
    @Override
    public int size() {
        return size;
    }
    
    @Override
    public void clear() {
        ca =  (E[])new Comparable[cap];
        size = 0;
        front = 0;
        back = 0;
    }

    @Override
    public int compareTo(FixedSizeFIFOWorkList<E> other) {
        // You will implement this method in project 2. Leave this method unchanged for project 1.
        throw new NotYetImplementedException();
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean equals(Object obj) {
        // You will finish implementing this method in project 2. Leave this method unchanged for project 1.
        if (this == obj) {
            return true;
        }
        else if (!(obj instanceof FixedSizeFIFOWorkList<?>)) {
            return false;
        }
        else {
            // Uncomment the line below for p2 when you implement equals
            // FixedSizeFIFOWorkList<E> other = (FixedSizeFIFOWorkList<E>) obj;

            // Your code goes here

            throw new NotYetImplementedException();
        }
    }

    @Override
    public int hashCode() {
        // You will implement this method in project 2. Leave this method unchanged for project 1.
        throw new NotYetImplementedException();
    }
}
