package com.quarto.GUI;
import javax.swing.*;
import java.awt.*;

public class Table {
    private final JFrame gameFrame;
    private static Dimension frameDimension= new Dimension(600,600);

    public Table(){
        this.gameFrame= new JFrame("Quarto");
        gameFrame.setSize(frameDimension);
        gameFrame.setVisible(true);
    }
}

