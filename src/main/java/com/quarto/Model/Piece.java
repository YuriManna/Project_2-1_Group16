package com.quarto.Model;

/*
Responsibilities:
    Represent a game piece with attributes like size, color, shape, and whether it has a hole or not.
*/

public class Piece {
    public boolean[] Properties;
    private final boolean COLOR; // White(T) or Black(F)
    private final boolean HEIGHT; // Small(T) or Big(F)
    private final boolean SHAPE; // Square(T) or Circle(F)
    private final boolean HOLE; // Hole(T) or Full(F)

    public Piece(boolean color, boolean height, boolean shape, boolean hole) {
        COLOR = color;
        HEIGHT = height;
        SHAPE = shape;
        HOLE = hole;
        Properties = new boolean[]{color, height, shape, hole};
    }

    public boolean getColor() {
        return COLOR;
    }

    public boolean getHeight() {
        return HEIGHT;
    }

    public boolean getShape() {
        return SHAPE;
    }

    public boolean getHole() {
        return HOLE;
    }

    public void setPiece(String piece) {

    }
    public boolean[] getProperties(){return Properties;}

//    public static void main(String[] args) {
//        int count =0;
//        Piece p1=new Piece(true,true,false,false);
//        Piece p2 =new Piece(false,true,true,true);
//        Piece p3 =new Piece(false,true,false,true);
//        Piece p4 =new Piece(true,true,false,true);
//        boolean[][] checkArr= {p1.getProperties(),p2.getProperties(),p3.getProperties(),p4.getProperties()};
//        for (int i = 0; i <checkArr.length; i++) {
//            for (int j = 0; j < checkArr.length; j++) {
//                System.out.print(checkArr[i][j]+ " " );
//            }
//            System.out.println();
//        }
//        for (int i = 0; i < checkArr.length; i++) {
//            count++;
//            for (int j = 0; j < checkArr.length; j++) {
//                if(j!=3&&checkArr[j][i]==checkArr[j+1][i]){
//                    count++;
//                    System.out.println(count);
//                    if(count==4){}
//                }
//
//            }
//            System.out.println("next collumn");
//            count=0;
//        }
//    }
}
