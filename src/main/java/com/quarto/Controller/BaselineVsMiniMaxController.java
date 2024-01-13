package com.quarto.Controller;

import com.quarto.Model.Move;
import com.quarto.Model.Piece;
import com.quarto.Model.QuartoGame;
import com.quarto.View.Console.ConsoleGame;
import com.quarto.ai.MiniMax;

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
                //step 1: minimax chooses baseline's piece
                long startTime = System.currentTimeMillis();
                Piece choosePiece = console.AIChoosePiece(game.getCurrentPlayer());
                long endTime = System.currentTimeMillis();
                long duration = (endTime - startTime);
                System.out.println("MiniMax piece calculation took " + duration + " milliseconds");
                //step 2: Baseline chooses where to place the piece
                startTime = System.currentTimeMillis();
                Move move = console.AIMakeMove(game.getGameBoard(), choosePiece);
                endTime = System.currentTimeMillis();
                duration = (endTime - startTime);
                game.getCurrentPlayer().makeMove(move, game.getGameBoard());
                //step 3: Show updated board
                console.showBoard(game.getGameBoard());
                System.out.println("Baseline move calculation took " + duration + " milliseconds");
            }
            else{
                //step 1:  Human chooses AI's piece
                long startTime = System.currentTimeMillis();
                Piece choosePiece = console.chooseRandomPiece(game.getCurrentPlayer());
                long endTime = System.currentTimeMillis();
                long duration = (endTime - startTime);
                System.out.println("Baseline piece calculation took " + duration + " milliseconds");
                //step 2: AI chooses where to place the piece
                startTime = System.currentTimeMillis();
                Move move = game.getCurrentPlayer().minimax(game.getGameBoard(), choosePiece, 8, true, Integer.MIN_VALUE, Integer.MAX_VALUE);
                endTime = System.currentTimeMillis();
                duration = (endTime - startTime);
                game.getCurrentPlayer().makeMove(move, game.getGameBoard());
                //step 3: Show updated board
                console.showBoard(game.getGameBoard());
                System.out.println("MiniMax move calculation took " + duration + " milliseconds");
            }

        }
        console.showWinningMessage(game.getCurrentPlayer());
    }
}
