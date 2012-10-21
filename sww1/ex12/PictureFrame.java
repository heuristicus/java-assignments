/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ex12;

import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.event.*;

/**
 *
 * @author Michal
 */
public class PictureFrame extends Frame implements MouseListener{

    private Picture thePicture;
    private String fileName;
    int[] line = new int[2];
    int rectX, rectY;
    int clicks;

    public PictureFrame(Picture p) {
        thePicture = p;
        setTitle(p.getFileName());
        setSize(840, 480);

        addMouseListener(this);
        addWindowListener(
                new WindowAdapter() {

                    @Override
                    public void windowClosing(WindowEvent e) {
                        System.exit(0);
                    }
                });

        setVisible(true);
    }

    public PictureFrame(Picture p, String inFile) {
        thePicture = p;
        fileName = inFile;
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(new Color((int) (Math.random() * 255),
                (int) (Math.random() * 255),
                (int) (Math.random() * 255)));
        /*
         * invariant: 0<i<thePicture.getNumberOfLines()
         */
        for (int i = 0; i < thePicture.getNumberOfLines(); i++) {
            Line currentLine = thePicture.getLine(i);
            g.drawLine(currentLine.getx1(), currentLine.gety1(), currentLine.getx2(), currentLine.gety2());
        }
    }

    /**
     * Draws a line between two clicked points.
     * @param e
     */
    public void mouseClicked(MouseEvent e) {
        Graphics g = getGraphics();

        clicks += 1;
        switch (clicks) {
            case 1:
                line[0] = e.getX();
                line[1] = e.getY();
                break;
            case 2:
                g.drawLine(line[0], line[1], e.getX(), e.getY());
                clicks = 0;
                break;
        }

    }

    /**
     * Adds the location of the mouseclick to the variables when the mouse button is pressed down.
     * @param e
     */
    public void mousePressed(MouseEvent e) {
        rectX = e.getX();
        rectY = e.getY();
    }

    /**
     * When the mouse button is released, a rectangle is drawn from the point at which the button was pressed,
     * to where the button was released.
     * @param e
     */
    public void mouseReleased(MouseEvent e) {
        Graphics g = getGraphics();
        g.drawRect(rectX, rectY, e.getX() - rectX, e.getY() - rectY);
    }

    /**
     * Repaints the picture when the mouse enters the window.
     * @param e
     */
    public void mouseEntered(MouseEvent e) {
        repaint();

    }

    /**
     * Repaints the the picture when the mouse exits the window.
     * @param e
     */
    public void mouseExited(MouseEvent e) {
        repaint();
    }

}
