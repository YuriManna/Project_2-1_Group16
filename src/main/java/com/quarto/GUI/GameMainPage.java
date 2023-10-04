package com.quarto.GUI;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.ImageObserver;
import java.io.IOException;
import java.io.InputStream;

public class GameMainPage extends JFrame {
    private JButton button1, button2, button3, button4;

    public GameMainPage() throws IOException {
        setTitle("Quarto");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 600); // Set a larger size for better visibility
        setLocationRelativeTo(null);
        // Load an image to use as a background



        ImageIcon backgroundImage = new ImageIcon(ImageIO.read(getClass().getResource("/images/QuartoBoard.png")));//change the image for background!!!
        JLabel backgroundLabel = new JLabel(backgroundImage);
        backgroundLabel.setBounds(0, 0, backgroundImage.getIconWidth(), backgroundImage.getIconHeight());

        // Create buttons and set their preferred size
        button1 = new JButton("PvP");
        button2 = new JButton("PvCP");
        button3 = new JButton("Rules");
        button4 = new JButton("Quit");
        
        Dimension buttonSize = new Dimension(100, 50); // Adjust button size as needed
        button1.setPreferredSize(buttonSize);
        button2.setPreferredSize(buttonSize);
        button3.setPreferredSize(buttonSize);
        button4.setPreferredSize(buttonSize);


        // Create a panel to hold the buttons
        JPanel buttonPanel = new JPanel();

        button1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { 
                try {
                    GameTable gameTable = new GameTable();
                    setVisible(false);
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                
              } 
        });

       
        button2.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e){
        JOptionPane.showMessageDialog(backgroundLabel,"Don't ask too much");}
            
        });

        button3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Create a new frame to display the text
                JFrame textFrame = new JFrame("You better know this by heart");
                textFrame.setSize(400, 300);
                textFrame.setLocationRelativeTo(null);
                // Create a JTextArea to display the text
                JTextArea textArea = new JTextArea("add rules here later. I guess");
                textArea.setEditable(false);

                // Add the JTextArea to the frame
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

    public static void main(String[] args) throws IOException {
        GameMainPage p = new GameMainPage();    
    }
}
