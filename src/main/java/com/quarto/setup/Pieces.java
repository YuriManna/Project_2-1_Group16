package com.quarto.setup;

public class Pieces {
private boolean Color;
private boolean Height;
private boolean Hole;
private boolean Shape;
private int column;
private int row;

    public Pieces(boolean color, boolean height, boolean hole, boolean shape) {
        Color = color;
        Height = height;
        Hole = hole;
        Shape = shape;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }
}
