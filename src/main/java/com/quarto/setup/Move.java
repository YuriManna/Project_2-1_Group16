package com.quarto.setup;

public class Move {
    private int row;
    private int column;
    private Pieces piece;
    private Board board;

    public Move(int row, int column, Pieces piece, Board board) {
        this.row = row;
        this.column = column;
        this.piece = piece;
        this.board = board;
    }

    public boolean isValid(int row, int column) {
        if (board.getBoard()[row][column] == null)
            return true;
        else
            return false;
    }
}
