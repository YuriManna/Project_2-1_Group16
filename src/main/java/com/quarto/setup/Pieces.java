package com.quarto.setup;

public class Pieces {
    public boolean[] Properties;
    private boolean Color; // White(T) or Black(F)
    private boolean Height; // Small(T) or Big(F)
    private boolean Shape; // Square(T) or Circle(F)
    private boolean Hole; // Hole(T) or Full(F)
    private int column;
    private int row;

    public Pieces(boolean color, boolean height, boolean shape, boolean hole) {
        Color = color;
        Height = height;
        Shape = shape;
        Hole = hole;
        Properties = new boolean[]{color, height, shape, hole};
    }

    public boolean getColor() {
        return Color;
    }

    public boolean getHeight() {
        return Height;
    }

    public boolean getShape() {
        return Shape;
    }

    public boolean getHole() {
        return Hole;
    }

    @Override
    public String toString() {
        char C,He,S,Ho;
        if(this.Color){C = 'W';}
        else{C = 'B';}
        if(this.Height){He = 'S';}
        else{He = 'B';}
        if(this.Shape){S = 'S';}
        else{S = 'C';}
        if(this.Hole){Ho = 'H';}
        else{Ho = 'F';}
        return ""+ C + He + S + Ho;
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

    public boolean isColor() {
        return Color;
    }

    public void setColor(boolean color) {
        Color = color;
    }
}
