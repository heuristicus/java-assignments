package ex13.extended.pictures;

import java.awt.*;
import java.io.Serializable;

public abstract class PictureElement implements Serializable{

    public static final int LINE = 1, RECTANGLE = 2, OVAL = 3;
    private int x1, y1, x2, y2;
    private int type;   // type of element - LINE, RECTANGLE or OVAL
    private Color colour = Color.BLACK;

    /**
     * Sets the values of variables in this element (i.e. the sub class that called this) to the values
     * passed to it.
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @param type
     */
    public PictureElement(int x1, int y1, int x2, int y2, int type, Color c) {
        setPosition(x1, y1, x2, y2);
        this.type = type;
        colour = c;
    }

    /**
     * Sets the values of the position variables.
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     */
    public void setPosition(int x1, int y1, int x2, int y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }


    public Color getColour() {
        return colour;
    }

    /**
     * Returns the type of the PictureElement object.
     * @return
     */
    public int getType() {
        return type;
    }

    /**
     * Returns x1.
     * @return
     */
    public int getX1() {
        return x1;
    }

    /**
     * Returns y1;
     * @return
     */
    public int getY1() {
        return y1;
    }

    /**
     * Returns x2.
     * @return
     */
    public int getX2() {
        return x2;
    }

    /**
     * Returns y2.
     * @return
     */
    public int getY2() {
        return y2;
    }

    /**
     * Outputs the position variables as a string.
     * @return
     */
    @Override
    public String toString() {
        return (getType() + " at" + "(" + getX1() + ", " + getY1() + ")" + "  " + "(" + getX2() + ", " + getY2() + ")");
    }

    /**
     * Abstract method to allow different shapes to be drawn differently depending on the
     * values initialised here.
     * @param g
     */
    public abstract void draw(Graphics g);
}
