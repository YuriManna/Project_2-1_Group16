package com.quarto.View.GUI;

import com.quarto.Model.*;
import com.quarto.View.GameInterface;
import com.quarto.ai.MiniMax;
import com.quarto.ai.PieceEvaluationFunction;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static javax.swing.SwingUtilities.isLeftMouseButton;
import static javax.swing.SwingUtilities.isRightMouseButton;

public class GUIGame implements GameInterface {
    private final JFrame gameFrame;
    private final BoardPanel boardPanel;
    private final SidePanel sidePanel;
    private final JLabel turnLabel;
    private final JPanel textPanel;
    private static Dimension GAME_FRAME_DIMENSION = new Dimension(1250,650);
    private static Dimension BOARD_PANEL_DIMENSION = new Dimension(600,605);
    private static Dimension TILE_PANEL_DIMENSION = new Dimension(20,20);
    private static Dimension SIDE_PANEL_DIMENSION = new Dimension(600,600);
    private static Dimension TURN_LABEL_DIMENSION = new Dimension(100,20);
    private boolean isAIGame = false;
    private GameBoard startBoard;

    public GUIGame(GameBoard board) throws IOException {
        startBoard = board;

        // initialize the frame
        this.gameFrame = new JFrame("Quarto");
        this.gameFrame.setSize(GAME_FRAME_DIMENSION);
        this.gameFrame.setLayout(new BorderLayout());

        // initialize the menu bar
        final JMenuBar tableMenuBar = new JMenuBar();
        populateMenuBar(tableMenuBar);
        this.gameFrame.setJMenuBar(tableMenuBar);

        // initialize panel to contain board (this is mainly here to make the gui look better, so that there is space from the borders of the frame)
        JPanel borderPanel = new JPanel();
        borderPanel.setLayout(new BorderLayout());
        borderPanel.setBorder(new MatteBorder(7, 7, 7, 7, new Color(165, 42, 42)));
        this.gameFrame.add(borderPanel, BorderLayout.CENTER);

        // initialize the panel for the game board
        this.boardPanel = new BoardPanel();
        borderPanel.add(this.boardPanel, BorderLayout.CENTER);
        this.boardPanel.setVisible(true);

        // initialize the panel for the available pieces on the side
        this.sidePanel = new SidePanel();
        this.gameFrame.add(this.sidePanel, BorderLayout.EAST);

        // initialize the help text panel on top
        this.textPanel = new JPanel();
        this.textPanel.setSize(TURN_LABEL_DIMENSION);
        this.textPanel.setBackground(new Color(227, 215, 183));
        this.turnLabel = new JLabel("White starts the game! Choose a white piece");
        this.turnLabel.setSize(TURN_LABEL_DIMENSION);
        this.textPanel.add(this.turnLabel);
        this.gameFrame.add(textPanel, BorderLayout.NORTH);

        this.gameFrame.setLocationRelativeTo(null);
        this.gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //this.gameFrame.setResizable(false);
        this.gameFrame.setVisible(true);
    }
//------------------------------------------------------------------------------------------------------------------
// PANELS DEFINITIONS
//------------------------------------------------------------------------------------------------------------------

    //panel for the board of the game
    private class BoardPanel extends JPanel{
        final List<TilePanel> boardTiles;
        public List<TilePanel> getBoardTiles() {return boardTiles;}
        Image im= ImageIO.read(getClass().getResource("/images/QuartoBoard.png"));
        BoardPanel() throws IOException {
            super(new GridLayout(4,4));
            this.boardTiles = new ArrayList<>();
            for (int i = 0; i < 16; i++){
                final GUIGame.TilePanel tilePanel = new GUIGame.TilePanel(this, i);
                this.boardTiles.add(tilePanel);
                add(tilePanel);
            }
            setPreferredSize(BOARD_PANEL_DIMENSION);

            this.setBackground(new Color(165, 42, 42));
            validate();
        }
        public void reloadTiles(GameBoard board) throws IOException {
            for(int i = 0; i < 16; i++){
                boardTiles.get(i).assignTilePieceIcon(board, i, board.getPieceById(i));
            }
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
            setBackground(Color.decode("#FFFACD"));
            setVisible(true);
            setOpaque(false);
            validate();
        }
        private void assignTilePieceIcon(final GameBoard board, final int tileId, Piece piece) throws IOException {
            this.removeAll();
            if (piece == null) {
                return;
            }
            if (board.getPieceById(tileId) != null) {
                final BufferedImage pieceImage = ImageIO.read(getClass().getResource("/images/" + piece.toString() + ".png"));
                this.add(new JLabel(new ImageIcon(pieceImage)));
            }
        }
    }

    //panel for the unplaced pieces
    private class SidePanel extends JPanel{
        final List<SideTilePanel> whites;
        final List<SideTilePanel> blacks;
        public List<SideTilePanel> getBlacks() {return blacks;}
        public List<SideTilePanel> getWhites() {return whites;}

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
        public void reloadTiles(GameBoard board) throws IOException {
            for(int i = 0; i < 8; i++){
                if(board.getWhitesArray()[i] == null){
                    whites.get(i).assignTilePieceIcon(board,i,true);
                }
                whites.get(i).setBorder(new MatteBorder(1, 1, 1, 1, Color.lightGray));
                if(board.getBlacksArray()[i] == null){
                    blacks.get(i).assignTilePieceIcon(board,i,false);
                }
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
            assignTilePieceIcon(startBoard, tileId,teamColor);

            setVisible(true);
            validate();
        }

        private void assignTilePieceIcon(final GameBoard board, final int tileId, boolean teamColor) throws IOException {
            this.removeAll();
            Piece piece;

            if (teamColor) {
                piece = board.getWhitesArray()[tileId];
            }else {
                piece = board.getBlacksArray()[tileId];
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
//------------------------------------------------------------------------------------------------------------------
// MOUSE LISTENERS
//------------------------------------------------------------------------------------------------------------------

    public class SideTileMouseListener implements MouseListener {
        private Piece piece;
        private SideTilePanel tile;
        public SideTileMouseListener(Piece piece, SideTilePanel tile) {
            this.piece = piece;
            this.tile = tile;
        }
        @Override
        public void mouseClicked(final MouseEvent e) {
            //if the player clicks on an empty tile do nothing
            if(piece == null) {
                return;
            }
            // if left clicks select the piece and pass it to the opponent

            if(isLeftMouseButton(e)) {
                tile.setBorder(new MatteBorder(3, 3, 3, 3, new Color(206, 32, 41)));
                selectedPiece = piece;
            }
        }
        @Override
        public void mousePressed(final MouseEvent e) {}
        @Override
        public void mouseReleased(final MouseEvent e) {}
        @Override
        public void mouseEntered(final MouseEvent e) {
            if(selectedPiece == null){
                tile.setBorder(new MatteBorder(3, 3, 3, 3, new Color(60, 32, 150)));
            }
        }
        @Override
        public void mouseExited(final MouseEvent e) {
            if(selectedPiece == null) {
                tile.setBorder(new MatteBorder(1, 1, 1, 1, Color.lightGray));
            }
        }
    }

    public class BoardTileMouseListener implements MouseListener {
        private TilePanel tile;
        private GameBoard board;
        private Piece piece;
        public BoardTileMouseListener(TilePanel tile, GameBoard board, Piece piece) {
            this.board = board;
            this.tile = tile;
            this.piece = piece;
        }
        @Override
        public void mouseClicked(MouseEvent e) {

            if(isLeftMouseButton(e)){
                //if the player clicks on an empty tile select it
                if(board.getPieceById(tile.tileId) == null){
                    selectedTile = tile;
                    try {
                        tile.assignTilePieceIcon(board, tile.tileId, piece);
                        sidePanel.reloadTiles(board);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        }
        @Override
        public void mousePressed(MouseEvent e) {}
        @Override
        public void mouseReleased(MouseEvent e) {}
        @Override
        public void mouseEntered(MouseEvent e) {}
        @Override
        public void mouseExited(MouseEvent e) {}
    }
//------------------------------------------------------------------------------------------------------------------
// GAME INTERFACE METHODS
//------------------------------------------------------------------------------------------------------------------

    private Piece selectedPiece;
    @Override
    public Piece choosePiece(Player opponent) {
        turnLabel.setText("Choose a piece for " + opponent.toString());
        List<SideTilePanel> tiles;
        if (opponent.getIsWhite()){
            tiles = sidePanel.getWhites();
        }else{
            tiles = sidePanel.getBlacks();
        }

        // add mouse listeners to the tiles
        for(SideTilePanel tile : tiles) {
            MouseListener mouseListener = new SideTileMouseListener(opponent.getFixedSizeAvailablePieces()[tile.tileId], tile);
            tile.addMouseListener(mouseListener);
        }

        // wait for the player to select a piece
        while (selectedPiece == null) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // remove mouse listeners from the tiles
        for(SideTilePanel tile : tiles) {
            for (MouseListener mouseListener : tile.getMouseListeners()) {
                tile.removeMouseListener(mouseListener);
            }
        }
        if(isAIGame){
            turnLabel.setText("The AI is thinking...");
        }else{
            turnLabel.setText("");
        }
        opponent.removeAvailablePiece(selectedPiece);
        // return the selected piece and reset the selected piece
        Piece returnPiece = selectedPiece;
        selectedPiece = null;
        return returnPiece;
    }

    private TilePanel selectedTile;
    @Override
    public Move makeMove(GameBoard board, Piece playablePiece) {
        turnLabel.setText("place the piece");
        // add mouse listeners to the tiles
        for(TilePanel tile : boardPanel.getBoardTiles()) {
            MouseListener mouseListener = new BoardTileMouseListener(tile, board, playablePiece);
            tile.addMouseListener(mouseListener);
        }
        // wait for the player to select a tile
        while (selectedTile == null) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        // remove mouse listeners from the tiles
        for(TilePanel tile : boardPanel.getBoardTiles()) {
            for (MouseListener mouseListener : tile.getMouseListeners()) {
                tile.removeMouseListener(mouseListener);
            }
        }
        turnLabel.setText("");
        // return the move and reset the selected tile
        Move userMove = new Move(playablePiece, selectedTile.tileId);
        selectedTile = null;
        return userMove;
    }

    @Override
    public void updateBoard(GameBoard newBoard) throws IOException {
        sidePanel.reloadTiles(newBoard);
        boardPanel.reloadTiles(newBoard);
    }

    @Override
    public void showWinningMessage(Player player) {
        turnLabel.setText("Game over! " + player.toString() + " wins!");
    }

    @Override
    public Piece AIChoosePiece(Player opponent, QuartoGame game) {
        //reconize that this is an AI game
        if(!isAIGame){
            isAIGame = true;
        }
        GameBoard board = game.getGameBoard();
        Piece chosenPiece;
        if(opponent.getAvailablePieces().length < 5){
            chosenPiece = AIChoosePieceMinMax(opponent, game);
        }else {
            PieceEvaluationFunction function = new PieceEvaluationFunction();
            chosenPiece = function.leastLikelyPiece(opponent.getAvailablePieces(), board);
        }
        Piece[] whites = board.getWhitesArray();
        //color the selected piece's tile
        for (int i = 0; i < whites.length; i++) {
            if(chosenPiece.equals(whites[i])){
                System.out.println("piece found");
                sidePanel.getWhites().get(i).setBorder(new MatteBorder(3, 3, 3, 3, new Color(206, 32, 41)));
                break;
            }
        }
        opponent.removeAvailablePiece(chosenPiece);
        return chosenPiece;
    }
    public Piece AIChoosePieceMinMax(Player opponent, QuartoGame game){
        MiniMax minimax = new MiniMax(game);
        Piece chosenPiece = null;
        GameBoard board = game.getGameBoard();
        int minScore = Integer.MAX_VALUE;
        int score;
        int alpha = Integer.MIN_VALUE;
        int beta = Integer.MAX_VALUE;
        for (Piece piece : opponent.getAvailablePieces()){
            for (int i = 0; i < 16; i++) {
                Move move = new Move(piece, i);
                if (board.isValidMove(move)) {
                    board.addPieceToBoard(move);
                    score = minimax.minimaxScore(game.getGameBoard(), piece, 5, true, alpha, beta);
                    board.removePieceFromBoard(move);

                    if (score < minScore) {
                        minScore = score;
                        chosenPiece = piece;
                    }
                    beta = Math.min(beta, score);
                }
            }
        }
        return chosenPiece;
    }

    //------------------------------------------------------------------------------------------------------------------
    // MENU BAR METHODS
    //------------------------------------------------------------------------------------------------------------------
    private void populateMenuBar(final JMenuBar tableMenuBar){
        tableMenuBar.add(createOptionMenu());
        tableMenuBar.add(createbackToMain());

    }
    // method to define the back to main button
    private JButton createbackToMain(){
        final JButton backToMain = new JButton("Back to main menu");
        backToMain.setBackground(Color.white);
        backToMain.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    GameMainPage mainmenu = new GameMainPage();
                    gameFrame.dispose();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        return backToMain;
    }

    // method to define the option button
    private JMenu createOptionMenu() {
        final JMenu optionsMenu = new JMenu("Options");

        final JMenuItem hideHelp = new JMenuItem("Hide help bar");
        hideHelp.addActionListener(e -> textPanel.setVisible(false));
        optionsMenu.add(hideHelp);
        final JMenuItem showBar = new JMenuItem("Show help bar");
        showBar.addActionListener(e -> textPanel.setVisible(true));
        optionsMenu.add(showBar);

        final JMenuItem exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.addActionListener(e -> System.exit(0));
        optionsMenu.add(exitMenuItem);

        return optionsMenu;
    }
}
