package com.quarto.ai;

import com.quarto.Model.GameBoard;
import com.quarto.Model.Move;
import com.quarto.Model.Piece;
import com.quarto.Model.Player;
import com.quarto.Model.QuartoGame;

import java.util.ArrayList;
import java.util.Arrays;

public class MiniMax {

    int MAX_DEPTH = 16;

    QuartoGame game;
    GameBoard gamestate;
    Player currentPlayer;
    Piece[] cPaP;
    Player opponent;
    Piece[] oPaP;
    Piece chosenPiece;

    ArrayList<Integer> availableLocations = new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15));


    public MiniMax(QuartoGame game, Piece chosenPiece){
        this.game = game;
        gamestate = game.getGameBoard();
        currentPlayer = game.getCurrentPlayer();
        opponent = game.getOpponent();
        cPaP = currentPlayer.getAvailablePieces();
        oPaP = opponent.getAvailablePieces();
        this.chosenPiece = chosenPiece;

    }

    private ArrayList<GameBoard> generate_moves(GameBoard gameState, Player player)
    {
        ArrayList<Move> moves = new ArrayList<>();

        for (Piece p : player.getAvailablePieces()) {
            for (int i = 0; i <= 16; i++) {
                if (gameState.isValidMove(new Move(p, i))) {
                    moves.add(new Move(p, i));
                }
            }
        }
        ArrayList<GameBoard> childStates = new ArrayList<>();
        for (Move move : moves) {
            gameState.addPieceToBoard(move);
            childStates.add(gameState);
        }

        return childStates;
    }

    private ArrayList<GameBoard> generate_moves(GameBoard gameState, Piece chosenPiece)
    {
        ArrayList<Move> moves = new ArrayList<>();

        for (int i = 0; i <= 16; i++) {
            if (gameState.isValidMove(new Move(chosenPiece, i))) {
                moves.add(new Move(chosenPiece, i));
            }
        }

        ArrayList<GameBoard> childStates = new ArrayList<>();
        for (Move move : moves) {
            gameState.addPieceToBoard(move);
            childStates.add(gameState);
        }
        return childStates;
    }



    private int minimax(GameBoard gameState, int depth, boolean isMaximizingPlayer, int alpha, int beta) {

        if (depth == 0 || gameState.checkWinCondition() || gameState.checkDrawCondition()) {
            return evaluate(gameState);
        }

        if (isMaximizingPlayer) {

            int maxEval = Integer.MIN_VALUE;

            for (GameBoard childState : generate_moves(gameState, currentPlayer)) {

                int eval = minimax(childState, depth - 1, false, alpha, beta);

                maxEval = Math.max(maxEval, eval);
                alpha = Math.max(alpha, eval);

                if (beta <= alpha) {
                    break;
                }
            }
            return maxEval;
        } else {

            int minEval = Integer.MAX_VALUE;

            for (GameBoard childState : generate_moves(gameState, opponent)) {

                int eval = minimax(childState, depth - 1, true, alpha, beta);

                minEval = Math.min(minEval, eval);
                beta = Math.min(beta, eval);

                if (beta <= alpha) {
                    break;
                }
            }
            return minEval;
        }

    }


    public int iterative_deepening() {
        int maxDepth = 1;
        Move bestMove = null;

        while (true){
            int result = minimax(gamestate, maxDepth, true, Integer.MIN_VALUE, Integer.MAX_VALUE);
            if ()
        }
    }

}