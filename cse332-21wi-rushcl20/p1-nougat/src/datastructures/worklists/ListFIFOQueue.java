package datastructures.worklists;

import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.worklists.FIFOWorkList;

import java.util.NoSuchElementException;

/**
 * See cse332/interfaces/worklists/FIFOWorkList.java
 * for method specifications.
 */
public class ListFIFOQueue<E> extends FIFOWorkList<E> {
    Node front;
    Node back;
    int size;

    public ListFIFOQueue() {
        // Initialize node values and size of list
        clear();
    }

    @Override
    public void add(E work) {
        // Initialize Node
        Node n = new Node(work);

        // If list is empty, front = back, else add to queue
        if (size == 0) {
            back = n;
            front = n;
            back = front;
        } else {
            back.next = n;
            back = back.next;
        }
        size++;
    }

    @Override
    public E peek() {
        if(size == 0){
            throw new NoSuchElementException();
        }
        return front.data;
    }

    @Override
    public E next() {
        if(size == 0){
            throw new NoSuchElementException();
        }
        E temp = front.data;
        front = front.next;
        size--;
        return temp;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        front = null;
        back = null;
        size = 0;
    }

    class Node {
        E data;
        Node next;

        Node(E d) {
            this.data = d;
        }
    }
}
