// Keren Truong
// CS 143
// This class implements the game of Sudoku 
// and establishes the gameboard.
import java.util.*;
import java.io.*;

public class SudokuBoard {
    // public static final int ROWS = 9;
    // public static final int COLS = 9;
    private char[][] board;

    // pre: gets the file name to fill the Sudoku grid.
    // throws a FileNotFoundException for the provided file name
    // post: fills the Sudoku grid with the values of the file
    public SudokuBoard(String theFile) throws FileNotFoundException{
        this.board = new char[9][9];
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
        boolean checkContainsValue = false;
        Set<Character> matchedBoardVal = new HashSet<>();
        for(int i = 0; i < board.length; i++){
            for(int j = 0; j < board[i].length; j++){
                char newOneChar = board[i][j];
                if(!(matchedBoardVal.contains(newOneChar))){
                    matchedBoardVal.add(newOneChar);
                }
                checkContainsValue = matchedBoardVal.contains(newOneChar) || matchedBoardVal.contains('.');
            }
        }
        //char[][] threeByThree = miniSquare(5);

        return checkContainsValue && 
            noDuplicateRow(this.board) && 
            noDuplicateColumnn(this.board) &&
            squareDupes(this.board);
    }

    // pre:
    // post:
    private boolean noDuplicateRow(char[][] checkBoard){
        // does not contain any duplicates of 1-9 in ROW 
        // but can with "."
        Set<Character> checkRowDupes = new HashSet<>();
        for(int i = 0; i < checkBoard.length; i++){
            for(int j = 0; j < checkBoard[i].length; j++){
                char rowVal = checkBoard[i][j];
                if(!(checkRowDupes.contains(rowVal))){
                    if(checkRowDupes.contains('.')){
                        checkRowDupes.add(rowVal);
                    }
                } else {
                    return false;
                }
            }
        }
        return true;
    }

    // pre: 
    // post:
    private boolean noDuplicateColumnn(char[][] checkBoard){
        // does not contain any duplicates of 1-9 in COLUMN 
        // but can with "."
        Set<Character> checkColDupes = new HashSet<>();
        int rowCounter = 0;
        for(int j = 0; j < checkBoard[rowCounter].length; j++){
            char colValue = checkBoard[rowCounter][j];
            if(!(checkColDupes.contains(colValue))){
                if((!(checkColDupes.contains('.'))) || checkColDupes.contains('.')){
                    checkColDupes.add(colValue);
                } else {
                    return false;
                }
            }
        }

        return true;
    }

    private boolean squareDupes(char[][] checkBoard){
        Set<Character> checkSquareDupes = new HashSet<>();
        for(int i = 0; i < checkBoard.length; i++){
            char[][] threeByThree = miniSquare(i);
            for(int j = 0; j < checkBoard[i].length; j++){
                char oneNumInNine = threeByThree[i][j];
                if(!(checkSquareDupes.contains(oneNumInNine))){
                    if((!(checkSquareDupes.contains('.'))) || checkSquareDupes.contains('.')){
                        checkSquareDupes.add(oneNumInNine);
                        return false;
                    }
                }
            }
        }
        return true;
    }
    
    // pre:
    // post: 
    private char[][] miniSquare(int spot) {
      char[][] mini = new char[3][3];
      for(int r = 0; r < 3; r++) {
         for(int c = 0; c < 3; c++) {
            mini[r][c] = board[(spot - 1) / 3 * 3 + r][(spot - 1) % 3 * 3 + c];
         }
      }
      return mini;
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

 */