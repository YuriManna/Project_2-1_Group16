package com.quarto.player;

import com.quarto.setup.Board;
import com.quarto.setup.Move;
import com.quarto.setup.Pieces;

import java.util.ArrayList;
import java.util.List;

public class Player { //Store available pieces and colour
    private final boolean color;
    private final Board board;
    private final Pieces[] availablePieces;

    public Player(boolean color, Board board) {
        this.board = board;
        this.color = color;
        availablePieces = board.getAvailablePieces(color);
    }

    public Pieces[] getAvailablePieces() {
        return availablePieces;
    }

    // returns all the possible moves at the current game status
    public List<Move> getLegalMoves(){
        Pieces piece = board.getSelectedPiece();
        List<Move> legalMoves = new ArrayList<>();
        Pieces[] opponentPieces = board.getAvailablePieces(!color);

        for (int i = 0; i < board.getAvailableTileIds().size(); i++) { // for each available tile
            for (int j = 0; j < opponentPieces.length; j++){ // for each opponent piece
                if(opponentPieces[j] == null){ // if the piece is not available
                    continue;
                }
                Move move = new Move(piece, board.getAvailableTileIds().get(i), opponentPieces[j]); // create a move
                legalMoves.add(move); // add the move to the list of possible moves
            }
        }

        return legalMoves;
    }
}

