package com.quarto.GUI;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.MouseInputAdapter;

public class DragListener extends MouseInputAdapter
{
    Point location;
    MouseEvent pressed;

    public void mousePressed(MouseEvent me)
    {
        pressed = me;
    }

    public void mouseDragged(MouseEvent me)
    {
        Component component = me.getComponent();
        location = component.getLocation(location);
        int x = location.x - pressed.getX() + me.getX();
        int y = location.y - pressed.getY() + me.getY();
        component.setLocation(x, y);
     }
}




  