package com.quarto.player.ai;

import com.quarto.setup.Board;
import com.quarto.setup.Pieces;

public class evaluationFunction {
    
    // Method to evaluate the game board based on the selected piece
    //TODO:positive value for the maximizing player and a negative value for the minimizing player. need to be discussed
    public int evaluateBoard(Board board, Pieces selectedPiece) {
        // Variable to store the total points earned by the evaluation
        int totalPoints = 0;
        
        //check if board is won, set point to 1000 is draw set to 0
        //TODO: add here later
        //if(board.checkIfWon()){
        //    return totalPoints = 1000;
        //}else if(board.checkIfDraw()){
        //    return totalPoints = 0;
        //}

        // Check rows, columns, and diagonals for potential wins
        for (int i = 0; i < 4; i++) {
            // Variables to track the count of empty slots and pieces with the same properties
            int rowEmptyCount = 0;
            int colEmptyCount = 0;
            int rowSameCount = 0;
            int colSameCount = 0;
    
            // Loop through the row and column of the board
            for (int j = 0; j < 4; j++) {
                // Get pieces from the row and column
                Pieces rowPiece = board.getPieceFromBoard(i, j);
                Pieces colPiece = board.getPieceFromBoard(j, i);
    
                // Check for empty slots and pieces with the same properties in the row
                if (rowPiece == null) {
                    rowEmptyCount++;
                } else {
                    rowSameCount += piecesHaveSameProperties(rowPiece, selectedPiece);
                }
    
                // Check for empty slots and pieces with the same properties in the column
                if (colPiece == null) {
                    colEmptyCount++;
                } else {
                    colSameCount += piecesHaveSameProperties(colPiece, selectedPiece);
                }
            }
    
            // Calculate points for the row and column and add to the total points
            totalPoints = Math.max(calculatePoints(rowSameCount, rowEmptyCount), totalPoints);
            totalPoints = Math.max(calculatePoints(colSameCount, colEmptyCount), totalPoints);

        }
    
        // Check diagonals for potential wins
        int diag1EmptyCount = 0;
        int diag2EmptyCount = 0;
        int diag1SameCount = 0;
        int diag2SameCount = 0;
    
        // Loop through the diagonals of the board
        for (int i = 0; i < 4; i++) {
            // Get pieces from the diagonals
            Pieces diag1Piece = board.getPieceFromBoard(i, i);
            Pieces diag2Piece = board.getPieceFromBoard(3 - i, i);
    
            // Check for empty slots and pieces with the same properties in diagonal 1
            if (diag1Piece == null) {
                diag1EmptyCount++;
            } else {
                diag1SameCount += piecesHaveSameProperties(diag1Piece, selectedPiece);
            }
    
            // Check for empty slots and pieces with the same properties in diagonal 2
            if (diag2Piece == null) {
                diag2EmptyCount++;
            } else {
                diag2SameCount += piecesHaveSameProperties(diag2Piece, selectedPiece);
            }
        }
    
        // Calculate points for the diagonals and add to the total points
        totalPoints = Math.max(calculatePoints(diag1SameCount, diag1EmptyCount), totalPoints);
        totalPoints = Math.max(calculatePoints(diag2SameCount, diag2EmptyCount), totalPoints);

        return totalPoints;
    }
    
    // Method to check if two pieces have the same properties and return the count of the most occurring property
    private int piecesHaveSameProperties(Pieces piece1, Pieces piece2) {
        // Check for null pieces
        if (piece1 == null || piece2 == null) {
            return 0;
        }
    
        // Variables to count occurrences of each property
        int colorCount = hasSameColor(piece1, piece2) ? 1 : 0;
        int heightCount = hasSameHeight(piece1, piece2) ? 1 : 0;
        int shapeCount = hasSameShape(piece1, piece2) ? 1 : 0;
        int holeCount = hasSameHole(piece1, piece2) ? 1 : 0;
    
        // Find the maximum count among the properties
        int maxCount = Math.max(colorCount, Math.max(heightCount, Math.max(shapeCount, holeCount)));
    
        return maxCount;
    }
    
    private boolean hasSameColor(Pieces selectedPiece, Pieces otherPiece) {
        return selectedPiece.getColor() == otherPiece.getColor();
    }
    
    private boolean hasSameHeight(Pieces selectedPiece, Pieces otherPiece) {
        return selectedPiece.getHeight() == otherPiece.getHeight();
    }
    
    private boolean hasSameHole(Pieces selectedPiece, Pieces otherPiece) {
        return selectedPiece.getHole() == otherPiece.getHole();
    }
    
    private boolean hasSameShape(Pieces selectedPiece, Pieces otherPiece) {
        return selectedPiece.getShape() == otherPiece.getShape();
    }
    
    // Method to calculate points based on the count of pieces with the same properties and empty slots
    private int calculatePoints(int sameCount, int emptyCount) {
        // Determine the points based on the count of pieces with the same properties and empty slots
        if (sameCount == 1 && emptyCount == 3) {
            return 1; // 1 point for one piece with the same property and three empty slots
        } else if (sameCount == 2 && emptyCount == 2) {
            return 5; // 5 points for two pieces with the same property and two empty slots
        } else if (sameCount == 3 && emptyCount == 1) {
            return 10; // 10 points for three pieces with the same property and one empty slot
        } else {
            return 0; // 0 points for other cases
        }
    }

    // Main method for testing the evaluation function
    public static void main(String[] args) {
        // Create a sample board
        Board board = new Board();
        evaluationFunction n = new evaluationFunction();
        // Create a sample selected piece
        Pieces selectedPiece = new Pieces(true, true, true, true);

        // Place some pieces on the board for testing
        board.addPiece(new Pieces(true, true, true, false), 0); // Example piece with a different hole property
        board.addPiece(new Pieces(true, true, true, true), 7);  // Example piece with the same properties
        board.addPiece(new Pieces(false, true, false, false), 3);  // Example piece with the same properties

        System.out.println(board.toString());
        
        // Evaluate the board
        int evaluation = n.evaluateBoard(board, selectedPiece);

        System.out.println("Board Evaluation: " + evaluation);
    }
}
