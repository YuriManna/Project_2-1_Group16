package com.quarto.View.Console;

import com.quarto.Model.GameBoard;
import com.quarto.Model.Piece;
import com.quarto.Model.Player;

public class ConsoleGame {

    private final GameBoard board;
    private final Player whitePlayer;
    private final Player blackPlayer;


    public ConsoleGame(GameBoard board, Player player1, Player player2) {
        this.board = board;
        this.whitePlayer = player1;
        this.blackPlayer = player2;
    }

    public void renderConsole() {

    }

    //Show the current board
    private void showBoard() {
            System.out.println("Current Board:");
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    Piece piece = board.getBoard()[i][j];
                    if (piece != null) {
                        // Display the piece details
                        System.out.print(showPiece(piece));
                        System.out.print(" ");
                    } else {
                        // Display an empty space
                        System.out.print("---- ");
                    }
                }
                System.out.println(); // Move to the next row
            }
            System.out.println(); // Add an empty line for better readability

    }

    //helper method to show the piece
    private String showPiece(Piece piece)
    {
        char C,He,S,Ho;
        if(piece.getColor()){C = 'W';}
        else{C = 'B';}
        if(piece.getHeight()){He = 'S';}
        else{He = 'B';}
        if(piece.getShape()){S = 'S';}
        else{S = 'C';}
        if(piece.getHole()){Ho = 'H';}
        else{Ho = 'F';}
        return ""+ C + He + S + Ho;
    }

    private void showPlayerPieces(Player player) {

    }



}
