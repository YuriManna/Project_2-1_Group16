package com.quarto.ai;

import com.quarto.Model.GameBoard;
import com.quarto.Model.Piece;

import java.util.ArrayList;

public class PieceEvaluationFunction {
    private GameBoard board;
    public enum Feature {SMALL, BIG, SQUARE, CIRCLE, HOLLOW, FULL}
    private int[] featureCount = new int[Feature.values().length];

    public Piece leastLikelyPiece(Piece[] availablePieces, GameBoard board) {
        this.board = board;
        Piece[] boardPieces = getBoardPieces();
        countFeatures(boardPieces);

        Piece leastLikelyPiece = null;
        int minScore = Integer.MAX_VALUE;

        for (Piece piece : availablePieces) {
            int similarityScore = calculateSimilarityScore(piece);
            if (similarityScore < minScore) {
                minScore = similarityScore;
                leastLikelyPiece = piece;
            }
        }
        return leastLikelyPiece;
    }

    private void countFeatures(Piece[] pieces) {
        for (Piece piece : pieces) {
            if (piece.getHeight()) {
                featureCount[Feature.SMALL.ordinal()] += 1;
            } else {
                featureCount[Feature.BIG.ordinal()] += 1;
            }
            if (piece.getShape()) {
                featureCount[Feature.SQUARE.ordinal()] += 1;
            } else {
                featureCount[Feature.CIRCLE.ordinal()] += 1;
            }

            if (piece.getHole()) {
                featureCount[Feature.HOLLOW.ordinal()] += 1;
            } else {
                featureCount[Feature.FULL.ordinal()] += 1;
            }
        }
    }

    private int calculateSimilarityScore(Piece piece) {
        int score = 0;

        if (piece.getHeight()) {
            score += featureCount[Feature.SMALL.ordinal()]; // Penalize for small pieces
        } else {
            score += featureCount[Feature.BIG.ordinal()]; // Penalize for big pieces
        }

        if (piece.getShape()) {
            score += featureCount[Feature.SQUARE.ordinal()]; // Penalize for square shapes
        } else {
            score += featureCount[Feature.CIRCLE.ordinal()]; // Penalize for circle shapes
        }

        if (piece.getHole()) {
            score += featureCount[Feature.HOLLOW.ordinal()]; // Penalize for hollow pieces
        } else {
            score += featureCount[Feature.FULL.ordinal()]; // Penalize for full pieces
        }

        return score;
    }

    private Piece[] getBoardPieces() {
        ArrayList<Piece> boardPieces = new ArrayList<>();
        for (int i = 0; i < 16; i++) {
            Piece piece = board.getPieceById(i);
            if(piece != null){
                boardPieces.add(piece);
            }
        }
        return boardPieces.toArray(new Piece[0]);
    }

}
