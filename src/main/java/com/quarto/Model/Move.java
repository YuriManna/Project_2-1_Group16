package com.quarto.Model;

public class Move {
    private Piece piece;
    private int tileId;

    public Move(Piece piece, int tileId) {
        this.piece = piece;
        this.tileId = tileId;
    }

    public Piece getPiece() {
        return piece;
    }

    public int getTileId() {
        return tileId;
    }

    public String moveToString() {

        return "Piece: " + this.getPiece().toString() + ", Tile: " + this.getTileId();
    }


}

