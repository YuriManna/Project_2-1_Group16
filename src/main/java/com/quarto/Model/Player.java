package com.quarto.Model;

import com.quarto.setup.Pieces;

import java.util.ArrayList;

/*
Responsibilities:
    Represent a player in the game.
    Keep track of the player's name, pieces, and whether they are human or AI.
*/

public class Player {
    boolean isHuman;
    boolean isWhite;
    Piece[] availablePieces;

    public Player(boolean isHuman, boolean isWhite, ArrayList<Piece> availablePieces) {
        this.isHuman = isHuman;
        this.isWhite = isWhite;
        this.availablePieces = availablePieces.toArray(new Piece[0]);
    }

    //Getters and Setters


    public Piece[] getAvailablePieces() {
        return availablePieces;
    }

    public int getAvailablePiecesLength() {
        return getAvailablePieces().length;
    }

    public Boolean getIsWhite() {
        return isWhite;
    }

    public Boolean getIsHuman() { return isHuman; }

    //Other methods
    //======================================================================================================================

    //Player chooses opponents piece
    public Piece choosePiece(Player opponent, Piece chosenPiece) {
        // Verify that the chosen piece is in the opponent available pieces
        for (Piece piece : opponent.getAvailablePieces()) {
            if (piece.equals(chosenPiece)) {
                // Remove the chosen piece from the opponent available pieces
                opponent.removeAvailablePiece(chosenPiece);

                // Return the chosen piece
                return chosenPiece;
            }
        }
        // If the chosen piece is not found, return null or handle accordingly
        return null;
    }

    //Helper method for choosePiece
    public void removeAvailablePiece(Piece piece) {
        // Create a new array to hold the remaining pieces
        Piece[] updatedPieces = new Piece[availablePieces.length - 1];
        int index = 0;

        // Copy pieces to the new array, excluding the specified piece
        for (Piece currentPiece : availablePieces) {
            if (!currentPiece.equals(piece)) {
                updatedPieces[index++] = currentPiece;
            }
        }
        // Update the availablePieces array
        availablePieces = updatedPieces;
    }

    //Player makes move
    public void makeMove(Move move, GameBoard board) {
        // Verify that the move is valid
        board.addPieceToBoard(move);
    }


}
