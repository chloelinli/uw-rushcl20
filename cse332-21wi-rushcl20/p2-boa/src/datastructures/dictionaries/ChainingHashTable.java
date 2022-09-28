package datastructures.dictionaries;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Supplier;

import cse332.datastructures.containers.Item;
import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.misc.DeletelessDictionary;
import cse332.interfaces.misc.Dictionary;
import cse332.interfaces.misc.SimpleIterator;

/**
 * TODO: Replace this comment with your own as appropriate.
 * 1. You must implement a generic chaining hashtable. You may not
 *    restrict the size of the input domain (i.e., it must accept 
 *    any key) or the number of inputs (i.e., it must grow as necessary).
 * 3. Your HashTable should rehash as appropriate (use load factor as
 *    shown in class!). 
 * 5. HashTable should be able to resize its capacity to prime numbers for more 
 *    than 200,000 elements. After more than 200,000 elements, it should 
 *    continue to resize using some other mechanism.
 * 6. We suggest you hard code some prime numbers. You can use this
 *    list: http://primes.utm.edu/lists/small/100000.txt 
 *    NOTE: Do NOT copy the whole list!
 * 7. When implementing your iterator, you should NOT copy every item to another
 *    dictionary/list and return that dictionary/list's iterator. 

 */
public class ChainingHashTable<K, V> extends DeletelessDictionary<K, V> {
    private static final int [] primeNumber = {5, 227, 487, 1051, 14887, 100003};
    private static final double loadFactor = 1.0;
    private final Supplier<Dictionary<K, V>> newChain;
    private Dictionary<K, V>[] array;
    private int sizeIndex;
    private int capacity;

    public ChainingHashTable(Supplier<Dictionary<K, V>> newChain) {
        this.capacity = primeNumber[0];
        this.sizeIndex = 0;
        this.newChain = newChain;
        array = new Dictionary[capacity];
    }

    @Override
    public V insert(K key, V value) {
        double temp = 0;
        int hashIndex;
        V tempVal;
        if (key == null || value == null) {
            throw new IllegalArgumentException();
        }
        temp = (double) this.size / array.length;

        if (temp > loadFactor) {
            if (sizeIndex == (primeNumber.length - 1)) {
                capacity = (2 * capacity) + 1;
            } else {
                sizeIndex++;
                capacity = primeNumber[sizeIndex];
            }
            rehash();
        }
        hashIndex = Math.abs(key.hashCode() % array.length);
        if (array[hashIndex] != null) {
            tempVal = array[hashIndex].find(key);
        } else {
            array[hashIndex] = newChain.get();
            tempVal = null;
        }
        if(tempVal == null){
            this.size++;
        }
        array[hashIndex].insert(key, value);
        return tempVal;
    }

    private void rehash() {
        Dictionary<K, V>[] rehash = new Dictionary[capacity];
        for (int i = 0; i < array.length; i++) {
            if (array[i] != null) {
                for (Item<K, V> item : array[i]) {
                    int hashVal = Math.abs(item.key.hashCode() % rehash.length);
                    if (rehash[hashVal] == null) {
                        rehash[hashVal] = newChain.get();
                    }
                    rehash[hashVal].insert(item.key, item.value);
                }
            }
        }
        array = rehash;
    }

    @Override
    public V find(K key) {
        int index = Math.abs(key.hashCode()%array.length);

        if(index >= 0){
            if(array[index] == null){
                array[index] = newChain.get();
                return null;
            }
            return array[index].find(key);
        }else {
            return null;
        }
    }

    @Override
    public Iterator<Item<K, V>> iterator() {
        return new ChainingHashTable.CHTIterator(this.array);
    }

    private class CHTIterator extends SimpleIterator<Item<K, V>> {
        private Iterator<Item<K,V>> currIterator;
        private int currHashInd;
        private Dictionary<K, V>[] hash;

        public CHTIterator(Dictionary<K, V>[] array) {
            currIterator = null;
            currHashInd = 0;
            hash = array;
        }
        @Override
        public boolean hasNext() {
            for(;currHashInd < hash.length; currHashInd++){
                if(currIterator == null && hash[currHashInd] != null ){
                    currIterator = hash[currHashInd].iterator();
                }

                if(currIterator != null){
                    if(currIterator.hasNext()){
                        return true;
                    }else{
                        currIterator = null;
                    }
                }
            }
            return false;
        }

        @Override
        public Item<K, V> next() {
            if(!hasNext()){
                throw new NoSuchElementException();
            }else{
                return currIterator.next();
            }
        }
    }
}