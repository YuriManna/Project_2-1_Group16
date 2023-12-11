package com.quarto;

import com.quarto.Model.Piece;

public class test {

    public test(){
    }

    //private final boolean COLOR; // White(T) or Black(F)
    //private final boolean HEIGHT; // Small(T) or Big(F)
    //private final boolean SHAPE; // Square(T) or Circle(F)
    //private final boolean HOLE; // Hole(T) or Full(F)
    public void visualizeBoard() {
        System.out.println("Current Board:");
        Piece WSSH = new Piece(true, true, true, true);
        // Display the piece details
        System.out.print(WSSH.toString());
    }

    public static void main(String[] args) {
        test t = new test();
        t.visualizeBoard();
    }
}



