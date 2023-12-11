package com.quarto.Controller;

import com.quarto.Model.GameBoard;
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

    public GameController(ConsoleGame console, QuartoGame game) {
        this.console = console;
        this.game = game;
    }

    public void play()
    {
        while(!game.gameOver())
        {

            //step 1: Black chooses White's piece
            //Piece chosenPiece;
            //game.getOpponent().choosePiece(game.getCurrentPlayer(), );
            //step 2: White chooses where to place the piece
            //step 3: Switch players
            //step 4: Repeat until game is over
        }
    }

    public QuartoGame getGame() {
        return game;
    }
}
