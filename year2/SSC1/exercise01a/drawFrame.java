/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package exercise01a;

import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;

/**
 *
 * @author Michal
 */
public class drawFrame extends JFrame {

    JFrame frame;

    public static void main(String[] args) {
        drawFrame frame = new drawFrame();
    }

    /**
     * General constructor.  Creates a new JFrame object and calls methods to set up the frame for use.
     */
    public drawFrame() {
        frame = new JFrame();
        setupFrame();
    }

    /**
     * Sets up the frame for use.
     */
    private void setupFrame() {
        frame.setSize(400, 400);
        Toolkit t = Toolkit.getDefaultToolkit();
        Dimension scrSize = t.getScreenSize();
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setLocation(scrSize.width / 3, scrSize.height / 3);
        frame.setTitle("Awesome Drawing Tool");
        addComponents();
        frame.setVisible(true);
    }

    /**
     * Adds drawing components to the frame.
     */
    private void addComponents() {
        frame.add(new drawPanel());
    }
}
