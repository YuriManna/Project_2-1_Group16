package com.quarto.setup;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BoardTest {
    Board board = new Board();

    @Test
    void testCheckIfWon() {
        // Test for a winning condition in a row
        board.addPiece(new Pieces(true, true, true, true), 0); // Placing a piece at (0,0)
        board.addPiece(new Pieces(true, true, true, true), 1);
        board.addPiece(new Pieces(true, true, true, true), 2);
        board.addPiece(new Pieces(true, true, true, true), 3);
        Assertions.assertTrue(board.checkIfWon(0, 0)); // Expecting a win at position (0,0)

        // Test for a winning condition in a column
        board = new Board(); // Reset the board for a new test
        board.addPiece(new Pieces(true, true, true, true), 0); // Placing a piece at (0,0)
        board.addPiece(new Pieces(true, true, true, true), 4);
        board.addPiece(new Pieces(true, true, true, true), 8);
        board.addPiece(new Pieces(true, true, true, true), 12);
        Assertions.assertTrue(board.checkIfWon(0, 0)); // Expecting a win at position (0,0)

        // Test for a winning condition in a diagonal
        board = new Board(); // Reset the board for a new test
        board.addPiece(new Pieces(true, true, true, true), 0); // Placing a piece at (0,0)
        board.addPiece(new Pieces(true, true, true, true), 5);
        board.addPiece(new Pieces(true, true, true, true), 10);
        board.addPiece(new Pieces(true, true, true, true), 15);
        Assertions.assertTrue(board.checkIfWon(0, 0)); // Expecting a win at position (0,0)
    }

    @Test
    void testTileIsOccupied() {
        // Assuming the board is empty initially
        Assertions.assertFalse(board.tileIsOccupied(0));

        // Assuming a piece is added at tileId 0
        board.addPiece(new Pieces(true, true, true, true), 0);
        Assertions.assertTrue(board.tileIsOccupied(0));

    }

    @Test
    void testAddPiece() {
        Pieces piece = new Pieces(true, true, true, true);
        board.addPiece(piece, 0);
        Assertions.assertEquals(piece, board.getPieceFromBoard(0, 0));
    }

    @Test
    void testRemovePiece() {
        // removing the piece from the available pieces of the current player
        Pieces piece = new Pieces(true, true, true, true);
        board.addPiece(piece, 0);
        board.removePiece(piece);
        Assertions.assertEquals(piece, board.getPieceFromBoard(0, 0));
    }

    @Test
    void testToTileID() {
        Board b = new Board();
        assertEquals(0,b.toTileID(0,0));
        assertEquals(11, b.toTileID(3,2));
        assertEquals(15,b.toTileID(3,3));
    }
}