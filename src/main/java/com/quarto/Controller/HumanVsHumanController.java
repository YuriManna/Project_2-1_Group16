package com.quarto.Controller;

import com.quarto.Model.Move;
import com.quarto.Model.Piece;
import com.quarto.Model.QuartoGame;
import com.quarto.View.Console.ConsoleGame;
import com.quarto.View.GameInterface;

import java.io.IOException;

/*
Responsibilities:
    Control the flow of the game.
    Handle user input from the view and update the model accordingly.
    Manage the switch between human and AI players.
*/

public class HumanVsHumanController implements Controller {

    private GameInterface gameView;
    private QuartoGame game;

    public HumanVsHumanController(QuartoGame game, GameInterface gameView) {
        this.gameView = gameView;
        this.game = game;
    }

    public void play() throws IOException {
        while(!game.gameOver()) {
            //step 1: Black chooses White's piece
            Piece choosePiece = gameView.choosePiece(game.getCurrentPlayer());
            //step 2: White chooses where to place the piece
            Move move = gameView.makeMove(game.getGameBoard(), choosePiece);
            //console.showBoard(game.getGameBoard());
            game.getCurrentPlayer().makeMove(move, game.getGameBoard());
            gameView.updateBoard(game.getGameBoard());
            //step 3: Switch players
            game.switchPlayers();
            //step 4: Repeat until game is over
        }
        gameView.showWinningMessage(game.getOpponent());
    }

}
