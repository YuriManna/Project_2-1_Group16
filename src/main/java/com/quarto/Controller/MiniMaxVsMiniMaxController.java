package com.quarto.Controller;

import com.quarto.Model.Move;
import com.quarto.Model.Piece;
import com.quarto.Model.QuartoGame;
import com.quarto.View.Console.ConsoleGame;

public class MiniMaxVsMiniMaxController implements Controller{

    private ConsoleGame console;
    private QuartoGame game;

    public MiniMaxVsMiniMaxController(QuartoGame game, ConsoleGame console) {
        this.console = console;
        this.game = game;
    }

    public void play()
    {
        game.switchPlayers();
        while(!game.gameOver()) {
            game.switchPlayers();
            {
                //step 1: minimax 1 chooses a piece

                Piece choosePiece = game.getOpponent().AIChoosePiece(game.getCurrentPlayer(), game.getGameBoard());
                console.AIChoosePiece(choosePiece, game.getCurrentPlayer());

                //step 2: minimax 2 chooses where to place the piece

                Move move = game.getCurrentPlayer().minimaxPlace(game, choosePiece);

                game.getCurrentPlayer().makeMove(move, game.getGameBoard());
                console.AIMakeMove(move, game.getGameBoard());
                //step 3: Show updated board
                console.showBoard(game.getGameBoard());
            }

        }
        console.showWinningMessage(game.getCurrentPlayer());
    }
}