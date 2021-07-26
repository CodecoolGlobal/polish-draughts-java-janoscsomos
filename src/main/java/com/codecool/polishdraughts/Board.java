package com.codecool.polishdraughts;


public class Board {
    String[][] board ;

    public void initBoard(int coordinates){
        board = new String[coordinates][coordinates];
        for (int row = 0; row<coordinates ; row++){
            for (int col = 0; col<coordinates ; col++){
                board[row][col] = "_";
            }
        }
        setInitBoard(coordinates);
    }

    public String [] [] getBoard(){
        return board;
    }

    public void setInitBoard(int coordinates) {
        for (int row = 0; row < coordinates; row++) {
            for (int column = 0; column < coordinates; column++) {
                if (row % 2 == 0 && column % 2 == 0 && row < coordinates / 2 - 1 ||
                        row % 2 != 0 && column % 2 != 0 && row < coordinates / 2 - 1) {
                    Pawn.Color color = new Pawn.Color();
                    board[row][column] = color.getColor("not black");
                } else if (row % 2 == 0 && column % 2 == 0 && row > coordinates / 2 ||
                        row % 2 != 0 && column % 2 != 0 && row > coordinates / 2) {
                    Pawn.Color color = new Pawn.Color();
                    board[row][column] = color.getColor("black");
                }
            }
        }
    }
}
