/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package paper2005.q6;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;

/**
 *
 * @author Michal
 */
public class BlobCanvas extends JPanel implements ActionListener {

    int move = 0;
    Point location = new Point(0, 0);
    Point newLocation = new Point(0, 0);

    public BlobCanvas() {
        JPanel panel = new JPanel();
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(Color.white);
        g.fillOval(location.x, location.y, 50, 50);
        g.setColor(Color.black);
        g.fillOval(newLocation.x, newLocation.y, 50, 50);
        location = (Point)newLocation.clone();
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("left")) {
            newLocation.x -= 10;
            repaint();
            System.out.println("left");
        } else if (e.getActionCommand().equals("right")) {
            newLocation.x += 10;
            repaint();
            System.out.println("right");
        } else if (e.getActionCommand().equals("up")) {
            newLocation.y -= 10;
            repaint();
            System.out.println("up");
        } else if (e.getActionCommand().equals("down")) {
            newLocation.y += 10;
            repaint();
            System.out.println("down");
        }
    }
}
