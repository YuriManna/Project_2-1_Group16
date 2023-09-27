package com.quarto.GUI;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public abstract class Images{
        BufferedImage board = ImageIO.read(new File("/images/Board.png"));
        BufferedImage WSSH = ImageIO.read(new File("/images/WSSH.png"));
        BufferedImage WSSF = ImageIO.read(new File("/images/WSSF.png"));
        BufferedImage WSCH = ImageIO.read(new File("/images/WSCH.png"));
        BufferedImage WSCF = ImageIO.read(new File("/images/WSCH.png"));
        BufferedImage WBSH = ImageIO.read(new File("/images/WBSH.png"));
        BufferedImage WBSF = ImageIO.read(new File("/images/WBSF.png"));
        BufferedImage WBCH = ImageIO.read(new File("/images/WBCH.png"));
        BufferedImage WBCF = ImageIO.read(new File("/images/WBCF.png"));
        BufferedImage BSSH = ImageIO.read(new File("/images/BSSH.png"));
        BufferedImage BSSF = ImageIO.read(new File("/images/BSSF.png"));
        BufferedImage BSCH = ImageIO.read(new File("/images/BSCH.png"));
        BufferedImage BSCF = ImageIO.read(new File("/images/BSCF.png"));
        BufferedImage BBSH = ImageIO.read(new File("/images/BBSH.png"));
        BufferedImage BBSF = ImageIO.read(new File("/images/BBSF.png"));
        BufferedImage BBCH = ImageIO.read(new File("/images/BBCH.png"));
        BufferedImage BBCF = ImageIO.read(new File("/images/BBCF.png"));
    protected Images() throws IOException {

    }
}
