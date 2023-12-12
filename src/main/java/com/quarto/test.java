package com.quarto;

import com.quarto.Model.GameBoard;
import com.quarto.Model.Move;
import com.quarto.Model.Piece;

import java.io.Console;

public class test {
    GameBoard board = new GameBoard();


    Piece [][] testboard = board.getBoard();

    Piece piece1 = new Piece(true, true, true, true);
    Piece piece2 = new Piece(true, true, true, true);
    Piece piece3 = new Piece(true, true, true, true);
    Piece piece4 = new Piece(true, true, true, true);

    Move move1 = new Move(piece1, 0);
    Move move2 = new Move(piece2, 1);
    Move move3 = new Move(piece3, 2);
    Move move4 = new Move(piece4, 3);


    public void test() {

        board.addPieceToBoard(move1);
        board.addPieceToBoard(move2);
        board.addPieceToBoard(move3);
        board.addPieceToBoard(move4);/// Add an empty line for better readability
    }

    public static void main(String[] args) {
        test test = new test();
        test.test();
    }


}



