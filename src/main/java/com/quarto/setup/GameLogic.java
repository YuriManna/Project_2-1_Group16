package com.quarto.setup;
public class GameLogic {
    Pieces chosenPiece;
    static int moveCounter=0;
    /* This happens after the game mode has been chosen
        1.Current move
            1.1 who's turn is it?
            1.2 Opponents picks piece
            1.3 Current player places piece
            1.4 next iteration of the current move is called and current player is Switched
    *
    * comment: have to implement win condition here too

     */
    public static void currentMove(Player player){
        moveCounter++;
        //opponent picks piece that will be placed
        // you place it
    }
    public static void pickOpponentPiece(Player curentPlayersPieces){
        //list out the Pieces
        //Opponenet makes the choice
    }
}
