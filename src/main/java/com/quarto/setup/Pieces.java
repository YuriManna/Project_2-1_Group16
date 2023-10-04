package com.quarto.setup;

public class Pieces {
private boolean Color;
private boolean Height;
private boolean Hole;
private boolean Shape;
public boolean[] Properties;

    public Pieces(boolean color, boolean height, boolean shape, boolean hole) {
        Color = color;
        Height = height;
        Shape = shape;
        Hole = hole;
        Properties= new boolean[]{color, height, shape, hole};
    }

}
