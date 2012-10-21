/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package test;

import fyw.turtles.*;

public class ShapeDrawer extends Turtle {


    final double MIN_MOVE_LENGTH;
    int recursions;
    double length;
    int sides;

    public ShapeDrawer() {
        InputFrame input = new InputFrame();
        length = input.getDouble("How far do you want to move? (bigger numbers are better");
        sides = input.getInt("How many sides do you want your shape to have?");
        MIN_MOVE_LENGTH = input.getDouble("Below what length do you want to stop recursion?");
        recursions = input.getInt("How many recursions do you want in the move method?");
    }
     

    /**
     * Draws a fractal line of input length.
     * @param length Desired length of the line.
     */
    @Override
    public void move(double length) {
        if (length < MIN_MOVE_LENGTH) {
            super.move(length);
        } else {
            for (int i = 0; i < recursions; i++) {
                move(length/3);
                double ran = Math.random();
                if (ran < 0.5) {
                    turn(Math.random()*360);
                } else {
                    turn(Math.random()*-360);
                }
                
            }
        }
    }

    public void drawShape() {
        Turtle t = new ShapeDrawer();
        new TurtleGUI(t);
        double turnAngle = 360/sides;
        t.move(length);
        t.turn(turnAngle);
    }

    public double getLength() {
        return this.length;
    }

}