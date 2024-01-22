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
                long startTime = System.currentTimeMillis();

                Piece choosePiece = game.getOpponent().AIChoosePiece(game.getCurrentPlayer(), game.getGameBoard());
                console.AIChoosePiece(choosePiece, game.getCurrentPlayer());

                long endTime = System.currentTimeMillis();
                long duration = (endTime - startTime);

                System.out.println("MiniMax 1 piece calculation took " + duration + " milliseconds");

                //step 2: minimax 2 chooses where to place the piece

                startTime = System.currentTimeMillis();

                Move move = game.getCurrentPlayer().minimaxPlace(game, choosePiece);

                endTime = System.currentTimeMillis();
                duration = (endTime - startTime);

                game.getCurrentPlayer().makeMove(move, game.getGameBoard());
                console.AIMakeMove(move, game.getGameBoard());
                //step 3: Show updated board
                console.showBoard(game.getGameBoard());
                System.out.println("MiniMax 2 move calculation took " + duration + " milliseconds");
            }

        }
        console.showWinningMessage(game.getCurrentPlayer());
    }
}







//    Piece alternativePiece = console.AIChoosePieceMinMax(game.getCurrentPlayer(), game);
//    System.out.println("alternative piece: " + alternativePiece.toString());