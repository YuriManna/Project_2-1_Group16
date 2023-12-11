package com.quarto.View.Console;
import com.quarto.Model.*;

import java.util.Scanner;

//GameView:
// Handles the user interface and displays the game board, player information, and prompts for moves.

public class ConsoleGame {
Scanner sc = new Scanner(System.in);

    //choose game mode (AI & player)
    public boolean[] chooseGameMode(){
        System.out.println("Welcome to Quarto!");
        System.out.println("Please choose a game mode:");
        System.out.println("1. Human vs Human");
        System.out.println("2. Human vs AI");
        System.out.println("3. AI vs AI");

        sc = new Scanner(System.in);
        int input = sc.nextInt();

        if (input == 1) {
            // Human vs Human
            return new boolean[]{true, true};
        } else if (input == 2) {
            // Human vs AI
            return new boolean[]{true, false};
        } else if (input == 3) {
            // AI vs AI
            return new boolean[]{false, false};

        } else {
            // Invalid input
            System.out.println("Invalid input! Please choose a valid game mode.");
            return chooseGameMode();
        }
    }

    //Show the current board
    private void showBoard(GameBoard board) {
        System.out.println("Current Board:");
        int id = 0;

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                Piece piece = board.getBoard()[i][j];

                if (piece != null) {
                    // Display the piece details
                    System.out.print(showPiece(piece));
                } else {
                    // Display an empty space with an ID
                    System.out.printf("--%02d-", id);
                }

                System.out.print(" ");
                id++;
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
    public Piece choosePiece(Player opponent){
        Piece chosenpiece = null;

        System.out.println("Please choose a piece to give to your opponent:");
        showPlayerPieces(opponent);

        for (Piece piece : opponent.getAvailablePieces()){
            if (showPiece(piece).equals(sc.next())){
                chosenpiece = piece;
                break;
            }
            else{
                System.out.println("Please choose a valid piece");
            }
        }
        return chosenpiece;
    }

    public Move makeMove(GameBoard board, Piece playablePiece){

        System.out.println("Please choose a tile to place your piece on:");
        showBoard(board);

        int tileId = sc.nextInt();
        return new Move(playablePiece, tileId);
    }



}
