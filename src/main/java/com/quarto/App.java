package com.quarto;

import com.quarto.GUI.Table;
import com.quarto.setup.Board;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@SpringBootApplication
public class App {

    public static void main(String[] args) {
        //SpringApplication.run(App.class, args);

        Board board = new Board();
        Table table = new Table();
    }
}
