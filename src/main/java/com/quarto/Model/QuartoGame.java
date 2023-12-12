package com.quarto.Model;


/*
Responsibilities:
    Manage the game state, players, and the current turn.
    Facilitate the communication between players and the game board.
*/

import com.quarto.setup.Pieces;

public class QuartoGame {
    private GameBoard gameBoard;
    private final Player whitePlayer;
    private final Player blackPlayer;
    private Player currentPlayer;
    private Player opponent;

    public QuartoGame(boolean isPlayer1Human, boolean isPlayer2Human) {
        this.gameBoard = new GameBoard();
        whitePlayer = new Player(isPlayer1Human, true, this.gameBoard.getWhitesList(), this);
        blackPlayer = new Player(isPlayer2Human, false, this.gameBoard.getBlacksList(), this);
        currentPlayer = whitePlayer;
        opponent = blackPlayer;

    }

    public GameBoard getGameBoard() {
        return gameBoard;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public Player getWhitePlayer() {
        return whitePlayer;
    }

    public Player getOpponent() {
        return opponent;
    }

    public void switchPlayers() {

        if (currentPlayer == whitePlayer) {
            currentPlayer = blackPlayer;
            opponent = whitePlayer;
        } else {
            currentPlayer = whitePlayer;
            opponent = blackPlayer;
        }
    }

    public boolean gameOver() {
        if (gameBoard.checkWinCondition()) {
            return true;
        }
        return gameBoard.checkDrawCondition();
    }



}
