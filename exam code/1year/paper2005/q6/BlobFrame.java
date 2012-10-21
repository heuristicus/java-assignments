/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package paper2005.q6;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Michal
 */
public class BlobFrame extends JFrame {

    public static void main(String[] args) {
        new BlobFrame();
    }

    public BlobFrame() {

        JFrame mainFrame = new JFrame("Blob");
        BlobCanvas canvas = new BlobCanvas();
        JPanel panel = new JPanel();
        JButton left = new JButton("left");
        left.setActionCommand("left");
        JButton right = new JButton("right");
        right.setActionCommand("right");
        JButton up = new JButton("up");
        up.setActionCommand("up");
        JButton down = new JButton("down");
        down.setActionCommand("down");
        left.addActionListener(canvas);
        right.addActionListener(canvas);
        up.addActionListener(canvas);
        down.addActionListener(canvas);
        panel.add(left);
        panel.add(right);
        panel.add(up);
        panel.add(down);
        mainFrame.add("Center", canvas);
        mainFrame.add("South", panel);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(800, 600);
        mainFrame.setVisible(true);
    }
}
