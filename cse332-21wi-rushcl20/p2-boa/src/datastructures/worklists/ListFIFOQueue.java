package datastructures.worklists;

import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.worklists.FIFOWorkList;

import java.util.NoSuchElementException;

/**
 * See cse332/interfaces/worklists/FIFOWorkList.java
 * for method specifications.
 */
public class ListFIFOQueue<E> extends FIFOWorkList<E> {

    Node<E> frontRef;
    Node<E> backRef;
    int size;

    public ListFIFOQueue() {
        frontRef = null;
        backRef = null;
        size = 0;
    }

    @Override
    public void add(E work) {
        Node<E> addNode = new Node<>(work);
        if(frontRef == null){
            frontRef = addNode;
        } else {
            backRef.next = addNode;
        }
        backRef = addNode;
        size++;
    }

    @Override
    public E peek() {
        if(!hasWork()){
            throw new NoSuchElementException("Queue is empty.");
        }
        return frontRef.work;
    }

    @Override
    public E next() {
        if(!hasWork()){
            throw new NoSuchElementException("Queue is empty.");
        }
        Node<E> nextNode = frontRef;
        frontRef = frontRef.next;
        size--;
        return nextNode.work;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        frontRef = null;
        backRef = null;
        size = 0;
    }

    private class Node<E> {
        public E work;
        public Node<E> next;
        public Node(E work){
            this.work = work;
            this.next = null;
        }
    }
}
