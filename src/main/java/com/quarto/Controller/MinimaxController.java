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

public class MinimaxController implements Controller{

    private ConsoleGame console;
    private QuartoGame game;
    public MinimaxController(QuartoGame game, ConsoleGame console) {
        this.console = console;
        this.game = game;
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
                Move move = game.getCurrentPlayer().minimax(game.getGameBoard(), choosePiece, 8, true, Integer.MIN_VALUE, Integer.MAX_VALUE);
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
