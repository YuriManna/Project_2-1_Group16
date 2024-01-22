package com.quarto.Controller;

import com.quarto.Model.QuartoGame;
import com.quarto.View.Console.ConsoleGame;

public class MasterController {
    private ConsoleGame console;
    private QuartoGame game;

    public MasterController() {
        //initialize the console version of the game
        this.console = new ConsoleGame();
        //initialize the game and therefore the player type
        boolean[] gameMode = console.chooseGameMode(); // [0] = isPlayer1Human, [1] = isPlayer2Human
        this.game = new QuartoGame(gameMode[0], gameMode[1]);
        //start the game
        Controller controller;

        // 0 & 1 true - human vs human
        // 0 & !1 or !0 & 1 - human vs minimax
        // !0 &!1 - minimax vs baseline
        // but check order

        if (gameMode[0] && gameMode[1]) {
            controller = new HumanVsHumanController(game,console);

        } else if (gameMode[0] && !gameMode[1] || !gameMode[0] && gameMode[1]) {
            controller = new MinimaxController(game,console);

        } else {
            controller = new BaselineVsMiniMaxController(game,console);
        }

        controller.play();
    }
}
