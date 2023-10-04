package com.quarto.data;

import com.quarto.setup.Board;
import java.io.*;

public class Save {
    Board board;
    public Save(Board board){
        this.board = board;
    }
    public void save(){
        try {
            ObjectOutputStream oos=new ObjectOutputStream(new FileOutputStream(new File("save.dat")));
            DataStorage dataStorage = new DataStorage();
            dataStorage.board = board;
            oos.writeObject(dataStorage);
            System.out.println("Save Success");
        }catch (Exception e){
            System.out.println("Save Exception ");
        }
    }
}

