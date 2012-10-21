/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ex07;

import fyw.turtles.*;
import javax.swing.Timer;

public class TestTimerTurtle {


    /**
     * Simply used to test the functionality of the RandomTurtleController class.
     * Effectively, it creates a new turtle and GUI, creates a new instance of
     * RandomTurtleController, and then starts a timer to cause the turtle aTurtle
     * to move in a random fashion on the GUI (due to the actionPerformed class in
     * RandomTurtleController.
     * 
     * @param args
     */
    public static void main(String[] args) {
        Turtle aTurtle = new Turtle();
        new TurtleGUI(aTurtle);
        RandomTurtleController c = new RandomTurtleController(aTurtle);
        Timer theTimer = new Timer(300, c);
        theTimer.start();
    }
}
