package com.quarto.GUI;

import com.quarto.setup.Pieces;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

public abstract class Images{
        BufferedImage board = ImageIO.read(new File("/Board.png"));
        BufferedImage WSSH = ImageIO.read(new File("/WSSH.png"));
        BufferedImage WSSF = ImageIO.read(new File("/WSSF.png"));
        BufferedImage WSCH = ImageIO.read(new File("/WSCH.png"));
        BufferedImage WSCF = ImageIO.read(new File("/WSCH.png"));
        BufferedImage WBSH = ImageIO.read(new File("/WBSH.png"));
        BufferedImage WBSF = ImageIO.read(new File("/WBSF.png"));
        BufferedImage WBCH = ImageIO.read(new File("/WBCH.png"));
        BufferedImage WBCF = ImageIO.read(new File("/WBCF.png"));
        BufferedImage BSSH = ImageIO.read(new File("/BSSH.png"));
        BufferedImage BSSF = ImageIO.read(new File("/BSSF.png"));
        BufferedImage BSCH = ImageIO.read(new File("/BSCH.png"));
        BufferedImage BSCF = ImageIO.read(new File("/BSCF.png"));
        BufferedImage BBSH = ImageIO.read(new File("/BBSH.png"));
        BufferedImage BBSF = ImageIO.read(new File("/BBSF.png"));
        BufferedImage BBCH = ImageIO.read(new File("/BBCH.png"));
        BufferedImage BBCF = ImageIO.read(new File("/BBCF.png"));
    protected Images() throws IOException {

    }
}
