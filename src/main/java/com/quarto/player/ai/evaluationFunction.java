package com.quarto.player.ai;

import java.util.ArrayList;
import java.util.List;

import com.quarto.setup.Board;
import com.quarto.setup.Pieces;

public class evaluationFunction {
    
    public Board board;
    public Pieces selectedPiece;
    
    public evaluationFunction(Board board){
        this.board = board;
        this.selectedPiece = board.getSelectedPiece();
    }
    
/* USE METHOD evaluateBoard TO EVALUATE BOARD.
 * USE METHOD worstPiece TO CHOOSE WORST PIECE FOR OPPONENT
 * 
 * 
 */





    public Pieces worstPiece(Pieces[]availablePieces){
        int[]points =  new int[availablePieces.length];
        for(int i = 0; i<availablePieces.length;i++){
            if(availablePieces[i]!=null){
            points[i]=evaluateSelectedPiece(availablePieces[i]);  
            }else{
            points[i]=99999;  
            }
        }

        int smallestIndex = 0;

        for (int i = 1; i < points.length; i++) {
            if (points[i] < points[smallestIndex]) {
                smallestIndex = i;
            }
        }
        
        for(int k =0;k<points.length;k++){
            System.out.println(points[k]);
        }

        return availablePieces[smallestIndex];
    }

    // Method to evaluate the game board based on the selected piece
    public int evaluateSelectedPiece(Pieces select) {
        // Variable to store the total points earned by the evaluation
        int totalPoints = 0;
        
        // Check rows, columns, and diagonals for potential wins
        for (int i = 0; i < 4; i++) {
            // Variables to track the count of empty slots and pieces with the same properties
            int rowEmptyCount = 0;
            int colEmptyCount = 0;
            int rowSameCount = 0;
            int colSameCount = 0;
            int colProperties = 0;
            int rowProperties = 0;
            // Loop through the row and column of the board
            for (int j = 0; j < 4; j++) {
                // Get pieces from the row and column
                Pieces rowPiece = board.getPieceFromBoard(i, j);
                Pieces colPiece = board.getPieceFromBoard(j, i);
    
                // Check for empty slots and pieces with the same properties in the row
                if (rowPiece == null) {
                    rowEmptyCount++;
                } else {
                    rowProperties += piecesHaveSameProperties(rowPiece, select);
                    rowSameCount = Math.max(rowSameCount, findMaxDigit(rowProperties));
                }
    
                // Check for empty slots and pieces with the same properties in the column
                if (colPiece == null) {
                    colEmptyCount++;
                } else {
                    colProperties += piecesHaveSameProperties(colPiece, select);
                    colSameCount = Math.max(colSameCount, findMaxDigit(colProperties));

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
        int diag1Properties = 0;
        int diag2Properties = 0;
    
        // Loop through the diagonals of the board
        for (int i = 0; i < 4; i++) {
            // Get pieces from the diagonals
            Pieces diag1Piece = board.getPieceFromBoard(i, i);
            Pieces diag2Piece = board.getPieceFromBoard(3 - i, i);
    
            // Check for empty slots and pieces with the same properties in diagonal 1
            if (diag1Piece == null) {
                diag1EmptyCount++;
            } else {
                diag1SameCount += piecesHaveSameProperties(diag1Piece, select);
                diag1SameCount = Math.max(diag1SameCount, findMaxDigit(diag1Properties));

            }
    
            // Check for empty slots and pieces with the same properties in diagonal 2
            if (diag2Piece == null) {
                diag2EmptyCount++;
            } else {
                diag2SameCount += piecesHaveSameProperties(diag2Piece, select);
                diag1SameCount = Math.max(diag2SameCount, findMaxDigit(diag2Properties));

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
    
        // Variables to represent whether each property is the same
        int colorSame = hasSameColor(piece1, piece2) ? 1 : 0;
        int heightSame = hasSameHeight(piece1, piece2) ? 1 : 0;
        int shapeSame = hasSameShape(piece1, piece2) ? 1 : 0;
        int holeSame = hasSameHole(piece1, piece2) ? 1 : 0;
    
        // Construct the binary representation
        return colorSame * 1000 + heightSame * 100 + shapeSame * 10 + holeSame;
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
            return 10; // 5 points for two pieces with the same property and two empty slots
        } else if (sameCount == 3 && emptyCount == 1) {
            return 100; // 10 points for three pieces with the same property and one empty slot
        } else if(sameCount == 4 && emptyCount == 0){
            return 1000;
        }else {
            return 0; // 0 points for other cases
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

    
    
/////////////////////////////////////////////



    public int evaluationOfOneRow(Board board, int row) {
        int[][] trueFalseCounts = new int[4][2]; // Array to store counts for true and false
        int emptyPieces = 0;
        int points = 0;

        for (int j = 0; j < 4; j++) {
            
            Pieces currentPiece = board.getPieceFromBoard(row, j);
            if (currentPiece == null)
            {emptyPieces++;}

            for (int k =0;k<4;k++){

            if (currentPiece != null){
                trueFalseCounts[k][0] += currentPiece.Properties[k] ? 1 : 0; // Increment count for true
                trueFalseCounts[k][1] += !currentPiece.Properties[k] ? 1 : 0; // Increment count for false
            }
            }
        }
        
        for(int l = 0; l<4;l++ ){
            points += calculatePoints(Math.max(trueFalseCounts[l][0],trueFalseCounts[l][1]),emptyPieces);
        }
        
        return points;
    }

    public int evaluationOfOneCol(Board board, int col) {
        int[][] trueFalseCounts = new int[4][2]; // Array to store counts for true and false
        int emptyPieces = 0;
        int points = 0;

        for (int i = 0; i < 4; i++) {
            Pieces currentPiece = board.getPieceFromBoard(i, col);
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

    public int evaluationOfOneDiagonal(Board board, boolean leftToRight) {
        int[][] trueFalseCounts = new int[4][2]; // Array to store counts for true and false
        int emptyPieces = 0;
        int points = 0;

        for (int i = 0; i < 4; i++) {
            int j = leftToRight ? i : 3 - i;
            Pieces currentPiece = board.getPieceFromBoard(i, j);
            
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

    
    public int evaluateBoard(Board board) {
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
        if(board.getCurrentPlayer()==board.whitePlayer){
            totalPoints = - totalPoints;
        }


        return totalPoints;
    }






    // Main method for testing the evaluation function
    public static void main(String[] args) {
        // Create a sample board
        Board board = new Board();
        
        // Create a sample selected piece
        Pieces selectedPiece = new Pieces(true, true, true, true);
        board.setSelectedPiece(selectedPiece);
        System.out.println(selectedPiece.toString()); 
        // Place some pieces on the board for testing
        Pieces wssf = new Pieces(true, true, true, false);
        
        board.addPiece(wssf, 0); // Example piece with a different hole property
        board.addPiece(new Pieces(true, true, true, true), 7);  // Example piece with the same properties
        board.addPiece(new Pieces(false, true, false, false), 3);  // Example piece with the same properties
        board.addPiece(new Pieces(false, false, false, true), 4); // Example piece with a different hole property
        board.addPiece(new Pieces(false, false, false, true), 8); // Example piece with a different hole property

       
        evaluationFunction n = new evaluationFunction(board);

        System.out.println(board.toString());
        
        // Evaluate the board
        int evaluation = n.evaluateBoard(board);

        System.out.println("Board Evaluation: " + evaluation);

        Pieces[] a =board.getAvailableWhites();
        a[0]=null;
        a[1]=null;

        Pieces worst= n.worstPiece(a);
        System.out.println(worst.toString());
    }
}
