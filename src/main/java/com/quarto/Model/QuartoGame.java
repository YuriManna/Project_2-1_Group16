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

    public QuartoGame(GameBoard gameBoard, Player player1, Player player2) {
        this.gameBoard = gameBoard;
        whitePlayer = player1;
        blackPlayer = player2;
        currentPlayer = whitePlayer;


        if (whitePlayer.getIsWhite()) {
            whitePlayer.givePieces(this.gameBoard.getWhitesList());
            blackPlayer.givePieces(this.gameBoard.getBlacksList());
        }
    }

    public GameBoard getGameBoard() {
        return gameBoard;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
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

    public Piece choosePiece()
    {

    }


}
