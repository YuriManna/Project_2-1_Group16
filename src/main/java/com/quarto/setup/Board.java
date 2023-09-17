package com.quarto.setup;

public class Board {
    private final int ROWS = 4;
    private final int COLS = 4;
    private final Pieces[][] Board = new Pieces[ROWS][COLS];
    private final Pieces[] availableWhites = new Pieces[8];
    private final Pieces[] availableBlacks = new Pieces[8];

    public Board() {
        // First letter White(T) or Black(F)
        // Second letter Small(T) or Big(F)
        // Third letter Square(T) or Circle(F)
        // Fourth letter Hole(T) or Full(F)
        Pieces WSSH = new Pieces(true, true, true ,true);
    }
}
