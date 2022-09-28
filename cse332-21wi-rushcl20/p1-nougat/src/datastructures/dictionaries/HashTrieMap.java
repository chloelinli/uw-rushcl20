package datastructures.dictionaries;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import cse332.interfaces.misc.BString;
import cse332.interfaces.trie.TrieMap;

/**
 * See cse332/interfaces/trie/TrieMap.java
 * and cse332/interfaces/misc/Dictionary.java
 * for method specifications.
 */
public class HashTrieMap<A extends Comparable<A>, K extends BString<A>, V> extends TrieMap<A, K, V> {

    //public int size;

    public class HashTrieNode extends TrieNode<Map<A, HashTrieNode>, HashTrieNode> {
        public HashTrieNode() {
            this(null);
        }

        public HashTrieNode(V value) {
            this.pointers = new HashMap<>();
            this.value = value;
        }

        @Override
        public Iterator<Entry<A, HashTrieMap<A, K, V>.HashTrieNode>> iterator() {
            return pointers.entrySet().iterator();
        }

    }

    public HashTrieMap(Class<K> KClass) {
        super(KClass);
        this.root = new HashTrieNode();
        size = 0;
    }

    @Override
    public V insert(K key, V value) {
        if ((key == null) || (value == null)) {
            throw new IllegalArgumentException();
        }
        Iterator<A> it = key.iterator();
        HashTrieNode temp = (HashTrieNode) root;
        if (!it.hasNext()) {
            root.value = value;
        }
        while (it.hasNext()) {
            A cha = it.next();
            if(!temp.pointers.containsKey(cha)){
                temp.pointers.put(cha, new HashTrieNode());
            }
            temp = temp.pointers.get(cha);
        }
        V curVal = temp.value;
        temp.value = value;
        size++; //need?
        return curVal;
    }

    @Override
    public V find(K key) {
        if (key == null) {
            throw new IllegalArgumentException();
        }
        Iterator<A> it = key.iterator();
        HashTrieNode temp = (HashTrieNode) root;
        if (!it.hasNext()) {
            return root.value;
        }
        while (it.hasNext()) {
            A cha = it.next();
            if(temp.pointers.containsKey(cha)){
                temp = temp.pointers.get(cha);
            } else {
                return null;
            }
        }
        return temp.value;
    }

    @Override
    public boolean findPrefix(K key) {
        if (key == null) {
            throw new IllegalArgumentException();
        }
        Iterator<A> it = key.iterator();
        HashTrieNode temp = (HashTrieNode) root;
        if (!it.hasNext()) {
            return true;
        }
        while (it.hasNext()) {
            A cha = it.next();
            if(!temp.pointers.containsKey(cha)){
                return false;
            }
            temp = temp.pointers.get(cha);
        }
        return true;
    }

    @Override
    public void delete(K key) {
        if (key == null) {
            throw new IllegalArgumentException();
        }

        HashTrieNode temp = (HashTrieNode) root;
        Iterator<A> it = key.iterator();
        boolean isKey = true;

        while (it.hasNext()) {
            A next = it.next();
            if(!temp.pointers.containsKey(next)){
                isKey = false;
            } else {
                temp = temp.pointers.get(next);
            }
        }

        if(temp.value == null) {
            isKey = false;
        }

        if (isKey) {
            temp.value = null;
            size--;
        }

        if (temp.pointers.keySet().size() == 0 && isKey) {
            Iterator<A> test = key.iterator();
            help((HashTrieNode)root, test);
            if(root == null) {
                this.root = new HashTrieNode();
            }
        }
    }

    private boolean help(HashTrieNode node, Iterator<A> itr) {
        if (node.pointers.keySet().size() == 0) {
            return true;
        } else {
            A letter = itr.next();
            HashTrieNode second = node.pointers.get(letter);
            boolean test = help(second, itr);
            if (test) {
                node.pointers.remove(letter);
            }
        }
        if (node.value == null && node.pointers.keySet().size() < 1) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void clear() {
        this.root = new HashTrieNode();
        size = 0;
    }

    public int size(){
        return size;
    }
}
