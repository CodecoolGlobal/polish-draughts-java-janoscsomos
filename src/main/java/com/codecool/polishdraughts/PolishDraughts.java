package com.codecool.polishdraughts;

import java.util.InputMismatchException;
import java.util.Scanner;

public class PolishDraughts {

    public static void main(String[] args) {
        mainMenu();
    }

    public static void mainMenu() {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Polish Draughts\n\n1: New game\n2: Exit");
            int userChoice = scanner.nextInt();
            if (userChoice == 1) {
                System.out.println("Please give me the size of the board! (Only even numbers are accepted!)");
                int boardSize = scanner.nextInt();
                if (boardSize >= 10 && boardSize <= 20 && boardSize % 2 == 0) {
                    Game game = new Game();
                    game.play(boardSize);
                }
            } else if (userChoice == 2)
                System.exit(0);
        } catch (InputMismatchException error) {
            mainMenu();
        }
    }
}
