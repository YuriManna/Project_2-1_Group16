package com.quarto.setup;

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
    public static void main(String[] args) {
        Board board = new Board();
        Pieces WSSH = new Pieces(true,true,true,true);
        System.out.println(WSSH);
        board.getBoard()[3][3] = WSSH;
        System.out.println(board.tileIsOccupied(15));
    }
}


