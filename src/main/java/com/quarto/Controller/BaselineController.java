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

public class BaselineController {

    private ConsoleGame console;
    private QuartoGame game;

    public BaselineController() {
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
        while(!game.gameOver()) {
            //step 1: Black chooses White's piece
            Piece choosePiece = console.chooseBaselinePiece(game.getCurrentPlayer());
            //step 2: White chooses where to place the piece
            Move move = console.makeMove(game.getGameBoard(), choosePiece);
            //console.showBoard(game.getGameBoard());
            game.getCurrentPlayer().makeMove(move, game.getGameBoard());
            console.showBoard(game.getGameBoard());
            //step 3: Switch players
            game.switchPlayers();
            //step 4: Repeat until game is over
        }
    }
}
