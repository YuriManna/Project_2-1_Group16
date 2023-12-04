package com.quarto.player.ai;

import com.quarto.setup.Board;
import com.quarto.setup.GameLogic;
import com.quarto.setup.Move;

public class MinMax implements MoveStrategy {
    private final BoardEvaluator boardEvaluator;
    private final Board board;
    public MinMax(Board board) {
        this.boardEvaluator = new SimpleBoardEvaluator();
        this.board = new Board(board);
    }

    @Override
    public String toString() {
        return "MiniMax";
    }

    @Override       
    public void execute(Board board, int depth) {

    }


    public int min(final Board board, final int depth, int alpha, int beta){
        if(depth == 0 || board.isGameDrawn() || board.isGameWon()) {
            return this.boardEvaluator.evaluate(board, depth);
        }
        int lowestSeenValue = Integer.MAX_VALUE;
        for(final Move move : board.getCurrentPlayer().getLegalMoves()){
            board.executeMove(move);
            int currentValue = max(new Board(board), depth -1, alpha, beta);
            if(currentValue <= lowestSeenValue){
                lowestSeenValue = currentValue; // if the current value is lower than the lowest seen value, set the lowest seen value to the current value
            }
            if(beta >= currentValue){
                beta = currentValue;
            }
            if(beta <= alpha) break;
        }
        return lowestSeenValue;
    }


    public int max(final Board board, final int depth, int alpha, int beta){
        if(depth == 0 || board.isGameDrawn() || board.isGameWon()) {
            return this.boardEvaluator.evaluate(board, depth);
        }
        int highestSeenValue = Integer.MIN_VALUE; // set the maximum seen value to the highest possible value (beta)
        for(final Move move : board.getCurrentPlayer().getLegalMoves()){
            board.executeMove(move);
            int currentValue = min(new Board(board), depth -1, alpha, beta);
            if(currentValue >= highestSeenValue){
                highestSeenValue = currentValue;
            }
            if(alpha <= currentValue){
                alpha = currentValue;
            }
            if(beta<=alpha) break;
            }
        return highestSeenValue;
    }
}

/**
 *         int highestSeenValue = Integer.MIN_VALUE; // set the highest seen value to the lowest possible value (beta)
 *         int lowestSeenValue = Integer.MAX_VALUE; // set the lowest seen value to the highest possible value (alpha)
 *         Move bestMove = null; // set the best move to null
 *         System.out.println(board.getCurrentPlayer() + " is thinking with depth = " + depth);
 *         for(final Move move : board.getCurrentPlayer().getLegalMoves(board)){ // loop through all the possible moves
 *             board.executeMove(move); // simulate the move
 *             int currentValue = min(new Board(board), depth -1); // call the min function on the new board
 *             if(currentValue >= highestSeenValue){ // if the current value is higher than the highest seen value
 *                 highestSeenValue = currentValue; // set the highest seen value to the current value
 *                 bestMove = move; // set the best move to the current move
 *             }
 *             if(currentValue <= lowestSeenValue){ // if the current value is lower than the lowest seen value
 *                 lowestSeenValue = currentValue; // set the lowest seen value to the current value
 *                 bestMove = move; // set the best move to the current move
 *             }
 *         }
 *         System.out.println("Best move is " + bestMove + " with a value of " + highestSeenValue);
 *         board.executeMove(bestMove); // execute the best move
 */
