package com.quarto.ai;

import com.quarto.Model.GameBoard;
import com.quarto.Model.Piece;

import java.util.ArrayList;

public class PieceEvaluationFunction {
    Piece[] availablePieces;
    GameBoard board;

    public Piece leastLikelyPiece(Piece[] availablePieces, GameBoard board){
        this.availablePieces = availablePieces;
        this.board = board;
        Piece[] boardPieces = getBoardPieces();

        int[] featureCount = new int[6];
        for (Piece piece : boardPieces) {
            if(piece.getHeight()){
                featureCount[0] += 1; // small
            }
            else featureCount[1] += 1; // big

            if(piece.getShape()){
                featureCount[2] += 1; // square
            }
            else featureCount[3] += 1; // circle

            if(piece.getHole()){
                featureCount[4] += 1; // hollow
            }
            else featureCount[5] += 1; // full
        }

        for (Piece piece : availablePieces) {

        }

        return null;
    }

    private Piece[] getBoardPieces() {
        ArrayList<Piece> boardPieces = new ArrayList<>();
        for (int i = 0; i < 16; i++) {
            Piece piece = board.getPieceById(i);
            if(piece != null){
                boardPieces.add(piece);
            }
        }
        return boardPieces.toArray(new Piece[0]);
    }

}
