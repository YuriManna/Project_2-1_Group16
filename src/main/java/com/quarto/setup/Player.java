package com.quarto.setup;

import java.util.ArrayList;
import java.util.List;

public class Player { //Store available pieces and colour
    private boolean isFirstPlayer;
    private List<Pieces> availablePieces;
    private Pieces playablePiece;
    private Pieces choosePiece;

    public Player(boolean isFirstPlayer) {
        availablePieces = new ArrayList<>();
        this.isFirstPlayer = isFirstPlayer;

    }

    public void removePiece(Pieces piece) {
        availablePieces.remove(piece);
    }

    public List<Pieces> getAvailablePieces() {
        return availablePieces;
    }

    public void choosePiece(Player player){

    }
}

