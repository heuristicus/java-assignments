/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package paper2006.q6;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JPanel;

/**
 *
 * @author Michal
 */
public class myPanel extends JPanel implements MouseListener{

    public myPanel(){
        JPanel panel = new JPanel();
        addMouseListener(this);
    }

    public void mouseClicked(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        System.out.println(x  + ", " + y);
        Graphics g = getGraphics();
        g.setColor(Color.red);
        g.drawRect(x-10, y-10, 20, 20);
    }

    public void mousePressed(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

}
