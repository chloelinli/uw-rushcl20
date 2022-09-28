package datastructures.worklists;

import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.worklists.FixedSizeFIFOWorkList;

import java.util.NoSuchElementException;

/**
 * See cse332/interfaces/worklists/FixedSizeFIFOWorkList.java
 * for method specifications.
 */
public class CircularArrayFIFOQueue<E extends Comparable<E>> extends FixedSizeFIFOWorkList<E> {
    E[] buffer;
    int frontIndex;
    int rearIndex;
    int numOfElements;

    public CircularArrayFIFOQueue(int capacity) {
        super(capacity);
        buffer = (E[])new Comparable[capacity];
        frontIndex = 0;
        rearIndex = 0;
        numOfElements = 0;
    }

    @Override
    public void add(E work) {
        if(isFull()){
            throw new IllegalStateException("Queue is full.");
        } else {
            buffer[rearIndex] = work;
            rearIndex = (rearIndex + 1) % buffer.length;
            numOfElements++;
        }
    }

    @Override
    public E peek() {
        if(!hasWork()){
            throw new NoSuchElementException("Queue is empty.");
        } else {
            return buffer[frontIndex];
        }

    }

    @Override
    public E peek(int i) {
        if(!hasWork()){
            throw new NoSuchElementException("Queue is empty.");
        } else if (i < 0 || i >= size()){
            throw new IndexOutOfBoundsException("Index out of bounds.");
        } else {
            i = (i + frontIndex) % buffer.length;
            return buffer[i];
        }
    }

    @Override
    public E next() {
        if(!hasWork()){
            throw new NoSuchElementException("Queue is empty.");
        }
        E nextElement = buffer[frontIndex];
        buffer[frontIndex] = null;
        frontIndex = (frontIndex + 1) % buffer.length;
        numOfElements--;
        return nextElement;
    }

    @Override
    public void update(int i, E value) {
        if(!hasWork()){
            throw new NoSuchElementException("Queue is empty.");
        } else if (i < 0 || i >= size()){
            throw new IndexOutOfBoundsException("Index out of bounds.");
        } else {
            buffer[i] = value;
        }
    }

    @Override
    public int size() {
        return numOfElements;
    }

    @Override
    public void clear() {
        int capacity = buffer.length;
        buffer = (E[]) new Comparable[capacity];
        frontIndex = 0;
        rearIndex = 0;
        numOfElements = 0;
    }

    @Override
    public int compareTo(FixedSizeFIFOWorkList<E> other) {
        int shortestLen = Math.min(this.size(), other.size());
        int ret = 0;
        for(int i = 0; i < shortestLen; i++){
            if(this.peek(i).compareTo(other.peek(i)) != 0) {
                return this.peek(i).compareTo(other.peek(i));
            } else{
                if(this.size() > other.size()){
                    ret =  1;
                }else if(this.size() < other.size()){
                    ret = -1;
                }else{
                    ret = 0;
                }
            }
        }
        return ret;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        else if (!(obj instanceof FixedSizeFIFOWorkList<?>)) {
            return false;
        }
        else {
             FixedSizeFIFOWorkList<E> other = (FixedSizeFIFOWorkList<E>) obj;
             if(this.size() != other.size()){
                 return false;
             } else{
                 int compare = this.compareTo(other);
                 return(compare == 0);
             }
        }
    }

    @Override
    public int hashCode() {
        int prime = 31;
        int result = 1;
        for(int i = 0; i < buffer.length; i++){
            result = prime * result + ((buffer[i] == null) ? 0 : buffer[i].hashCode());
        }
        return result;
    }
    //bad hashcode
//   public int hashCode(){
//        return 31;
//   }
}
