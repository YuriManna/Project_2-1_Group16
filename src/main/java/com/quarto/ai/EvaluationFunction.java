package com.quarto.ai;

import com.quarto.Model.GameBoard;
import com.quarto.Model.Piece;
import com.quarto.Model.QuartoGame;

public class EvaluationFunction {

    public int evaluateBoard(GameBoard board, Piece selectedPiece, QuartoGame game){
        int totalPoints = 0;

        // Check rows, columns, and diagonals for potential wins
        for (int i = 0; i < 4; i++) {
            int rowEmptyCount = 0;
            int colEmptyCount = 0;
            int rowSameCount = 0;
            int colSameCount = 0;
            int colProperties = 0;
            int rowProperties = 0;

            for (int j = 0; j < 4; j++) {
                Piece rowPiece = board.getBoard()[i][j];
                Piece colPiece = board.getBoard()[j][i];

                if (rowPiece == null) {
                    rowEmptyCount++;
                } else {
                    rowProperties += piecesHaveSameProperties(rowPiece, selectedPiece);
                    rowSameCount = Math.max(rowSameCount, findMaxDigit(rowProperties));
                }

                if (colPiece == null) {
                    colEmptyCount++;
                } else {
                    colProperties += piecesHaveSameProperties(colPiece, selectedPiece);
                    colSameCount = Math.max(colSameCount, findMaxDigit(colProperties));
                }
            }

            totalPoints = Math.max(calculatePoints(rowSameCount, rowEmptyCount), totalPoints);
            totalPoints = Math.max(calculatePoints(colSameCount, colEmptyCount), totalPoints);
        }

        int diag1EmptyCount = 0;
        int diag2EmptyCount = 0;
        int diag1SameCount = 0;
        int diag2SameCount = 0;
        int diag1Properties = 0;
        int diag2Properties = 0;

        for (int i = 0; i < 4; i++) {
            Piece diag1Piece = board.getBoard()[i][i];
            Piece diag2Piece = board.getBoard()[i][3 - i];

            if (diag1Piece == null) {
                diag1EmptyCount++;
            } else {
                diag1SameCount += piecesHaveSameProperties(diag1Piece, selectedPiece);
                diag1SameCount = Math.max(diag1SameCount, findMaxDigit(diag1Properties));
            }

            if (diag2Piece == null) {
                diag2EmptyCount++;
            } else {
                diag2SameCount += piecesHaveSameProperties(diag2Piece, selectedPiece);
                diag2SameCount = Math.max(diag2SameCount, findMaxDigit(diag2Properties));
            }
        }

        totalPoints = Math.max(calculatePoints(diag1SameCount, diag1EmptyCount), totalPoints);
        totalPoints = Math.max(calculatePoints(diag2SameCount, diag2EmptyCount), totalPoints);


        if(game.getCurrentPlayer()==game.getWhitePlayer()){
            totalPoints = - totalPoints;
        }

        return totalPoints;
    }

    private int piecesHaveSameProperties(Piece piece1, Piece piece2) {
        if (piece1 == null || piece2 == null) {
            return 0;
        }

        int colorSame = hasSameColor(piece1, piece2) ? 1 : 0;
        int heightSame = hasSameHeight(piece1, piece2) ? 1 : 0;
        int shapeSame = hasSameShape(piece1, piece2) ? 1 : 0;
        int holeSame = hasSameHole(piece1, piece2) ? 1 : 0;

        return colorSame * 1000 + heightSame * 100 + shapeSame * 10 + holeSame;
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

    private int calculatePoints(int sameCount, int emptyCount) {
        if (sameCount == 1 && emptyCount == 3) {
            return 1;
        } else if (sameCount == 2 && emptyCount == 2) {
            return 5;
        } else if (sameCount == 3 && emptyCount == 1) {
            return 10;
        } else if(sameCount == 4 && emptyCount == 0){
            return 1000;
        }else {
            return 0;
        }
    }

    private static int findMaxDigit(int number) {
        int maxDigit = 0;
        while (number > 0) {
            int digit = number % 10;
            maxDigit = Math.max(maxDigit, digit);
            number /= 10;
        }

        return maxDigit;
    }
}