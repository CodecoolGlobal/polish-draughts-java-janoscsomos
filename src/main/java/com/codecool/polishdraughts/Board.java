package com.codecool.polishdraughts;


public class Board {
    private Pawn[][] board;

    public Board(int boardSize) {
        this.board = new Pawn[boardSize][boardSize];
    }

    public void initBoard(Board board){
    }

    public Pawn[][] getBoard(){
        return board;
    }

    public void setBoard() {
    }
}
