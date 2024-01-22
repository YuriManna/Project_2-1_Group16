package com.quarto.ai;

import com.quarto.Model.GameBoard;
import com.quarto.Model.Piece;

public class EvaluationFunction {

    public int evaluateBoard(GameBoard board, Boolean isMaximizing) {
        int totalPoints = 0;

        // Evaluate all rows
        for (int i = 0; i < 4; i++) {
            totalPoints += evaluationOfOneRow(board, i);
        }

        // Evaluate all columns
        for (int j = 0; j < 4; j++) {
            totalPoints += evaluationOfOneCol(board, j);
        }

        // Evaluate both diagonals
        totalPoints += evaluationOfOneDiagonal(board, true);  // Left to right diagonal
        totalPoints += evaluationOfOneDiagonal(board, false); // Right to left diagonal

        // max turn gives positive and min turn gives negetive
        if(isMaximizing){
            totalPoints = - totalPoints;
        }
        return totalPoints;
    }

    public int evaluationOfOneRow(GameBoard board, int row) {
        int[][] trueFalseCounts = new int[4][2]; // Array to store counts for true and false
        int emptyPieces = 0;
        int points = 0;

        for (int j = 0; j < 4; j++) {

            Piece currentPiece = board.getPieceFromBoard(row, j);
            if (currentPiece == null) {
                emptyPieces++;
            }

            for (int k = 0; k < 4; k++) {

                if (currentPiece != null) {
                    trueFalseCounts[k][0] += currentPiece.Properties[k] ? 1 : 0; // Increment count for true
                    trueFalseCounts[k][1] += !currentPiece.Properties[k] ? 1 : 0; // Increment count for false
                }
            }
        }

        for (int l = 0; l < 4; l++) {
            points += calculatePoints(Math.max(trueFalseCounts[l][0], trueFalseCounts[l][1]), emptyPieces);
        }

        return points;
    }

    public int evaluationOfOneCol(GameBoard board, int col) {
        int[][] trueFalseCounts = new int[4][2]; // Array to store counts for true and false
        int emptyPieces = 0;
        int points = 0;

        for (int i = 0; i < 4; i++) {
            Piece currentPiece = board.getPieceFromBoard(i, col);
            if (currentPiece == null) {
                emptyPieces++;
            }

            for (int k = 0; k < 4; k++) {
                if (currentPiece != null) {
                    trueFalseCounts[k][0] += currentPiece.Properties[k] ? 1 : 0; // Increment count for true
                    trueFalseCounts[k][1] += !currentPiece.Properties[k] ? 1 : 0; // Increment count for false
                }
            }
        }

        for (int l = 0; l < 4; l++) {
            points += calculatePoints(Math.max(trueFalseCounts[l][0], trueFalseCounts[l][1]), emptyPieces);
        }

        return points;
    }

    public int evaluationOfOneDiagonal(GameBoard board, boolean leftToRight) {
        int[][] trueFalseCounts = new int[4][2]; // Array to store counts for true and false
        int emptyPieces = 0;
        int points = 0;

        for (int i = 0; i < 4; i++) {
            int j = leftToRight ? i : 3 - i;
            Piece currentPiece = board.getPieceFromBoard(i, j);

            if (currentPiece == null) {
                emptyPieces++;
            }

            for (int k = 0; k < 4; k++) {
                if (currentPiece != null) {
                    trueFalseCounts[k][0] += currentPiece.Properties[k] ? 1 : 0; // Increment count for true
                    trueFalseCounts[k][1] += !currentPiece.Properties[k] ? 1 : 0; // Increment count for false
                }
            }
        }

        for (int l = 0; l < 4; l++) {
            points += calculatePoints(Math.max(trueFalseCounts[l][0], trueFalseCounts[l][1]), emptyPieces);
        }

        return points;
    }

    private int calculatePoints(int sameCount, int emptyCount) {
        // Determine the points based on the count of pieces with the same properties and empty slots
        if (sameCount == 1 && emptyCount == 3) {
            return 1; // 1 point for one piece with the same property and three empty slots
        } else if (sameCount == 2 && emptyCount == 2) {
            return 5; // 5 points for two pieces with the same property and two empty slots
        } else if (sameCount == 3 && emptyCount == 1) {
            return 10; // 10 points for three pieces with the same property and one empty slot
        } else if (sameCount == 4 && emptyCount == 0) {
            return 1000;
        } else {
            return 0; // 0 points for other cases
        }


    }
}
