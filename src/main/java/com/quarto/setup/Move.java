package com.quarto.setup;

public class Move {
    private final Pieces pieceToPlace;
    private final int pieceLocation;
    private final Pieces pieceChosen;

    public Move( Pieces pieceToPlace, int pieceLocation,  Pieces pieceChosen) {
        this.pieceLocation = pieceLocation;
        this.pieceToPlace = pieceToPlace;
        this.pieceChosen = pieceChosen;
    }
}
