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
    public void select(Pieces piece,int row, int column){//TODO move everything thats useful to GameLogic
        if (isValid(row,column)){
            board.getBoard()[row][column] = piece;
            piece.setRow(row);
            piece.setColumn(column);
            if (piece.isColor()==true){
                for (int i = 0; i < board.getAvailableBlacks().length; i++) {
                    if (board.getAvailableBlacks()[i] == piece){
                        board.getAvailableBlacks()[i] = null;
                    }
                }
            }else {
                for (int i = 0; i < board.getAvailableWhites().length; i++) {
                    if (board.getAvailableWhites()[i] == piece){
                        board.getAvailableWhites()[i] = null;
                    }
                }
            }
        }
    }

    public boolean isValid(int row, int column) {
        if (board.getBoard()[row][column] == null)
            return true;
        else
            return false;
    }
}
