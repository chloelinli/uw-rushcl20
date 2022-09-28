package datastructures.dictionaries;

import java.util.Iterator;
import java.util.NoSuchElementException;

import cse332.datastructures.containers.Item;
import cse332.datastructures.trees.BinarySearchTree;
import cse332.interfaces.misc.DeletelessDictionary;
import cse332.interfaces.misc.SimpleIterator;
import cse332.interfaces.worklists.WorkList;
import datastructures.worklists.ArrayStack;

/**
 * TODO: Replace this comment with your own as appropriate.
 * 1. The list is typically not sorted.
 * 2. Add new items to the front of the list.
 * 3. Whenever find is called on an item, move it to the front of the 
 *    list. This means you remove the node from its current position 
 *    and make it the first node in the list.
 * 4. You need to implement an iterator. The iterator SHOULD NOT move
 *    elements to the front.  The iterator should return elements in
 *    the order they are stored in the list, starting with the first
 *    element in the list. When implementing your iterator, you should 
 *    NOT copy every item to another dictionary/list and return that 
 *    dictionary/list's iterator. 
 */
public class MoveToFrontList<K, V> extends DeletelessDictionary<K, V> {


    Node head;

    public MoveToFrontList(){
        this.head = null;
        this.size = 0;
    }

    private class Node<K, V>{
        public K key;
        public V value;
        public Node next;
        public Node (K key, V value){
            this.key = key;
            this.value = value;
            this.next = null;
        }

    }
    @Override
    public V insert(K key, V value) {
        if(key == null || value == null){
            throw new IllegalArgumentException();
        }
        V oldVal = null;
        if(this.head == null){
            Node headNode = new Node(key, value);
            this.head = headNode;
            this.size++;
        }else if(head.key.equals(key)) {
            oldVal = (V) this.head.value;
            this.head.value = value;
        }else{
            Node currentNode = head.next;
            Node previousNode = head;
            while (currentNode != null && !currentNode.key.equals(key)) {
                previousNode = currentNode;
                currentNode = currentNode.next;
            }

            if (currentNode != null) {
                previousNode.next = currentNode.next;
                currentNode.next = head;
                head = currentNode;
                oldVal = (V) currentNode.value;
                currentNode.value = value;

            }else{
                Node newNode = new Node(key, value);
                newNode.next = this.head;
                this.head = newNode;
                this.size++;
            }
        }
        return oldVal;
    }

    @Override
    public V find(K key) {
       if(key == null){
            throw new IllegalArgumentException();
       }

       Node currentNode = head;
       Node previousNode = head;
       V val = null;

       while (currentNode != null && !currentNode.key.equals(key)) {
           previousNode = currentNode;
           currentNode = currentNode.next;
       }

       if (currentNode != null) {
           if(currentNode != head) {
               previousNode.next = currentNode.next;
               currentNode.next = head;
               head = currentNode;
           }
           val = (V) currentNode.value;
       }
       return val;
    }

    @Override
    public Iterator<Item<K, V>> iterator() {
        return new MoveToFrontList.KVIterator(this.head);
    }

    private class KVIterator extends SimpleIterator<Item<K, V>> {
        private Node currentNode;


        public KVIterator(Node head) {
            this.currentNode = head;
        }

        @Override
        public boolean hasNext() {
            return currentNode != null;
        }

        @Override
        public Item<K, V> next() {
            Item<K, V> returnItem = new Item<>((K) this.currentNode.key, (V) this.currentNode.value);
            if(returnItem == null){
                throw new NoSuchElementException();
            }
            currentNode = currentNode.next;
            return returnItem;
        }
    }
}
