/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ex13.basic.pictures;

import java.awt.Graphics;

/**
 *
 * @author Michal
 */
public class Rectangle extends PictureElement {

    public Rectangle(int x1, int y1, int x2, int y2) {

        super(x1, y1, x2, y2, RECTANGLE);
    }

    /**
     * Draws an oval using the co-ordinates used in the constructor.
     * This method allows the drawing of the shape in any of the four
     * quadrants possible to enter, i.e. top left, top right, bottom left and bottom right.
     * @param g
     */
    @Override
    public void draw(Graphics g) {

        if (getX1() > getX2() && getY1() < getY2()) { // Bottom left
            g.drawRect(getX2(), getY1(), getX1() - getX2(), getY2() - getY1());
        } else if (getX1() > getX2() && getY1() > getY2()) { // Top left
            g.drawRect(getX2(), getY2(), getX1() - getX2(), getY1() - getY2());
        } else if (getX1() < getX2() && getY1() > getY2()) { // Top right
            g.drawRect(getX1(), getY2(), getX2() - getX1(), getY1() - getY2());
        } else if (getX1() < getX2() && getY1() < getY2()) { // Bottom right
            g.drawRect(getX1(), getY1(), getX2() - getX1(), getY2() - getY1());
        } else {
        }
    }
}
