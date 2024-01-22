package com.quarto.Controller;

import com.quarto.Model.Move;
import com.quarto.Model.Piece;
import com.quarto.Model.QuartoGame;
import com.quarto.View.GameInterface;
import java.io.IOException;

/*
Responsibilities:
    Control the flow of the game.
    Handle user input from the view and update the model accordingly.
    Manage the switch between human and AI players.
*/

public class MinimaxController implements Controller{

    private GameInterface gameView;
    private QuartoGame game;
    public MinimaxController(QuartoGame game, GameInterface gameView) {
        this.gameView = gameView;
        this.game = game;
    }
    public void play() throws IOException {
        game.switchPlayers();
        while(!game.gameOver()) {
            game.switchPlayers();

            if (game.getCurrentPlayer().getIsHuman())
            {
                //step 1: AI chooses HUMAN's piece
                long startTime = System.currentTimeMillis();
                Piece choosePiece = gameView.AIChoosePiece(game.getCurrentPlayer(), game);
                long endTime = System.currentTimeMillis();
                long duration = (endTime - startTime);
                System.out.println("AI piece calculation took " + duration + " milliseconds");
                //step 2: Human chooses where to place the piece
                Move move = gameView.makeMove(game.getGameBoard(), choosePiece);
                game.getCurrentPlayer().makeMove(move, game.getGameBoard());
                //step 3: Show updated board
                gameView.updateBoard(game.getGameBoard());
            }
            else{
                //step 1:  Human chooses AI's piece
                Piece choosePiece = gameView.choosePiece(game.getCurrentPlayer());
                //step 2: AI chooses where to place the piece
                long startTime = System.currentTimeMillis();
                Move move = game.getCurrentPlayer().minimax(game.getGameBoard(), choosePiece, 5, true, Integer.MIN_VALUE, Integer.MAX_VALUE);
                long endTime = System.currentTimeMillis();
                long duration = (endTime - startTime);
                game.getCurrentPlayer().makeMove(move, game.getGameBoard());
                //step 3: Show updated board
                gameView.updateBoard(game.getGameBoard());
                System.out.println("AI move calculation took " + duration + " milliseconds");
            }

        }
        gameView.showWinningMessage(game.getCurrentPlayer());
    }
}
