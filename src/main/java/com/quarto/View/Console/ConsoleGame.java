package com.quarto.View.Console;
import com.quarto.Model.*;
import com.quarto.ai.MiniMax;
import com.quarto.ai.PieceEvaluationFunction;

import java.util.Random;
import java.util.Scanner;

//GameView:
// Handles the user interface and displays the game board, player information, and prompts for moves.

public class ConsoleGame {
    Scanner sc = new Scanner(System.in);
    MiniMax minimax;
    boolean   AITurn;
    boolean[] players = {false, false};

    //choose game mode (AI & player)
    public boolean[] chooseGameMode(){
        // Display the game mode options (creates different type of players)
        System.out.println("Welcome to Quarto!");
        System.out.println("Please choose a game mode:");
        System.out.println("1. Human vs Human");
        System.out.println("2. Human vs AI");
        System.out.println("3. AI vs AI");

        // Take input from user to make a choice
        sc = new Scanner(System.in);
        int input = sc.nextInt();

        // Return the game mode based on the user input
        if (input == 1) {
            // Human vs Human
            System.out.println("You can play Human vs Human with GUI, switching to Human vs AI");
            players[0] = true;
            players[1] = true;
        } else if (input == 2) {
            // Human vs AI
            players[0] = true;
        } else if (input == 3) {
            // AI vs AI
            //return new boolean[]{false, false};
            //System.out.println("You can't play AI vs AI yet, switching to Human vs AI");
        } else {
            // Invalid input
            System.out.println("Invalid input! Please choose a valid game mode.");
            return chooseGameMode();
        }
        return players;
    }

    //Show the current board
    public void showBoard(GameBoard board) {
        System.out.println("Current Board:");
        int id = 0;

        // Run through the board and display the pieces
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
        // Looks at attributes of the piece and returns a string
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
        int i = 1;
        for (Piece piece : player.getAvailablePieces()) {

            // Display the piece details
            System.out.print(i + ". " + showPiece(piece));
            System.out.print(" ");
            i++;
        }
        System.out.println(); // Add an empty line for better readability
    }

    // Choose a piece to give to the opponent
    public Piece chooseBaselinePiece(Player opponent){

        Piece chosenpiece = null;
        if(opponent.getIsHuman()==true && opponent.getIsWhite()==true)
        {
            this.AITurn = false;
            chosenpiece = chooseRandomPiece(opponent);
        }
        else
        {
            this.AITurn = true;
            System.out.println("Please choose a piece to give to your opponent:");
            showPlayerPieces(opponent);

            chosenpiece = opponent.getAvailablePieces()[sc.nextInt() - 1];
            opponent.removeAvailablePiece(chosenpiece);

        }
        return chosenpiece;
    }
    public Piece chooseRandomPiece(Player opponent){
        //System.out.println("AI is choosing a piece to give to the opponent:");
        showPlayerPieces(opponent);
        Piece chosenpiece = null;
        Random random = new Random();
        int randomInt = random.nextInt(opponent.getAvailablePieces().length);
        chosenpiece = opponent.getAvailablePieces()[randomInt];
        opponent.removeAvailablePiece(chosenpiece);
        System.out.println("AI chose: " + showPiece(chosenpiece));
        return chosenpiece;
    }

    public Piece AIChoosePiece(Player opponent, QuartoGame game){
        //System.out.println("AI is choosing a piece to give to the opponent:");
        showPlayerPieces(opponent);
        GameBoard board = game.getGameBoard();
        PieceEvaluationFunction function = new PieceEvaluationFunction();
        Piece chosenPiece = function.leastLikelyPiece(opponent.getAvailablePieces(),board);
        opponent.removeAvailablePiece(chosenPiece);
        System.out.println("AI chose: " + showPiece(chosenPiece));
        return chosenPiece;
    }

    public Piece AIChoosePieceMinMax(Player opponent, QuartoGame game){
        minimax = new MiniMax(game);
        showPlayerPieces(opponent);
        Piece chosenPiece = null;
        GameBoard board = game.getGameBoard();
        int minScore = Integer.MAX_VALUE;
        int score;
        int alpha = Integer.MIN_VALUE;
        int beta = Integer.MAX_VALUE;
        for (Piece piece : opponent.getAvailablePieces()){
            for (int i = 0; i < 16; i++) {
                Move move = new Move(piece, i);
                if (board.isValidMove(move)) {
                    board.addPieceToBoard(move);
                    score = minimax.minimaxScore(game.getGameBoard(), piece, 4, true, alpha, beta);
                    board.removePieceFromBoard(move);

                    if (score < minScore) {
                        minScore = score;
                        chosenPiece = piece;
                    }
                    beta = Math.min(beta, score);
                }
            }
        }
        opponent.removeAvailablePiece(chosenPiece);
        //System.out.println("AI chose: " + showPiece(chosenPiece));
        return chosenPiece;
    }

    // Choose a piece to give to the opponent
    public Piece choosePiece(Player opponent){
        System.out.println("Please choose a piece to give to your opponent:");
        showPlayerPieces(opponent);
        Piece chosenPiece = opponent.getAvailablePieces()[sc.nextInt()-1];
        opponent.removeAvailablePiece(chosenPiece);
        return chosenPiece;
    }


    // Make a move
    public Move makeMove(GameBoard board, Piece playablePiece){
        Move move = null;
        System.out.println("Please choose a tile to place your piece on:");
        showBoard(board);
        int tileId = sc.nextInt();

        move = new Move(playablePiece, tileId);

        if (!board.isValidMove(move)) {
            System.out.println("Invalid move! Please choose a valid tile.");
            return makeMove(board, playablePiece);
        }
        return move;
    }


    public Move AIMakeMove(GameBoard board, Piece playablePiece){

        Random random = new Random();

        int randomTileId = random.nextInt(16);

        Move move = new Move(playablePiece, randomTileId);

        if(!board.isValidMove(move)){
            //System.out.println("Invalid move! Please choose a valid tile.");
            return AIMakeMove(board, playablePiece);
        }
        showBoard(board);
        return move;
    }

    public boolean getAITurn() {
        return this.AITurn;
    }
    public void showWinningMessage(Player player){
        if (player.getIsWhite()) {
            System.out.println("White player wins!");
        } else {
            System.out.println("Black player wins!");
        }
    }

}
