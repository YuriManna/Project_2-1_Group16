package com.quarto.View;

import com.quarto.Model.*;

import java.io.IOException;

public interface GameInterface {
    public Piece choosePiece(Player opponent);
    public Move makeMove(GameBoard board, Piece playablePiece);
    public void updateBoard(GameBoard board) throws IOException;
    public void showWinningMessage(Player player);
    public Piece AIChoosePiece(Player opponent, QuartoGame game);
}
