package com.quarto.setup;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class GameLogicTest {
    GameLogic gameLogic = new GameLogic();
    Pieces WSSH = new Pieces(true,true,true,true);
    Pieces WSSF = new Pieces(true,true,true,false);
    Pieces WSCH = new Pieces(true,true,false,true);
    Pieces WSCF = new Pieces(true,true,false,false);

    @Test
    public void testPlacePiece() {
        gameLogic.placePiece(WSSF, 0);
        assertEquals(WSSF, gameLogic.getBoard().getPieceFromBoard(0, 0));
        assertFalse(gameLogic.getBoard().checkIfPieceIsAvailable(WSSF));

        gameLogic.placePiece(WSSH, 5);
        assertEquals(WSSH, gameLogic.getBoard().getPieceFromBoard(1, 1));
    }

    @Test
    public void testCheckIfPieceIsAvailable() {
        gameLogic.placePiece(WSCF, 0);
        assertFalse(gameLogic.getBoard().checkIfPieceIsAvailable(WSCF));
    }

    @Test
    public void testMoveNotValid() {
        // Test when the selected piece is not available
        gameLogic.placePiece(WSSF, 0);
        assertTrue(gameLogic.moveNotValid(WSSF, 5));

        // Test when the tile is already occupied
        gameLogic.placePiece(WSSH, 8);
        assertTrue(gameLogic.moveNotValid(WSCH, 8));

        // Test when the game is won
        gameLogic.getBoard().addPiece(WSCH, 0);
        gameLogic.getBoard().addPiece(WSCF, 1);
        gameLogic.getBoard().addPiece(WSSH, 2);
        gameLogic.getBoard().addPiece(WSSF, 3);
        assertTrue(gameLogic.moveNotValid(new Pieces(false, false, false, false), 5));
    }

    @Test
    public void testSetSelectedPiece() {
        gameLogic.SetSelectedPiece(WSSH);
        assertEquals(WSSH, gameLogic.getSelectedPiece());
    }
}
