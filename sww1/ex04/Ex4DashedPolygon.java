package ex04;

import fyw.turtles.*;

public class Ex4DashedPolygon {

    /**
     * create turtle in a GUI, and an input frame
     * @param args strings from command line
     */
    public static void main(String[] args) {
        
        InputFrame theInputFrame = new InputFrame();

        /* Write your own code and comments within the area below
         *vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv
         */
        
        //i is used as a count to keep the while loop going.  j is used in
        //a loop later on to draw the dashed lines.

        int i = 1;
        int j = 10;

        /*This while loop repeats indefinitely, unless a value less than 3 is
         * input into the input frame.
         */

        while (i > 0)
        {
            //The value from the inputframe is assigned to the integer variable sides.

            int sides = theInputFrame.getInt();

            /*This double variable is the same as sides.  However, its value does not
             * change.  It is used in the while loop to divide 360, as a double
             * value is needed to do this accurately.
             */

             double staticsides = sides;

             //The variable turnangle stores the angle to turn the turtle.

             double turnangle = 360.0/staticsides;

             /*This loop checks whether the value of sides is less than 3.  If yes,
              * it outputs an error message and exits.  If the value is in range,
              * a new turtle is created and a message telling you how many sides the
              * polygon will have is printed.
              */

              if (sides < 3)
                {
                    System.out.println("Please enter a value greater than 2.");
                    System.exit(1);
                }
                    else
                        {
                            Turtle theTurtle = new Turtle();
                            new TurtleGUI(theTurtle);
                            System.out.println("The polygon drawn will have  " + sides + " sides.");

                            /*This while loop is used to draw the polygon.  1 is subtracted from
                             * sides every loop, and once the value of sides hits zero, the loop stops.
                             */

                             while (sides != 0)
                                {

                                    /* This loop draws the side of a polygon as
                                     * a series of dashes.
                                     */

                                    while (j != 0)
                                    {
                                        theTurtle.move(5);
                                        theTurtle.penUp();
                                        theTurtle.move(5);
                                        theTurtle.penDown();
                                        j = j - 1;
                                    }
                                    theTurtle.turn(turnangle);
                                    sides = sides - 1;
                                    j = 10;
                                }

                        }

        }

                   

        /* ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
         * Write your own code and comments within the area above
         */

    }

}