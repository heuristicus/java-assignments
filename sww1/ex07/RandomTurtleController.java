/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ex07;

import fyw.turtles.*;
import java.awt.event.ActionEvent;

public class RandomTurtleController implements java.awt.event.ActionListener {

    private Turtle t;

    /**
     * Constructor for this class
     * @param aTurtle Requires a turtle to be passed to the class.
     */
    public RandomTurtleController(Turtle aTurtle) {
        t = aTurtle;
    }

    /**
     * This causes the turtle assigned to the instance of this class to turn
     * a certain number of degrees, between -90 and 90, and then move a random
     * distance between 10 and 25 units.
     *
     * @param e Entering a parameter here is not entirely necessary, but
     * can be done if you want to.
     */
    public void actionPerformed(ActionEvent e) {
        double determinant = java.lang.Math.random();
        double turnRandom = 0;
        if (determinant > 0.5) {
            turnRandom = java.lang.Math.random() * 90;
        } else {
            turnRandom = (java.lang.Math.random() -1) * 90;
        }
        double moveRandom = 10 + (java.lang.Math.random() * 25);
        t.turn(turnRandom);
        t.move(moveRandom);
    }
    
}
