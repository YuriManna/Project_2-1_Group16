package com.quarto.Model;

import com.quarto.ai.EvaluationFunction;
import com.quarto.ai.MiniMax;

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
    QuartoGame game;

    public Player(boolean isHuman, boolean isWhite, ArrayList<Piece> availablePieces, QuartoGame game) {
        this.isHuman = isHuman;
        this.isWhite = isWhite;
        this.availablePieces = availablePieces.toArray(new Piece[0]);
        this.game = game;
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

    public boolean getIsHuman(){return isHuman;}


    public void removeAvailablePiece(Piece piece) {
        // Create a new array to hold the remaining pieces
        Piece[] updatedPieces = new Piece[availablePieces.length - 1];
        int index = 0;

        // Copy pieces to the new array, excluding the specified piece
        for (Piece currentPiece : availablePieces) {//Issues with removing pieces. Should be resolved.
            if (!currentPiece.equals(piece)) {
                updatedPieces[index++] = currentPiece;
            }
        }
        // Update the availablePieces array
        availablePieces = updatedPieces;
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
//changed a lot to accommodate the app starting
    public Move minimax(GameBoard board, Piece chosenPiece, int depth, boolean isMaximizing, int alpha, int beta){
        //game.getCurrentPlayer().removeAvailablePiece(chosenPiece);
        MiniMax miniMax = new MiniMax(game,chosenPiece);//what is the game that is being passed to this constructor
        return miniMax.iterative_deepening( board, chosenPiece, depth, isMaximizing, alpha, beta);
    }

}
