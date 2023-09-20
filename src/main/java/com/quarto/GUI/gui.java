package com.quarto.GUI;

import java.awt.*;  
import java.awt.event.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;

class gui extends JFrame{

    JFrame frameObj;    
    JPanel pieces;
    JPanel board;
    JPanel full;
// constructor  
gui()  
{    
frameObj = new JFrame();    
full = new JPanel();
pieces = new JPanel ();
board=new JPanel();
DragListener drag = new DragListener();
// creating 9 buttons  

JButton btn1 = new JButton("1,1"); 
JButton btn2 = new JButton("1,2");    
JButton btn3 = new JButton("1,3");    
JButton btn4 = new JButton("1,4");    
JButton btn5 = new JButton("2,1");    
JButton btn6 = new JButton("2,2");    
JButton btn7 = new JButton("2,3");    
JButton btn8 = new JButton("2,4");    
JButton btn9 = new JButton("3,1");    
JButton btn10 = new JButton("3,2");    
JButton btn11 = new JButton("3,3");    
JButton btn12 = new JButton("3,4");    
JButton btn13 = new JButton("4,1");    
JButton btn14 = new JButton("4,2");    
JButton btn15 = new JButton("4,3");    
JButton btn16 = new JButton("4,4"); 

btn1.addMouseListener(drag);
btn1.addMouseMotionListener(drag);

btn2.addMouseListener(drag);
btn2.addMouseMotionListener(drag);

pieces.add(btn1); pieces.add(btn2); pieces.add(btn3);  
pieces.add(btn4); pieces.add(btn5); pieces.add(btn6); pieces.add(btn7); pieces.add(btn8);
pieces.add(btn9); pieces.add(btn10);pieces.add(btn11);pieces.add(btn12);pieces.add(btn13);pieces.add(btn14);pieces.add(btn15);pieces.add(btn16);
pieces.setLayout(new GridLayout(8, 2));    
full.setLayout(new GridLayout());
full.add(board);
full.add(pieces);
frameObj.add(full);
frameObj.setSize(1000, 1000);    
frameObj.setVisible(true);    
}  



public static void main(String argvs[])   
{    
new gui();    
}    
}    


