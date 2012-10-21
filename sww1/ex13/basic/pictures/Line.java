package ex13.basic.pictures;

import java.awt.*;

public class Line extends PictureElement {

    public Line(int x1, int y1, int x2, int y2) {
        super(x1, y1, x2, y2, LINE);
    }

    /**
     * Draws a line using the co-ordinates used in the constructor.
     * @param g
     */
    public void draw(Graphics g) {
        g.drawLine(getX1(), getY1(), getX2(), getY2());
    }
}
