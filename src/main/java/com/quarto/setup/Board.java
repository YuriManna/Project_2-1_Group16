package com.quarto.setup;

import java.lang.ref.Reference;
import java.lang.ref.Reference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Board {

    private final int ROWS = 4;
    private final int COLS = 4;
    private final Pieces[][] board = new Pieces[ROWS][COLS];
    private final Pieces[] availableWhites;
    private final Pieces[] availableBlacks;

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
    public boolean checkIfWon(int x, int y){
        /**
         * 1. check vertically and horizontally
         *      lock one of the coordinates and loop through the other
         * 2. if possible check diagonally
         *      2.1 if x and y match check downwards diagonal
         *      2.2 if x+y=3 check upwards diagonal
         */
        boolean[] chechkingArray= new boolean[4];
        Pieces referencePiece= getPieceFromBoard(x,y);

        //Horizontal check
        for (int i = 0; i < board.length; i++) {
            if(i==y){break;}
            if(board[x][i]==null){break;}
            for (int j = 0; j <referencePiece.Properties.length; j++) {
                if(referencePiece.Properties[j]==getPieceFromBoard(x,i).Properties[j]){
                    chechkingArray[j]=true;
                }
            }
        }
        for (int j = 0; j <chechkingArray.length; j++) {
            if(chechkingArray[j]==true){return true;}
        }

        //Vertical check
        for (int i = 0; i < board.length; i++) {
            if(i==y){break;}
            if(board[i][y]==null){break;}
            for (int j = 0; j <referencePiece.Properties.length; j++) {
                if(referencePiece.Properties[j]==getPieceFromBoard(i,y).Properties[j]){
                    chechkingArray[j]=true;
                }
            }
        }
        for (int j = 0; j <chechkingArray.length; j++) {
            if(chechkingArray[j]==true){return true;}
        }
        //diagonal check(downwards)
        if(x==y){
            for (int i = 0; i <board.length ; i++) {
                if (i == y) {break;}
                if (board[i][y] == null) {break;}
                for (int j = 0; j <referencePiece.Properties.length ; j++) {
                    if(referencePiece.Properties[j]==getPieceFromBoard(i,i).Properties[j]){
                        chechkingArray[j]=true;
                    }
                }

            }
            for (int j = 0; j <chechkingArray.length; j++) {
                if(chechkingArray[j]==true){return true;}
            }
        }
        //diagonal check(Upwards)
        if(x+y==3){
            for (int i = 0; i <board.length ; i++) {
                if (i == y) {break;}
                if (board[i][y] == null) {break;}
                for (int j = 0; j <referencePiece.Properties.length ; j++) {
                    int h =3-j;
                    if(referencePiece.Properties[j]==getPieceFromBoard(j,h).Properties[j]){
                        chechkingArray[j]=true;
                    }

                }
            }
            for (int j = 0; j <chechkingArray.length; j++) {
                if(chechkingArray[j]==true){return true;}
            }
        }

        return false;
    }
    public Pieces getPieceFromBoard(int x, int y){
        return board[y][x];
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
    public int toTileID(int x, int y){
        if(y==0){
            return x;
        }
        else{
            return (4*y+x);
        }
    }
}


