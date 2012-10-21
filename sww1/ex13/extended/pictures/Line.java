package ex13.extended.pictures;

import java.awt.*;
import java.awt.Color;
import java.io.Serializable;

public class Line extends PictureElement implements Serializable{

    public Line(int x1, int y1, int x2, int y2, Color c) {
        super(x1, y1, x2, y2, LINE, c);
    }

    /**
     * Draws a line using the co-ordinates used in the constructor.
     * @param g
     */
    public void draw(Graphics g) {
        g.drawLine(getX1(), getY1(), getX2(), getY2());
    }
}
