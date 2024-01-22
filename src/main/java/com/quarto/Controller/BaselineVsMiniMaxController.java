package com.quarto.Controller;

import com.quarto.Model.Move;
import com.quarto.Model.Piece;
import com.quarto.Model.QuartoGame;
import com.quarto.View.Console.ConsoleGame;

public class BaselineVsMiniMaxController implements Controller{

    private ConsoleGame console;
    private QuartoGame game;

    public BaselineVsMiniMaxController(QuartoGame game, ConsoleGame console) {
            this.console = console;
            this.game = game;
    }

    public void play()
    {
        game.switchPlayers();
        while(!game.gameOver()) {
            game.switchPlayers();


            if (game.getCurrentPlayer() == game.getWhitePlayer() ) //white is baseline
            {
                Piece choosePiece = game.getOpponent().AIChoosePiece(game.getCurrentPlayer(), game.getGameBoard());
                //step 2: Baseline chooses where to place the piece
                Move move = console.AIMakeMove(game.getGameBoard(), choosePiece);
                game.getCurrentPlayer().makeMove(move, game.getGameBoard());
                //step 3: Show updated board
                console.updateBoard(game.getGameBoard());

            }
            else{
                //step 1:  baseline chooses AI's piece
                Piece choosePiece = console.chooseRandomPiece(game.getCurrentPlayer());
                //step 2: minimax chooses where to place the piece
                Move move = game.getCurrentPlayer().minimaxPlace(game, choosePiece);
                game.getCurrentPlayer().makeMove(move, game.getGameBoard());
                //step 3: Show updated board
                console.updateBoard(game.getGameBoard());
            }

        }
        console.showWinningMessage(game.getCurrentPlayer());
    }
}
