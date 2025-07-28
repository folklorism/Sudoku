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
}
// TODO HELLOOOOOOO
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