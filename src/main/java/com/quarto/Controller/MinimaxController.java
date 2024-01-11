package com.quarto.Controller;

import com.quarto.Model.Move;
import com.quarto.Model.Piece;
import com.quarto.Model.QuartoGame;
import com.quarto.View.Console.ConsoleGame;

/*
Responsibilities:
    Control the flow of the game.
    Handle user input from the view and update the model accordingly.
    Manage the switch between human and AI players.
*/

public class MinimaxController {

    private ConsoleGame console;
    private QuartoGame game;

    public MinimaxController() {
        //initialize the console version of the game
        this.console = new ConsoleGame();
        //initialize the game and therefore the player type
        boolean[] gameMode = console.chooseGameMode(); // [0] = isPlayer1Human, [1] = isPlayer2Human
        this.game = new QuartoGame(gameMode[0], gameMode[1]);
        //start the game
        play();
    }

    public void play()
    {
        game.switchPlayers();
        while(!game.gameOver()) {
            game.switchPlayers();

            if (game.getCurrentPlayer().getIsHuman())
            {
                //step 1: AI chooses HUMAN's piece
                long startTime = System.currentTimeMillis();
                Piece choosePiece = console.AIChoosePiece(game.getCurrentPlayer());
                long endTime = System.currentTimeMillis();
                long duration = (endTime - startTime);
                System.out.println("AI piece calculation took " + duration + " milliseconds");
                //step 2: Human chooses where to place the piece
                Move move = console.makeMove(game.getGameBoard(), choosePiece);
                game.getCurrentPlayer().makeMove(move, game.getGameBoard());
                //step 3: Show updated board
                console.showBoard(game.getGameBoard());
            }
            else{
                //step 1:  Human chooses AI's piece
                Piece choosePiece = console.choosePiece(game.getCurrentPlayer());
                //step 2: AI chooses where to place the piece
                long startTime = System.currentTimeMillis();
                Move move = game.getCurrentPlayer().minimax(game.getGameBoard(), choosePiece, 4, true, Integer.MIN_VALUE, Integer.MAX_VALUE);
                long endTime = System.currentTimeMillis();
                long duration = (endTime - startTime);
                game.getCurrentPlayer().makeMove(move, game.getGameBoard());
                //step 3: Show updated board
                console.showBoard(game.getGameBoard());
                System.out.println("AI move calculation took " + duration + " milliseconds");
            }

        }
        console.showWinningMessage(game.getCurrentPlayer());
    }
}
