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
    GameBoard gameState;
    Player currentPlayer;
    Piece[] cPaP;
    Player opponent;
    Piece[] oPaP;
    Piece chosenPiece;
    int roundDepth;

    ArrayList<Integer> availableLocations = new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15));


    public MiniMax(QuartoGame game, Piece chosenPiece){
        this.game = game;
        gameState = game.getGameBoard();
        currentPlayer = game.getCurrentPlayer();
        opponent = game.getOpponent();
        cPaP = currentPlayer.getAvailablePieces();
        oPaP = opponent.getAvailablePieces();
        this.chosenPiece = chosenPiece;

    }

    private ArrayList<GameBoard> generate_moves(GameBoard gameState, Piece[] availablePieces)
    {
        ArrayList<Move> moves = new ArrayList<>();

        for (Piece p : availablePieces) {
            for (int i = 0; i <= 16; i++) {
                if (gameState.isValidMove(new Move(p, i))) {
                    moves.add(new Move(p, i));
                }
            }
            currentPlayer.removeAvailablePiece(p);
            cPaP = currentPlayer.getAvailablePieces();
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



    private stateScore minimax(GameBoard gameState, int depth, boolean isMaximizingPlayer, int alpha, int beta) {

        if (depth == 0 || gameState.checkWinCondition() || gameState.checkDrawCondition()) {
            EvaluationFunction evaluate = new EvaluationFunction();
            return evaluate.evaluateBoard(gameState, chosenPiece, game);

        }

        if (isMaximizingPlayer) {

            stateScore maxEval = new stateScore(null, Integer.MIN_VALUE);

            for (GameBoard childState : generate_moves(gameState, cPaP)) {

                stateScore eval = minimax(childState, depth - 1, false, alpha, beta);

                maxEval = new stateScore(childState, Math.max(maxEval.getScore(), eval.getScore()));
                alpha = Math.max(alpha, eval.getScore());

                if (beta <= alpha) {
                    break;
                }
            }
            return maxEval;
        } else {

            stateScore minEval = new stateScore(null, Integer.MAX_VALUE);

            for (GameBoard childState : generate_moves(gameState, oPaP)) {

                stateScore eval = minimax(childState, depth - 1, true, alpha, beta);

                minEval = new stateScore(childState, Math.min(minEval.getScore(), eval.getScore()));
                beta = Math.min(beta, eval.getScore());

                if (beta <= alpha) {
                    break;
                }
            }
            return minEval;
        }

    }


    public int iterative_deepening(int maxDepth) {

        int bestMove = 0;
        while (true){
            stateScore result = minimax(gameState, maxDepth, true, Integer.MIN_VALUE, Integer.MAX_VALUE);
            if(result.getScore() > 0) {
                bestMove = result.getState().getIdByPiece(chosenPiece);
            }
            else{
                break;
            }
            maxDepth++;
        }
        return bestMove;
    }

}

