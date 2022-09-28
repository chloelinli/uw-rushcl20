package datastructures.worklists;
import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.worklists.LIFOWorkList;

import java.util.NoSuchElementException;
import java.util.Stack;

/**
 * See cse332/interfaces/worklists/LIFOWorkList.java
 * for method specifications.
 */
public class ArrayStack<E> extends LIFOWorkList<E> {
    private E[] stack;
    private int topStack;

    public ArrayStack() {
        stack = (E[]) new Object[10]; //stack of size 10
        topStack = 0; //empty stack
    }

    //double the size of stack if it is full, add work to the top of stack, and increment the size of the stack.
    @Override
    public void add(E work) {
        if(topStack == stack.length - 1){
            E[] newStack = (E[]) new Object[stack.length * 2];
            for(int i = 1; i <= topStack; i++){
                newStack[i] = stack[i];
            }
            stack = newStack;
        }
        topStack++;
        stack[topStack] = work;
    }

    //if hasWork() is false throw new NoSuchElementException else return the next element in the stack.
    @Override
    public E peek() {
        if(!hasWork()){
            throw new NoSuchElementException("Stack is empty");
        }
        return stack[topStack];
    }

    /*if hasWork() is false throw new NoSuchElementException() else decrement the size of the stack and return
    the next element in the stack. */
    @Override
    public E next() {
        if(!hasWork()){
            throw new NoSuchElementException();
        }
        E nextElement = stack[topStack];
        topStack --;
        return nextElement;
    }

    //return the size of the stack.
    @Override
    public int size() {
        return topStack;
    }

    //empty the stack.
    @Override
    public void clear() {
        stack = (E[]) new Object[10];
        topStack = 0;
    }
}
