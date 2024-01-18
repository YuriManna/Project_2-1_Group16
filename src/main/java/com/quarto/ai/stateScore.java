package com.quarto.ai;

import com.quarto.Model.GameBoard;

public class stateScore {
    GameBoard state;
    int score;

    public stateScore(GameBoard state, int score) {
        this.state = state;
        this.score = score;
    }

    public GameBoard getState() {
        return state;
    }

    public int getScore() {
        return score;
    }

    public void setState(GameBoard state) {
        this.state = state;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
