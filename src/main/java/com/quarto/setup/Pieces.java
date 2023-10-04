package com.quarto.setup;

public class Pieces {
private boolean Color;
private boolean Height;
private boolean Shape;
private boolean Hole;
private int column;
private int row;

    public Pieces(boolean color, boolean height, boolean shape, boolean hole) {
        Color = color;
        Height = height;
        Shape = shape;
        Hole = hole;
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
