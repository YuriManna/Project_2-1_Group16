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

    public int min(final Board board, final int depth,int alpha, int beta){
        int minEval= Integer.MAX_VALUE;
        if(depth == 0 /*game over*/) {
            return new EvaluationFunction().evaluateBoard(board, board.getSelectedPiece());
        }

        for(final Move move : board.getCurrentPlayer().getLegalMoves()){
            board.executeMove(move);
            int eval = max(new Board(board), depth -1,alpha,beta);
            minEval= Math.min(minEval, eval);
            beta= Math.min(beta, eval);
            if(beta <= alpha){
                break;
            }
        }
        return minEval;
    }

    public int max(final Board board, final int depth, int alpha, int beta){
        int maxEval= Integer.MIN_VALUE;
        if(depth == 0 /*game over*/) {
            return new EvaluationFunction().evaluateBoard(board, board.getSelectedPiece());
        }
        for(final Move move : board.getCurrentPlayer().getLegalMoves()){
            board.executeMove(move);
            int eval = min(new Board(board), depth -1,alpha,beta);
            maxEval= Math.max(maxEval, eval);
            alpha = Math.max(alpha, eval);
            if(beta <= alpha){
                break;
            }
        }
        return maxEval;
    }

}
