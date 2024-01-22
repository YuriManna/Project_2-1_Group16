package com.quarto.ai;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

import com.quarto.Model.GameBoard;
import com.quarto.Model.Move;
import com.quarto.Model.Piece;
import com.quarto.Model.Player;
import com.quarto.Model.QuartoGame;

public class GameDataGenerator {
    private QuartoGame game;
    private EvaluationFunction ef;

    

    public GameDataGenerator() {
        this.game = new QuartoGame(false,false);
        this.ef = new EvaluationFunction();
        
    }

    public void generateGameData(String filename, int numGames) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (int i = 0; i < numGames; i++) {
                playGame(writer);
            }
        }
    }

    void playGame(BufferedWriter writer) throws IOException {
        GameBoard gameBoard = new GameBoard();

        // Perform actions in the environment
        while(!gameBoard.isGameFinished()) {

            String csv = gameBoard.gameStateToCSV(null, game, true);
            writer.write(csv);
            writer.newLine();

            Move whiteMove = getRandomAction(gameBoard, true);
            takeAction(gameBoard, whiteMove, true);

            csv = gameBoard.gameStateToCSV(whiteMove.getPiece(), game, true);
            writer.write(csv);
            writer.newLine();

            if (gameBoard.isGameFinished()) {
                break;
            }

            Move blackMove = getRandomAction(gameBoard, false);
            takeAction(gameBoard, blackMove, false);

            csv = gameBoard.gameStateToCSV(blackMove.getPiece(), game, false);
            writer.write(csv);
            writer.newLine();

            if (gameBoard.isGameFinished()) {
                break;
            }
        }
    }


      public Move makeRandomMove(GameBoard board, Piece playablePiece){
        Random random = new Random();
        
        int randomTileId = random.nextInt(16);
        Move move = new Move(playablePiece, randomTileId);

        if(!board.isValidMove(move)){
            return makeRandomMove(board, playablePiece);
        }
        return move;
    }

    public Piece chooseRandomPiece(Player opponent, QuartoGame game){
        GameBoard board = game.getGameBoard();
        PieceEvaluationFunction function = new PieceEvaluationFunction();
        if (opponent.getAvailablePieces().length > 0) {
            Piece chosenPiece = function.leastLikelyPiece(opponent.getAvailablePieces(),board);
            opponent.removeAvailablePiece(chosenPiece);
            return chosenPiece;
        } else {
            return null;
        }
    }

    static Move getRandomAction(GameBoard gameBoard, boolean isWhitesTurn) {
        Random random = new Random();
            int tileId = random.nextInt(16);
            Piece piece;
            if (isWhitesTurn) {
                piece = gameBoard.getWhitesList().get(random.nextInt(gameBoard.getWhitesList().size()));
            } else {
                piece = gameBoard.getBlacksList().get(random.nextInt(gameBoard.getBlacksList().size()));
            }
            return new Move(piece, tileId);
        }

    static void takeAction(GameBoard gameBoard, Move action, boolean isWhitesTurn) {
        while (!gameBoard.isValidMove(action)) {
            action = getRandomAction(gameBoard, isWhitesTurn);
        }

        gameBoard.addPieceToBoard(action);

        if (isWhitesTurn) {
            gameBoard.getWhitesList().remove(action.getPiece());
        } else {
            gameBoard.getBlacksList().remove(action.getPiece());
        }
        //showBoard(gameBoard);
    }

    public static void showBoard(GameBoard board) {
        System.out.println("Current Board:");
        int id = 0;

        //Run through the board and display the pieces
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                Piece piece = board.getBoard()[i][j];

                if (piece != null) {
                    System.out.print(piece.toString());
                } else {
                    System.out.printf("--%02d-", id);
                }

                System.out.print(" ");
                id++;
            }
            System.out.println(); 
        }

        System.out.println(); 
    }

    public void showWinningMessage(boolean isWhitesTurn){
        if (isWhitesTurn) {
            System.out.println("White player wins!");
        } else {
            System.out.println("Black player wins!");
        }
    }

    public static void main(String[] args) {
        GameDataGenerator generator = new GameDataGenerator();
        try {
            generator.generateGameData("src/main/resources/gamedata.csv", 10000);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    

}