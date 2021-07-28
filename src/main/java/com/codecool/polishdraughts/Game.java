package com.codecool.polishdraughts;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Game {

    String alphabetString = "abcdefghijklmnopqrstuvwxyz".toUpperCase();
    int player1Pawns = 0;
    int player2Pawns = 0;
    int activePlayer = 1;

    public void play(int boardSize) {
        Board board = new Board(boardSize);
        board.initBoard(board);
        Pawn toMove = board.getBoard()[6][0];
        board.movePawn(toMove, 4, 2);
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
        tableHeader.append("Player ").append(activePlayer).append("'s turn!\n").append("    ");
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
        List<int[]> mustHits = new ArrayList<>();
        List<int[]> movables = new ArrayList<>();
        findMustHits(friendlyPawns, enemyColor, hitDirection, board, mustHits);
        findMovables(friendlyPawns, movables, board, hitDirection);
        System.out.print("musthits");
        for (int [] hits : mustHits){
            System.out.println(Arrays.toString(hits));
        }
        System.out.println("move");
        for (int [] hits : movables){
            System.out.println(Arrays.toString(hits));
        }
    }

    private int[][] tryToMakeAMove(Pawn activePawn) {
        Coordinates pawnCoords = activePawn.getField();
        int pawnRow = pawnCoords.row;
        int pawnColumn = pawnCoords.column;
        int[][] validMoves = new int[2][2];
        int fieldsInFront = -1;
        //ifnotcrowned new variable with getIsCrowned
        for (int i = 0; i < 2; i++) {
            try {
                if (activePawn.getColor().equals("white")) {
                    validMoves[i][0] = pawnRow + 1;
                    validMoves[i][1] = pawnColumn + fieldsInFront;
                    fieldsInFront += 2;
                } else {
                    validMoves[i][0] = pawnRow - 1;
                    validMoves[i][1] = pawnColumn + fieldsInFront;
                    fieldsInFront += 2;
                }
            } catch (ArrayIndexOutOfBoundsException | NullPointerException ignored) {
        }
        }
        return validMoves;
    }

    private int[][] getPawns(String friendlyColor, Board board) {
        int[][] pawnCoos = new int[activePlayer == 1 ? player1Pawns : player2Pawns][2];
        int pawnCounter = 0;
        for (int row = 0; row < board.getBoard().length; row++) {
            for (int column = 0; column < board.getBoard().length; column++) {
                if (board.getBoard()[row][column] != null &&
                        board.getBoard()[row][column].getColor().equals(friendlyColor)) {
                    pawnCoos[pawnCounter] = new int[]{row, column};
                    pawnCounter++;
                }
            }
        }
        return pawnCoos;
    }

    private void findMovables(int[][] friendlyPawns,
                              List<int[]> movables,
                              Board board,
                              int hitDirection) {
        for (int[] pawn : friendlyPawns) {
            boolean contains = false;
            try {
                if (board.getBoard()[pawn[0] + hitDirection][pawn[1] + 1] == null) {
                    movables.add(new int[]{pawn[0], pawn[1]});
                    contains = true;
                }
            } catch (IndexOutOfBoundsException ignored) {
            }
            try {
                if (board.getBoard()[pawn[0] + hitDirection][pawn[1] - 1] == null && !contains) {
                    movables.add(new int[]{pawn[0], pawn[1]});
                }
            } catch (IndexOutOfBoundsException ignored) {
            }
        }
    }

    public void findMustHits(int[][] friendlyPawns,
                             String enemyColor,
                             int hitDirection,
                             Board board,
                             List<int[]> mustHits) {
        for (int[] pawn : friendlyPawns) {
            boolean contains = false;
            try {
                if (board.getBoard()[pawn[0] + hitDirection][pawn[1] + 1].getColor().equals(enemyColor)
                        && board.getBoard()[pawn[0] + hitDirection * 2][pawn[1] + 2] == null) {
                    mustHits.add(new int[]{pawn[0], pawn[1]});
                    contains = true;
                }
            } catch (IndexOutOfBoundsException | NullPointerException ignored) {
            }
            try {
                if (board.getBoard()[pawn[0] + hitDirection][pawn[1] + 1].getColor().equals(enemyColor)
                        && board.getBoard()[pawn[0] + hitDirection * 2][pawn[1] - 2] == null && !contains) {
                    mustHits.add(new int[]{pawn[0], pawn[1]});
                }
            } catch (IndexOutOfBoundsException | NullPointerException ignored) {
            }
        }
    }
}

