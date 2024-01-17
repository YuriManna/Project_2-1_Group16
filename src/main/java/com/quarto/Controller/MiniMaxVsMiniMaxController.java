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


            if (game.getCurrentPlayer() == game.getWhitePlayer() ) //white is baseline
            {
                //step 1: minimax 1 chooses a piece
                long startTime = System.currentTimeMillis();
                Piece choosePiece = console.AIChoosePiece(game.getCurrentPlayer(), game);
                long endTime = System.currentTimeMillis();
                long duration = (endTime - startTime);
                System.out.println("MiniMax 1 piece calculation took " + duration + " milliseconds");
                //step 2: minimax 2 chooses where to place the piece
                startTime = System.currentTimeMillis();
                Move move = game.getCurrentPlayer().minimax(game.getGameBoard(), choosePiece, 6, true, Integer.MIN_VALUE, Integer.MAX_VALUE);
                endTime = System.currentTimeMillis();
                duration = (endTime - startTime);
                game.getCurrentPlayer().makeMove(move, game.getGameBoard());
                //step 3: Show updated board
                console.showBoard(game.getGameBoard());
                System.out.println("MiniMax 2 move calculation took " + duration + " milliseconds");
            }
            else{
                //step 1:  minimax 2 chooses AI's piece
                long startTime = System.currentTimeMillis();
                Piece choosePiece = console.AIChoosePieceMinMax(game.getCurrentPlayer(), game);
                long endTime = System.currentTimeMillis();
                long duration = (endTime - startTime);
                System.out.println("minimax 2 piece calculation took " + duration + " milliseconds");
                //step 2: minimax 1 chooses where to place the piece
                startTime = System.currentTimeMillis();
                Move move = game.getCurrentPlayer().minimax(game.getGameBoard(), choosePiece, 6, true, Integer.MIN_VALUE, Integer.MAX_VALUE);
                endTime = System.currentTimeMillis();
                duration = (endTime - startTime);
                game.getCurrentPlayer().makeMove(move, game.getGameBoard());
                //step 3: Show updated board
                console.showBoard(game.getGameBoard());
                System.out.println("MiniMax 1 move calculation took " + duration + " milliseconds");
            }

        }
        console.showWinningMessage(game.getCurrentPlayer());
    }
}







//    Piece alternativePiece = console.AIChoosePieceMinMax(game.getCurrentPlayer(), game);
//    System.out.println("alternative piece: " + alternativePiece.toString());