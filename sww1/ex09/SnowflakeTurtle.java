/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ex09;

import fyw.turtles.*;

public class SnowflakeTurtle extends Turtle {

    private final static double MIN_MOVE_LENGTH = 4;

    /*
    public static void main(String[] args) {
        Turtle theTurtle = new SnowflakeTurtle();
        new TurtleGUI(theTurtle);
        theTurtle.move(300);
    }
     */

    /**
     * Draws a fractal line of input length.
     * @param length Desired length of the line.
     */
    @Override
    public void move(double length) {
        if (length < MIN_MOVE_LENGTH) {
            super.move(length);
        } else {
            move(length/3);
            turn(-60);
            move(length/3);
            turn(120);
            move(length/3);
            turn(-60);
            move(length/3);
        }
    }

}
