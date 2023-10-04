package com.quarto.GUI;

import com.quarto.setup.Board;
import com.quarto.setup.Pieces;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static javax.swing.SwingUtilities.isLeftMouseButton;
import static javax.swing.SwingUtilities.isRightMouseButton;


public class GameTable {
    private final Board board;
    private final JFrame gameFrame;
    private final BoardPanel boardPanel;
    private final SidePanel sidePanel;

    private final JLabel turnLabel;

    private final JPanel textPanel;

    private Pieces selectedPiece;

    private static Dimension GAME_FRAME_DIMENSION = new Dimension(1200,650);
    private static Dimension BOARD_PANEL_DIMENSION = new Dimension(600,605);
    private static Dimension TILE_PANEL_DIMENSION = new Dimension(20,20);
    private static Dimension SIDE_PANEL_DIMENSION = new Dimension(600,600);

    private static Dimension TURN_LABEL_DIMENSION = new Dimension(100,20);

    private static int turnCounter = 1;
    //main game frame
    public GameTable() throws IOException {
        this.board = new Board();

        this.gameFrame = new JFrame("Quarto");
        this.gameFrame.setSize(GAME_FRAME_DIMENSION);
        this.gameFrame.setLayout(new BorderLayout());

        final JMenuBar tableMenuBar = new JMenuBar();
        populateMenuBar(tableMenuBar);
        this.gameFrame.setJMenuBar(tableMenuBar);

        JPanel borderPanel = new JPanel();
        borderPanel.setLayout(new BorderLayout());
        borderPanel.setBorder(new MatteBorder(7, 7, 7, 7, new Color(165, 42, 42)));
        this.gameFrame.add(borderPanel, BorderLayout.CENTER);

        this.boardPanel = new BoardPanel();
        borderPanel.add(this.boardPanel, BorderLayout.CENTER);
        this.boardPanel.setVisible(true);

        this.sidePanel = new SidePanel();
        this.gameFrame.add(this.sidePanel, BorderLayout.EAST);

        this.textPanel = new JPanel();
        this.textPanel.setSize(TURN_LABEL_DIMENSION);
        this.textPanel.setBackground(new Color(227, 215, 183));
        this.turnLabel = new JLabel("Player 1 starts the game! Choose a white piece");
        this.turnLabel.setSize(TURN_LABEL_DIMENSION);
        this.textPanel.add(this.turnLabel);
        this.gameFrame.add(textPanel, BorderLayout.NORTH);

        this.gameFrame.setLocationRelativeTo(null);
        this.gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //this.gameFrame.setResizable(false);
        this.gameFrame.setVisible(true);
    }
    private void populateMenuBar(final JMenuBar tableMenuBar){
        tableMenuBar.add(createFileMenu());
    }

    private JMenu createFileMenu() {
        final JMenu fileMenu = new JMenu("Options");
        final JMenuItem openPGN = new JMenuItem("Load game");
        openPGN.addActionListener(e -> System.out.println("need a pgn file"));
        fileMenu.add(openPGN);

        final JMenuItem hideHelp = new JMenuItem("Hide help bar");
        hideHelp.addActionListener(e -> textPanel.setVisible(false));
        fileMenu.add(hideHelp);

        final JMenuItem exitMenuItem = new JMenuItem("Exit");
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
            for (int i = 0; i < 16; i++){
                final TilePanel tilePanel = new TilePanel(this, i);
                this.boardTiles.add(tilePanel);
                add(tilePanel);
            }
            setPreferredSize(BOARD_PANEL_DIMENSION);
            
            //this.setBorder(new MatteBorder(1, 1, 1, 1, Color.black));
            this.setBackground(new Color(165, 42, 42));
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
            //setBorder(new MatteBorder(1, 1, 1, 1, Color.black));
            setBackground(Color.decode("#FFFACD"));
            setVisible(true);
            setOpaque(false);

            addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(final MouseEvent e) {

                    if(isLeftMouseButton(e)){
                        if(selectedPiece == null || board.tileIsOccupied(tileId)){return;}
                        board.addPiece(selectedPiece, tileId);
                        board.removePiece(selectedPiece);

                        try {
                            assignTilePieceIcon(board, tileId, selectedPiece);

                            sidePanel.reloadTiles();
                            //turnCounter++;
                            if(turnCounter%2==0){
                                turnLabel.setText("Player 2 chooses the opponent's piece");

                            } else {
                                turnLabel.setText("Player 1 chooses the opponent's piece");

                            }

                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                        System.out.println(board);
                        selectedPiece = null;
                        turnCounter++;
                    }
                }

                @Override
                public void mousePressed(final MouseEvent e) {}
                @Override
                public void mouseReleased(final MouseEvent e) {}
                @Override
                public void mouseEntered(final MouseEvent e) {}
                @Override
                public void mouseExited(final MouseEvent e) {}
            });

            validate();
        }
        
    
        private void assignTilePieceIcon(final Board board, final int tileId, Pieces piece) throws IOException {
            this.removeAll();
            if(piece == null){return;}
            if(board.tileIsOccupied(tileId)){
                final BufferedImage pieceImage = ImageIO.read(getClass().getResource("/images/"+piece.toString()+".png"));
                this.add(new JLabel(new ImageIcon(pieceImage)));

            }

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
            setBorder(new MatteBorder(40, 40, 40, 40, new Color(165, 42, 42)));
            setPreferredSize(SIDE_PANEL_DIMENSION);
            validate();
        }
        public void reloadTiles() throws IOException {
            for(int i = 0; i < 8; i++){
                whites.get(i).assignTilePieceIcon(board,i,true);
                whites.get(i).setBorder(new MatteBorder(1, 1, 1, 1, Color.lightGray));
                blacks.get(i).assignTilePieceIcon(board,i,false);
                blacks.get(i).setBorder(new MatteBorder(1, 1, 1, 1, Color.lightGray));
            }
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
            if(teamColor){color = new Color(120, 63, 4);
            }else{color = new Color(245, 245, 220);}
            setBackground(color);
            assignTilePieceIcon(board, tileId,teamColor);
            addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(final MouseEvent e) {

                    if(isRightMouseButton(e)){
                        if(selectedPiece == null){return;}
                        selectedPiece = null;
                        try {
                            sidePanel.reloadTiles();
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    }else if(isLeftMouseButton(e)){
                        Pieces piece;
                        if(selectedPiece != null){
                            if (teamColor) {
                                piece = board.getAvailableWhites()[tileId];
                            }else {
                                piece = board.getAvailableBlacks()[tileId];
                            }
                            if(selectedPiece == piece) {
                                selectedPiece = null;
                                setBorder(new MatteBorder(1, 1, 1, 1, Color.lightGray));
                            }
                            return;
                        }
                        try {
                            sidePanel.reloadTiles();
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                        setBorder(new MatteBorder(3, 3, 3, 3, new Color(206, 32, 41)));
                        if (teamColor) {
                            piece = board.getAvailableWhites()[tileId];
                        }else {
                            piece = board.getAvailableBlacks()[tileId];
                        }
                        if (piece != null){
                            selectedPiece = piece;
                            if(turnCounter%2==0){
                                turnLabel.setText("Player 2 places the selected piece");
                                if(selectedPiece.toString().charAt(0)!='B'){
                                    selectedPiece = null;
                                    turnLabel.setText("Wrong colour!");
                                }

                            } else {
                                turnLabel.setText("Player 1 places the selected piece");
                                if(selectedPiece.toString().charAt(0)!='W'){
                                    selectedPiece = null;
                                    turnLabel.setText("Wrong colour!");
                                }
                            }

                        }

                        System.out.println("selected piece: " + selectedPiece);

                    }
                }

                @Override
                public void mousePressed(final MouseEvent e) {}
                @Override
                public void mouseReleased(final MouseEvent e) {}
                @Override
                public void mouseEntered(final MouseEvent e) {}
                @Override
                public void mouseExited(final MouseEvent e) {}
            });
            setVisible(true);
            validate();
        }
        private void assignTilePieceIcon(final Board board, final int tileId, boolean teamColor) throws IOException {
            this.removeAll();
            Pieces piece;
            if (teamColor) {
                piece = board.getAvailableWhites()[tileId];
            }else {
                piece = board.getAvailableBlacks()[tileId];
            }
            if(piece == null){return;}
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
