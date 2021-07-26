package com.codecool.polishdraughts;


public class Board {
    String [][] board ;

    public void initBoard(int coordinates){
        board = new String[coordinates][coordinates];
        for (int row = 0; row<coordinates ; row++){
            for (int col = 0; col<coordinates ; col++){
                board[row][col] = "_";
            }
        }
    }
    public String [] [] getBoard(){
        return board;
    }
}
