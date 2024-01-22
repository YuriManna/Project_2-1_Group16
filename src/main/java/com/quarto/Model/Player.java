package com.quarto.Model;

import com.quarto.ai.EvaluationFunction;
import com.quarto.ai.MiniMax;
import com.quarto.ai.PieceEvaluationFunction;

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
    Piece[] fixedSizeAvailablePieces;
    QuartoGame game;

    public Player(boolean isHuman, boolean isWhite, ArrayList<Piece> availablePieces, QuartoGame game) {
        this.isHuman = isHuman;
        this.isWhite = isWhite;
        this.availablePieces = availablePieces.toArray(new Piece[0]);
        this.fixedSizeAvailablePieces = this.availablePieces;
        this.game = game;
    }

    public Player(Player currentPlayer) {
        this.isHuman = currentPlayer.getIsHuman();
        this.isWhite = currentPlayer.getIsWhite();
        this.availablePieces = currentPlayer.getAvailablePieces();
    }

    //Getters and Setters

    public Piece[] getAvailablePieces() {
        return availablePieces;
    }
    public Piece[] getFixedSizeAvailablePieces() {
        return fixedSizeAvailablePieces;
    }

    public int getAvailablePiecesLength() {
        return getAvailablePieces().length;
    }

    public Boolean getIsWhite() {
        return isWhite;
    }

    public boolean getIsHuman(){return isHuman;}


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

        // Update the fixedSizeAvailablePieces arrays
        for (int i = 0; i < fixedSizeAvailablePieces.length; i++) {
            if (fixedSizeAvailablePieces[i] == piece) {
                fixedSizeAvailablePieces[i] = null;
            }
        }
        game.getGameBoard().setPiecesArray(fixedSizeAvailablePieces, isWhite);
    }

    public void addAvailablePiece(Piece piece) {
        Piece[] updatedPieces = new Piece[availablePieces.length + 1];
        System.arraycopy(availablePieces, 0, updatedPieces, 0, availablePieces.length);
        updatedPieces[availablePieces.length] = piece;
        availablePieces = updatedPieces;
    }

    //Player makes move
    public void makeMove(Move move, GameBoard board) {
        // Verify that the move is valid
        board.addPieceToBoard(move);
    }

    public Move minimaxPlace(QuartoGame game, Piece chosenPiece){

        Move move;
        MiniMax miniMax = new MiniMax(game, chosenPiece);
        move = new Move(chosenPiece, miniMax.iterative_deepening(game));
        return move;
    }

    public Piece AIChoosePiece(Player opponent, GameBoard board){
        PieceEvaluationFunction function = new PieceEvaluationFunction();
        Piece chosenPiece = function.leastLikelyPiece(opponent.getAvailablePieces(),board);
        opponent.removeAvailablePiece(chosenPiece);
        return chosenPiece;
    }

}
