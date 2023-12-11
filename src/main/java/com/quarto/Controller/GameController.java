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

public class GameController {

    private ConsoleGame console;
    private QuartoGame game;

    public GameController(ConsoleGame console) {
        this.console = console;
        this.game = new QuartoGame(console.chooseGameMode()[0], console.chooseGameMode()[1]);
    }

    public void play()
    {
        while(!game.gameOver())
        {
            //step 1: Black chooses White's piece
            Piece choosePiece = console.choosePiece(game.getCurrentPlayer());
            //step 2: White chooses where to place the piece
            Move move = console.makeMove(game.getGameBoard(), choosePiece);
            game.getCurrentPlayer().makeMove(move, game.getGameBoard());
            //step 3: Switch players
            game.switchPlayers();
            //step 4: Repeat until game is over
        }
    }

    public QuartoGame getGame() {
        return game;
    }
}
