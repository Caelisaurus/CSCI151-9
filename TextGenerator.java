import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/* 
 * @author cmassey2
 * 
 * TextGenerator takes two integers, k and M, and a text file name from the command line
 * and provides a randomly generated text of M characters using an order k Markov Model
 * of the file! */
public class TextGenerator {

    public static void main(String[] args) throws FileNotFoundException {

	int k = 0;
	int M = 0;
	String fileName = "";
	MarkovModel markovMod = null;
	String lastK = "";

	if(args.length < 3) {
	    System.out.println("Please enter the k-order of the Markov model, the "
		    + "number of characters to print, and a 'trainer' file name.");
	} else {
	    int nextChar;
	    FileReader fileRead = null;
	    // Gets k and M
	    try {
		k = Integer.parseInt(args[0]);

		// So program will print a total of m letters including first k.
		M = Integer.parseInt(args[1]) - k;
		// System.out.println(k);
	    } catch (NumberFormatException n) {
		System.out.println("First two parameters must be integers.");
		System.exit(4);
	    }

	    // Gets the file name to be used in the Markov Model.
	    if (args[2] instanceof String) {
		fileName = args[2];
		markovMod = new MarkovModel(fileName, k);
	    } else {
		System.out.println("Third argument must be a file name!");
		System.exit(4);
	    }

	    // Creates a file reader to get the first k characters of the file.
	    try {
		fileRead = new FileReader(fileName);
	    } catch (FileNotFoundException e) {
		System.out.println("Invalid file.");
		System.exit(4);
	    }

	    int charCount = 0;
	    String firstK = "";
	    try {
		while ((nextChar = fileRead.read()) != -1 && charCount < k) {
		    // The next character (and string version)
		    char c = (char) nextChar;
		    String stringC = Character.toString(c);

		    firstK += stringC;
		    charCount += 1;
		}
	    } catch (IOException e) {
		System.out.println("An error occurred.");
		System.exit(4);
	}

	    System.out.print(firstK);

	    // Prints the text generation!! :)
	    lastK = firstK; // .toLowerCase()?
	    int i = 0;
	    while (i < M) {
		String nextLet = markovMod.getNextChar(lastK);
		System.out.print(nextLet);
		i += 1;

		if (!nextLet.equals("")) {
		    lastK = lastK.substring(1) + nextLet; // .toLowerCase()?
		} else {
		    System.out.print("\n");
		    System.exit(0);
		}

	    }

	    System.out.print("\n");
	}
    }
}
