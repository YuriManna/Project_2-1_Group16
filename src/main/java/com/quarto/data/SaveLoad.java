package com.quarto.data;

import com.quarto.setup.Board;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

public class SaveLoad {
    Board board;
    public SaveLoad(Board board){
        this.board = board;
    }
    public void save(){
        try {
            ObjectOutputStream oos=new ObjectOutputStream(new FileOutputStream(new File("save.dat")));
            DataStorage dataStorage = new DataStorage();
            for (int i = 0; i < board.getBoard().length; i++) {
                for (int j=0;j<board.getBoard()[0].length;j++){
                    if (board.getBoard()[i][j]!=null){
                        dataStorage.piece.add(board.getBoard()[i][j]);
                    }
                }
            }
            oos.writeObject(dataStorage);
            System.out.println("Save Success");
        }catch (Exception e){
            System.out.println("Save Exception");
        }
    }
}

