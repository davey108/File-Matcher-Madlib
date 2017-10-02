import java.io.FileNotFoundException;
import java.io.File;
import java.util.Scanner;

class Compare {
 /**
  *  Main method. Takes two file names as command line arguments, and prints the diff.
  */
    public static void main(String[] args) {
      // catch 2 cases, if wrong file or not sufficient input and print out appropriate
      // messages for each cases
      try{
        if(args.length != 2){
          System.out.println("Two file arguments required. E.g. java Compare file1.txt file2.txt");
        }
        else{
          String [] file1 = getFileIntoArray(args[0]);
          String [] file2 = getFileIntoArray(args[1]);
          String output = getDiff(file1,file2);
          System.out.println(output);
        }
      }
      catch(FileNotFoundException e){
        System.out.println("Could not open one or more files!");
      }  
    }
 
 /**
  *  Loads a file into an array.
  *  
  *  @param filename the file to load into the array
  *  @return the file loaded into an array (one line per element of the array)
  *  @throws FileNotFoundException if the file requested can not be found
  */
 public static String[] getFileIntoArray(String filename) throws FileNotFoundException {
  int countLine = 0;
  // read number of lines to construct an accurate array
  // that can hold all the elements
  Scanner lineCounter = new Scanner(new File(filename));
  while(lineCounter.hasNextLine()){
   lineCounter.nextLine();
   countLine ++;
  }
  lineCounter.close();  
  String [] wordsArray = new String[countLine];
  // set an index to add the first line of the file
  // onto the String array
  int arrayIndex = 0;
  // add each line into the array and move on to next spot 
  // on the array for next line
  Scanner lineReader = new Scanner(new File(filename));
  while(lineReader.hasNextLine()){
   String temp = lineReader.nextLine();   
   wordsArray[arrayIndex] = temp;
   arrayIndex ++;
  }
  lineReader.close();
  return wordsArray;
 }
 
 /**
  *  Returns the diff, as a string, between two files.
  *  
  *  @param file1 one of the two files to be compared, loaded line by line into a string array
  *  @param file2 the other of the two files to be compared, loaded line by line into a string array
  *  @return the "diff" output between the two files, indented to look nice
  */
  public static String getDiff(String[] file1, String[] file2){
  // make a hypothetical large array to store the difference value in between 
  // 2 matching values of both files but different indexes
  // Ex: file1 = {"a","b","c","d"}
  //     file2 = {"b","c","a"}
  // then the storeArray will hold b and c to be release later as
  // "< b" and "< c"
  String[] storeArray = new String[40];
  // construct a new string to store output 
  StringBuffer resultOutput = new StringBuffer();
  int storeArrayCounter = 0;
  int counter = 0;  
  int i = 0;
  try{    
    for(i = 0; i< file1.length; i++){
      // check for if element is alike, then record it as "\n +the element+"
      // and move onto the next index to check
      if(file1[i].equals(file2[counter])){      
        resultOutput.append(String.format("\n  %s",file1[i]));        
        counter ++;
      }
      // case for if the 2 are not alike
      else{
        // a boolean to keep track for the case if the item in file1
        // doesn't exist in file2
        boolean found = true;
        // add the different element of file2 into the array
        // and move onto next index if there are more difference
        // then the array can enter it in the next place
        storeArray[storeArrayCounter] = file2[counter];        
        storeArrayCounter++;
        // keep checking in file2 to see if the item from file1
        // can be find anywhere else from its position to end of file2
        // j = counter+1 because we want to check next spot, it is 
        // repetitive to check the same spot again when already did it
        for(int j = counter+1; j <file2.length; j++){
          // if it finds a match at different later index in file2,
          // then add all the item difference found earlier from
          // initial spot of the item in file1 to the matching item
          // spot in file2 in the storeArray 
          if(file1[i].equals(file2[j])){
            for(int store = 0; store < storeArray.length; store++){
              if(storeArray[store] == null){
                break;
              }
              else{
                resultOutput.append(String.format("\n< %s",storeArray[store]));
              }
            }
            // after appending all the difference from storeArray, then write
            // the item onto the string
            resultOutput.append(String.format("\n  %s",file1[i]));
            // reconstruct the storeArray to fresh because we need a fresh
            // array should another situation appear and want to release
            // the difference of that situation, not the ones from before
            storeArray = new String [40];            
            storeArrayCounter = 0;
            // move onto the next spot in file2 array after this matching spot
            // for the normal checking process in the first for loop
            counter = j+1;
            // set found to false because we found it in the array, so it doesn't
            // think to get to the case where the item doesn't exist at all
            found = false;
            // found the match, so go back to the original for statement to check
            // like normal
            break;
          }
          // if no matching, keep adding to array until a match is found
          else{
            storeArray[storeArrayCounter] = file2[j];
            storeArrayCounter++;
          }
          
        }
        // case for if the item doesn't exist at all aka doesn't match
        // any item in file2 just add in the item and 
        // reconstruct the a new storeArray for different case
        if(found){          
        resultOutput.append(String.format("\n> %s",file1[i]));
        storeArray = new String[40];
        storeArrayCounter = 0;
        }              
      }     
  }
  // for if file2 is longer than file 1 then remove extras
  if(counter-1 < file2.length){
    // starting where we left off from file2
    for(int a = counter; a<file2.length; a++){
        resultOutput.append(String.format("\n< %s",file2[a]));        
      }    
  }  
}
// catch for file1 is longer than file2 then add in extras
// from file1
catch(Exception e){
  // starting where we left off from file1
  for(int c = i; c<file1.length; c++){
    resultOutput.append(String.format("\n> %s",file1[c]));
    }  
  }
  return resultOutput.toString();
}  
}
