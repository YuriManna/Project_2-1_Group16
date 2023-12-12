package com.quarto.Model;

/*
Responsibilities:
    Represent a game piece with attributes like size, color, shape, and whether it has a hole or not.
*/

public class Piece {
    public boolean[] Properties;
    private final boolean COLOR; // White(T) or Black(F)
    private final boolean HEIGHT; // Small(T) or Big(F)
    private final boolean SHAPE; // Square(T) or Circle(F)
    private final boolean HOLE; // Hole(T) or Full(F)

    public Piece(boolean color, boolean height, boolean shape, boolean hole) {
        COLOR = color;
        HEIGHT = height;
        SHAPE = shape;
        HOLE = hole;
        Properties = new boolean[]{color, height, shape, hole};
    }

    public boolean getColor() {
        return COLOR;
    }

    public boolean getHeight() {
        return HEIGHT;
    }

    public boolean getShape() {
        return SHAPE;
    }

    public boolean getHole() {
        return HOLE;
    }

    public void setPiece(String piece) {

    }
}
