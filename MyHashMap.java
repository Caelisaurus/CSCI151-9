import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

/* 
 * @author cmassey2
 * 
 * MyHashMap is an implementation of the hash map data type. It is implemented in an array of linked
 * lists and uses chaining to avoid collisions.*/
public class MyHashMap<K, V> implements MapADT<K, V> {

    int size; // The num of elements in the table
    double MLF; // Max load factor, for resizing
    LinkedList<Map.Entry<K, V>>[] table; // Holds key/value pairs inserted in table.

    @SuppressWarnings("unchecked")
    MyHashMap(int capacity, float maxLoadFactor) {
	this.table = (LinkedList<Map.Entry<K, V>>[]) new LinkedList[capacity];
	this.MLF = maxLoadFactor;
	this.size = 0;

	for (int i = 0; i < capacity; i++) {
	    table[i] = new LinkedList<Map.Entry<K, V>>();
	}
    }

    /*
     * A constructor that requires no arguments; calls the second constructor with
     * "default" settings.
     */
    MyHashMap() {
	this(11, 0.75f);
    }

    /*
     * Returns an integer corresponding to where the key should be places in the
     * hash map
     */
    private int hash(K key) {
	return Math.abs(key.hashCode() % table.length);
    }

    /*
     * A private helper method that tests the load factor of the hash map to see
     * ifit's in need of resizing.
     */
    private void LFCheck() {
	double loadFactor = (double) (this.size) / (double) (this.table.length);
	// System.out.println(loadFactor);
	if (loadFactor >= this.MLF) {
	    this.resize();
	}
    }

    /* Creates a new hash map of at least double size */
    @SuppressWarnings("unchecked")
    private void resize() {

	int newCapacity = nextPrime(this.table.length);
	LinkedList<Map.Entry<K, V>>[] prevTable = this.table;
	this.table = (LinkedList<Map.Entry<K, V>>[]) new LinkedList[newCapacity];

	for (int k = 0; k < newCapacity; k++) {
	    this.table[k] = new LinkedList<Map.Entry<K, V>>();
	}

	for (int i = 0; i < prevTable.length; i++) {
	    LinkedList<Map.Entry<K, V>> currentChain = prevTable[i];
	    for (int j = 0; j < currentChain.size(); j++) {
		K nextKey = currentChain.get(j).getKey();
		// System.out.println(hash(nextKey));
		table[hash(nextKey)].add(currentChain.get(j));
	    }
	}
    }

    /*
     * This private helper method returns a prime that is larger than the hash map's
     * current capacity so that resize always sets the new table size to a prime
     * number.
     */
    private int nextPrime(int i) {

	List<Integer> primes = new ArrayList<Integer>(Arrays.asList(11, 23, 47, 97, 197, 397, 797, 1597, 3203, 6421,
		12853, 25717, 51437, 102877, 205759, 411527, 823117, 1646237, 3292489, 6584983, 13169977, 26339969,
		52679969, 105359939, 210719881, 421439783, 842879579, 1685759167));

	for (int prime : primes) {
	    if (prime > 2 * this.table.length) {
		// System.out.println(prime);
		return prime;
	    }
	}

	// Since the list has some suuuuper big nums, I don't see this ever actually
	// executing!!
	return 1;
    }

    /* Adds a (key, value) pair in the correct index of the hash table */
    @Override
    public V put(K key, V value) {
	// System.out.println("\nCalled put()\n");
	int hashIndex = hash(key);
	LinkedList<Map.Entry<K, V>> insertChain = table[hashIndex];

	if (key == null || value == null) {
	    throw (new NullPointerException("ERROR: Cannot insert null keys or values."));
	} else {

	    // SHould've used iterator here!! >:O
	    for (int i = 0; i < insertChain.size(); i++) {
		if (insertChain.get(i).getKey().equals(key)) {
		    V toReturn = insertChain.get(i).getValue();
		    insertChain.get(i).setValue(value);
		    return toReturn;
		}
	    }

	    insertChain.add(new AbstractMap.SimpleEntry<K, V>(key, value));
	    this.size += 1;
	    // System.out.println(this);
	    LFCheck();
	    return null;
	}
    }

    /*
     * Returns the value associated with the given key in the hash table; if there
     * is no value matching the key, returns null.
     */
    @Override
    public V get(K key) {
	LinkedList<Map.Entry<K, V>> chain = table[this.hash(key)];
	for (int i = 0; i < chain.size(); i++) {
	    if (chain.get(i).getKey().equals(key)) {
		return chain.get(i).getValue();
	    }
	}

	return null;
    }

    /* Returns true if the hash map is empty, false otherwise. */
    public boolean isEmpty() {
	return this.size == 0;
    }

    /*
     * Returns the number of (key, value) pairs currently being stored in the table.
     */
    public int size() {
	return this.size;
    }

    /* Clears the hash map of all existing entries. */
    public void clear() {
	for (int i = 0; i < table.length; i++) {
	    this.table[i] = new LinkedList<Map.Entry<K, V>>();
	}
	this.size = 0;
    }

    /* Returns a string representation of the hash map */
    public String toString() {
	String toReturn = "";
	for (int i = 0; i < this.table.length; i++) {
	    LinkedList<Map.Entry<K, V>> currentChain = this.table[i];

	    if (!currentChain.toString().equals("[]"))
	    {
		toReturn += ("\n" + "Index " + i + " : " + currentChain.toString());
	    }

	}

	return toReturn;
    }

    /*
     * Creates an iterator to iterate over all of the keys stored in the hash map.
     */
    @Override
    public Iterator<K> keys() {
	LinkedList<K> keys = new LinkedList<K>();

	for (int i = 0; i < table.length; i++) {
	    LinkedList<Map.Entry<K, V>> ithChain = table[i];

	    for (int j = 0; j < ithChain.size(); j++) {
		keys.add(ithChain.get(j).getKey());
	    }
	}

	ListIterator<K> toReturn = keys.listIterator();
	return toReturn;
    }

    /*
     * Similar to keys(), returns an iterator that iterates over all of the (key,
     * value) pairs entered in the table
     */
    @Override
    public Iterator<Map.Entry<K, V>> entries() {
	LinkedList<Map.Entry<K, V>> entries = new LinkedList<Map.Entry<K, V>>();

	for (int i = 0; i < table.length; i++) {
	    LinkedList<Map.Entry<K, V>> ithChain = table[i];

	    for (int j = 0; j < ithChain.size(); j++) {
		entries.add(ithChain.get(j));
	    }
	}

	ListIterator<Map.Entry<K, V>> toReturn = entries.listIterator();
	return toReturn;
    }
}
