package com.quarto.player.ai;

import com.quarto.Model.GameBoard;
import com.quarto.Model.Move;
import com.quarto.Model.Piece;


import com.quarto.setup.Pieces;

import java.util.ArrayList;

/*
Responsibilities:
    Represent a player in the game.
    Keep track of the player's name, pieces, and whether they are human or AI.
*/

public class Minimax {
    boolean isHuman;
    boolean isWhite;
    Piece[] availablePieces;

    public Minimax(boolean isHuman, boolean isWhite, ArrayList<Piece> availablePieces) {
        this.isHuman = isHuman;
        this.isWhite = isWhite;
        this.availablePieces = availablePieces.toArray(new Piece[0]);
    }

    //Getters and Setters


    public Piece[] getAvailablePieces() {
        return availablePieces;
    }

    public Boolean getIsWhite() {
        return isWhite;
    }

    public boolean getIsHuman(){return isHuman;}

    //Other methods
    //======================================================================================================================

    //Player chooses opponents piece
    public Piece choosePiece(com.quarto.Model.Player opponent, Piece chosenPiece) {
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

    public Move minimax(GameBoard board, Piece chosenPiece) {
        Move bestMove = null;
        int bestScore = Integer.MIN_VALUE;

        for (int i = 0; i < 16; i++) {
            Move move = new Move(chosenPiece, i);
            if (board.isValidMove(move)) {
                board.addPieceToBoard(move);
                removeAvailablePiece(chosenPiece);

                int score = minimax(board, 0, false, chosenPiece);

                board.removePieceFromBoard(move);
                addAvailablePiece(chosenPiece);

                if (score > bestScore) {
                    bestScore = score;
                    bestMove = move;
                }
            }
        }

        return bestMove;
    }

    private int minimax(GameBoard board, int depth, boolean isMaximizing, Piece chosenPiece) {
        if (board.checkWinCondition()) {
            return isMaximizing ? -1 : 1;
        }

        if (board.checkDrawCondition()) {
            return 0;
        }

        if (isMaximizing) {
            int bestScore = Integer.MIN_VALUE;

            for (Piece piece : availablePieces) {
                for (int i = 0; i < 16; i++) {
                    Move move = new Move(chosenPiece, i);
                    if (board.isValidMove(move)) {
                        board.addPieceToBoard(move);
                        removeAvailablePiece(chosenPiece);

                        int score = minimax(board, depth + 1, false, piece);

                        board.removePieceFromBoard(move);
                        addAvailablePiece(chosenPiece);

                        bestScore = Math.max(score, bestScore);
                    }
                }
            }

            return bestScore;
        } else {
            int bestScore = Integer.MAX_VALUE;

            for (Piece piece : availablePieces) {
                for (int i = 0; i < 16; i++) {
                    Move move = new Move(chosenPiece, i);
                    if (board.isValidMove(move)) {
                        board.addPieceToBoard(move);
                        removeAvailablePiece(chosenPiece);

                        int score = minimax(board, depth + 1, true, piece);

                        board.removePieceFromBoard(move);
                        addAvailablePiece(chosenPiece);

                        bestScore = Math.min(score, bestScore);
                    }
                }
            }

            return bestScore;
        }
    }

    public void addAvailablePiece(Piece piece) {
        Piece[] updatedPieces = new Piece[availablePieces.length + 1];
        System.arraycopy(availablePieces, 0, updatedPieces, 0, availablePieces.length);
        updatedPieces[availablePieces.length] = piece;
        availablePieces = updatedPieces;
    }


}
