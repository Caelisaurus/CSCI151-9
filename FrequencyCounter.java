import java.util.Iterator;
import java.util.Scanner;
/* 
 * @author cmassey2
 * 
 * FrequencyCounter takes an integer from the command line, as well as a string 
 * input from the user. The string may be either a regular ole' String or a text file name.
 * 
 * It then returns a list of the k-length substrings in the string/file along with their counts. */

public class FrequencyCounter {

    public static void main(String args[]) {
	int k = 0;
	MarkovModel markovMod = null;
	Scanner input = new Scanner(System.in);

	if (args.length < 1) {
	    System.out.println("Must enter an integer value.");
	} else {
	    // Gets k
	    try {
		k = Integer.parseInt(args[0]);
	    } catch (NumberFormatException n) {
		System.out.println("Starting parameter must be an integer.");
	    }

	    if (k != 0) {
		// Gets the string from user
		System.out.print("Enter a string: ");
		// reads it in to use in a file reader
		if (input.hasNextLine()) {
		    String string = input.nextLine();
		    // Creates a Markov Model for the string
		    markovMod = new MarkovModel(string, k);
		    // Don't want an empty string!!
		    if (string.equals("")) {
			System.out.println("Must enter a string!");
		    } else {
			// Prints the stuffff (/*_*)/
			Iterator<String> keys = markovMod.keys();
			System.out.println("" + markovMod.size() + " distinct keys");
			while (keys.hasNext()) {
			    String nextKey = keys.next();
			    System.out.println("" + markovMod.get(nextKey).size() + " " + nextKey
				    + markovMod.get(nextKey));
			}
		    }
		}
	    }

	    // (Closes the scanner so Eclipse stops nagging me
	    input.close();
	}
    }
}
