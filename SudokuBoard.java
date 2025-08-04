// Keren Truong
// CS 143
// This class implements the game of Sudoku 
// and establishes the gameboard. Checks for valid values in the grid
// and also checks if the board is solved.
import java.util.*;
import java.io.*;

public class SudokuBoard {
    // public static final int ROWS = 9;
    // public static final int COLS = 9;
    private char[][] board;

    // pre: gets the file name to fill the Sudoku grid.
    // throws a try catch FileNotFoundException for the provided file name
    // post: fills the Sudoku grid with the values of the file
    public SudokuBoard(String theFile) {
        this.board = new char[9][9];
        try{
            File sudokuFile = new File(theFile);
            Scanner console = new Scanner(sudokuFile);

            int rowCount = 0;

            while(console.hasNextLine()){
                String wholeLine = console.nextLine();
                for(int i = 0; i < this.board.length; i++){
                    board[rowCount][i] = wholeLine.charAt(i);
                }
                rowCount++;
            }
        } catch (FileNotFoundException e){
            System.out.println("Error: File not found: " + theFile);
        }
    }
 
    // pre: none
    // post: visualizes the Sudoku grid with a grid of 9 by 9
    public String toString(){
        String visualBoard = "";
        for(int i = 0; i < board.length; i++){
            visualBoard += "__";
        }
        visualBoard += "\n";
        
        for(int i = 0; i < 9; i++){
            if(i == 3 || i == 6){
                for(int j = 0; j < board.length; j++){
                    visualBoard += "__";
                }
                visualBoard += "\n";
                System.out.println();
            }

            visualBoard += "|";
            for(int j = 0; j < 9; j++){
                visualBoard += board[i][j] + " ";
               //System.out.print(board[i][j] + " ");
               if(j == 2 || j == 5){
                    visualBoard += "|";
                    visualBoard += " ";
                }

               if((j+1) % 9 == 0){
                    visualBoard += "|";
                    visualBoard += "\n";
                }
            }
        }

        for(int j = 0; j < board.length; j++){
            visualBoard += "__";
        }

        return visualBoard;
    }

    // pre:
    // post:
    public boolean isValid(){
        return checkContainsValue() && 
            noDuplicateRow() && 
            noDuplicateColumnn() &&
            squareDupes();
    }

    private boolean checkContainsValue(){
        for(int i = 0; i < this.board.length; i++){
            Set<Character> validValues = new HashSet<>();
            for(int j = 0; j < this.board[i].length; j++){
                char oneVal = board[i][j];
                if(validValues.contains('.')){
                    if(validValues.contains(oneVal >= '1') || 
                        validValues.contains(oneVal <= '9')){
                        return false;
                    } 
                    validValues.add(oneVal);
                }
            }
        }
        return true;
    }

    // pre: provides a board of 9x9 to check for duplicate numbers between 1-9 for; '.' don't count
    // post: returns true if no duplicates were found in a row, false otherwise
    private boolean noDuplicateRow(){
        // does not contain any duplicates of 1-9 in ROW 
        // but can with "."
        for(int i = 0; i < board.length; i++){
            Set<Character> noRowDupes = new HashSet<>();
            for(int j = 0; j < board[i].length; j++){
                char oneVal = board[i][j];
                if(oneVal != '.'){
                    if(noRowDupes.contains(oneVal)){
                        return false;
                    }
                    noRowDupes.add(oneVal);
                }
            }
        }
        return true;
    }

    // pre: looks through each column to find duplicate numbers 1-9 in the 9x9 grid
    // post: returns true if there were no duplicates found in a column; false otherwise
    private boolean noDuplicateColumnn(){
        // does not contain any duplicates of 1-9 in COLUMN 
        // but can with "."
        for(int i = 0; i < board.length; i++){
            Set<Character> checkColDupes = new HashSet<>();
            for(int j = 0; j < board[i].length; j++){
                char oneVal = board[j][i];
                if(oneVal != '.'){
                    if(checkColDupes.contains(oneVal)){
                        return false;
                    }
                    checkColDupes.add(oneVal);
                }
            }
        }
        return true;
    }

    // pre: breaks down the 9x9 grid to 3x3 to check for duplicates in one square
    // post: returns true if no duplicates were found in one square, false otherwise
    private boolean squareDupes(){
        for(int i = 1; i <= 9; i++){
            Set<Character> checkMiniSquares = new HashSet<>();
            char[][] miniThree = miniSquare(i);
            for(int j = 0; j < 3; j++){
                for(int k = 0; k < 3; k++){
                    char oneMiniVal = miniThree[j][k]; 
                    if(oneMiniVal != '.'){
                        if(checkMiniSquares.contains(oneMiniVal)){
                            return false;
                        }
                        checkMiniSquares.add(oneMiniVal);
                    }
                }
            }
        }
        return true;
    }
    
    // pre: checks duplicates within the 3x3 squares with helper method
    // post: returns the 3x3 grid of squares to check for duplicates
    private char[][] miniSquare(int spot) {
      char[][] mini = new char[3][3];
      for(int r = 0; r < 3; r++) {
         for(int c = 0; c < 3; c++) {
            mini[r][c] = board[(spot - 1) / 3 * 3 + r][(spot - 1) % 3 * 3 + c];
         }
      }
      return mini;
   }

   // pre: checks that values are all valid and that 1-9 all have nine counts
   // post: returns true if values are valid and there are nine counts of each num
   public boolean isSolved(){
        Map<Character, Integer> trackGrid = new HashMap<>();
        boolean isNine = false;
        for(int i = 0; i < 9; i++){
            for(int j = 0; j < 9; j++){
                char oneVal = board[i][j];
                if(trackGrid.containsKey(oneVal)){
                    trackGrid.put(oneVal, trackGrid.get(oneVal) + 1);
                } else {
                    trackGrid.put(oneVal, 1);
                }
            }
        }

        for(char value : trackGrid.keySet()){
            int nine = trackGrid.get(value);
            if(nine == 9){
                isNine = true;
            }
        }
        return isValid() && isNine;
   }

   public boolean solve(){
        boolean boardSolved = false;
        if(!isValid()){
            return false;
        } else if(isSolved()){
            return true;
        } else {
            for(int i = 0; i < 9; i++){
                for(int j = 0; j < 9; j++){
                    if(board[i][j] == '.'){
                        // the row is empty --> start trying digits 1 - 9
                        // to see if any lead to solution
                        for(int k = 1; k <= 9 && !boardSolved; k++){
                            board[i][j] = (char)k;
                            boardSolved = solve();
                        }

                        if(!isSolved()){
                            board[i][j] = '.';
                        }
                    }
                }
            }
        }
        return boardSolved;
   }
}

/*

__________________
|2 . . | 1 . 5 | . . 3 |
|. 5 4 | . . . | 7 1 . |
|. 1 . | 2 . 3 | . 8 . |
__________________
|6 . 2 | 8 . 7 | 3 . 4 |
|. . . | . . . | . . . |
|1 . 5 | 3 . 9 | 8 . 6 |
__________________
|. 2 . | 7 . 1 | . 6 . |
|. 8 1 | . . . | 2 4 . |
|7 . . | 4 . 2 | . . 1 |
__________________
true 
false 

Checking empty board...passed.
Checking incomplete, valid board...passed.
Checking complete, valid board...passed.
Checking dirty data board...failed.
Checking row violating board...passed.
Checking col violating board...passed.
Checking row&col violating board...passed.
Checking mini-square violating board...passed.
 */