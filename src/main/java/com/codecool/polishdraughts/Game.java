package com.codecool.polishdraughts;

public class Game {

    String alphabetString = "abcdefghijklmnopqrstuvwxyz".toUpperCase();
    int player1Pawns = 0;
    int player2Pawns = 0;
    int activePlayer = 1;

    public void play(int boardSize) {
        Board board = new Board(boardSize);
        board.initBoard(board);
        while (true){
            clear();
            printBoard(board);
            playRound();
            if (checkForWinner()){
                printResults();
            }
            setActivePlayer();
        }
    }

    private void setActivePlayer() {
        activePlayer = activePlayer == 1 ? 2 : 1;
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
                    }
                    else {
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
}
