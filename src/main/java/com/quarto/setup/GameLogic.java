package com.quarto.setup;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

import static com.sun.java.accessibility.util.AWTEventMonitor.addMouseListener;
import static javax.swing.SwingUtilities.isLeftMouseButton;

public class GameLogic {
    private int turnCounter = 1;
    private final Board board;
    private String message = "";

    private Pieces selectedPiece;


    /* This happens after the game mode has been chosen
        1.Current move
            1.1 who's turn is it?
            1.2 Opponents picks piece
            1.3 Current player places piece
            1.4 next iteration of the current move is called and current player is Switched
    *
    * comment: have to implement win condition here too

     */
    public GameLogic(){
        this.board = new Board();
    }

    public void currentMove(Player player){

        //opponent picks piece that will be placed
        // you place it
    }
    public void pickOpponentPiece(Player curentPlayersPieces){
        //list out the Pieces nad pick
        //Remove the piece from the available pieces
        //Opponenet makes the choice
    }
    //TODO from tile ID to x y
    //TODO return boolean if piece can be placed
    /*
    1.CurrentMove
    2.PickPiece
    3.Translation from ID-> coordinates
    4.Move form Board winCondition
        4.1 If all pieces placed ->Draw
     */


    public Pieces checkSelectedPieceColour(Pieces piece){
        Pieces selectedPiece = piece;
        if(this.turnCounter%2==0){
            setMessage("Player 2 places the selected piece");
            if(selectedPiece.toString().charAt(0)!='B'){
                selectedPiece = null;
                setMessage("Wrong colour!");
            }

        } else {
            setMessage("Player 1 places the selected piece");
            if(selectedPiece.toString().charAt(0)!='W'){
                selectedPiece = null;
                setMessage("Wrong colour!");
            }
        }
        return selectedPiece;
    }

    public void checkTurn(){
        if(this.turnCounter%2==0){
           setMessage("Player 2 chooses the opponent's piece");
        } else {
            setMessage("Player 1 chooses the opponent's piece");
        }
    }

    public void incrementTurnCounter(){
        this.turnCounter++;
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

    public void setPiece(Pieces piece) {
        this.selectedPiece = piece;
    }
    public Board getBoard(){
        return this.board;
    }

    public void checkIfWon(){

    }


}


