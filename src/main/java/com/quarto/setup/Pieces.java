package com.quarto.setup;

public class Pieces {
private boolean Color;
private boolean Height;
private boolean Shape;
private boolean Hole;

    public Pieces(boolean color, boolean height, boolean shape, boolean hole) {
        Color = color;
        Height = height;
        Shape = shape;
        Hole = hole;
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
}
