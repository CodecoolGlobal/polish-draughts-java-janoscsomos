package com.codecool.polishdraughts;

public class Pawn {

    public static class Color {
        String black = "black";
        String white = "white";

        public String getColor(String color) {
            return color.equals("black") ? "⚪" : "⚫";
        }
    }

    public static class Coordinates {
        int row;
        int column;
    }
}
