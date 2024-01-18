package com.quarto.ai;

import com.quarto.Model.GameBoard;
import com.quarto.Model.Piece;
import com.quarto.Model.QuartoGame;

public class EvaluationFunction {

    public int evaluateBoard(GameBoard board, Piece selectedPiece, QuartoGame game) {
        int totalPoints = 0;

        // Check rows, columns, and diagonals for potential wins
        for (int i = 0; i < 4; i++) {
            int rowProperties = evaluateLine(board.getRow(i), selectedPiece);
            int colProperties = evaluateLine(board.getColumn(i), selectedPiece);

            totalPoints = Math.max(totalPoints, calculatePoints(rowProperties));
            totalPoints = Math.max(totalPoints, calculatePoints(colProperties));
        }

        // Check diagonals
        int diag1Properties = evaluateLine(board.getDiagonal1(), selectedPiece);
        int diag2Properties = evaluateLine(board.getDiagonal2(), selectedPiece);

        totalPoints = Math.max(totalPoints, calculatePoints(diag1Properties));
        totalPoints = Math.max(totalPoints, calculatePoints(diag2Properties));

        // Adjust points based on player's perspective
        if (game.getCurrentPlayer() == game.getWhitePlayer()) {
            totalPoints = -totalPoints;
        }

        return totalPoints;
    }

    private int evaluateLine(Piece[] line, Piece selectedPiece) {
        int sameCount = 0;
        int emptyCount = 0;

        for (Piece piece : line) {
            if (piece == null) {
                emptyCount++;
            } else {
                sameCount += piecesHaveSameProperties(piece, selectedPiece);
            }
        }

        return sameCount * 1000 + emptyCount;
    }

    private int piecesHaveSameProperties(Piece piece1, Piece piece2) {
        if (piece1 == null || piece2 == null) {
            return 0;
        }

        int colorSame = hasSameColor(piece1, piece2) ? 1 : 0;
        int heightSame = hasSameHeight(piece1, piece2) ? 1 : 0;
        int shapeSame = hasSameShape(piece1, piece2) ? 1 : 0;
        int holeSame = hasSameHole(piece1, piece2) ? 1 : 0;

        return colorSame * 1000 + heightSame * 500 + shapeSame * 500 + holeSame * 500;
    }

    private boolean hasSameColor(Piece selectedPiece, Piece otherPiece) {
        return selectedPiece.getColor() == otherPiece.getColor();
    }

    private boolean hasSameHeight(Piece selectedPiece, Piece otherPiece) {
        return selectedPiece.getHeight() == otherPiece.getHeight();
    }

    private boolean hasSameHole(Piece selectedPiece, Piece otherPiece) {
        return selectedPiece.getHole() == otherPiece.getHole();
    }

    private boolean hasSameShape(Piece selectedPiece, Piece otherPiece) {
        return selectedPiece.getShape() == otherPiece.getShape();
    }

    private int calculatePoints(int properties) {
        return switch (properties) {
            case 1000 -> 1000;  // Four in a row
            case 100 -> 10;    // Three in a row
            case 10 -> 5;     // Two in a row
            case 1 -> 1;     // One in a row (potential)
            default -> 0;     // No match
        };
    }
}
