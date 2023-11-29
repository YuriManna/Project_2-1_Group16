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

    // Method to make a random move
    public void makeRandomMove(Pieces pieceToPlace) {
        Random random = new Random();
        int tileId = random.nextInt(board.getAvailableTileIds().size()); // Random tile
        Pieces pieceToChoose = availablePiecesOpponent[random.nextInt(availablePiecesOpponent.length)]; // Random piece from opponent's color
        pieceChosenForOpponent = pieceToChoose;

        Move move = new Move(pieceToPlace,tileId,pieceToChoose);

        if(agent.getLegalMoves().contains(move)){
            board.executeMove(move);
        }
        else makeRandomMove(pieceToPlace);
    }
}

