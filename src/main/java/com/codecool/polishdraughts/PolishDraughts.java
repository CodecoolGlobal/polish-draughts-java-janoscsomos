package com.codecool.polishdraughts;

import java.util.Scanner;

public class PolishDraughts {

    public static void main(String[] args) {
        System.out.println("Please give me the size of the board! (Only even numbers are accepted!)");
        Scanner getBoardSize = new Scanner(System.in);
        int boardSize = getBoardSize.nextInt();
        if (boardSize >= 10 && boardSize <= 20 && boardSize % 2 == 0) {
            Game game = new Game();
            game.play(boardSize);
        }
        else
            main(args);
    }
}
