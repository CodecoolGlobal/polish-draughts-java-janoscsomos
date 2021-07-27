package com.codecool.polishdraughts;


public class Board {
    private Pawn[][] board;

    public Board(int boardSize) {
        this.board = new Pawn[boardSize][boardSize];
    }

    public void initBoard(Board board){
        int boardSize = board.getBoard().length;
        Pawn[][] actualBoard = board.getBoard();
        for (int row = 0; row < boardSize; row++) {
            for (int column = 0; column < boardSize; column++) {
                if (row % 2 == 0 && column % 2 == 0 && row < boardSize / 2 -1 ||
                row % 2 != 0 && column % 2 != 0 && row < boardSize / 2 - 1) {
                    Pawn pawn = new Pawn("white", row, column);
                    actualBoard[row][column] = pawn;
                } else if (row % 2 == 0 && column % 2 == 0 && row > boardSize / 2 ||
                row % 2 != 0 && column % 2 != 0 && row > boardSize / 2) {
                    Pawn pawn = new Pawn("black", row, column);
                    actualBoard[row][column] = pawn;
                }
            }
        }
    }

    public Pawn[][] getBoard(){
        return board;
    }

    public void setBoard() {
    }
}
