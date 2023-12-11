package com.quarto.player.ai;

import com.quarto.setup.Board;

public class SimpleBoardEvaluator implements BoardEvaluator {

    private static final int WIN_SCORE = 100;
    private static final int LOSE_SCORE = -100;
    private static final int DRAW_SCORE = 0;

    @Override
    public int evaluate(Board board, int depth) {
        if (board.isGameWon()) {
            // If the AI won, return a high positive score
            return WIN_SCORE;
        } else if (board.isGameDrawn()) {
            // If the game is a draw, return a neutral score
            return DRAW_SCORE;
        } else {
            // If the opponent won, return a high negative score
            return LOSE_SCORE;
        }
    }
}