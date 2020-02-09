import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Test;

/* 
 * @author cmassey2
 * 
 * This is a test file for my MyHashMap class.*/
public class MyHashMapTest {

    @Test
    public void testMyHashMapIntFloat() {
	MyHashMap<String, Integer> test = new MyHashMap<String, Integer>(5, 0.8f);

	assertEquals("Size", test.size(), 0);
	assertEquals("Initial capacity of array", test.table.length, 5);
	assertTrue("Max load factor", test.MLF == 0.8f);
    }

    /* Tests the constructor that takes no arguments */
    @Test
    public void testMyHashMap() {
	MyHashMap<String, Integer> test = new MyHashMap<String, Integer>();

	assertEquals("Size", test.size(), 0);
	assertEquals("Initial capacity of array", test.table.length, 11);
	assertTrue("Max load factor", test.MLF == 0.75f);
    }

    /*
     * The next few methods test that put() works as expected, including for
     * attempts at inserting null values and cases that require resizing.
     */
    @Test
    public void testPut1() {
	MyHashMap<String, Integer> test = new MyHashMap<String, Integer>(4, 0.75f);

	test.put("one", 1);
	test.put("two", 2);
	test.put("three", 3);


	assertEquals("Should have resized!!", test.table.length, 11);
    }

    @Test
    public void testPut2() {
	MyHashMap<String, Integer> test = new MyHashMap<String, Integer>(4, 0.75f);

	test.put("one", 1);
	test.put("two", 2);
	test.put("three", 3);

	assertTrue("changing value of existing key", test.put("three", 33) == 3);
	assertTrue("New value in key 'three'", test.get("three") == 33);

    }

    @Test
    public void testPut3() {
	MyHashMap<String, Integer> test = new MyHashMap<String, Integer>(4, 0.75f);

	assertNull("adding new key shoud return null", test.put("one", 1));
	assertTrue("should return previous value", test.put("one", 11) == 1);
    }

    @Test(expected = NullPointerException.class)
    public void testPut4() {
	MyHashMap<String, Integer> test = new MyHashMap<String, Integer>(4, 0.75f);

	test.put(null, 3);
    }

    @Test(expected = NullPointerException.class)
    public void testPut5() {
	MyHashMap<String, Integer> test = new MyHashMap<String, Integer>(4, 0.75f);

	test.put("three", null);
    }

    /*
     * The next few tests make sure the get() method returns the appropriate values
     */
    @Test
    public void testGet1() {
	MyHashMap<String, Integer> test = new MyHashMap<String, Integer>(10, 0.75f);

	test.put("one", 1);
	test.put("two", 2);
	test.put("three", 3);

	assertEquals("Should be 1", test.get("one"), (Integer) 1);
	assertEquals("Should be 2", test.get("two"), (Integer) 2);
	assertEquals("Should be 3", test.get("three"), (Integer) 3);
	assertNull("Shouldn't return a value", test.get("four"));
    }


    // Tests that isEmpty() returns the correct boolean value
    @Test
    public void testIsEmpty() {
	MyHashMap<String, Integer> test = new MyHashMap<String, Integer>(10, 0.75f);

	assertTrue("Should begin  empty", test.isEmpty());

	test.put("one", 1);
	test.put("two", 2);
	test.put("three", 3);

	assertFalse("Shouldn't be empty!", test.isEmpty());

	test.clear();
	assertTrue("Now empty again", test.isEmpty());
    }


    // Tests that the hash map's entry count is kept track of/ returned correctly
    @Test
    public void testSize() {
	MyHashMap<String, Integer> test = new MyHashMap<String, Integer>(10, 0.75f);

	test.put("one", 1);
	test.put("two", 2);
	test.put("three", 3);

	assertEquals("Should have 3 elements", test.size(), 3);
	test.clear();
	assertEquals("Should have 0 elements", test.size(), 0);
    }

    /* Tests that the clear method performs as expected */

    @Test
    public void testClear() {
	MyHashMap<String, Integer> test = new MyHashMap<String, Integer>(10, 0.75f);

	test.put("one", 1);
	test.put("two", 2);
	test.put("three", 3);

	assertFalse("Shouldn't be empty!", test.isEmpty());

	test.clear();

	assertTrue("Should be!", test.isEmpty());
	assertNull("i.e. should not return any value for get()", test.get("one"));
    }

    /*
     * Tests the toString operator (I did this test visually rather than by
     * assert methods)
     */
    @Test
    public void testToString() {
	MyHashMap<String, Integer> test = new MyHashMap<String, Integer>(10, 0.75f);

	test.put("one", 1);
	test.put("two", 2);
	test.put("three", 3);

	System.out.println(test + "\n");
	Iterator<String> testIt = test.keys();

	while (testIt.hasNext()) {
	    System.out.println(testIt.next());
	}

    }

    /*
     * Tests that the keys() method returns an accurate iterator over the keys in
     * the table.
     */
    @Test
    public void testKeys() {
	MyHashMap<String, Integer> test = new MyHashMap<String, Integer>(10, 0.75f);

	test.put("one", 1);
	test.put("two", 2);
	test.put("three", 3);
	test.put("four", 4);
	test.put("five", 5);

	Iterator<String> testIt = test.keys();

	while (testIt.hasNext()) {
	    String nextKey = (String) testIt.next();
	    assertTrue("should have existing value, i.e. not return null", test.put(nextKey, 3) != null);
	}
    }

    /*
     * Tests that the entries() method returns an accurate iterator over the entries
     * in the table.
     */
    @Test
    public void testEntries() {
	MyHashMap<String, Integer> test = new MyHashMap<String, Integer>(10, 0.75f);

	test.put("one", 1);
	test.put("two", 2);
	test.put("three", 3);
	test.put("four", 4);
	test.put("five", 5);

	Iterator<Entry<String, Integer>> testIt = test.entries();

	while (testIt.hasNext()) {
	    Map.Entry<String, Integer> nextEntry = (Map.Entry<String, Integer>) testIt.next();

	    assertEquals("key/value pairs initerator vs hash map", test.get(nextEntry.getKey()), nextEntry.getValue());
	}
    }

}
