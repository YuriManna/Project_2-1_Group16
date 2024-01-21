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
    Boolean firstCall=true;//first call of the generate moves method
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
        if(firstCall){
            System.out.println("first call");
            for (int i = 0; i <16; i++) {
                if (gameState.isValidMove(new Move(chosenPiece, i))) {
                    moves.add(new Move(chosenPiece, i));
                }
            }
            firstCall=false;
        }
        else {
            for (Piece p : availablePieces) {
                for (int i = 0; i <= 15; i++) {//should go up to 15
                    if (gameState.isValidMove(new Move(p, i))) {
                        moves.add(new Move(p, i));
                    }
                }

            }
        }
        ArrayList<GameBoard> childStates = new ArrayList<>();

        for (Move move : moves) {
            GameBoard tmp_gameboard=new GameBoard();
            tmp_gameboard.copyGameBoard(gameState);
            tmp_gameboard.addPieceToBoard(move);
            tmp_gameboard.setLatestPieceAdded(move.getPiece());
            childStates.add(tmp_gameboard);

//           tmp_gameboard.removePieceFromBoard(move);

        }

        return childStates;
    }

    private ArrayList<GameBoard> generate_moves(GameBoard gameState, Piece chosenPiece)//avert your eyes
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



    public stateScore minimax(GameBoard gameState, int depth, boolean isMaximizingPlayer, int alpha, int beta) {//from private to pub

        if (depth == 0 || gameState.checkWinCondition() || gameState.checkDrawCondition()) {
            EvaluationFunction evaluate = new EvaluationFunction();
            return evaluate.evaluateBoard(gameState, chosenPiece, game);//this is wrong

        }

        if (isMaximizingPlayer) {

                    stateScore maxEval = new stateScore(null, Integer.MIN_VALUE);//?
            for (GameBoard childState : generate_moves(gameState, cPaP)) {
               for(Piece piece : cPaP) {
                 if(piece.equals(childState.getLatestPieceAdded())){
                    currentPlayer.removeAvailablePiece(piece);
                    cPaP=currentPlayer.getAvailablePieces();

                    }
                }
                stateScore eval = minimax(childState, depth - 1, false, alpha, beta);
               // System.out.println("Max player state score: " +eval.getScore());
                currentPlayer.addAvailablePiece(childState.getLatestPieceAdded());//this prolly wont work
                cPaP=currentPlayer.getAvailablePieces();
                if (eval.getScore()>maxEval.getScore()) {
                    maxEval = new stateScore(childState, eval.getScore());//dubious assigment of state score. Should be fixed
                    alpha = Math.max(alpha, eval.getScore());
                }

                if (beta <= alpha) {//might be wrong
                    break;
                }
            }
            return maxEval;
        } else {

            stateScore minEval = new stateScore(null, Integer.MAX_VALUE);

            for (GameBoard childState : generate_moves(gameState, oPaP)) {
                for (Piece piece : oPaP) {
                    if (piece.equals(childState.getLatestPieceAdded())) {
                        opponent.removeAvailablePiece(childState.getLatestPieceAdded());
                        oPaP=opponent.getAvailablePieces();
                    }
                }
                stateScore eval = minimax(childState, depth - 1, true, alpha, beta);
                //System.out.println("Min player state score: " +minEval.getScore());
                opponent.addAvailablePiece(childState.getLatestPieceAdded());
                oPaP=opponent.getAvailablePieces();
                if (eval.getScore()<minEval.getScore()) {
                    minEval = new stateScore(childState, eval.getScore());//dubious assigment of state score. Should be fixed
                    beta = Math.min(beta, eval.getScore());
                }
                if (beta <= alpha) {//might be wrong
                    break;
                }
            }
            return minEval;
        }

    }


    public Move iterative_deepening(GameBoard board, Piece chosenPiece, int maxDepth, boolean isMaximizing, int alpha, int beta) {//made it so that the game is passed through
        Move test;

        int bestMove = 0;
        while (true){
            stateScore result = minimax(gameState, maxDepth, isMaximizing, alpha, beta);//remeber these changes: done so to see if its a fix
           // System.out.println("MinMax score: " +result.getScore());
            if(result.getScore() > 0) {
//                System.out.println("-------------Fake Board-----------------------");
//                showBoardtmp(result.getState());
//                System.out.println("-------------   OVer   -----------------------");
                bestMove = result.getState().getIdByPiece(chosenPiece);

            }
            else{
              //  System.out.println("something actually happened");
                break;

            }
            maxDepth++;
        }
        test= new Move(chosenPiece,bestMove);
//        if (test.getPiece().equals(chosenPiece)){
//            System.out.println("chosenPiece Piece");
//            System.out.println(test.getTileId());
//        }
//        if(!gameState.isValidMove(test))
//        {
//            System.out.println("an illegal move was made");
//        }
       return test;
    }
    public void showBoardtmp(GameBoard board) {
        System.out.println("Current Board:");
        int id = 0;

        // Run through the board and display the pieces
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                Piece piece = board.getBoard()[i][j];

                if (piece != null) {
                    // Display the piece details
                    System.out.print(showPiecetmp(piece));
                } else {
                    // Display an empty space with an ID
                    System.out.printf("--%02d-", id);
                }

                System.out.print(" ");
                id++;
            }
            System.out.println(); // Move to the next row
        }

        System.out.println(); // Add an empty line for better readability
    }
    private String showPiecetmp(Piece piece)
    {
        // Looks at attributes of the piece and returns a string
        char C,He,S,Ho;
        if(piece.getColor()){C = 'W';}
        else{C = 'B';}
        if(piece.getHeight()){He = 'S';}
        else{He = 'B';}
        if(piece.getShape()){S = 'S';}
        else{S = 'C';}
        if(piece.getHole()){Ho = 'H';}
        else{Ho = 'F';}
        return ""+ C + He + S + Ho;
    }
}

