package com.quarto.setup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Board implements java.io.Serializable{

    private final int ROWS = 4;
    private final int COLS = 4;
    private  Pieces[][] board = new Pieces[ROWS][COLS];
    private  Pieces[] availableWhites;
    private Pieces[] availableBlacks;

    /**
     * constructs our board with all the pieces
     */
    public Board() {
        // First letter White(T) or Black(F)
        // Second letter Small(T) or Big(F)
        // Third letter Square(T) or Circle(F)
        // Fourth letter Hole(T) or Full(F)
        Pieces WSSH = new Pieces(true,true,true,true);
        Pieces WSSF = new Pieces(true,true,true,false);
        Pieces WSCH = new Pieces(true,true,false,true);
        Pieces WSCF = new Pieces(true,true,false,false);
        Pieces WBSH = new Pieces(true,false,true,true);
        Pieces WBSF = new Pieces(true,false,true,false);
        Pieces WBCH = new Pieces(true,false,false,true);
        Pieces WBCF = new Pieces(true,false,false,false);
        ArrayList<Pieces> whitesList = new ArrayList<>(List.of(WSSH, WSSF, WSCH, WSCF, WBSH, WBSF, WBCH, WBCF));
        availableWhites = whitesList.toArray(new Pieces[8]);
        Pieces BSSH = new Pieces(false,true,true,true);
        Pieces BSSF = new Pieces(false,true,true,false);
        Pieces BSCH = new Pieces(false,true,false,true);
        Pieces BSCF = new Pieces(false,true,false,false);
        Pieces BBSH = new Pieces(false,false,true,true);
        Pieces BBSF = new Pieces(false,false,true,false);
        Pieces BBCH = new Pieces(false,false,false,true);
        Pieces BBCF = new Pieces(false,false,false,false);
        ArrayList<Pieces> blacksList = new ArrayList<>(List.of(BSSH, BSSF, BSCH, BSCF, BBSH, BBSF, BBCH, BBCF));
        availableBlacks = blacksList.toArray(new Pieces[8]);
    }
    /**
     * returns the arraylist containing the available whites
     * @return
     */
    public Pieces[] getAvailableWhites() {
        return availableWhites;
    }
    /**
     * returns the arraylist containing the available blacks
     * @return
     */
    public Pieces[] getAvailableBlacks() {
        return availableBlacks;
    }

    /**
     * returns the 2D list representation of the board
     * @return
     */
    public Pieces[][] getBoard() {
        return board;
    }

    @Override
    public String toString() {
        String str = "\n";
        for (Pieces[] row : board) {
            str = str + "|";
            for (Pieces p : row) {
                if (p != null) {
                    str = str + p.toString() + "|";
                }else{
                    str = str + "    |";
                }
            }
            str = str + "\n";
        }
        str = str + "whites: ";
        for(Pieces p : availableWhites){
            if (p != null) {
                str = str + p.toString() + "|";
            }else{
                str = str + "    |";
            }
        }
        str = str + "\n" + "blacks: ";
        for(Pieces p : availableBlacks){
            if (p != null) {
                str = str + p.toString() + "|";
            }else{
                str = str + "    |";
            }
        }
        str = str + "\n";
        return str;
    }

    public boolean tileIsOccupied(int tileId){
        int tile = 0;
        for(int i = 0; i < 4; i++){
            for(int j = 0; j < 4; j++){
                if(tile == tileId){
                    return board[i][j] != null;
                }
                tile++;
            }
        }
        return false;
    }
    public void addPiece(Pieces piece, int tileId){
        int tile = 0;
        for(int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (tile == tileId) {
                    board[i][j] = piece;
                    piece.setRow(i);
                    piece.setCol(j);
                }
                tile++;
            }
        }
    }

    public void removePiece(Pieces Piece) {
        for(int i = 0; i < 8; i++){
            if(availableWhites[i] == Piece){
                availableWhites[i] = null;
            }
            if(availableBlacks[i] == Piece){
                availableBlacks[i] = null;
            }
        }
    }
    public void setAvailableBlacks(Pieces[] availableBlacks) {
        this.availableBlacks = availableBlacks;
    }
    public void setAvailableWhites(Pieces[] availableWhites) {
        this.availableWhites = availableWhites;
    }

    public void setBoard(Pieces[][] board) {
        this.board = board;
    }
}


