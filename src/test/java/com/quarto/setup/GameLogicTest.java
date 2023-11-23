package com.quarto.setup;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class GameLogicTest {
    GameLogic gameLogic = new GameLogic();

    @Test
    public void testPlacePiece() {
        Pieces piece = new Pieces(true, true, true, true);
        gameLogic.placePiece(piece, 0);
        Assertions.assertEquals(piece, gameLogic.getBoard().getPieceFromBoard(0, 0));
        Assertions.assertFalse(gameLogic.getBoard().checkIfPieceIsAvailable(piece));

        Pieces secondPiece = new Pieces(false, false, false, false);
        gameLogic.placePiece(secondPiece, 5);
        Assertions.assertEquals(secondPiece, gameLogic.getBoard().getPieceFromBoard(1, 1));
    }

    @Test
    public void testCheckIfPieceIsAvailable() {
        Pieces piece = new Pieces(true, true, true, true);
        gameLogic.placePiece(piece, 0);
        Assertions.assertFalse(gameLogic.getBoard().checkIfPieceIsAvailable(piece));
    }

    @Test
    public void testMoveNotValid() {
        // Test invalid move
        Pieces piece = new Pieces(true, true, true, true);
        gameLogic.placePiece(piece, 0);
        Assertions.assertTrue(gameLogic.moveNotValid(piece, 0));

        // Test a valid move
        Pieces newPiece = new Pieces(false, false, false, false);
        Assertions.assertFalse(gameLogic.moveNotValid(newPiece, 1));
    }

    @Test
    public void testSetSelectedPiece() {
        Pieces piece = new Pieces(true, true, true, true);
        gameLogic.SetSelectedPiece(piece);
        Assertions.assertEquals(piece, gameLogic.getSelectedPiece());
    }
}