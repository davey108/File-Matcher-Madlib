# File Matcher & Madlib
This project contains a file matcher class that compares two files and make 1 match the other and an interactive madlib game

# Files Description:
- *Compare.java* - This is the source code for the file matcher algorithm. Given 2 *.txt* files, it will output codes that will match the second file to the first file by showing what needs to be input in the second file and remove from it to match the first one
- *GameNotFoundException.java* - A custom exception class for the madlib game. 
- *Madlib.java* - This is the source code that support madlib game like an interactive game. It has a command prompt like menu and allow the user to input their words and replace it into the message block like the game.
- *MyUnitTests.java* - Own custom made test file to test with both Compare.java and Madlib.java

### Folders Description
- madlibs - Contains *.txt* files for madlibs game. These files have places that mark where the users can replace words through typing in the command line in *Madlib.java*.
- testfiles - Contains *.txt* files testing for *Compare.java*.

# How to Run:
1. Download all files in this repository
2. Run *MyUnitTests.java* to see the outputs of the codes.
3. To run *Compare.java* separately, run its compiled code with 2 *.txt* arguments and see the outputs.
3. If you want to play the madlibs game, you can directly run the compiled code of *Madlib.java* with the game *.txt* file argument and type in needed replaced words in the command line!
