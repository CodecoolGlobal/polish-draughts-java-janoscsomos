package com.codecool.polishdraughts;


import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Board {
    private final String alphabetString = "abcdefghijklmnopqrstuvwxyz".toUpperCase();
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




    public String toString(int[] coordinates) {
        return "hello";
    }

    public int[] toCoordinates(String coordinates) {
        int row = Integer.parseInt(coordinates.substring(1))-1;
        int columnNumber = alphabetString.indexOf(coordinates.charAt(0));
        return new int[] {row,columnNumber};
    }
}
