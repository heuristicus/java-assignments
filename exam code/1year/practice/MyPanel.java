/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package practice;

import java.awt.event.*;
import java.awt.*;
import javax.swing.*;

public class MyPanel extends JPanel implements MouseListener, ActionListener, MouseMotionListener {

    Color currentColour = Color.black;

    public MyPanel() {

        JPanel panel = new JPanel();
        addMouseListener(this);
        addMouseMotionListener(this);

    }

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("red")) {
            currentColour = Color.red;
        } else if (e.getActionCommand().equals("black")) {
            currentColour = Color.black;
        } else if (e.getActionCommand().equals("green")) {
            currentColour = Color.green;
        }
    }

    public void mouseClicked(MouseEvent e) {
        Graphics g = getGraphics();
        g.setColor(currentColour);
        g.fillRect(e.getX() - 10, e.getY() - 10, 20, 20);
    }

    public void mousePressed(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mouseDragged(MouseEvent e) {
        Graphics g = getGraphics();
        g.setColor(currentColour);
        g.fillRect(e.getX() - 10, e.getY() - 10, 20, 20);
    }

    public void mouseMoved(MouseEvent e) {
        Graphics g = getGraphics();
        g.setColor(currentColour);
        g.fillRect(e.getX() - 10, e.getY() - 10, 20, 20);
    }
}
