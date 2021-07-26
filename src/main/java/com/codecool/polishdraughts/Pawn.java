package com.codecool.polishdraughts;

public class Pawn {
    private final String color;
    private Coordinates field;

    public Pawn(String color, int row, int col) {
        this.color = color;
        this.field = new Coordinates(row, col);
    }

    public static class Coordinates {
        int row;
        int column;

        public Coordinates(int row, int column) {
            this.row = row;
            this.column = column;
        }
    }

    public String getColor() {
        return color;
    }

    public Coordinates getField() {
        return field;
    }

    public void setField(Coordinates field) {
        this.field = field;
    }
}
