package ex13.basic;

import java.awt.*;
import java.awt.event.*;

import javax.swing.JPanel;
import ex13.basic.pictures.Line;
import ex13.basic.pictures.Oval;
import ex13.basic.pictures.Picture;
import ex13.basic.pictures.PictureElement;
import ex13.basic.pictures.Rectangle;

class DrawingCanvas extends JPanel implements MouseListener, MouseMotionListener {

    private int lastX, lastY, startX, startY;
    private PictureElement rubberElement;
    private Picture thePicture = new Picture();
    private int currentShape = 1;

    /**
     * Constructor which adds mouseListeners and motion listeners to the Panel object.
     * Allows mouse movements on the canvas to be detected.
     */
    public DrawingCanvas() {
        addMouseListener(this);
        addMouseMotionListener(this);
    }

    /**
     * Repaints the shapes that are stored in thePicture if the window is minimised or moved.
     * @param g
     */
    @Override
    protected void paintComponent(Graphics g) {

        for (int i = 0; i < thePicture.getNumberOfDrawingElements(); i++) {
            System.out.println(thePicture.getNumberOfDrawingElements());
            PictureElement p = thePicture.getDrawingElement(i);
            if (p.getType() == 1) {
                g.drawLine(p.getX1(), p.getY1(), p.getX2(), p.getY2());
            } else if (p.getType() == 2) {
                if (p.getX1() > p.getX2() && p.getY1() < p.getY2()) { // Bottom left
                    g.drawRect(p.getX2(), p.getY1(), p.getX1() - p.getX2(), p.getY2() - p.getY1());
                } else if (p.getX1() > p.getX2() && p.getY1() > p.getY2()) { // Top left
                    g.drawRect(p.getX2(), p.getY2(), p.getX1() - p.getX2(), p.getY1() - p.getY2());
                } else if (p.getX1() < p.getX2() && p.getY1() > p.getY2()) { // Top right
                    g.drawRect(p.getX1(), p.getY2(), p.getX2() - p.getX1(), p.getY1() - p.getY2());
                } else { // Bottom right
                    g.drawRect(p.getX1(), p.getY1(), p.getX2() - p.getX1(), p.getY2() - p.getY1());
                }
            } else if (p.getType() == 3) {
                if (p.getX1() > p.getX2() && p.getY1() < p.getY2()) { // Bottom left
                    g.drawOval(p.getX2(), p.getY1(), p.getX1() - p.getX2(), p.getY2() - p.getY1());
                } else if (p.getX1() > p.getX2() && p.getY1() > p.getY2()) { // Top left
                    g.drawOval(p.getX2(), p.getY2(), p.getX1() - p.getX2(), p.getY1() - p.getY2());
                } else if (p.getX1() < p.getX2() && p.getY1() > p.getY2()) { // Top right
                    g.drawOval(p.getX1(), p.getY2(), p.getX2() - p.getX1(), p.getY1() - p.getY2());
                } else { // Bottom right
                    g.drawOval(p.getX1(), p.getY1(), p.getX2() - p.getX1(), p.getY2() - p.getY1());
                }
            }
        }
    }

    /**
     * Starts drawing a rubberbanded shape in red, depending on the value of the
     * currentShape variable.
     * @param e
     */
    public void mousePressed(MouseEvent e) {
        startX = e.getX();
        startY = e.getY();
        lastX = startX;
        lastY = startY;
        switch (currentShape) {
            case 1:
                rubberElement = new Line(startX, startY, lastX, lastY) {
                };
                break;
            case 2:
                rubberElement = new Rectangle(startX, startY, lastX, lastY) {
                };
                break;
            case 3:
                rubberElement = new Oval(startX, startY, lastX, lastY) {
                };
                break;
        }

        Graphics g = getGraphics();
        g.setColor(Color.RED);
        g.setXORMode(Color.white);
        rubberElement.draw(g); //draw red shape
    }

    /**
     * Draws a red rubberbanded shape which follows the mouse cursor's position. The shape
     * is redrawn every time the mouse position changes.
     * @param e
     */
    public void mouseDragged(MouseEvent e) {
        int newX, newY;
        newX = e.getX();
        newY = e.getY();
        Graphics g = getGraphics();
        g.setColor(Color.RED);
        g.setXORMode(Color.white);
        rubberElement.draw(g);  //erase red line

        rubberElement.setPosition(startX, startY, newX, newY);  //draw red line in new position
        rubberElement.draw(g);
        lastX = newX;
        lastY = newY;
    }

    /**
     * Draws the properly coloured shape, after erasing the red shape.  This happens when
     * the mouse button is released.  Also adds the rubberElement object to an array so that
     * it can be redrawn if the window is moved or resized.
     * @param e
     */
    public void mouseReleased(MouseEvent e) {
        Graphics g = getGraphics();
        //erase red line:
        g.setColor(Color.RED);
        g.setXORMode(Color.white);
        rubberElement.draw(g);
        //draw line properly in black:
        g.setColor(Color.BLACK);
        g.setPaintMode();
        rubberElement.draw(g);
        thePicture.add(rubberElement);

    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mouseMoved(MouseEvent e) {
    }

    /**
     * Allows the current shape to be changed.
     * @param i
     */
    protected void setCurrentShape(int i) {
        currentShape = i;
    }
}
