package com.quarto.player.ai;

import com.quarto.setup.Board;
import com.quarto.setup.GameLogic;
import com.quarto.setup.Move;

public class MinMax implements MoveStrategy {
    private final BoardEvaluator boardEvaluator;
    private final Board board;
    public MinMax(Board board) {
        this.boardEvaluator = null;
        this.board = new Board(board);
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
        for(final Move move : board.getCurrentPlayer().getLegalMoves()){
            board.executeMove(move);
            int currentValue = max(new Board(board), depth -1);
            if(currentValue <= lowestSeenValue){
                lowestSeenValue = currentValue;
            }
        }
        return lowestSeenValue;
    }

    public int max(final Board board, final int depth){
        if(depth == 0 /*game over*/) {
            return this.boardEvaluator.evaluate(board, depth);
        }
        int highestSeenValue = Integer.MIN_VALUE;
        for(final Move move : board.getCurrentPlayer().getLegalMoves()){
            board.executeMove(move);
            int currentValue = min(new Board(board), depth -1);
            if(currentValue <= highestSeenValue){
                highestSeenValue = currentValue;
            }
        }
        return highestSeenValue;
    }

}
