package com.quarto.GUI;

import com.quarto.setup.Board;
import com.quarto.setup.Pieces;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


public class GameTable {
    private final Board board;
    private final JFrame gameFrame;
    private final BoardPanel boardPanel;
    private final SidePanel sidePanel;
    private static Dimension GAME_FRAME_DIMENSION = new Dimension(1100,750);
    private static Dimension BOARD_PANEL_DIMENSION = new Dimension(700,700);
    private static Dimension TILE_PANEL_DIMENSION = new Dimension(20,20);
    private static Dimension SIDE_PANEL_DIMENSION = new Dimension(700,700);
    //main game frame
    public GameTable() throws IOException {
        this.board = new Board();

        this.gameFrame = new JFrame("Quarto");
        this.gameFrame.setSize(GAME_FRAME_DIMENSION);
        this.gameFrame.setLayout(new BorderLayout());

        final JMenuBar tableMenuBar = new JMenuBar();
        populateMenuBar(tableMenuBar);
        this.gameFrame.setJMenuBar(tableMenuBar);

        this.boardPanel = new BoardPanel();
        this.gameFrame.add(this.boardPanel, BorderLayout.CENTER);
        this.boardPanel.setVisible(true);

        this.sidePanel = new SidePanel();
        this.gameFrame.add(this.sidePanel, BorderLayout.EAST);

        this.gameFrame.setLocationRelativeTo(null);
        this.gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //this.gameFrame.setResizable(false);
        this.gameFrame.setVisible(true);
    }
    private void populateMenuBar(final JMenuBar tableMenuBar){
        tableMenuBar.add(createFileMenu());
    }

    private JMenu createFileMenu() {
        final JMenu fileMenu = new JMenu("options");
        final JMenuItem openPGN = new JMenuItem("load game");
        openPGN.addActionListener(e -> System.out.println("need a pgn file"));
        fileMenu.add(openPGN);

        final JMenuItem exitMenuItem = new JMenuItem("exit");
        exitMenuItem.addActionListener(e -> System.exit(0));
        fileMenu.add(exitMenuItem);
        return fileMenu;
    }
    //panel for the board of the game
    private class BoardPanel extends JPanel{
        final List<TilePanel> boardTiles;
        Image im= ImageIO.read(getClass().getResource("/images/QuartoBoard.png"));
        BoardPanel() throws IOException {
            super(new GridLayout(4,4));
            this.boardTiles = new ArrayList<>();
            //final BufferedImage pieceImage = ImageIO.read(getClass().getResource("/images/QuartoBoard.png"));
            //add(new JLabel(new ImageIcon(pieceImage)));
            for (int i = 0; i < 16; i++){
                final TilePanel tilePanel = new TilePanel(this, i);
                this.boardTiles.add(tilePanel);
                add(tilePanel);
            }
            setPreferredSize(BOARD_PANEL_DIMENSION);
            
            this.setBorder(new MatteBorder(1, 1, 1, 1, Color.black));

            validate();
        }
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(im, 0, 0, getWidth(), getHeight(), this);
        
        }
    }
    //panel for the tiles inside the board
    private class TilePanel extends JPanel{
        private final int tileId;
        TilePanel(final BoardPanel boardPanel, final int tileId) throws IOException {
            super(new GridBagLayout());
            this.tileId = tileId;
            setPreferredSize(TILE_PANEL_DIMENSION);
            setBorder(new MatteBorder(1, 1, 1, 1, Color.black));
            setBackground(Color.decode("#FFFACD"));
            validate();            
            setOpaque(false);
            setVisible(true);

        }
        
    
    }
        private void assignTilePieceIcon(final Board board,final TilePanel tilePanel, final int tileId, Pieces piece) throws IOException {
            tilePanel.removeAll();
            if(board.tileIsOccupied(tileId)){
                final BufferedImage pieceImage = ImageIO.read(getClass().getResource("/images/"+piece.toString()+".png"));
                tilePanel.add(new JLabel(new ImageIcon(pieceImage)));
            }

        }
    //panel for the unplaced pieces
    private class SidePanel extends JPanel{
        final List<SideTilePanel> whites;
        final List<SideTilePanel> blacks;

        SidePanel() throws IOException {
            super(new GridLayout(4,4));
            this.whites = new ArrayList<>();
            for (int i = 0; i < 8; i++){
                final SideTilePanel sideTilePanel = new SideTilePanel(this, i, true);
                this.whites.add(sideTilePanel);
                add(sideTilePanel);
            }
            this.blacks = new ArrayList<>();
            for (int i = 0; i < 8; i++){
                final SideTilePanel sideTilePanel = new SideTilePanel(this, i, false);
                this.blacks.add(sideTilePanel);
                add(sideTilePanel);
            }

            setPreferredSize(SIDE_PANEL_DIMENSION);
            validate();
        }
    }
    //panel for tiles in the side board
    private class SideTilePanel extends JPanel{
        private final int tileId;
        SideTilePanel(final SidePanel sidePanel, final int tileId, boolean teamColor) throws IOException {
            super(new GridBagLayout());
            this.tileId = tileId;
            setPreferredSize(TILE_PANEL_DIMENSION);
            setBorder(new MatteBorder(1, 1, 1, 1, Color.lightGray));
            Color color;
            if(teamColor){color = Color.WHITE;
            }else{color = Color.GRAY;}
            setBackground(color);
            //Pieces piece = board.getAvailableWhites().get(tileId);
            //final BufferedImage pieceImage = ImageIO.read(getClass().getResource("/images/"+piece.toString()+".png"));
            //add(new JLabel(new ImageIcon(pieceImage)));
            assignTilePieceIcon(board, tileId,teamColor);
            setVisible(false);
            validate();
        }
        private void assignTilePieceIcon(final Board board, final int tileId, boolean teamColor) throws IOException {
            this.removeAll();
            Pieces piece;
            if (teamColor) {
                piece = board.getAvailableWhites().get(tileId);
            }else {
                piece = board.getAvailableBlacks().get(tileId);
            }
            String url = "/images/"+piece.toString()+".png";
            try (InputStream inputStream = getClass().getResourceAsStream(url)) {
                if (inputStream != null) {
                    final BufferedImage pieceImage = ImageIO.read(inputStream);
                    this.add(new JLabel(new ImageIcon(pieceImage)));
                } else {
                    System.err.println("Resource not found: " + url);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
