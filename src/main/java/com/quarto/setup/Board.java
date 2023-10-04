package com.quarto.setup;

import java.lang.ref.Reference;
import java.util.ArrayList;
import java.util.List;

public class Board {
    private final int ROWS = 4;
    private final int COLS = 4;
    private final Pieces[][] board = new Pieces[ROWS][COLS];
    private final ArrayList<Pieces> availableWhites = new ArrayList<>();
    private final ArrayList<Pieces> availableBlacks = new ArrayList<>();

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
        availableWhites.addAll(List.of(WSSH, WSSF, WSCH, WSCF, WBSH, WBSF, WBCH, WBCF));
        Pieces BSSH = new Pieces(false,true,true,true);
        Pieces BSSF = new Pieces(false,true,true,false);
        Pieces BSCH = new Pieces(false,true,false,true);
        Pieces BSCF = new Pieces(false,true,false,false);
        Pieces BBSH = new Pieces(false,false,true,true);
        Pieces BBSF = new Pieces(false,false,true,false);
        Pieces BBCH = new Pieces(false,false,false,true);
        Pieces BBCF = new Pieces(false,false,false,false);
        availableBlacks.addAll(List.of(BSSH, BSSF, BSCH, BSCF, BBSH, BBSF, BBCH, BBCF));
    }
    /**
     * returns the arraylist containing the available whites
     * @return
     */
    public ArrayList<Pieces> getAvailableWhites() {
        return availableWhites;
    }
    /**
     * returns the arraylist containing the available blacks
     * @return
     */
    public ArrayList<Pieces> getAvailableBlacks() {
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
        //TODO change this method to acctually do something
        Pieces piece= new Pieces(true,true,true,true);
        return piece;
    }
}
