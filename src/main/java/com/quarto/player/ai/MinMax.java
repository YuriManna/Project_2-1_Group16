package com.quarto.player.ai;

import com.quarto.setup.GameLogic;

public class MinMax implements MoveStrategy {
    private final BoardEvaluator boardEvaluator;
    private final GameLogic gameLogic;
    public MinMax(GameLogic gameLogic) {
        this.boardEvaluator = null;
        this.gameLogic = gameLogic;
    }

    @Override
    public String toString() {
        return "MiniMax";
    }

    @Override
    public void execute(GameLogic gamelogic) {

    }
}
