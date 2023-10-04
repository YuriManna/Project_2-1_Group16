package com.quarto.setup;

public class Pieces implements java.io.Serializable{
private boolean Color;
private boolean Height;
private boolean Shape;
private boolean Hole;
private int row;
private int col;

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

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }
}
