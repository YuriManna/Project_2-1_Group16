package com.quarto.setup;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class BoardTest {
    Pieces WSSH = new Pieces(true,true,true,true);
    Pieces WSSF = new Pieces(true,true,true,false);
    Pieces WSCH = new Pieces(true,true,false,true);
    Pieces WSCF = new Pieces(true,true,false,false);
    Board board = new Board();

    @Test
    void testCheckIfWon() {
        // Test for a winning condition in a row
        board.addPiece(WSSF, 0);
        board.addPiece(WSSH, 1);
        board.addPiece(WSCF, 2);
        board.addPiece(WSCH, 3);
        assertTrue(board.checkIfWon(0, 0));

        // Test for a winning condition in a column
        board = new Board();
        board.addPiece(WSCF, 0);
        board.addPiece(WSCH, 4);
        board.addPiece(WSSF, 8);
        board.addPiece(WSSH, 12);
        assertTrue(board.checkIfWon(0, 0));

        // Test for a winning condition in a diagonal
        board = new Board();
        board.addPiece(WSSH, 0);
        board.addPiece(WSSF, 5);
        board.addPiece(WSCH, 10);
        board.addPiece(WSCF, 15);
        assertTrue(board.checkIfWon(0, 0));
    }

    @Test
    void testTileIsOccupied() {
        // Assuming the board is empty initially
        assertFalse(board.tileIsOccupied(0));

        // Assuming a piece is added at tileId 0
        board.addPiece(WSCH, 0);
        assertTrue(board.tileIsOccupied(0));

    }

    @Test
    void testAddPiece() {
        board.addPiece(WSSF, 0);
        assertEquals(WSSF, board.getPieceFromBoard(0, 0));
    }

    @Test
    void testRemovePiece() {
        // Removing the piece from the available pieces of the current player
        board.addPiece(WSSF, 0);
        board.removePiece(WSSF);
        assertEquals(WSSF, board.getPieceFromBoard(0, 0));
    }

    @Test
    void testToTileID() {
        assertEquals(0, board.toTileID(0, 0));
        assertEquals(11, board.toTileID(3, 2));
        assertEquals(15, board.toTileID(3, 3));
    }

    @Test
    void testGetAvailableTileIds() {
        // All tiles available for an empty board
        List<Integer> availableTiles = board.getAvailableTileIds();
        assertEquals(16, availableTiles.size());

        // Test for an occupied tile
        int tileToOccupy = 5;
        board.addPiece(WSSF, tileToOccupy);
        availableTiles = board.getAvailableTileIds();
        assertFalse(availableTiles.contains(tileToOccupy));
    }

    @Test
    void testExecuteMove() {
        Pieces chosenPiece = new Pieces(false, false, false, false);
        Move move = new Move(WSSH, 5, chosenPiece);
        board.executeMove(move);

        // Check if the piece is added to the specified location
        assertEquals(WSSH, board.getPieceFromBoard(1, 1));
        // Check if the same piece is removed
        assertEquals(8,board.getAvailableWhites().length);
        // Check if the selected piece is set correctly
        assertEquals(chosenPiece, board.getSelectedPiece());
    }

    @Test
    void testCheckIfPieceIsAvailable(){
        board.addPiece(WSSF, 5);
        assertFalse(board.checkIfPieceIsAvailable(WSSF));
    }
}
