import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

/* 
 * @author cmassey2
 * 
 * This program uses a hash map (using my MyHashMap class) to create a Markov model
 * for a given text file. It contains some of the same methods as MyHashMap because I 
 * found them useful in writing other parts of the lab. :)*/
public class MarkovModel extends MyHashMap<String, ArrayList<String>> {

    MyHashMap<String, ArrayList<String>> markovMod; // Holds the k-length strings and possible successors
    String file; // The name of the file (or string in some cases) to be "Markoved"
    int order; // Parameter determining the k-order of the Markov model.
    int nextChar; // The next character in the file
    Queue<String> nextKChars = new LinkedList<String>(); // Keeps track of the current k-length string
    FileReader fileRead = null; // Reads in the given file name
    Random rand = new Random(); // Produces a random number for use in getNextChar()
    StringReader stringRead = null;

    MarkovModel(String fileName, int order) {
	this.file = fileName;
	this.order = order;
	markovMod = new MyHashMap<String, ArrayList<String>>();

	// Reads in the file/string in character by character
	try {
	    fileRead = new FileReader(this.file);
	} catch (FileNotFoundException e1) {
	    /* If a file of the given name cannot be located, it will read the name as a string. :) 
	     * This is mainly for use in FrequencyCount, which the lab says should take a string (NOT
	     * a file name) from the user. */
	    stringRead = new StringReader(this.file);
	}

	/*
	 * Uses a queue to add each k-length string within user file to the hash map
	 * (Markov model).
	 */
	try {
	    String nextString;

	    while ((fileRead != null && (nextChar = fileRead.read()) != -1) || (stringRead != null && (nextChar = stringRead.read()) != -1)) {
		// The next character (plus a string version)
		char c = (char) nextChar;
		String stringC = Character.toString(c);

		// If there are k characters in the queue,time to add a new string to the hash
		// map!
		if (nextKChars.size() == order) {
		    nextString = "";

		    // Creates the new string
		    for (String next : nextKChars) {
			nextString += next;
		    }

		    // Either adds the string to the hash map or adds to n existing entry
		    if (markovMod.get(nextString) == null && !stringC.equals("")) {
			markovMod.put(nextString, new ArrayList<String>());
			markovMod.get(nextString).add(stringC);
		    } else if (!stringC.equals("")) {
			markovMod.get(nextString).add(stringC);
			// System.out.println(nextString);
		    }
		    // Removes the first character from the queue (not needed once counted)
		    nextKChars.remove();
		}

		// Enqueues newest character
		nextKChars.add(stringC);
		}

	    /*
	     * The rest of the try block accounts for the very last k-length substring of a
	     * string in FrequencyCount. My program didn't count it the way it does for a
	     * file for some reason?
	     */
	    if (stringRead != null) {
		nextString = "";
		for (String next : nextKChars) {
		    nextString += next;
		}
		if (markovMod.get(nextString) == null) {
		    markovMod.put(nextString, new ArrayList<String>());
		}
	    }
	     

	} catch (IOException e) {
	    System.err.println("Error reading from " + this.file + ": " + e.getMessage());
	    System.exit(4);
	    }
	}

    // Returns the list of possible successors for given k-length string in the
    // model.
    public ArrayList<String> get(String key) {
	return markovMod.get(key);
    }

    /*
     * Creates an iterator to iterate over all of the k-length strings in the model.
     */
    public Iterator<String> keys() {
	return markovMod.keys();
    }

    // returns the number of k-length strings in the model.
    public int size() {
	return markovMod.size;
    }

    // returns a random next character based on possible successors for the given
    // string.
    public String getNextChar(String lastK) {
	if (this.markovMod.get(lastK) != null) {
	    ArrayList<String> roullette = markovMod.get(lastK);
	    int randomIdx = rand.nextInt(roullette.size());

	    return roullette.get(randomIdx);
	} else {
	    return "";
	}
    }
    }

