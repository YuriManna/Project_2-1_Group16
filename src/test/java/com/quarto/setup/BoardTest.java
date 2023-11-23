package com.quarto.setup;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    @Test
    void checkIfWon() {
    }

    @Test
    void tileIsOccupied() {
    }

    @Test
    void addPiece() {
    }

    @Test
    void removePiece() {
    }

    @Test
    void toTileID() {
        Board b = new Board();
        assertEquals(0,b.toTileID(0,0));
        assertEquals(11, b.toTileID(3,2));
        assertEquals(15,b.toTileID(3,3));
    }
}