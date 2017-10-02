import java.io.*;
import java.util.*;
import org.junit.*;
//import java.nio.file.*;
import static org.junit.Assert.*;


public class MyUnitTests {
 

 static ByteArrayOutputStream localOut, localErr;
 static PrintStream sysOut, sysErr; 
 String newline = System.getProperty("line.separator");
 
 @BeforeClass
 public static void setup() throws Exception {
  sysOut = System.out;
  sysErr = System.err;
 }

 // Method for making testFile in the same place as this unit test file
 public static void createTestFiles(String filename, String parseString){
  try{
    PrintWriter out = new PrintWriter(filename);
    // to deal with compare test cases and print new line
    // instead of single line
    if(parseString.contains("\n")){
      Scanner writeSingleLine = new Scanner(parseString);
      while(writeSingleLine.hasNextLine()){
        String singleLine = writeSingleLine.nextLine();
        out.println(singleLine);
        out.flush();
      }

    }
    else{
      out.print(parseString);
    }

    out.close();
  }
  catch(Exception e){
    e.getMessage();
  }
 }
 @BeforeClass
 // make the test files with specific words into it
 public static void makeTestFiles(){
  createTestFiles("MadlibTest1.txt","Today I went to the <location> but encountered a <name> and it is > <noun> a chicken. Yet, <noun> <location> chicken is a great food. I think I should go do <type-of-work> and eat <type-of-food> .");
  createTestFiles("MadlibTest2.txt","This is a weird text. Where > > > > > <location> > > > <some-word> <noun> < < < < < . Even though it might looks fun but actually it is <agonizing-word> . But you will figure that it is actually not that <noun> .");
  createTestFiles("MadlibTest3.txt","You have noticed that we switched to a new <location> but this isn't easy as it is. <location> is a haunted place, where <name> destroyed it. But the people survived, with <action-verb> and they are <noun> . However, this isn't the end yet and only more <noun> people to come.");
  createTestFiles("MadlibTest4.txt","<noun> is <verb> <noun> but <animal> is <noun> at <location> <verb> <word-begin-with-c> .");
  createTestFiles("MadlibTest5.txt","<noun> <some-word> <some-more-word> <word-end-with-g> <fruit> .");
  createTestFiles("CompareTest1.txt","banana\nbanana\nbanana\nfingers\npeanuts\ndango");
  createTestFiles("CompareTest2.txt","peanuts\npeanuts\napple\ncinnamon\ndango\nfingers\nginger\nmango");
  createTestFiles("CompareTestEmptyLine.txt","a\na\na\n\nA\nc\nB\nC");  
 } 
 @Before
 public void setupStreams() {
  localOut = new ByteArrayOutputStream();
  localErr = new ByteArrayOutputStream();
  System.setOut(new PrintStream(localOut));
  System.setErr(new PrintStream(localErr));
 } 
 @After
 public void cleanUpStreams() {
  System.setOut(null);
  System.setErr(null);
  System.setOut(sysOut);
  System.setErr(sysErr);
 }
 // method to delete text files produced by earlier method for testing 
 public static void deleteFiles(String filename){
   try{
    File deleter = new File(filename);
    //boolean result = Files.deleteIfExists(deleter.toPath()); if import java.nio.file.* is allowed
    // safe check to delete an existing file
    if(deleter.exists()){
      deleter.delete();
    }
   }
   catch(Exception e){
     System.err.println(e);
   }
  }
 
 @AfterClass
 // delete the files after all tests run (comment out if you need to see the test files)
 public static void removeFiles(){
   String [] filesToDel = {"MadlibTest1.txt","MadlibTest2.txt","MadlibTest3.txt","MadlibTest4.txt","MadlibTest5.txt","CompareTest1.txt","CompareTest2.txt","CompareTestEmptyLine.txt"};
   for(int i = 0; i < filesToDel.length; i++){
     deleteFiles(filesToDel[i]);
   }
}
 

 // Test cases for Madlib
 @Test(timeout = 2000)
 // Test for if by changing the template, will the code
 // be able to count placeHolders correctly and replace
 // the input to the correct template
 public void testlib01() throws GameFileNotFoundException {
  Madlib game = new Madlib("./MadlibTest1.txt");
  assertEquals(7, game.getNumPlaceHolders());
  game.readInTemplate("./MadlibTest3.txt");
  assertEquals(6, game.getNumPlaceHolders());
  game.readInTemplate("./MadlibTest4.txt");
  assertEquals(8, game.getNumPlaceHolders());
  String [] inputString = {"Doctor","swim","nurse","cat","Dentist","Jamestown","eat","car"};
  String testMessage = "Doctor is swim nurse but cat is Dentist at Jamestown eat car .";
  assertEquals(testMessage, game.replace(inputString));
 }
 @Test(timeout = 2000)
 // Testing for shorted user input and also see if the program
 // can tell from real placeholder from just > and < 
 public void testlib02() throws GameFileNotFoundException {
   Madlib game = new Madlib("./MadlibTest2.txt");
   assertEquals(5, game.getNumPlaceHolders());
   // should leave 1 space empty
   String [] inputString = {"home","is","mom","frustrating"};
   String testMessage = "This is a weird text. Where > > > > > home > > > is mom < < < < < . Even though it might looks fun but actually it is frustrating . But you will figure that it is actually not that <noun> .";
   assertEquals(testMessage, game.replace(inputString));
   Madlib game2 = new Madlib("./MadlibTest5.txt");
   game2.promptUser();   
   String output = "Please enter 5 replacements:" + newline + 
   "1. noun" + newline + 
   "2. some word" + newline + 
   "3. some more word" + newline + 
   "4. word end with g" + newline + 
   "5. fruit" + newline;
   // see if promptUser output is correct
   assertEquals(output, localOut.toString());
   assertEquals(5, game2.getNumPlaceHolders());
   game.readInTemplate("./MadlibTest5.txt");
   assertEquals(5, game.getNumPlaceHolders());
 }
 // Test cases for Compare
 @Test(timeout = 2000)
 // test for if program can load file correctly into array
 // and compare between 2 files with file2 being the longer one
 public void testCompare01() throws FileNotFoundException{
  String filename1 = "CompareTest1.txt";
  String filename2 = "CompareTest2.txt";
  String [] file1Array = Compare.getFileIntoArray(filename1);
  String [] file1Expected = {"banana","banana","banana","fingers","peanuts","dango"};
  String [] file2Array = Compare.getFileIntoArray(filename2);
  String [] file2Expected = {"peanuts","peanuts","apple","cinnamon","dango","fingers","ginger","mango"};

  assertArrayEquals(file1Expected,file1Array);
  assertArrayEquals(file2Expected,file2Array);
  String result = Compare.getDiff(file1Array,file2Array);
  String expected = "\n> banana\n> banana\n> banana\n< peanuts\n< peanuts\n< apple\n< cinnamon\n< dango\n  fingers\n> peanuts\n> dango\n< ginger\n< mango";
  assertEquals(expected,result);
 }
 // testing for if program can identify this is a wrong location
 // and/or unexisting file and throw correct exception
 @Test(expected = FileNotFoundException.class,timeout = 2000)
 public void testCompare02() throws FileNotFoundException{
  String [] fileFail = Compare.getFileIntoArray("Failure");
 }
 
 // testing with space line to see if program can handle 
 // Cap and normal letters and compare them correctly with file1 being longer
 @Test(timeout = 2000)
 public void testCompare03() throws FileNotFoundException{
   String array1file = "CompareTestEmptyLine.txt";
   String [] array1 = {"a","a","a","","A","c","B","C"};
   assertArrayEquals(array1, Compare.getFileIntoArray(array1file));
   String [] array2 = {"c","c","d","D"};
   String result = Compare.getDiff(array1,array2);
   // String should produce just "\n " because it is treated as ""
   String expected = "\n> a\n> a\n> a\n> \n> A\n  c\n> B\n> C\n< c\n< d\n< D";
   assertEquals(expected,result);   
 }
 
 // test if program is able to compare empty line also and produce
 // right output with empty line included
 @Test(timeout = 2000)
 public void testCompare04(){
  String [] array1 = {"","","a","","","b","c","c"};
  String [] array2 = {"","a","","c","b","","","f","","c"};
  String result = Compare.getDiff(array1,array2);
  String expected = "\n  \n< a\n  \n> a\n< c\n< b\n  \n  \n> b\n< f\n< \n  c\n> c";
  assertEquals(expected,result);
 }
 /**
  *  Runs the test file
  */
 public static void main(String args[]) {
  org.junit.runner.JUnitCore.main("MyUnitTests");
 }
}