package datastructures.dictionaries;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import cse332.datastructures.containers.Item;
import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.misc.BString;
import cse332.interfaces.misc.Dictionary;
import cse332.interfaces.trie.TrieMap;

/**
 * See cse332/interfaces/trie/TrieMap.java
 * and cse332/interfaces/misc/Dictionary.java
 * for method specifications.
 */
public class HashTrieMap<A extends Comparable<A>, K extends BString<A>, V> extends TrieMap<A, K, V> {
    public class HashTrieNode extends TrieNode<Dictionary<A, HashTrieNode>, HashTrieNode> {
        public HashTrieNode() {
            this(null);
        }

        public HashTrieNode(V value) {
            this.pointers = new ChainingHashTable<>(MoveToFrontList::new);
            this.value = value;
        }

        @Override
        public Iterator<Entry<A, HashTrieNode>> iterator() {
            Iterator<Item<A, HashTrieNode>> CHTIterator = pointers.iterator();
            Iterator<Entry<A, HashTrieNode>> retIterator = new Iterator<Entry<A, HashTrieNode>>() {
                @Override
                public boolean hasNext() {
                    return CHTIterator.hasNext();
                }

                @Override
                public Entry<A, HashTrieNode> next() {
                    Item<A, HashTrieNode> item = CHTIterator.next();
                    Entry<A, HashTrieNode> entry = new AbstractMap.SimpleEntry<>(item.key, item.value);
                    return entry;
                }
            };
            return retIterator;
        }
    }


    public HashTrieMap(Class<K> KClass) {
        super(KClass);
        this.root = new HashTrieNode();
    }

    @Override
    public V insert(K key, V value) {
        HashTrieNode current = (HashTrieNode) this.root;
        if(key == null || value == null){
            throw new IllegalArgumentException();
        }
        for (A letter : key) {
            if (current.pointers.find(letter) == null) {
                HashTrieNode letterNode = new HashTrieNode();
                current.pointers.insert(letter, letterNode);
            }
            current = current.pointers.find(letter);
        }
        V oldValue = current.value;
        current.value = value;
        this.size++;
        return oldValue;
    }

    @Override
    public V find(K key) {
        HashTrieNode current = (HashTrieNode) this.root;
        if(key == null){
            throw new IllegalArgumentException("Key cannot be null.");
        }
        for (A letter : key) {
            if (current.pointers.find(letter) == null) {
                return null;
            }
            current = current.pointers.find(letter);
        }
        return current.value;
    }

    @Override
    public boolean findPrefix(K key) {
        HashTrieNode current = (HashTrieNode) this.root;
        if(key == null){
            throw new IllegalArgumentException("Key cannot be null.");
        }
        for (A letter : key) {
            if (current.pointers.find(letter) == null) {
                return false;
            } else {
                current = current.pointers.find(letter);
            }
        }
        return true;
    }

    @Override
    public void delete(K key) throws UnsupportedOperationException{
        HashTrieNode current = (HashTrieNode) this.root;
        HashTrieNode deleteBelow = current;
        A deleteLetter = null;
        boolean endOfKey = true;
        if(key == null){
            throw new IllegalArgumentException("Key cannot be null.");
        }

        Iterator<A> iterator = key.iterator();
        if(iterator.hasNext()){
            deleteLetter = iterator.next();
        }

        for (A letter : key) {
            if (current.pointers.find(letter)==null) {
                endOfKey = false;
                break;
            }
            if (current.value != null || current.pointers.size() > 1) {
                deleteBelow = current;
                deleteLetter = letter;
            }
            current = current.pointers.find(letter);
        }

        if(endOfKey){
            if(!(current.value == null)){
                size--;
                if(!current.pointers.isEmpty()){
                    current.value = null;
                } else {
                    current.value = null;
                    if(deleteLetter != null) {
                        deleteBelow.pointers.delete(deleteLetter);
                    }
                }
            }
        }
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException();
    }
}
