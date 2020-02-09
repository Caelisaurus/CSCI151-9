import java.util.Iterator;

/* 
 * @author cmassey2
 * 
 * This is a very short test file for my MarkovModel class. */
import org.junit.Test;

public class MarkovModelTest {

    @Test
    public void testMarkovModel() {
	MarkovModel testMark = new MarkovModel("myTestFile.txt", 2);

	// System.out.println(testMark.size());
    }

    @Test
    public void testKeys() {
	MarkovModel testMark = new MarkovModel("myTestFile.txt", 2);
	Iterator<String> keys = testMark.keys();

	while (keys.hasNext()) {
	    String nextEntry = keys.next();
	    // System.out.println(nextEntry + testMark.get(nextEntry));
	}
    }

    @Test
    public void testGetNextChar() {
	MarkovModel testMark = new MarkovModel("myTestFile.txt", 2);
	
	for (int i = 0; i < 10; i++) {
	    System.out.println(testMark.getNextChar("gg"));
	}

	// (should print nothing after heyo)
	// System.out.println("Heyo" + testMark.getNextChar("ab"));
    }
}

