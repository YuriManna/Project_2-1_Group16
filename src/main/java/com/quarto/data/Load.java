package com.quarto.data;

import com.quarto.setup.Board;
import com.quarto.setup.Pieces;

import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

public class Load {
    Board board;
    public Board load(){
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File("save.dat")));
            DataStorage dataStorage = (DataStorage) ois.readObject();
            board= dataStorage.board;
            System.out.println("Load Success");

        }catch  (Exception e){
            System.out.println("Load Exception ");
        }
        return board;
    }
}
