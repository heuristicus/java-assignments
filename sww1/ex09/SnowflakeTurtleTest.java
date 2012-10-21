/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ex09;

import fyw.turtles.*;

public class SnowflakeTurtleTest {


    public static void main(String[] args) {
        Turtle theTurtle = new SnowflakeTurtle();
        new TurtleGUI(theTurtle);
        int sides = 3;
        for (int i = 0; i < sides; i++) {
            drawFractalShape(250, sides, theTurtle);
        }
        
    }

    /**
     * Draws a fractal shape of the specified dimensions.  This method assumes
     * that a TurtleGUI has already been created for the instance of Turtle that
     * is being called.
     * @param length Desired length of each side of the shape.
     * @param sides Number of sides the shape is to have.
     * @param t An instance of turtle with which to draw the shape.
     */
    public static void drawFractalShape(double length, int sides, Turtle t) {
        double turnAngle = 360/sides;
        t.move(length);
        t.turn(turnAngle);
    }

    /**
     * Same method as drawFractalShape, but does not require a Turtle to be passed,
     *  and also creates a new TurtleGUI.
     * @param length Desired length of the side of the shape.
     * @param sides Number of sides the shape is to have.
     */
    public static void drawFractalShape(double length, int sides) {
        Turtle t = new SnowflakeTurtle();
        new TurtleGUI(t);
        double turnAngle = 360/sides;
        t.move(length);
        t.turn(turnAngle);
    }

}
