/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ex12;

/**
 *
 * @author Michal
 */
public class Line {

    private int x1, y1, x2, y2;

//    /**
//     * A basic test method for this class.  Creates a new line and assigns it
//     * some values, and then tests all the methods in the class, outputting the
//     * result to the console.
//     *
//     * @param args
//     */
//    public static void main(String[] args) {
//        Line lineTest = new Line(1, 2, 3, 4);
//        System.out.println(lineTest.getx1());
//        System.out.println(lineTest.gety1());
//        System.out.println(lineTest.getx2());
//        System.out.println(lineTest.gety2());
//        System.out.println(lineTest.toString());
//    }

    /**
     * Constructor for this class.  Takes an input of four different integer
     * values which are intended to correspond to coordinates of two points.
     *
     * @param x1 x coordinate of the first point.
     * @param y1 y coordinate of the first point.
     * @param x2 x coordinate of the second point.
     * @param y2 y coordinate of the second point.
     */
    public Line(int x1, int y1, int x2, int y2) {
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
    }


    /*
     * Below are getter methods for this class.  They are self explanatory.
     */
    /**
     *
     * @return Current value of x1.
     */
    public int getx1() {
        return x1;
    }

    /**
     *
     * @return Current value of x2.
     */
    public int getx2() {
        return x2;
    }

    /**
     *
     * @return Current value of y1.
     */
    public int gety1() {
        return y1;
    }

    /**
     *
     * @return Current value of y2.
     */
    public int gety2() {
        return y2;
    }

    /**
     *
     * @return A string of characters corresponding to the coordinate values
     * that are in the object's variables.  Formatted so that the coordinates
     * of the initial point come first, and the point where the line terminates
     * is shown after a tab character.
     */
    @Override
    public String toString() {
        String out = "(" +  x1 + ", " + y1 + ")"+ "  " + "(" + x2 + ", " + y2 + ")";
        return out;
    }
}