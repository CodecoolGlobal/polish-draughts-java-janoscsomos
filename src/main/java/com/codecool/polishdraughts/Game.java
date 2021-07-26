package com.codecool.polishdraughts;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Game {
    public static void main(String[] args) {
        System.out.println("Please give me the size of the board! (Only even numbers are accepted!)");
        Scanner boardSize = new Scanner(System.in);
        try {
            int coordinates = boardSize.nextInt();
            if (coordinates % 2 == 0 && coordinates >= 10 && coordinates <= 20) {
                Board board = new Board();
                board.initBoard(coordinates);
                printBoard(board.getBoard(),coordinates);
            } else
                main(args);
        } catch (InputMismatchException error) {
            main(args);
        }
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
