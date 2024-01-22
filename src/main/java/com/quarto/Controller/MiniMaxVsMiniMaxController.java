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
            if(game.getCurrentPlayer()==game.getWhitePlayer())
            {
                //step 1: minimax 1 chooses a piece

                Piece choosePiece = console.AIChoosePiece(game.getOpponent(), game);

                //step 2: minimax 2 chooses where to place the piece

                Move move = game.getCurrentPlayer().minimaxPlace(game, choosePiece);

                game.getCurrentPlayer().makeMove(move, game.getGameBoard());
                //step 3: Show updated board
                console.updateBoard(game.getGameBoard());
                
            }
            else{
                //step 1:  minimax 2 chooses AI's piece
                
                Piece choosePiece = console.AIChoosePiece(game.getOpponent(), game);


                //step 2: minimax 1 chooses where to place the piece

                Move move = game.getCurrentPlayer().minimaxPlace(game, choosePiece);

                game.getCurrentPlayer().makeMove(move, game.getGameBoard());
                //step 3: Show updated board
                console.updateBoard(game.getGameBoard());

            }

        }
        console.showWinningMessage(game.getCurrentPlayer());
    }
}