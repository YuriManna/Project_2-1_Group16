package com.quarto.setup;

import java.util.ArrayList;
import java.util.List;

public class Board {

    private final int ROWS = 4;
    private final int COLS = 4;
    private final Pieces[][] board = new Pieces[ROWS][COLS];
    private final Pieces[] availableWhites;
    private final Pieces[] availableBlacks;
    private Pieces selectedPiece;
    private boolean gameWon = false;
    private boolean gameDrawn = false;

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

        this.selectedPiece = null;
    }
    /**
     * @return the arraylist containing the available whites
     */
    public Pieces[] getAvailableWhites() {
        return availableWhites;
    }
    /**
     * @return the arraylist containing the available blacks
     */
    public Pieces[] getAvailableBlacks() {
        return availableBlacks;
    }
    /**
     * @param color team color
     * @return the arraylist containing the available pieces depending on the color
     */
    public Pieces[] getAvaileblePieces(boolean color){
        Pieces[] pieces;
        if(color) {
            pieces = availableWhites;
        }
        else{
            pieces = availableBlacks;
        }
        return pieces;
    }

    public void setSelectedPiece(Pieces piece){selectedPiece=piece;}
    public Pieces getSelectedPiece(){return selectedPiece;}
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

    public List<Integer> getAvailableTileIds(){
        List<Integer> availableTiles = new ArrayList<>();
        for(int i = 0; i < 16; i++) {
            if (!tileIsOccupied(i)) {
                availableTiles.add(i);
            }
        }
        return availableTiles;
    }

    public void addPiece(Pieces piece, int tileId){
        if(gameWon){return;}
        int tile = 0;
        int x = 0;
        int y = 0;
        for(int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (tile == tileId) {
                 //   System.out.println("Board 127 Piece has been placed");
                    board[i][j] = piece;
                    System.out.println("Board 127: remeber this change x<->y");
                    x = i;
                    y = j;
                }
                tile++;
            }
        }
        if(checkIfWon(x,y)){
            gameWon = true;
            System.out.println("game has been won!");
        }
        else if(checkIfDraw()){
            gameDrawn = true;
            System.out.println("game ended in a tie!");

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


    /**
     * method to check if the conditions are met for winning
     * @param x position of the row on the board at which the winning piece is positioned
     * @param y position of the column on the board at which the winning piece is positioned
     * @return true or false if the player that placed the piece at position (x, y) won
     * */
    public boolean checkIfWon(int x, int y){
        //("x: "+x+" y: "+y);
        /**
         * 0. check wether the row/collumn/diagonal is full
         * 1. check vertically and horizontally
         *      lock one of the coordinates and loop through the other
         * 2. if possible check diagonally
         *      2.1 if x and y match check downwards diagonal
         *      2.2 if x+y=3 check upwards diagonal
         */
        boolean[] chechkingArray= new boolean[4];
        int count=0; //keeps track of how many cycles the individual checks go through
        Pieces referencePiece= getPieceFromBoard(x,y);
        for (int i = 0; i <chechkingArray.length; i++) {
            chechkingArray[i]=true;
        }

        //Horizontal check
        for (int i = 0; i < board.length; i++) {//Loops through horizontal line
            if(getPieceFromBoard(x, i)==null){ break;}
            for (int j = 0; j <referencePiece.Properties.length; j++) {//Loops through the properties of the pieces that are being compared
                if(i==y){break;}
                if(chechkingArray[j]!=false&&getPieceFromBoard(x, i)!=null&&referencePiece.Properties[j]==getPieceFromBoard(x,i).Properties[j]){
                    chechkingArray[j]=true;
                }
                else{chechkingArray[j]=false;}

            }
            count++;
        }
        for (int i = 0; i <chechkingArray.length; i++) {
            System.out.print(chechkingArray[i]+" ");
        }
        if(count==4) {
            for (int j = 0; j < chechkingArray.length; j++) {
                if (chechkingArray[j] == true) {
                    return true;
                }
            }
        }
        //reset variables for new check
        count=0;
        for (int i = 0; i < chechkingArray.length; i++) {
            chechkingArray[i]=true;
        }
        //Vertical check

        for (int i = 0; i < board.length; i++) {
            if(getPieceFromBoard(i, y)==null){ break;}
            for (int j = 0; j <referencePiece.Properties.length; j++) {
                if(i==x){break;}
                if(chechkingArray[j]!=false&&referencePiece.Properties[j]==getPieceFromBoard(i,y).Properties[j]){
                    chechkingArray[j]=true;
                }else{chechkingArray[j]=false;}

            }
            count++;
        }
        if(count==4) {
            for (int j = 0; j < chechkingArray.length; j++) {
                if (chechkingArray[j] == true) {
                    return true;
                }
            }
        }
        //reset variables for new check
        count=0;
        for (int i = 0; i < chechkingArray.length; i++) {
            chechkingArray[i]=true;
        }

        // Diagonal check(downwards)
        if(x==y){
            for (int i = 0; i <board.length ; i++) {
                if(getPieceFromBoard(i, i)==null){break;}
                for (int j = 0; j <referencePiece.Properties.length ; j++) {
                    if (i == y) {break;}
                    if(chechkingArray[j]!=false&&referencePiece.Properties[j]==getPieceFromBoard(i,i).Properties[j]){
                        chechkingArray[j]=true;
                    }else{chechkingArray[j]=false;}

                }
                count++;
            }
            if(count==4) {
                for (int j = 0; j < chechkingArray.length; j++) {
                    if (chechkingArray[j] == true) {
                        return true;
                    }
                }
            }
        }
        //reset variables for new check
        count=0;
        for (int i = 0; i < chechkingArray.length; i++) {
            chechkingArray[i]=true;
        }

        // Diagonal check(Upwards)
        if(x+y==3){
            for (int i = 0; i < board.length; i++) {
                if(getPieceFromBoard(3-i, i)==null){break;}
                for (int j = 0; j <referencePiece.Properties.length ; j++) {
                    if(i==y){break;}
                    if(chechkingArray[j]!=false&&referencePiece.Properties[j]==getPieceFromBoard(3-i,i).Properties[j]){
                        chechkingArray[j]=true;
                }else{chechkingArray[j]=false;}
            }
                count++;
            }
            if(count==4) {
                for (int j = 0; j < chechkingArray.length; j++) {
                    if (chechkingArray[j] == true) {

                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * method check if a piece is available
     * @param piece piece to be checked
     * @return true if available, false if not
     * */
    public boolean checkIfPieceIsAvailable(Pieces piece){

        for (int i = 0; i < (availableWhites.length); i++) {
            if (piece == availableWhites[i]){
                return true;
            }
        }
        for (int i = 0; i < (availableBlacks.length); i++) {
            if (piece == availableBlacks[i]){
                return true;
            }
        }
        return false;
    }

    /**
     * method to retrieve a piece from a specific position on the board
     * @param x position of the row on the board
     * @param y position of the column on the board
     * @return what piece is stored at that place or null if the tile is empty
     * */
    public Pieces getPieceFromBoard(int x, int y){
        return board[x][y];
    }

    /**
     * method to check if the game is a draw
     * @return true or false depending on the state of the game
     * */
    public boolean checkIfDraw(){
        for(int i = 0; i < 4; i++){
            for(int j = 0; j < 4; j++){
                if (board[i][j] == null){
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isGameWon() {
        return gameWon;
    }

    public boolean isGameDrawn() {
        return gameDrawn;
    }
}


