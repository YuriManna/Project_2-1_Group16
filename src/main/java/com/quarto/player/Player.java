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
        availablePieces = board.getAvaileblePieces(color);
    }

    public Pieces[] getAvailablePieces() {
        return availablePieces;
    }

    // returns all the possible moves at the current game status
    public List<Move> getLegalMoves(){
        Pieces piece = board.getSelectedPiece();
        List<Move> legalMoves = new ArrayList<>();
        Pieces[] opponentPieces = board.getAvaileblePieces(!color);

        for (int i = 0; i < board.getAvailableTileIds().size(); i++) {
            for (int j = 0; j < opponentPieces.length; j++){
                if(opponentPieces[j] == null){
                    continue;
                }
                Move move = new Move(piece, i, opponentPieces[j]);
            }
        }

        return legalMoves;
    }
}

