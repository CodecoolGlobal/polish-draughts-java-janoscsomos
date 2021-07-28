package com.codecool.polishdraughts;

import java.util.Arrays;

public class Game {

    String alphabetString = "abcdefghijklmnopqrstuvwxyz".toUpperCase();
    int player1Pawns = 0;
    int player2Pawns = 0;
    int activePlayer = 1;

    public void play(int boardSize) {
        Board board = new Board(boardSize);
        board.initBoard(board);
        //Pawn toMove = board.getBoard()[6][0];
        //board.movePawn(toMove, 5, 1);
        //while (true) {
        clear();
        printBoard(board);
        playRound(board);
        if (checkForWinner(player1Pawns, player2Pawns)) {
            printResults(board);
        }
        setActivePlayer();
        }


    private void setActivePlayer() {
        activePlayer = activePlayer == 1 ? 2 : 1;
    }

    private void printResults(Board board) {
        printBoard(board);
        String winner = player1Pawns == 0 ? "Player 2 " : "Player 1 ";
        System.out.println(winner + "won!");
        System.exit(0);
    }

    public void printBoard(Board board) {
        StringBuilder output = new StringBuilder();
        String tableHeader = createTableHeader(board.getBoard().length);
        for (int row = 0; row < board.getBoard().length; row++) {
            output.append(row + 1);
            if (row + 1 < 10) output.append(" ");
            for (int column = 0; column < board.getBoard().length; column++) {
                if (board.getBoard()[row][column] == null) output.append("|_").append("__");
                else {
                    if (board.getBoard()[row][column].getColor().equals("black")) {
                        output.append("|_").append("⚪_");
                        player2Pawns++;
                    } else {
                        output.append("|_").append("⚫_");
                        player1Pawns++;
                    }
                }
            }
            output.append("|\n");
        }
        System.out.println(tableHeader);
        System.out.println(output);
    }

    private String createTableHeader(int boardSize) {
        StringBuilder tableHeader = new StringBuilder();
        tableHeader.append("Player ").append(activePlayer).append("'s turn!\n");
        tableHeader.append("    ");
        for (int index = 0; index < boardSize; index++) {
            tableHeader.append(alphabetString.charAt(index)).append("   ");
        }
        return tableHeader.toString();
    }

    public void clear() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public boolean checkForWinner(int player1Counter, int player2Counter) {
        return player1Counter == 0 || player2Counter == 0;
    }

    public void playRound(Board board) {
        StringBuilder output = new StringBuilder();
        String friendlyColor = activePlayer == 1 ? "white" : "black";
        String enemyColor = friendlyColor.equals("white") ? "black" : "white";
        int hitDirection = activePlayer == 1 ? 1 : -1;
        int[][] friendlyPawns = getPawns(friendlyColor, board);
        int[][] mustHits = new int[100][2];
        int[][] movable = new int[100][2];

        fillCoordinates(friendlyPawns, mustHits, movable, board, hitDirection, enemyColor, friendlyColor);
        System.out.println(Arrays.deepToString(mustHits));
        System.out.println(Arrays.deepToString(movable));
        if (mustHits.length > 0) {
            for (int[] coordinate : mustHits) {
                output.append(board.toString(coordinate));
            }
        }
    }

    private int[][] getPawns(String friendlyColor, Board board) {
        int counter = activePlayer == 1 ? player1Pawns : player2Pawns;
        int[][] pawnCoos = new int[counter][2];
        int pawnCounter = 0;
        for (int row = 0; row < board.getBoard().length; row++) {
            for (int column = 0; column < board.getBoard().length; column++) {
                if (board.getBoard()[row][column] != null &&
                        board.getBoard()[row][column].getColor().equals(friendlyColor)) {
                    pawnCoos[pawnCounter] = new int[]{row, column};
                    pawnCounter ++;
                }
            }
        }
        return pawnCoos;
    }

    private void fillCoordinates(int[][] friendlyPawns, int[][] mustHits, int[][] movable,
                                 Board board, int hitDirection, String enemyColor, String friendlyColor) {
        for (int coordinates = 0; coordinates < friendlyPawns.length; coordinates++) {
            try {
                //System.out.println(board.getBoard()[friendlyPawns[coordinates][0] + hitDirection][friendlyPawns[coordinates][1] + 1]);
                //System.out.println(board.getBoard()[friendlyPawns[coordinates][0] + hitDirection][friendlyPawns[coordinates][1] - 1]);
                if (board.getBoard()[friendlyPawns[coordinates][0] + hitDirection][friendlyPawns[coordinates][1] + 1].getColor().equals(enemyColor) ||
                        board.getBoard()[friendlyPawns[coordinates][0] + hitDirection][friendlyPawns[coordinates][1] - 1].getColor().equals(enemyColor)) {
                    System.out.println("if");
                    movable[coordinates][0] = friendlyPawns[coordinates][0];
                    movable[coordinates][1] = friendlyPawns[coordinates][1];
                } else if (board.getBoard()[friendlyPawns[coordinates][0] + hitDirection][friendlyPawns[coordinates][1] + 1] != null
                || board.getBoard()[friendlyPawns[coordinates][0] + hitDirection][friendlyPawns[coordinates][1] - 1] != null ) {
                    //System.out.println("elif");
                    movable[coordinates][0] = friendlyPawns[coordinates][0];
                    movable[coordinates][1] = friendlyPawns[coordinates][1];
                }
            } catch (ArrayIndexOutOfBoundsException | NullPointerException ignored) {
                //System.out.println("catch");
            }
        }
    }
}
