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
        if(depth == 0 || board.isGameDrawn() || board.isGameWon() ) { // if game is over or if we are at the end of the tree
            return this.boardEvaluator.evaluate(board, depth); // evaluate the board
        }
        int lowestSeenValue = Integer.MAX_VALUE; // set the lowest seen value to the highest possible value (alpha)
        for(final Move move : board.getCurrentPlayer().getLegalMoves(board)){ // loop through all the possible moves
            board.executeMove(move); // simulate the move
            int currentValue = max(new Board(board), depth -1); // call the max function on the new board
            if(currentValue <= lowestSeenValue){
                lowestSeenValue = currentValue; // if the current value is lower than the lowest seen value, set the lowest seen value to the current value

            }

        }
        return lowestSeenValue;
    }

    public int max(final Board board, final int depth){
        if(depth == 0 || board.isGameDrawn() || board.isGameWon()) {
            return this.boardEvaluator.evaluate(board, depth);
        }
        int highestSeenValue = Integer.MIN_VALUE; // set the maximum seen value to the highest possible value (beta)
        for(final Move move : board.getCurrentPlayer().getLegalMoves(board)){
            board.executeMove(move);
            int currentValue = min(new Board(board), depth -1);
            if(currentValue >= highestSeenValue){
                highestSeenValue = currentValue;
            }
        }
        return highestSeenValue;
    }

}
