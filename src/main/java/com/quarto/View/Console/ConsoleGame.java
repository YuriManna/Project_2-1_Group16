package com.quarto.View.Console;

import com.quarto.Controller.GameController;
import com.quarto.Model.GameBoard;
import com.quarto.Model.Piece;
import com.quarto.Model.Player;
import com.quarto.Model.QuartoGame;

//GameView:
// Handles the user interface and displays the game board, player information, and prompts for moves.

public class ConsoleGame {



    public void renderConsole() {

    }

    //Show the current board
    private void showBoard(GameBoard board) {
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

    // Show the available pieces for the current player
    private void showPlayerPieces(Player player) {
        if (player.getIsWhite()) {
            System.out.println("White player's available pieces:");
        } else {
            System.out.println("Black player's available pieces:");
        }
        for (Piece piece : player.getAvailablePieces()) {
            // Display the piece details
            System.out.print(showPiece(piece));
            System.out.print(" ");
        }
        System.out.println(); // Add an empty line for better readability
    }

    //choose piece
    public Piece choosePiece



}
