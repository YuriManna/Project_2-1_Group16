package com.quarto.player.ai;
import com.quarto.player.Player;
import com.quarto.setup.*;

import java.util.Random;

public class BaselineAgent {
    private Board board;
    private Pieces[] availablePiecesAgent;
    private Pieces[] availablePiecesOpponent;
    private Pieces pieceChosenForOpponent;
    private Player agent;
    private Player opponent;

    public BaselineAgent(boolean color) {
        board = new Board();
        agent = new Player(color,board);
        opponent = new Player(!color, board);
        availablePiecesAgent = agent.getAvailablePieces();
        availablePiecesOpponent = opponent.getAvailablePieces();
    }

    public Pieces getPieceChosenForOpponent() {
        return getPieceChosenForOpponent();
    }

    public Pieces[] getAvailablePiecesAgent() {
        return availablePiecesAgent;
    }

    //Method to make a random move
    // public void makeRandomMove(Pieces pieceToPlace) {
    //     Random random = new Random();
    //     int tileId = random.nextInt(board.getAvailableTileIds().size()); // Random tile
    //     Pieces pieceToChoose = availablePiecesOpponent[random.nextInt(availablePiecesOpponent.length)]; // Random piece from opponent's color
    //     pieceChosenForOpponent = pieceToChoose;

    //     Move move = new Move(pieceToPlace,tileId,pieceToChoose);

    //     if(agent.getLegalMoves().contains(move)){
    //         board.executeMove(move);
    //     }
    //     else makeRandomMove(pieceToPlace);
    // }

    // public void makeRandomMove(Pieces pieceToPlace) {
    //     Random random = new Random();
    //     int tileId = random.nextInt(board.getAvailableTileIds().size()); // Random tile
    //     Pieces pieceToChoose = availablePiecesOpponent[random.nextInt(availablePiecesOpponent.length)]; // Random piece from opponent's color
    //     pieceChosenForOpponent = pieceToChoose;

    //     Move move = new Move(pieceToPlace,tileId,pieceChosenForOpponent);

    //     if(agent.getLegalMoves().contains(move)){
    //         board.placePiece(pieceToPlace,tileId);
    //         board.setSelectedPiece(pieceChosenForOpponent);
    //     }

    //     else makeRandomMove(pieceToPlace);
    // }

    public int chooseRandomPlace(Pieces pieceToPlace) {
        //int chosenTileId = -1;
        Random random = new Random();
        int chosenTileId = random.nextInt(board.getAvailableTileIds().size()); // Random tile
        // Pieces pieceToChoose = availablePiecesOpponent[random.nextInt(availablePiecesOpponent.length)]; // Random piece from opponent's color
        // pieceChosenForOpponent = pieceToChoose;
        // Move move = new Move(pieceToPlace,tileId,pieceChosenForOpponent);

        // if(agent.getLegalMoves().contains(move)){
        //     //board.placePiece(pieceToPlace,tileId);
        //     chosenTileId = tileId;
        // }

        // else chooseRandomPlace(pieceToPlace);

        System.out.println("TILEID CHOSEN BY AI: "+chosenTileId);
        return chosenTileId;
    }

    public Pieces chooseRandomOpponentPiece() {
        Random random = new Random();
        Pieces pieceToChoose = availablePiecesOpponent[random.nextInt(availablePiecesOpponent.length)]; // Random piece from opponent's color
        pieceChosenForOpponent = pieceToChoose;

     
        // board.setSelectedPiece(pieceChosenForOpponent);
        System.out.println("PIECE CHOSEN BY AI: "+pieceChosenForOpponent);
        return pieceChosenForOpponent;
    }






    // if(isLeftMouseButton(e)){
    //     Pieces selectedPiece = gameLogic.getSelectedPiece();
    //     if(selectedPiece == null || gameLogic.moveNotValid(selectedPiece,tileId)){return;}
    //     gameLogic.placePiece(selectedPiece,tileId);
    //     try {
    //         assignTilePieceIcon(gameLogic.getBoard(), tileId, selectedPiece);
    //         sidePanel.reloadTiles();
    //         gameLogic.checkTurn();
    //         gameLogic.checkGameStatus();
    //         turnLabel.setText(gameLogic.getMessage());
    //     } catch (IOException ex) {
    //         throw new RuntimeException(ex);
    //     }
    //     gameLogic.SetSelectedPiece(null);



    // public void chooseRandomPiece(Pieces pieceToPlace) {
    //     Random random = new Random();
    //     int tileId = random.nextInt(board.getAvailableTileIds().size()); // Random tile
    //     Pieces pieceToChoose = availablePiecesOpponent[random.nextInt(availablePiecesOpponent.length)]; // Random piece from opponent's color
    //     pieceChosenForOpponent = pieceToChoose;

    //     Move move = new Move(pieceToPlace,tileId,pieceToChoose);

    //     if(agent.getLegalMoves().contains(move)){
    //         board.executeMove(move);
    //     }
    //     else chooseRandomPiece(pieceToPlace);
    // }







}

