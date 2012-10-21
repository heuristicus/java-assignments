/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package randomPatternDrawer;

import fyw.turtles.*;

public class RandomPatternDrawer extends Turtle {

    public static void main(String[] args) {
        InputFrame input = new InputFrame();
        Turtle test = new RandomPatternDrawer();
        new TurtleGUI(test);
        test.move(input.getDouble("Enter a length to move (make it big)"));
    }

    public RandomPatternDrawer() {
        super();
    }

    @Override
    public void move(double length) {
        double[] randomValues = randomValueGenerator();
        double test = randomValues[1];
        if (length < randomValues[3]) {
            System.out.println("checking base case");
            this.turn(randomValues[0]);
            super.move(length);
        } else {
            System.out.println("starting loop");
            //for (int i = 0; i < randomValues[2]; i++) {
                //System.out.println("loop " + i);
            System.out.println("turn and recurse");
                this.turn(randomValues[0]);
                move(length / test);
            //}

        }
    }

    /**
     *
     * @return Array of doubles which contain pseudo-random values to be used
     * to control the turtle's movement.
     * Array location 0 holds the value the turtle will turn.<p>
     * Array location 1 holds the value that the length will be divided by on the
     * recursive call.<p>
     * Array location 2 holds the number of times the move method will be called again.
     * Array location 3 holds the minimum move value.
     */
    public double[] randomValueGenerator() {

        double randomSeed = Math.random();

        if (randomSeed < 0.5) {
            randomSeed = randomSeed * -1;
        }

        double[] valuesArray = new double[4];

        valuesArray[0] = 360 / (randomSeed * 500);
        valuesArray[1] = randomSeed * 50;
        valuesArray[2] = (double) (int) randomSeed * 25;
        valuesArray[3] = randomSeed * (randomSeed * 50);

        return valuesArray;

    }
}
