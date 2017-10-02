import java.io.*;
import java.util.*;
import org.junit.*;
import static org.junit.Assert.*;

public class MadlibSampleTests {
	static ByteArrayOutputStream localOut, localErr;
	static PrintStream sysOut, sysErr;
	static String[] empty = {};
	
	// Determine what the newline is on the running system
	String newline = System.getProperty("line.separator");

	//set the print streams we will be using
	@BeforeClass
	public static void setup() throws Exception {
		sysOut = System.out;
		sysErr = System.err;
	}

	//before every test is run, reset the streams to capture stdout/stderr
	@Before
	public void setupStreams() {
		localOut = new ByteArrayOutputStream();
		localErr = new ByteArrayOutputStream();
		System.setOut(new PrintStream(localOut));
		System.setErr(new PrintStream(localErr));
	}

	//after every test, restore stdout/stderr
	@After
	public void cleanUpStreams() {
		System.setOut(null);
		System.setErr(null);
		System.setOut(sysOut);
		System.setErr(sysErr);
	}

	//make sure reading in a template sets the number of placeholders
	@Test(timeout=2000)
	public void game01() throws GameFileNotFoundException {
		Madlib game = new Madlib("./madlibs/example1in.txt");
		assertEquals(5, game.getNumPlaceHolders());
	}

	//make sure GameFileNotFoundException is thrown for constructor
	@Test(expected=GameFileNotFoundException.class,timeout=2000)
	public void game02() throws GameFileNotFoundException {
		Madlib game = new Madlib("banana"); //assumes file "banana" doesn't exist
	}

	//make sure reading in a new template sets the number of placeholders
	@Test(timeout=2000)
	public void game03() throws GameFileNotFoundException {
		Madlib game = new Madlib("./madlibs/example1in.txt");
		game.readInTemplate("./madlibs/example3in.txt");
		assertEquals(3, game.getNumPlaceHolders());
	}

	//make sure GameFileNotFoundException is thrown for reading a new template
	@Test(expected=GameFileNotFoundException.class,timeout=2000)
	public void game04() throws GameFileNotFoundException {
		Madlib game = new Madlib("./madlibs/example1in.txt");
		game.readInTemplate("banana"); //assumes file "banana" doesn't exist
	}

	//make sure prompting the user works
	@Test(timeout=2000)
	public void game05() throws GameFileNotFoundException {
		Madlib game = new Madlib("./madlibs/example1in.txt");
		game.promptUser();
		String output = "Please enter 5 replacements:" + newline + 
			"1. location" + newline + 
			"2. plural noun" + newline + 
			"3. location" + newline + 
			"4. adjective" + newline + 
			"5. location" + newline;
		assertEquals(output, localOut.toString());
	}
	
	//run the test file
	public static void main(String args[]) {
		org.junit.runner.JUnitCore.main("MadlibSampleTests");
	}
}