package com.quarto.ai;

import com.quarto.Model.GameBoard;
import com.quarto.Model.Move;
import com.quarto.Model.Piece;
import com.quarto.Model.Player;
import com.quarto.Model.QuartoGame;

import java.util.ArrayList;
import java.util.Arrays;

public class MiniMax {

    private QuartoGame game;
    private GameBoard gameState;
    private Player currentPlayer;
    private Piece[] cPaP;
    private Player opponent;
    private Piece[] oPaP;
    private Piece chosenPiece;

    public MiniMax(QuartoGame game, Piece chosenPiece) {
        this.game = game;
        gameState = game.getGameBoard();
        currentPlayer = game.getCurrentPlayer();
        opponent = game.getOpponent();
        cPaP = Arrays.copyOf(currentPlayer.getAvailablePieces(), currentPlayer.getAvailablePieces().length);
        oPaP = Arrays.copyOf(opponent.getAvailablePieces(), opponent.getAvailablePieces().length);
        this.chosenPiece = chosenPiece;
    }

    private ArrayList<GameBoard> generate_moves(GameBoard gameState, Piece[] availablePieces) {
        ArrayList<Move> moves = new ArrayList<>();
        ArrayList<GameBoard> childStates = new ArrayList<>();

        for (Piece p : availablePieces) {
            if (!gameState.isPieceOnBoard(p)) {
                for (int i = 0; i < 16; i++) {
                    if (gameState.isValidMove(new Move(p, i))) {
                        moves.add(new Move(p, i));
                    }
                }
            }
        }
            for (Move move : moves) {  // Create a copy of the current player
                GameBoard tmp_gameboard=new GameBoard();
                tmp_gameboard.copyGameBoard(gameState);
                tmp_gameboard.addPieceToBoard(move);
                childStates.add(tmp_gameboard);
            }

        return childStates;
    }

    private ArrayList<GameBoard> generate_moves(GameBoard gameState, Piece chosenPiece) {

        ArrayList<Move> moves = new ArrayList<>();
        ArrayList<GameBoard> childStates = new ArrayList<>();

        for (int i = 0; i < 16; i++) {
            if (gameState.isValidMove(new Move(chosenPiece, i))) {
                moves.add(new Move(chosenPiece, i));
            }
        }

        for (Move move : moves) {
            GameBoard tmp_gameboard=new GameBoard();
            tmp_gameboard.copyGameBoard(gameState);
            tmp_gameboard.addPieceToBoard(move);
            childStates.add(tmp_gameboard);
        }
        System.out.println("child states" + childStates.size());
        return childStates;
    }

    private stateScore minimax(GameBoard gameState, int depth, int alpha, int beta) {

        stateScore maxEval = new stateScore(null, Integer.MIN_VALUE);

        for (GameBoard childState : generate_moves(gameState, chosenPiece)) {

            stateScore eval = minimax(childState, depth - 1, false, alpha, beta);

            maxEval = new stateScore(childState, Math.max(maxEval.getScore(), eval.getScore()));
            alpha = Math.max(alpha, eval.getScore());

            if (beta <= alpha) {
                break;
            }
        }
        return maxEval;
    }

    private stateScore minimax(GameBoard gameState, int depth, boolean isMaximizingPlayer, int alpha, int beta) {

        if (depth == 0 || gameState.checkWinCondition() || gameState.checkDrawCondition()) {

            EvaluationFunction evaluate = new EvaluationFunction();
            stateScore tmp = new stateScore(gameState,evaluate.evaluateBoard(gameState, isMaximizingPlayer));

        }
        if (isMaximizingPlayer) {
            stateScore maxEval = new stateScore(null, Integer.MIN_VALUE);

            ArrayList<GameBoard> childStates = generate_moves(gameState, cPaP);
            for (GameBoard childState : childStates) {

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
            ArrayList<GameBoard> childStates = generate_moves(gameState, oPaP);
            for (GameBoard childState : childStates) {
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

    public int iterative_deepening(QuartoGame game) {
        int maxDepth = game.getTurnCounter();
        int bestMove = 0;
        stateScore result;

       ArrayList<GameBoard> gameStates = generate_moves(gameState, chosenPiece);

       int score = Integer.MIN_VALUE;
       for (GameBoard state : gameStates) {
           System.out.println(maxDepth);
           result = minimax(state, maxDepth, false, Integer.MIN_VALUE, Integer.MAX_VALUE);
           if( score < result.getScore()) {
               score = result.getScore();
               bestMove = result.getState().getIdByPiece(chosenPiece);
           }
       }
        return bestMove;
    }
}
