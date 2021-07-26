package com.codecool.polishdraughts;

public class Pawn {

    public static class Color {
        String black = "⚪";
        String white = "⚫";

        public String getColor(String color) {
            return color.equals("black") ? black : white;
        }
    }

    public static class Coordinates {
        int row;
        int column;
    }
}
