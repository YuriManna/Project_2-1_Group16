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
        for (Piece currentPiece : availablePieces) {
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

    public Move minimax(GameBoard board, Piece chosenPiece, int depth, boolean isMaximizing, int alpha, int beta){

        MiniMax miniMax = new MiniMax(game);
        return miniMax.minimax(board, chosenPiece, depth, isMaximizing, alpha, beta);
    }

    //minimax bullshit ====================================================================================================
//    public Move iterativeDeepeningMinimax(GameBoard board, Piece chosenPiece, int maxDepth) {
//        Move bestMove = null;
//        int bestScore = Integer.MIN_VALUE;
//
//        for (int depth = 1; depth <= maxDepth; depth++) {
//            Move move = minimax(board, chosenPiece, depth, false);
//            if (move != null) {
//                int score = minimaxScore(board, chosenPiece, depth, false);
//                if (score > bestScore) {
//                    bestScore = score;
//                    bestMove = move;
//                }
//                if (score == 1) { // Winning move found
//                    break;
//                }
//            }
//        }
//
//        return bestMove;
//    }

//    public Move minimax(GameBoard board, Piece chosenPiece, int depth, boolean isMaximizing, int alpha, int beta) {
//        if (depth == 0 || board.checkWinCondition() || board.checkDrawCondition()) {
//            return null;
//        }
//
//        Move bestMove = null;
//        int bestScore = isMaximizing ? Integer.MIN_VALUE : Integer.MAX_VALUE;
//
//        for (Piece piece : availablePieces) {
//            for (int i = 0; i < 16; i++) {
//                Move move = new Move(chosenPiece, i);
//                if (board.isValidMove(move)) {
//                    board.addPieceToBoard(move);
//
//                    int score = minimaxScore(board, piece, depth - 1, !isMaximizing, alpha, beta);
//
//                    board.removePieceFromBoard(move);
//
//                    if (isMaximizing){
//                        if (score > bestScore) {
//                            bestScore = score;
//                            bestMove = move;
//                        }
//                        alpha = Math.max(alpha, bestScore);
//                    } else{
//                        if (score < bestScore) {
//                            bestScore = score;
//                            bestMove = move;
//                        }
//                        beta = Math.min(beta, bestScore);
//                    }
//
//                    if (beta <= alpha) {
//                        break;
//                    }
//                }
//            }
//        }
//
//        return bestMove;
//    }
//
//    private int minimaxScore(GameBoard board, Piece chosenPiece, int depth, boolean isMaximizing, int alpha, int beta) {
//        if (depth == 0 || board.checkWinCondition() || board.checkDrawCondition()) {
//            EvaluationFunction eval = new EvaluationFunction();
//            return eval.evaluateBoard(board, chosenPiece, game);
//        }
//
//        int bestScore = isMaximizing ? Integer.MIN_VALUE : Integer.MAX_VALUE;
//
//        for (Piece piece : availablePieces) {
//            for (int i = 0; i < 16; i++) {
//                Move move = new Move(chosenPiece, i);
//                if (board.isValidMove(move)) {
//                    board.addPieceToBoard(move);
//
//                    int score = minimaxScore(board, piece, depth - 1, !isMaximizing, alpha, beta);
//
//                    board.removePieceFromBoard(move);
//
//                    if (isMaximizing){
//                        if (score > bestScore) {
//                            bestScore = score;
//                        }
//                        alpha = Math.max(alpha, bestScore);
//
//                    } else{
//                        if (score < bestScore) {
//                            bestScore = score;
//                        }
//                        beta = Math.min(beta, bestScore);
//                    }
//
//                    if (beta <= alpha) {
//                        break;
//                    }
//                }
//            }
//        }
//
//        return bestScore;
//    }

}
