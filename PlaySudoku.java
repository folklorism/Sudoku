// Keren Truong
// CS 143
// This class implements the game of Sudoku 
// by having the player start the game with the main method.
import java.util.*;
import java.io.*;

public class PlaySudoku{
    public static void main(String[] args) throws FileNotFoundException{
        SudokuBoard boarded = new SudokuBoard("Sudoku/data1.sdk");
        System.out.println(boarded.toString());


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