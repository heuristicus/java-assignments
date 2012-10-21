/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package randomPatternDrawer;

import fyw.turtles.*;
import java.awt.*;
import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.ImageIcon;
import java.util.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class RandomPatternDrawerUtils {


    public static void main(String[] args) {
        
    }  

    /**
     *
     * @param t Instance of turtle which does the drawing.
     * @param s seed value which is being used.
     */
    void randomMove(Turtle t, double s) {
        double[] randomValues = randomValueGenerator(s);

        for (int i = 0; i < (int)randomValues[2]; i++) {
            RandomPatternDrawer.move(randomValues[1]);
        }

    }

    public void randomMove(Turtle t) {
        double[] randomValues = randomValueGenerator();

    }


    /**
     *
     * @param s Seed value from user input.
     * @return Array of doubles which contain pseudo-random values to be used
     * to control the turtle's movement. <p>
     * Array location 0 holds the value the turtle will turn.<p>
     * Array location 1 holds the value the turtle will move.<p>
     * Array location 2 holds the number of times the move method will be called again.<p>
     * Array location 3 holds the value of the minimum move length (base case of
     * recursion).
     */
    public double[] randomValueGenerator(double s) {

        double randomSeed = Math.random();
        double[] valuesArray = new double[4];

        valuesArray[4] = randomSeed * (s / randomSeed * 50);

        if (randomSeed < 0.5) {
            randomSeed = randomSeed * -1;
        }

        

        valuesArray[0] = 360/(randomSeed * s);
        valuesArray[1] = randomSeed * (s * 100);
        valuesArray[2] = (double)(int) randomSeed * (s % (Math.pow(randomSeed + 1,
                Math.pow(randomSeed + 1, randomSeed * (s % randomSeed * 20)))));


        return valuesArray;

    }

    /**
     *
     * @return Array of doubles which contain pseudo-random values to be used
     * to control the turtle's movement.
     * Array location 0 holds the value the turtle will turn.<p>
     * Array location 1 holds the value the turtle will move.<p>
     * Array location 2 holds the number of times the move method will be called again.
     */
    public double[] randomValueGenerator() {

        double randomSeed = Math.random();

        if (randomSeed < 0.5) {
            randomSeed = randomSeed * -1;
        }

        double[] valuesArray = new double[3];

        valuesArray[0] = 360/(randomSeed * 500);
        valuesArray[1] = randomSeed * 50000;
        valuesArray[2] = (double)(int) randomSeed * 25;
        
        return valuesArray;

    }

    Color getRandomColor() {
        Random numberGenerator = new Random();
        return new Color(numberGenerator.nextInt(256), numberGenerator.nextInt(256),
                numberGenerator.nextInt(256));
   }

}
