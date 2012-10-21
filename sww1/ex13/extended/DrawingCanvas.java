package ex13.extended;

import java.awt.*;
import java.awt.event.*;
import javax.swing.JPanel;
import ex13.extended.pictures.*;
import ex13.extended.pictures.Rectangle;
import java.io.*;
import javax.swing.JFileChooser;

class DrawingCanvas extends JPanel implements MouseListener, MouseMotionListener {

    private int lastX, lastY, startX, startY;
    private PictureElement rubberElement;
    private Picture thePicture = new Picture();
    private int currentShape = 1;
    private Color currentColour = Color.BLACK;
    final JFileChooser fileChooser = new JFileChooser();
    private File f;

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
            PictureElement p = thePicture.getDrawingElement(i);
            g.setColor(p.getColour());
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
                rubberElement = new Line(startX, startY, lastX, lastY, currentColour);
                break;
            case 2:
                rubberElement = new Rectangle(startX, startY, lastX, lastY, currentColour);
                break;
            case 3:
                rubberElement = new Oval(startX, startY, lastX, lastY, currentColour);
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
        g.setColor(currentColour);
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
     * Draws a picture from a specified file containing a Picture element.
     * @param f A file with a Picture element
     */
    protected void drawFromFile(File f) {
        try {
            ObjectInputStream drawingFile =
                    new ObjectInputStream(
                    new FileInputStream(f.getName()));
            Picture newPicture =
                    (Picture) drawingFile.readObject();
            drawingFile.close();
            thePicture = newPicture;
            System.out.println("Load successful");
        } catch (Exception e) {
            System.out.println("Loading failed");
            e.printStackTrace();
        }
        repaint();
    }

    /**
     * Saves the current state of the canvas to a file.
     */
    protected void saveToFile() {
        int returnValue = fileChooser.showSaveDialog(this);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            f = fileChooser.getSelectedFile();
        }
        try {
            ObjectOutputStream saveFile =
                    new ObjectOutputStream(
                    new FileOutputStream(f.getName()));
            saveFile.writeObject(thePicture);
            saveFile.close();
            System.out.println("Save successful!");
        } catch (Exception e) {
            System.out.println("Saving failed");
            e.printStackTrace();
        }
    }


    /**
     * Allows the current shape to be changed.
     * @param i
     */
    protected void setCurrentShape(int i) {
        currentShape = i;
    }

    /**
     * Allows the current colour to be changed.  This will change the colour of the final
     * drawn object.
     * @param c A colour to change the current colour to.
     */
    protected void setCurrentColour(Color c) {
        currentColour = c;
        System.out.println("colour received");
    }
}
