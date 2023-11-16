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

    public Pieces getPieceToPlace() {
        return pieceToPlace;
    }

    public int getPieceLocation() {
        return pieceLocation;
    }

    public Pieces getPieceChosen() {
        return pieceChosen;
    }
}
