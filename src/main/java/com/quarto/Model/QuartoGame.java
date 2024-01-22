package com.quarto.Model;


/*
Responsibilities:
    Manage the game state, players, and the current turn.
    Facilitate the communication between players and the game board.
*/


public class QuartoGame {
    private GameBoard gameBoard;
    private final Player whitePlayer;
    private final Player blackPlayer;
    private Player currentPlayer;
    private Player opponent;
    private int turnCounter;

    public QuartoGame(boolean isPlayer1Human, boolean isPlayer2Human) {
        this.gameBoard = new GameBoard();
        whitePlayer = new Player(isPlayer1Human, true, this.gameBoard.getWhitesList(), this);
        blackPlayer = new Player(isPlayer2Human, false, this.gameBoard.getBlacksList(), this);
        currentPlayer = whitePlayer;
        opponent = blackPlayer;
        this.turnCounter = 0;

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

    public int getTurnCounter() { return turnCounter; }

    public void switchPlayers() {

        if (currentPlayer == whitePlayer) {
            currentPlayer = blackPlayer;
            opponent = whitePlayer;
            this.turnCounter++;
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

    public Player getWinner() {
        if (gameOver() && gameBoard.checkWinCondition()) {
            return currentPlayer;
        }
        return null;
    }

    public boolean getGameJustEnded(){
        return this.justEnded;
    }

    public void setGameJustEnded(boolean justEnded){
        this.justEnded = justEnded;
    }


}
