package com.quarto.player.ai;

import com.quarto.setup.Board;
import com.quarto.setup.GameLogic;
import com.quarto.setup.Move;

public class MinMax implements MoveStrategy {
    private final BoardEvaluator boardEvaluator;
    private final GameLogic gameLogic;
    private final Board board;
    public MinMax(GameLogic gameLogic) {
        this.boardEvaluator = null;
        this.gameLogic = gameLogic;
        this.board = gameLogic.getBoard();
    }

    @Override
    public String toString() {
        return "MiniMax";
    }

    @Override
    public void execute(Board board, int depth) {

    }

    public int min(final Board board, final int depth){
        if(depth == 0 /*game over*/) {
            return this.boardEvaluator.evaluate(board, depth);
        }
        int lowestSeenValue = Integer.MAX_VALUE;
        for(final Move move : gameLogic.getCurrentPlayer().getLegalMoves()){
            //to finish
        }
        return lowestSeenValue;
    }
    public int max(final Board board, final int depth){
        if(depth == 0 /*game over*/) {
            return this.boardEvaluator.evaluate(board, depth);
        }
        int lowestSeenValue = Integer.MAX_VALUE;
        for(final Move move : gameLogic.getCurrentPlayer().getLegalMoves()){
            //to finish
        }
        return lowestSeenValue;
    }

}
