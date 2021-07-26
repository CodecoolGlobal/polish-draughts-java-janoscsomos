package com.codecool.polishdraughts;
import java.util.Scanner;

public class Game {
    public static void main(String[] args) {
        System.out.println("Please give me the size of the board");
        Scanner boardSize = new Scanner(System.in);
        int coordinates = boardSize.nextInt();
        Board board = new Board();
        board.initBoard(coordinates);
        printBoard(board.getBoard(),coordinates);
    }

    public static void printBoard(String[][] board,int coordinates) {
        StringBuilder visualBoard = new StringBuilder();
        int rowNumber = 1;
        final String alphabetString = "abcdefghijklmnopqrstuvwxyz".toUpperCase();
        visualBoard.append("   ");
        for (int index = 0; index<coordinates; index++){
            visualBoard.append("   ").append(alphabetString.toCharArray()[index]);
        }
        visualBoard.append("\n");
        for (String[] row : board) {

            visualBoard.append(rowNumber);
            if (rowNumber < 10) {
                visualBoard.append("   ");
            } else {
                visualBoard.append("  ");
            }
            for (String element : row) {
                visualBoard.append("|_").append(element).append("_");
            }
            visualBoard.append("|\n");
            rowNumber++;
        }
        System.out.println(visualBoard);
    }
}
