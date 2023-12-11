package com.quarto.setup;
import com.quarto.player.ai.MoveStrategy;

import javax.swing.*;
import java.util.Arrays;

public class GameLogic {
    private int turnCounter = 1;
    private final Board board;
    private String message = "";
    private Pieces selectedPiece;
    private boolean pvc = false;
    private MoveStrategy moveStrategy;



    /* This happens after the game mode has been chosen
        1.Current move
            1.1 who's turn is it?
            1.2 Opponents picks piece
            1.3 Current player places piece
            1.4 next iteration of the current move is called and current player is Switched
     */
    public GameLogic(){
        this.board = new Board();
    }

    /**
     * method to place a piece on the board increments the turn counter
     * @param selectedPiece piece to place
     * @param tileId position on the board
     * */
    public void placePiece(Pieces selectedPiece, int tileId){
        getBoard().addPiece(selectedPiece, tileId);
        getBoard().removePiece(selectedPiece);
        System.out.println(board);
        incrementTurnCounter();
        System.out.println("turn count: " + turnCounter);
    }

    /**
     * method to check if a move is valid
     * @param tileId position on the board
     * @param selectedPiece piece to place
     * @return false if the move is invalid, true otherwise
     * */
    public boolean moveNotValid(Pieces selectedPiece,int tileId){
        return !getBoard().checkIfPieceIsAvailable(selectedPiece) || getBoard().tileIsOccupied(tileId) || getBoard().isGameWon();
    }

    public void SetSelectedPiece(Pieces piece) {
        this.selectedPiece = piece;
        board.setSelectedPiece(piece);
        System.out.println("selected piece: " + piece);
    }

    /**
     * method to check if the conditions are met for winning
     * @param x position of the row on the board at which the winning piece is positioned
     * @param y position of the column on the board at which the winning piece is positioned
     * @return true or false if the player that placed the piece at position (x, y) won
     * */
    public boolean checkIfWon(int x, int y){
        /*
          1. check vertically and horizontally
               lock one of the coordinates and loop through the other
          2. if possible check diagonally
               2.1 if x and y match check downwards diagonal
               2.2 if x+y=3 check upwards diagonal
         */
        boolean[] checkingArray= new boolean[4];
        Pieces referencePiece= getPieceFromBoard(x,y);
        Arrays.fill(checkingArray, true);

        // Horizontal check
        for (int i = 0; i < board.getBoard().length; i++) {
            if(i==y){break;}
            for (int j = 0; j <referencePiece.Properties.length; j++) {
                if(getPieceFromBoard(x, i)!=null&&referencePiece.Properties[j]!=getPieceFromBoard(x,i).Properties[j]){
                    checkingArray[j]=false;
                }
            }
        }
        for (boolean b : checkingArray) {
            if (b) {
                return true;
            }
        }

        // Vertical check
        for (int i = 0; i < board.getBoard().length; i++) {
            if(i==y){break;}
            if(board.getBoard()[i][y]==null){break;}
            for (int j = 0; j <referencePiece.Properties.length; j++) {
                if(referencePiece.Properties[j]==getPieceFromBoard(i,y).Properties[j]){
                    checkingArray[j]=true;
                }
            }
        }
        for (boolean b : checkingArray) {
            if (b) {
                return true;
            }
        }

        // Diagonal check(downwards)
        if(x==y){
            for (int i = 0; i <board.getBoard().length ; i++) {
                if (i == y) {break;}
                if (board.getBoard()[i][y] == null) {break;}
                for (int j = 0; j <referencePiece.Properties.length ; j++) {
                    if(referencePiece.Properties[j]==getPieceFromBoard(i,i).Properties[j]){
                        checkingArray[j]=true;
                    }
                }

            }
            for (boolean b : checkingArray) {
                if (b) {
                    return true;
                }
            }
        }

        // Diagonal check(Upwards)
        if(x+y==3){
            for (int i = 0; i <board.getBoard().length ; i++) {
                if (i == y) {break;}
                if (board.getBoard()[i][y] == null) {break;}
                for (int j = 0; j <referencePiece.Properties.length ; j++) {
                    int h =3-j;
                    if(referencePiece.Properties[j]==getPieceFromBoard(j,h).Properties[j]){
                        checkingArray[j]=true;
                    }
                }
            }
            for (boolean b : checkingArray) {
                if (b) {
                    return true;
                }
            }
        }
        return false;
    }
    /**
     * method to check if the computer is playing
     * @return true or false depending on the game mode
     * */
    public boolean isPvc() {
        return pvc;
    }

    /**
     * method to get the turn count
     * @return the turn count
     * */
    public int getTurnCounter() {
        return turnCounter;
    }

    /**
     * method to retrieve a piece from a specific position on the board
     * @param x position of the row on the board
     * @param y position of the column on the board
     * @return what piece is stored at that place or null if the tile is empty
     * */
    public Pieces getPieceFromBoard(int x, int y){
        return board.getBoard()[x][y];
    }


    /**
     * method to check if the game is a draw, after
     * all the pieces have been placed on the board
     * @return true or false depending on the state of the game
     * */
    public boolean CheckIfDraw(){
        boolean result = false;
        if(board.getAvailableWhites() == null && board.getAvailableBlacks() == null) {
            for (int i = 0; i < board.getBoard().length; i++) {
                for (int j = 0; j < board.getBoard().length; j++) {
                   if (!checkIfWon(i, j)){
                        result = true;
                    }
                }
            }
        }
        return result;
    }

    /**
     * method to check if the selected piece is the right team's
     * @param piece piece to check
     * @return null if the piece is the wrong colour, otherwise the piece will stay unchanged
     * */
    public Pieces checkSelectedPieceColour(Pieces piece){
        Pieces selectedPiece = piece;
        if(this.turnCounter%2==0){
            setMessage("Black places the selected piece");
            if(selectedPiece.isColor()){
                selectedPiece = null;
                setMessage("Wrong colour!");
            }

        } else {
            setMessage("White places the selected piece");
            if(!selectedPiece.isColor()){
                selectedPiece = null;
                setMessage("Wrong colour!");
            }
        }
        return selectedPiece;
    }

    public void checkTurn(){
        if(this.turnCounter%2==0){
            setMessage("White chooses the opponent's piece");
        } else {
            setMessage("Black chooses the opponent's piece");
        }
    }
    public void checkGameStatus(){
        if(board.isGameWon()){
            if(this.turnCounter%2==0){
                setMessage("White won the game!");
                JOptionPane.showMessageDialog(null, "You have won the game!", "White won!", JOptionPane.INFORMATION_MESSAGE);
            }else {
                setMessage("Black won the game!");
                JOptionPane.showMessageDialog(null, "You have won the game!", "Black won!", JOptionPane.INFORMATION_MESSAGE);
            }
        }
        else if(board.isGameDrawn()){
            setMessage("It's a tie!");
            JOptionPane.showMessageDialog(null, "Game ended in a tie!", "Quarto", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    /*
     * method to add a computer player
     * @param moveStrategy the algorithm the computer will use to play
     */
    public void setComputerPlayer(MoveStrategy moveStrategy) {
        pvc = true;
        this.moveStrategy = moveStrategy;
    }

    public void incrementTurnCounter(){
        this.turnCounter++; board.incrementTurnCounter();
    }

    public void setMessage(String text){
        this.message=(text);
    }
    public String getMessage(){
        return this.message;
    }

    public Pieces getSelectedPiece(){
        return this.selectedPiece;
    }

    public Board getBoard(){
        return this.board;
    }


}


