package com.quarto.setup;


/**
 *  this class is used to store a move which is made by placing the piece
 *  and selecting the piece to give to the opponent
 * 
 * */
public class Move {
    private final Pieces pieceToPlace; // piece given by the opponent
    private final int pieceLocation; // location of the piece given by the opponent
    private final Pieces pieceChosen; // piece we chose to give to the opponent

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
