package hashmap;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;


/**
 *  A hash table-backed Map implementation. Provides amortized constant time
 *  access to elements via get(), remove(), and put() in the best case.
 *
 *  Assumes null keys will never be inserted, and does not resize down upon remove().
 *  @author YOUR NAME HERE
 */
public class MyHashMap<K, V> implements Map61B<K, V> {

    /**
     * Protected helper class to store key/value pairs
     * The protected qualifier allows subclass access
     */
    protected class Node {
        K key;
        V value;

        Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    /* Instance Variables */
    private Collection<Node>[] buckets;
    private double loadFactor;
    private int capacity;
    private int size;
    // You should probably define some more!

    /** Constructors */
    public MyHashMap() {
        this(16, 0.75);
    }

    public MyHashMap(int initialCapacity) {
        this(initialCapacity, 0.75);
    }

    /**
     * MyHashMap constructor that creates a backing array of initialCapacity.
     * The load factor (# items / # buckets) should always be <= loadFactor
     *
     * @param initialCapacity initial size of backing array
     * @param loadFactor maximum load factor
     */
    public MyHashMap(int initialCapacity, double loadFactor) {
        this.capacity = initialCapacity;
        this.loadFactor = loadFactor;
        this.buckets = createTable(initialCapacity);
        size = 0;
    }

    /**
     * Returns a new node to be placed in a hash table bucket
     */
    private Node createNode(K key, V value) {
        return new Node(key, value);
    }

    /**
     * Returns a data structure to be a hash table bucket
     *
     * The only requirements of a hash table bucket are that we can:
     *  1. Insert items (`add` method)
     *  2. Remove items (`remove` method)
     *  3. Iterate through items (`iterator` method)
     *
     * Each of these methods is supported by java.util.Collection,
     * Most data structures in Java inherit from Collection, so we
     * can use almost any data structure as our buckets.
     *
     * Override this method to use different data structures as
     * the underlying bucket type
     *
     * BE SURE TO CALL THIS FACTORY METHOD INSTEAD OF CREATING YOUR
     * OWN BUCKET DATA STRUCTURES WITH THE NEW OPERATOR!
     */
    protected Collection<Node> createBucket() {
        return new LinkedList<>();
    }

    /**
     * Returns a table to back our hash table. As per the comment
     * above, this table can be an array of Collection objects
     *
     * BE SURE TO CALL THIS FACTORY METHOD WHEN CREATING A TABLE SO
     * THAT ALL BUCKET TYPES ARE OF JAVA.UTIL.COLLECTION
     *
     * @param tableSize the size of the table to create
     */
    private Collection<Node>[] createTable(int tableSize) {
        return new Collection[tableSize];
    }

    private Node getNode(K key) {
        int h = key.hashCode();
        int index = Math.floorMod(h, capacity);
        if (buckets[index] == null) return null;
        for (Node n : buckets[index]) {
            if (n.key.equals(key)) {
                return n;
            }
        }
        return null;
    }

    @Override
    public void put(K key, V value) {
        Node n = getNode(key);
        if (n == null) {
            size++;
            put(buckets, capacity,key, value);
        } else {
            n.value = value;
        }
    }

    private void put(Collection<Node>[] buckets, int capacity, K key, V value) {
        int h = key.hashCode();
        int index = Math.floorMod(h, capacity);
        if (buckets[index] == null) {
            buckets[index] = createBucket();
        }
        buckets[index].add(createNode(key, value));
        if ((double) size / capacity > loadFactor) {
            resize();
        }
    }

    private void resize() {
        int newCapacity = 2 * capacity;
        Collection<Node>[] newBuckets = createTable(newCapacity);
        for (Collection<Node> b : buckets) {
            if (b != null) {
                for (Node n : b) {
                    put(newBuckets, newCapacity,n.key, n.value);
                }
            }
        }
        capacity = newCapacity;
        buckets = newBuckets;
    }
    @Override
    public V get(K key) {
        Node n = getNode(key);
        if (n == null) return null;
        else return n.value;
    }

    @Override
    public boolean containsKey(K key) {
        Node n = getNode(key);
        if (n == null) return false;
        else return true;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        buckets = createTable(capacity);
        size = 0;
    }

    @Override
    public Set<K> keySet() {
        throw new UnsupportedOperationException();
    }

    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<K> iterator() {
        throw new UnsupportedOperationException();
    }
    // TODO: Implement the methods of the Map61B Interface below
    // Your code won't compile until you do so!

}
