package com.quarto.player.ai;

import com.quarto.setup.Board;
import com.quarto.setup.GameLogic;

public interface MoveStrategy {

    public void execute(Board board, int depth);
}
