package com.codecool.polishdraughts;

import java.util.*;

public class Game {
    Scanner scanner = new Scanner(System.in);
    String alphabetString = "abcdefghijklmnopqrstuvwxyz".toUpperCase();
    int player1Pawns = 0;
    int player2Pawns = 0;
    int activePlayer = 1;

    public void play(int boardSize) {
        Board board = new Board(boardSize);
        player1Pawns = boardSize * 2;
        player2Pawns = boardSize * 2;
        board.initBoard(board);
        while (true) {
            clear();
            printBoard(board);
            playRound(board);
            System.out.println("Player 1 :" + player1Pawns + " pawns");
            System.out.println("Player 2 :" + player2Pawns + " pawns");
            if (checkForWinner(player1Pawns, player2Pawns)) {
                printResults(board);
            }
            setActivePlayer();
        }
    }


    private void setActivePlayer() {
        activePlayer = activePlayer == 1 ? 2 : 1;
    }

    private void printResults(Board board) {
        clear();
        printBoard(board);
        if (player1Pawns == player2Pawns) {
            System.out.println("It's a tie!");
            PolishDraughts.mainMenu();
        }
        String winner = player1Pawns < player2Pawns ? "Player 2 " : "Player 1 ";
        System.out.println(winner + "won!");
        PolishDraughts.mainMenu();
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
                    } else {
                        output.append("|_").append("⚫_");
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
        String friendlyColor = activePlayer == 1 ? "white" : "black";
        String enemyColor = friendlyColor.equals("white") ? "black" : "white";
        int hitDirection = activePlayer == 1 ? 1 : -1;
        int[][] friendlyPawns = getPawns(friendlyColor, board);
        List<int[]> mustHits = new ArrayList<>();
        List<int[]> movables = new ArrayList<>();
        List<int[]> validMoves = new ArrayList<>();
        try {
            String moveOptions = findMoveOptions(friendlyPawns, enemyColor, hitDirection, board, mustHits, movables);
            if (mustHits.size() > 0) {
                System.out.println("Pawns you can hit with: " + moveOptions);
                String startingPoint = scanner.nextLine().toUpperCase();
                if (startingPoint.equals("EXIT")) {
                    clear();
                    PolishDraughts.mainMenu();
                }
                if (moveOptions.contains(startingPoint)) {
                    inputPawnChecker(startingPoint, board, enemyColor, hitDirection);
                } else {
                    playRound(board);
                }
            } else {
                System.out.println("Pawns you can move with: " + moveOptions);
                String playerChoice = scanner.nextLine().toUpperCase();
                if (playerChoice.equals("EXIT")) {
                    clear();
                    PolishDraughts.mainMenu();
                }
                if (moveOptions.contains(playerChoice)) {
                    Pawn activePawn = board.getBoard()[board.toCoordinates(playerChoice)[0]][board.toCoordinates(playerChoice)[1]];
                    tryToMakeAMove(activePawn, validMoves);
                    removeInvalidFields(validMoves, board, friendlyColor, enemyColor);
                    int[] newField = chooseMove(validMoves, board);
                    board.movePawn(activePawn, newField[0], newField[1]);
                } else {
                    playRound(board);
                }
            }
        } catch (NumberFormatException | StringIndexOutOfBoundsException | InputMismatchException error) {
            drawChecker(movables, mustHits, board);
            playRound(board);
        }
        drawChecker(movables, mustHits, board);
    }

    public void drawChecker(List<int[]> movables, List<int[]> mustHits, Board board) {
        if (movables.size() == 0 && mustHits.size() == 0) {
            printResults(board);
        }
    }

    public void inputPawnChecker(String startingCoordinate, Board board, String enemyColor, int hitDirection) {
        int[] startingPoint = board.toCoordinates(startingCoordinate);
        try {
            if (insideBoard(board, startingPoint, hitDirection)) {
                if (bothHitsAvailable(board, startingPoint, hitDirection, enemyColor)) {
                    hitChoice(startingPoint, board, hitDirection);
                }
            }
        } catch (NullPointerException ignored) {
        }
        try {
            if (board.getBoard()[startingPoint[0] + hitDirection][startingPoint[1] - 1].getColor().equals(enemyColor)) {
                hit(startingPoint, board, hitDirection, -1);
            }
        } catch (NullPointerException | IndexOutOfBoundsException ignored) {
        }
        try {
            if (board.getBoard()[startingPoint[0] + hitDirection][startingPoint[1] + 1].getColor().equals(enemyColor)) {
                hit(startingPoint, board, hitDirection, 1);
            }
        } catch (NullPointerException | IndexOutOfBoundsException ignored) {
        }
    }

    private boolean bothHitsAvailable(Board board, int[] startingPoint, int hitDirection, String enemyColor) {
        return board.getBoard()[startingPoint[0] + hitDirection][startingPoint[1] + 1].getColor().equals(enemyColor)
                && board.getBoard()[startingPoint[0] + hitDirection][startingPoint[1] - 1].getColor().equals(enemyColor)
                && board.getBoard()[startingPoint[0] + hitDirection * 2][startingPoint[1] + 2] == null
                && board.getBoard()[startingPoint[0] + hitDirection * 2][startingPoint[1] - 2] == null;
    }

    private boolean insideBoard(Board board, int[] startingPoint, int hitDirection) {
        return startingPoint[0] + hitDirection * 2 < board.getBoard().length && startingPoint[0] + hitDirection * 2 > 0
                && startingPoint[1] + 2 < board.getBoard().length && startingPoint[1] - 2 > 0;
    }

    private void hitChoice(int[] startingPoint, Board board, int hitDirection) {
        String options = findOptions(board, hitDirection, startingPoint);
        autoHit(options, startingPoint, board, hitDirection);
        if (options.contains("left") && options.contains("right")) {
            System.out.println(options + "!");
            String input = scanner.nextLine();
            if (input.equals("exit")) {
                clear();
                PolishDraughts.mainMenu();
            }
            if (input.equals("left"))
                hit(startingPoint, board, hitDirection, -1);
            if (input.equals("right"))
                hit(startingPoint, board, hitDirection, 1);
            if (!input.equals("left") && !input.equals("right"))
                hitChoice(startingPoint, board, hitDirection);
        }
    }

    private void autoHit(String options, int[] startingPoint, Board board, int hitDirection) {
        if (options.contains("left") && !options.contains("right"))
            hit(startingPoint, board, hitDirection, -1);
        if (!options.contains("left") && options.contains("right"))
            hit(startingPoint, board, hitDirection, 1);
    }

    private String findOptions(Board board, int hitDirection, int[] startingPoint) {
        String optionsString = "Choose direction: ";
        if (startingPoint[0] + hitDirection * 2 < board.getBoard().length && startingPoint[0] + hitDirection * 2 > 0 &&
                startingPoint[1] - 2 < board.getBoard().length && startingPoint[1] - 2 > 0)
            optionsString += "left /";
        if (startingPoint[0] + hitDirection * 2 < board.getBoard().length && startingPoint[0] + hitDirection * 2 > 0 &&
                startingPoint[1] + 2 < board.getBoard().length && startingPoint[1] + 2 > 0)
            optionsString += " right";
        return optionsString;
    }


    private void hit(int[] startingPoint, Board board, int hitDirection, int direction) {

        board.movePawn(board.getBoard()[startingPoint[0]][startingPoint[1]],
                startingPoint[0] + hitDirection * 2, startingPoint[1] + direction * 2);
        board.removePawn(board.getBoard()[startingPoint[0] + hitDirection][startingPoint[1] + direction]);
        if (activePlayer == 1) {
            player2Pawns--;
        } else {
            player1Pawns--;
        }
    }


    private String findMoveOptions(int[][] friendlyPawns,
                                   String enemyColor,
                                   int hitDirection,
                                   Board board,
                                   List<int[]> mustHits,
                                   List<int[]> movables) {
        findMustHits(friendlyPawns, enemyColor, hitDirection, board, mustHits);
        StringBuilder moveOptions = new StringBuilder();
        if (mustHits.size() > 0) {
            for (int[] coordinates : mustHits) moveOptions.append(board.toString(coordinates)).append(", ");
        } else {
            findMovables(friendlyPawns, movables, board, hitDirection);
            for (int[] coordinates : movables) moveOptions.append(board.toString(coordinates)).append(", ");
        }
        return moveOptions.substring(0, moveOptions.length() - 2);
    }

    private int[] chooseMove(List<int[]> availableMoves, Board board) {
        try {
            Scanner input = new Scanner(System.in);
            System.out.println("Choose from available moves:");
            for (int i = 0; i < availableMoves.size(); i++) {
                System.out.println(i + 1 + " - " + board.toString(availableMoves.get(i)));
            }
            int playerChoice = input.nextInt();
            return availableMoves.get(playerChoice - 1);
        } catch (IndexOutOfBoundsException ignored) {
        }
        return chooseMove(availableMoves, board);
    }

    private void tryToMakeAMove(Pawn activePawn, List<int[]> validMoves) {
        int startingRow = activePawn.getField().row;
        int startingCol = activePawn.getField().column;
        if (activePawn.getColor().equals("white")) {
            validMoves.add(new int[]{startingRow + 1, startingCol - 1});
            validMoves.add(new int[]{startingRow + 1, startingCol + 1});
        } else {
            validMoves.add(new int[]{startingRow - 1, startingCol - 1});
            validMoves.add(new int[]{startingRow - 1, startingCol + 1});
        }
    }

    private void removeInvalidFields(List<int[]> validMoves, Board board, String friendlyColor, String enemyColor) {
        int boardSize = board.getBoard().length;
        int invalidField = -1;
        for (int i = 0; i < validMoves.size(); i++) {
            if (validMoves.get(i)[0] == -1 || validMoves.get(i)[0] == boardSize) {
                invalidField = i;
            }
            if (validMoves.get(i)[1] == -1 || validMoves.get(i)[1] == boardSize) {
                invalidField = i;
            }
            try {

                if (board.getBoard()[validMoves.get(i)[0]][validMoves.get(i)[1]].getColor().equals(friendlyColor) ||
                        board.getBoard()[validMoves.get(i)[0]][validMoves.get(i)[1]].getColor().equals(enemyColor)) {
                    invalidField = i;
                }
            } catch (NullPointerException | IndexOutOfBoundsException ignored) {
            }
        }
        try {
            validMoves.remove(invalidField);
        } catch (IndexOutOfBoundsException ignored) {
        }
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
                if (board.getBoard()[pawn[0] + hitDirection][pawn[1] - 1].getColor().equals(enemyColor)
                        && board.getBoard()[pawn[0] + hitDirection * 2][pawn[1] - 2] == null && !contains) {
                    mustHits.add(new int[]{pawn[0], pawn[1]});
                }
            } catch (IndexOutOfBoundsException | NullPointerException ignored) {
            }
        }
    }
}