import java.io.*;
import java.util.*;
import org.junit.*;
import static org.junit.Assert.*;

public class DiffSampleTests {
	/**
	 *  Runs the test file
	 */
	public static void main(String args[]) {
		org.junit.runner.JUnitCore.main("DiffSampleTests");
	}
	
	/**
	 *  Input: two identical arrays
	 *  Action: get the difference between the two arrays
	 *  Output: a string showing they are the same (per formatting instructions)
	 */
	@Test
	public void compareSameArrays() {
		//set up scenario
		String[] array1 = {"a", "b", "c"};
		String[] array2 = {"a", "b", "c"};
		
		//perform some action
		String result = Compare.getDiff(array1, array2);
		
		//test the output
		assertEquals("\n  a\n  b\n  c", result);
	}
	
	/**
	 *  Input: two different arrays
	 *  Action: get the difference between the two arrays
	 *  Output: a string showing they are completely different
	 */
	@Test
	public void compareDiffArrays() {
		//set up scenario
		String[] array1 = {"a", "b"};
		String[] array2 = {"c", "d"};
		
		//perform some action
		String result = Compare.getDiff(array1, array2);
		
		//test the output
		assertEquals("\n> a\n> b\n< c\n< d", result);
	}
	
	/**
	 *  Input: A file with the numbers 0-6
	 *  Action: File contents are loaded into an array (one line per array element)
	 *  Output: A string array containing the numbers 0-6.
	 *  Assumes: the file "testfiles/list1.txt" exists and contains the numbers 0-6.
	 */
	@Test
	public void loadFromFile() throws FileNotFoundException {
		//set up scenario
		String filename = "testfiles/list1.txt";
		
		//perform some action
		String[] fileAsArray = Compare.getFileIntoArray(filename);
		
		//test the output
		String[] expectedArray = {"0", "1", "2", "3", "4", "5", "6"};
		assertArrayEquals(expectedArray, fileAsArray);
	}
}