package com.quarto.Model;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.quarto.ai.EvaluationFunction;

/*
RESPONSIBILITIES:
    Represent the game board state.
    Keep track of the positions of Piece on the board.
    Provide methods to check for a winner, update the board, etc.
*/

public class GameBoard {
    private final int ROWS = 4;
    private final int COLS = 4;
    private Piece[][] BOARD = new Piece[ROWS][COLS];
    private ArrayList<Piece> whitesList;
    private Piece[] whitesArray;
    private Piece[] blacksArray;
    private ArrayList<Piece> blacksList;


    public GameBoard() {
        // Sets up dimensions of the board (aka the grid)
        // Initialize Piece for board
        // First letter White(T) or Black(F)
        // Second letter Small(T) or Big(F)
        // Third letter Square(T) or Circle(F)
        // Fourth letter Hole(T) or Full(F)
        Piece WSSH = new Piece(true, true, true, true);
        Piece WSSF = new Piece(true, true, true, false);
        Piece WSCH = new Piece(true, true, false, true);
        Piece WSCF = new Piece(true, true, false, false);
        Piece WBSH = new Piece(true, false, true, true);
        Piece WBSF = new Piece(true, false, true, false);
        Piece WBCH = new Piece(true, false, false, true);
        Piece WBCF = new Piece(true, false, false, false);
        whitesList = new ArrayList<>(List.of(WSSH, WSSF, WSCH, WSCF, WBSH, WBSF, WBCH, WBCF));
        whitesArray = whitesList.toArray(new Piece[0]);
        Piece BSSH = new Piece(false, true, true, true);
        Piece BSSF = new Piece(false, true, true, false);
        Piece BSCH = new Piece(false, true, false, true);
        Piece BSCF = new Piece(false, true, false, false);
        Piece BBSH = new Piece(false, false, true, true);
        Piece BBSF = new Piece(false, false, true, false);
        Piece BBCH = new Piece(false, false, false, true);
        Piece BBCF = new Piece(false, false, false, false);
        blacksList = new ArrayList<>(List.of(BSSH, BSSF, BSCH, BSCF, BBSH, BBSF, BBCH, BBCF));
        blacksArray = blacksList.toArray(new Piece[0]);
    }

    //GETTERS AND SETTERS
    public ArrayList<Piece> getBlacksList() { return blacksList; }

    public ArrayList<Piece> getWhitesList() { return whitesList; }
    public Piece[] getWhitesArray() { return whitesArray; }
    public Piece[] getBlacksArray() { return blacksArray; }

    public Piece[][] getBoard() {
        return BOARD;
    }

    public Piece getPieceById(int tileId){
        int[] coord = convertTileIdToRowAndColumn(tileId);
        return BOARD[coord[0]][coord[1]];
    }
    public void setPiecesArray(Piece[] pieceArray, boolean isWhite){
        if(isWhite) {
            this.whitesArray = pieceArray;
        }else {
            this.blacksArray = pieceArray;
        }
    }

    //OTHER METHODS
    //==================================================================================================================


    public int getIdByPiece(Piece piece){
        for(int i = 0; i < ROWS; i++){
            for(int j = 0; j < COLS; j++){
                if(BOARD[i][j] == piece){
                    return convertRowAndColumnToTileId(i,j);
                }
            }
        }
        return 0;
    }

    public Piece getPieceFromBoard(int row, int j) {
        return this.BOARD[row][j];
    }

    // Add piece to the board (assign piece to a tile on the board)
    public void addPieceToBoard(Move move) {
        int[] rowAndCol = convertTileIdToRowAndColumn(move.getTileId());
        int row = rowAndCol[0];
        int col = rowAndCol[1];
        BOARD[row][col] = move.getPiece();
    }

    // Check if the game is a draw (no more valid moves available)
    public boolean checkDrawCondition() {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                if (BOARD[i][j] == null) {
                    return false; // At least one empty space is available, game is not a draw
                }
            }
        }
        return true; // All spaces on the board are filled, game is a draw
    }

    // Check if the move is valid
    public boolean isValidMove(Move move) {
        int tileId = move.getTileId();
        return tileId >= 0 && tileId < 16 && BOARD[convertTileIdToRowAndColumn(tileId)[0]][convertTileIdToRowAndColumn(tileId)[1]] == null;
    }

    private int convertRowAndColumnToTileId(int row, int col) {
        return row * COLS + col;
    }

    // Convert tileId to row and column
    private int[] convertTileIdToRowAndColumn(int tileId) {
        int row = tileId / COLS;
        int col = tileId % COLS;
        return new int[]{row, col};
    }

    public boolean isPieceOnBoard(Piece piece) {
        for (Piece[] row : BOARD) {
            for (Piece currentPiece : row) {
                if (currentPiece != null && currentPiece.equals(piece)) {
                    return true;
                }
            }
        }
        return false;
    }



    // Check if there is a winner (basic implementation)
    public boolean checkWinCondition() {
        // Check rows
        for (int i = 0; i < ROWS; i++) {
            if (checkLine(BOARD[i][0], BOARD[i][1], BOARD[i][2], BOARD[i][3])) {
                return true;
            }
        }

        // Check columns
        for (int j = 0; j < COLS; j++) {
            if (checkLine(BOARD[0][j], BOARD[1][j], BOARD[2][j], BOARD[3][j])) {
                return true;
            }
        }

        // Check diagonals
        return checkLine(BOARD[0][0], BOARD[1][1], BOARD[2][2], BOARD[3][3]) || checkLine(BOARD[0][3], BOARD[1][2], BOARD[2][1], BOARD[3][0]);
    }

    // Helper method to check if all pieces in a line are the same
    private boolean checkLine(Piece a, Piece b, Piece c, Piece d) {
        if(a==null||b==null||c==null||d==null){return false;}
        int count = 0;
        boolean[][] checkArr = {a.getProperties(), b.getProperties(), c.getProperties(), d.getProperties()};
        for (int i = 0; i < checkArr.length; i++) {
            count++;
            for (int j = 0; j < checkArr.length; j++) {
                if (j != 3 && checkArr[j][i] == checkArr[j + 1][i]) {
                    count++;
                    if (count == 4) {
                        return true;
                    }
                }
            }
            count = 0;
        }
        return false;
    }

    

    //minimax bullshit:
    public void removePieceFromBoard(Move move) {
        int[] rowAndCol = convertTileIdToRowAndColumn(move.getTileId());
        int row = rowAndCol[0];
        int col = rowAndCol[1];
        BOARD[row][col] = null;
    }

    //qlearning bullshit
    public boolean isGameFinished() {
    return checkWinCondition() || checkDrawCondition();
    }



    public String gameStateToCSV(Piece chosenPiece, QuartoGame game, boolean isWhitesTurn) {
        StringBuilder sb = new StringBuilder();
    
        // Iterate over each piece in the game board
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                // Get the piece at the current position
                Piece piece = BOARD[i][j];
    
                // If the position is occupied, append the properties of the piece to the CSV string
                if (piece != null) {
                    sb.append(piece.getColor() ? 1 : 0).append(",");
                    sb.append(piece.getHeight() ? 1 : 0).append(",");
                    sb.append(piece.getShape() ? 1 : 0).append(",");
                    sb.append(piece.getHole() ? 1 : 0).append(",");
                }
                // If the position is not occupied, append zeros to the CSV string
                else {
                    sb.append("-1,-1,-1,-1,");
                }
            }
        }

        // regular move reward
        EvaluationFunction ef = new EvaluationFunction();
        int reward = ef.evaluateBoard(this, chosenPiece, game);
        if(!isWhitesTurn){
            reward = -reward;       
        }

        sb.append(Math.abs(reward));
        return sb.toString();
    }

    public Piece[] getRow(int i) {
        return BOARD[i];
    }

    public Piece[] getColumn(int i) {
        Piece[] column = new Piece[4];
        for (int j = 0; j < 4; j++) {
            column[j] = BOARD[j][i];
        }
        return column;
    }


    public Piece[] getDiagonal1() {
        Piece[] diagonal = new Piece[4];
        for (int i = 0; i < 4; i++) {
            diagonal[i] = BOARD[i][i];
        }
        return diagonal;
    }


    public Piece[] getDiagonal2() {
        Piece[] diagonal = new Piece[4];
        for (int i = 0; i < 4; i++) {
            diagonal[i] = BOARD[i][3 - i];
        }
        return diagonal;
    }
}
