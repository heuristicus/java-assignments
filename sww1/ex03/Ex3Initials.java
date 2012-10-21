package ex03;

import fyw.turtles.*;

public class Ex3Initials {
    
    /**
     * create turtle in a GUI, and an input frame
     * @param args strings from command line
     */
    public static void main(String[] args) {
        InputFrame theInputFrame = new InputFrame();
        
        /* Write your own code and comments within the area below
         *vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv
         */

         double height = theInputFrame.getDouble();

         /*These two while loops are used to check whether the value in height
         is in the range 20<height<600.  If it isn't, the user is prompted to
         input a value within this range.
         */

         while (height<20)
         {
             System.out.println("Height is " + height);
             System.out.println("Too small;");
             System.out.println("Please enter a value between 20 and 600");
             height = theInputFrame.getDouble();
         }

         while (height>600)
         {
             System.out.println("Height is " + height);
             System.out.println("Too big;");
             System.out.println("Please enter a value between 20 and 600");
             height = theInputFrame.getDouble();
         }

         //Create a new Turtle GUI onto which to draw the initials.

         Turtle theTurtle = new Turtle();
         new TurtleGUI(theTurtle);

         //This section of code draws the initials after a value within the
         //range has been input.

         //Draws the M.

         theTurtle.move(height);
         theTurtle.turn(150);
         theTurtle.move(height*0.6);
         theTurtle.turn(-120);
         theTurtle.move(height*0.6);
         theTurtle.turn(150);
         theTurtle.move(height);

         //Move the turtle into position to draw the S.

         theTurtle.turn(-90);
         theTurtle.penUp();
         theTurtle.move(height*0.4);
         theTurtle.penDown();

         //Turn the turtle so that the S is well aligned.

         theTurtle.turn(25);

         //Draws the bottom curve of the S.

         for (int i = 0; i < 13; i++)
         {
             theTurtle.move(height*0.07);
             theTurtle.turn(-15);
         }

         //Draws the top curve of the S.

         for (int i = 0; i < 13; i++)
         {
             theTurtle.move(height*0.07);
             theTurtle.turn(15);
         }

         
         /* ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
         * Write your own code and comments within the area above
         */
        
    }
    
}