package com.quarto.View.GUI;

import com.quarto.PlayMinimax;


import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class GameMainPage extends JFrame {
    private JButton button1, button2, button3, button4;
    private JFrame textFrame;
    private int size = 600;

    public GameMainPage() throws IOException {
        setTitle("Quarto");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(size, size); // Set a larger size for better visibility
        setLocationRelativeTo(null);
        // Load an image to use as a background

        Image backImage = (ImageIO.read(getClass().getResource("/images/QuartoMain.png")));
        Image scaledBackImage = backImage.getScaledInstance(size,size, Image.SCALE_SMOOTH);
        ImageIcon backgroundImage = new ImageIcon(scaledBackImage);
        JLabel backgroundLabel = new JLabel(backgroundImage);
        //backgroundLabel.setBounds(0, 0, backgroundImage.getIconWidth(), backgroundImage.getIconHeight());

        // Create buttons and set their preferred size
        button1 = coolButton("PvP");
        button2 = coolButton("PvCPU");
        button3 = coolButton("Rules");
        button4 = coolButton("Quit");
        
        Dimension buttonSize = new Dimension(120, 65); // Adjust button size as needed
        button1.setPreferredSize(buttonSize);
        button2.setPreferredSize(buttonSize);
        button3.setPreferredSize(buttonSize);
        button4.setPreferredSize(buttonSize);

        // Create a panel to hold the buttons
        JPanel buttonPanel = new JPanel();

        button1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { 
                //try {
                    // add class
                    setVisible(false);
                    dispose();
                //} catch (IOException e1) {
                //    // TODO Auto-generated catch block
                //    e1.printStackTrace();
                //}
                
              } 
        });

       
        button2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
//                try {
//                    GameLogic gameLogic = new GameLogic();
//                    GameTable gameTable = new GameTable(gameLogic);
//                    MinMax minMaxPlayer = new MinMax(gameLogic.getBoard());
//                    gameLogic.setComputerPlayer(minMaxPlayer);
//                    setVisible(false);
//                    dispose();
//                } catch (IOException e1) {
//                    // TODO Auto-generated catch block
//                    e1.printStackTrace();
//                }
                System.out.println("GUI for PvCPU still in development, you can try playing against Minimax model or baseline agent through the corresponding files." );
                System.out.println("Launching PvCPU with Minimax");
                PlayMinimax.main(null);
            }
        });

        button3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(textFrame != null){
                    textFrame.dispose();
                }
                textFrame = new JFrame("Game rules");
                textFrame.setSize(400, 415);
                textFrame.setLocationRelativeTo(null);
                textFrame.setResizable(false);

                JTextArea textArea = rulesTextArea();

                textFrame.add(textArea);
                textFrame.setVisible(true);
            }
        });

        button4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                System.exit(0);            }
        });



        buttonPanel.setLayout(new GridLayout(4, 1));
        buttonPanel.add(button1);
        buttonPanel.add(button2);
        buttonPanel.add(button3);
        buttonPanel.add(button4);
        buttonPanel.setOpaque(false);
        // Add the button panel to the right side of the main content pane
        backgroundLabel.add(buttonPanel);
        backgroundLabel.setLayout(new FlowLayout(FlowLayout.RIGHT, 20, 20)); // Adjust alignment and spacing
        getContentPane().add(backgroundLabel);

        // Display the frame
        setVisible(true);
    }

    private static JButton coolButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 20));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(189, 31, 55)); // Gradient start color
        button.setOpaque(true);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Add hover effect using MouseAdapter
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                button.setBackground(new Color(0, 122, 255)); // Gradient end color on hover
            }

            public void mouseExited(MouseEvent evt) {
                button.setBackground(new Color(189, 31, 55)); // Restore original color on exit
            }
        });

        return button;
    }
    // Create a customized JTextArea with cool styling
    private static JTextArea rulesTextArea() {
        JTextArea textArea = new JTextArea();
        textArea.setFont(new Font("Arial", Font.PLAIN, 13));
        textArea.setForeground(Color.BLACK);
        textArea.setBackground(Color.lightGray);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setEditable(false);
        textArea.setMargin(new Insets(10, 10, 10, 10));

        // Insert your game rules here
        String gameRules =
                "In Quarto, two players compete in a game played on a 4x4 grid with 16 unique pieces, each possessing four distinct attributes: big/small, dark/light, square/round, and hollow/solid.\n" +
                "\n" +
                "The objective of Quarto is to be the first player to create a row, column, or diagonal of four pieces on the board that share at least one common attribute.\n" +
                "\n" +
                "Players take turns placing one of their pieces on an empty space on the board and then select the next piece for their opponent to play. The opponent must place the chosen piece on their turn.\n" +
                "\n" +
                "The game ends immediately when a player successfully creates a row, column, or diagonal of four pieces with a common attribute. This player wins the game.\n" +
                "\n" +
                "If the entire board is filled, and no player has achieved the winning condition, the game results in a draw.\n" +
                "\n" +
                "Select pieces with LMB, deselect with RMB.\n"
                ;

        textArea.setText(gameRules);
        return textArea;
    }
}
