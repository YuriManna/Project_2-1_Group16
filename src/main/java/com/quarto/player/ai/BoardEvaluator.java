package com.quarto.player.ai;

import com.quarto.setup.Board;
import com.quarto.setup.GameLogic;

public interface BoardEvaluator {
    int evaluate(Board board, int depth);
}
