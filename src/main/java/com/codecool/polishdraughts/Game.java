package com.codecool.polishdraughts;

import java.util.Arrays;
import java.util.Scanner;

public class Game {
    public static void main(String[] args) {
        System.out.println("Please give me the size of the board");
        Scanner boardSize = new Scanner(System.in);
        int coordinates = boardSize.nextInt();
        Board board = new Board();
        board.initBoard(coordinates);
        System.out.println(Arrays.deepToString(board.getBoard()));
    }
}
