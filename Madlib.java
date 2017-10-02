import java.io.FileNotFoundException;
import java.io.File;
import java.util.Scanner;

/**
 *  This class supports a Mad-lib like interactive game.
 */
public class Madlib {
 // field to store the string template from the input file
 // so other methods within Madlib can use it without the need
 // to reaccess the file
 public String message = "";
 
 /**
  *  Constructs a new game from a template contained in a file.
  *  
  *  @param fileName the name of the file containing the tempalte
  *  @throws GameFileNotFoundException if the file in question can not be located
  */
 public Madlib(String fileName) throws GameFileNotFoundException {
   try{
     // Scanner to read the file and add in the lines
     // template to message
     Scanner reader = new Scanner(new File(fileName));
     while(reader.hasNextLine()){
       String singleLine = reader.nextLine();
       message += singleLine;
     }
     reader.close();
   }
   // working with files, java wants it to be FileNotFoundException
   // so catch it (since method signature already throws GameFileNotFoundException)
   // and throw the custom exception so main can catch it and fix it
   catch(FileNotFoundException e){
     throw new GameFileNotFoundException();
   }
 }
 
 /**
  *  Retrieves the most number of tokens in the most recently read template.
  *  
  *  @return the number of tokens
  */
 public int getNumPlaceHolders() {
  int countPlace = 0;
  // Scanner to look through field message and 
  // see if a token contains both "<" and ">"
  // as indication for placeholder and add in to count
  Scanner numPlace = new Scanner(message);
  while(numPlace.hasNext()){
   String temp = numPlace.next();
   if(temp.contains("<") && temp.contains(">")){    
    countPlace ++;
   }
  }
  
  return countPlace;
 }
 
 /**
  *  Reads in a new template from a file.
  *  
  *  @param fileName the name of the file containing the tempalte
  *  @throws GameFileNotFoundException if the file in question can not be located
  */
 public void readInTemplate(String fileName) throws GameFileNotFoundException {
  // create a new separate object Madlib but update the message of this
  // Madlib to same as that new Madlib object (because we changing template)
  Madlib secondMadLib = new Madlib(fileName);
  message = secondMadLib.message;
 }
 
 /**
  *  Prompts the user for replacements to the template. This method only
  *  prints the prompt and does not actually get the user input.
  */
 public void promptUser() {
  System.out.println("Please enter " + getNumPlaceHolders() + " replacements:");
  int indexTracker = 0;
  // create a string [] that holds the correct key words in the 
  // place holders to display on screen (aka the word after all the
  // "<" and ">" and "-" have been replaced)
  String [] wordsToFill = new String [getNumPlaceHolders()];
  Scanner findHolder = new Scanner(message);
  while(findHolder.hasNext()){
   String temp = findHolder.next();
   // find where the placeholder is and make a 
   // temp string that is the string after all
   // replacement had been made and store
   // the ready to display word into wordsToFill
   if(temp.contains("<") && temp.contains(">")){
    // these inside statements replace all
    // the special characters that need to be replace    
    String temp2 = temp.replace("<", " ");
    temp2 = temp2.replace(">", " ");
    if(temp2.contains("-")){
     temp2 = temp2.replace("-", " ");
    }
    wordsToFill[indexTracker] = temp2;
    // move onto next index so to store next word
    indexTracker++;
   }
  }
  // set where to start the number on display as
  int listCountingNum = 1;
  // loop through the ready to display keywords and display
  // them on screen with corresponding number using listCountingNum
  for(int i = 0; i< wordsToFill.length; i++){
     // use .trim() to eliminate all the space from the "<" and ">" replacement
     String temp = wordsToFill[i].trim();
     System.out.println(String.format("%s. %s",listCountingNum,temp));
     listCountingNum++;
  }  
 }
 
 /**
  *  Gets the user input for replacements to the template
  *  
  *  @return an array containing each replacement to the template
  */
 public String[] getUserInput() {
  int lengthOfSArray = getNumPlaceHolders();
  // construct a string that store userInput to appropriate
  // number of required input
  String [] userInput = new String[lengthOfSArray];
  Scanner inputTaker = new Scanner(System.in);
  // use to check if user input the required
  // number of input or not
  // also act as an index mover
  int counter = 0;
  while(inputTaker.hasNextLine()){
   String temp = inputTaker.nextLine();
   userInput[counter] = temp;
   counter++;
   // only exit if user enter enough input
   // otherwise, keep forcing them to put inputs
   if(counter == lengthOfSArray){
    break;
   }
  }
  return userInput;
 }
 
 /**
  *  Replaces placeholders in the template. If there are not enough
  *  replacements, then the remaining tokens are displayed without
  *  being replaced. If there are too many tokens, then the extra
  *  replacements are ignored.
  *  
  *  @param replacements an array of replacements to the template
  *  @return the template with placeholders replaced
  */
 
 public String replace(String[] replacements) {
  // a new string to display the template + all the inputs
  // in the placeholders
  String message2 = "";
  Scanner messageReader = new Scanner(message);  
  int indexCounter = 0;
  while(messageReader.hasNext()){
   String temp = messageReader.next();
   // finding the placeholder and making sure the index is still within the array
   // to prevent out of bound
   if(temp.contains("<") && temp.contains(">") && indexCounter < replacements.length){
    // replace the placeholder with the user input from String [] and a space for
    // to separate the next word also move the index to next user input for next placeholder
    message2 += replacements[indexCounter] + " ";    
    indexCounter++;
   }
   // add in the normal word from template
   else{
    message2 += temp + " ";
   }
  }
  // use to delete the space at the end word because the loop above always add a space
  return message2.trim();
 }
 
 /**
  *  An example main method that runs a small game.
  *  
  *  @param args input to the program from the command line
  */
 public static void main(String[] args){
  // run the game and to catch all the irregular input
  // such as 0 args or more than 1 arg and give out correct error message
  try{
   if(args.length == 0){
    System.err.println("Usage:\njava Madlib templateFile");    
    System.exit(0);
   }
   else if(args.length != 1){
     System.err.println("Error: Could not find requested game file.");
     System.exit(0);
   }
   // use to display the game by making new Madlib object, asking for user input
   // and displayed the change they made
   Madlib game = new Madlib(args[0]);
   game.promptUser();
   String [] replacements = game.getUserInput();
   String result = game.replace(replacements);
   System.out.println("Result: " + result);
  }
  // catching the GameFileNotFound exception throw by constructor
  // and produce appropriate error message
  catch(GameFileNotFoundException e){
   System.err.println("Error: Could not find requested game file.");
  }  
 }
}