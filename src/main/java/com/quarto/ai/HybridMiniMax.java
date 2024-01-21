package com.quarto.ai;

import com.quarto.Model.GameBoard;
import com.quarto.Model.Move;
import com.quarto.Model.Piece;
import com.quarto.Model.QuartoGame;

public class HybridMiniMax extends MiniMax {

    private MachineLearningModel mlModel;

    public HybridMiniMax(QuartoGame game, MachineLearningModel mlModel) {
        super(game);
        this.mlModel = mlModel;
    }

    @Override
    public Move minimax(GameBoard board, Piece chosenPiece, int depth, boolean isMaximizing, int alpha, int beta) {
        if (depth == 0 || board.checkWinCondition() || board.checkDrawCondition()) {
            return null;
        }

        Move bestMove = null;
        int bestScore = isMaximizing ? Integer.MIN_VALUE : Integer.MAX_VALUE;

        for (Piece piece : getAvailablePieces()) {
            for (int i = 0; i < 16; i++) {
                Move move = new Move(chosenPiece, i);
                if (board.isValidMove(move)) {
                    board.addPieceToBoard(move);

                    int score;
                    if (isMaximizing) {
                        score = (int) mlModel.predict(board, piece, depth); // Use ML model for evaluation
                    } else {
                        score = minimaxScore(board, piece, depth - 1, !isMaximizing, alpha, beta);
                    }

                    board.removePieceFromBoard(move);

                    if (isMaximizing) {
                        if (score > bestScore) {
                            bestScore = score;
                            bestMove = move;
                        }
                        alpha = Math.max(alpha, bestScore);
                    } else {
                        if (score < bestScore) {
                            bestScore = score;
                            bestMove = move;
                        }
                        beta = Math.min(beta, bestScore);
                    }

                    if (beta <= alpha) {
                        break;
                    }
                }
            }
        }

        // Update weights based on the rewards after each move
        mlModel.updateWeights(board, chosenPiece, depth);

        return bestMove;
    }

    public int minimaxScore(GameBoard board, Piece chosenPiece, int depth, boolean isMaximizing, int alpha, int beta) {
        if (depth == 0 || board.checkWinCondition() || board.checkDrawCondition()) {
            EvaluationFunction eval = new EvaluationFunction();
            return eval.evaluateBoard(board, chosenPiece, game);
        }

        int bestScore = isMaximizing ? Integer.MIN_VALUE : Integer.MAX_VALUE;

        for (Piece piece : availablePieces) {
            for (int i = 0; i < 16; i++) {
                Move move = new Move(chosenPiece, i);
                if (board.isValidMove(move)) {
                    board.addPieceToBoard(move);

                    int score = minimaxScore(board, piece, depth - 1, !isMaximizing, alpha, beta);

                    board.removePieceFromBoard(move);

                    if (isMaximizing){
                        if (score > bestScore) {
                            bestScore = score;
                        }
                        alpha = Math.max(alpha, bestScore);

                    } else{
                        if (score < bestScore) {
                            bestScore = score;
                        }
                        beta = Math.min(beta, bestScore);
                    }

                    if (beta <= alpha) {
                        break;
                    }
                }
            }
        }

        return bestScore;
    }
}