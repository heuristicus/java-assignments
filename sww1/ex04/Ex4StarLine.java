package ex04;

import fyw.turtles.*;

public class Ex4StarLine {
    
    /**
     * create an input frame
     * @param args strings from command line
     */
    public static void main(String[] args) {
        InputFrame theInputFrame = new InputFrame();
        
        /* Write your own code and comments within the area below
         *vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv
         */


        //This gets the integer input into the input frame and assigns it to number.
        int number = theInputFrame.getInt();

        /* This loop checks if the input number is less than zero.  If it is,
         * it outputs an error message and asks for another number.
         * If the number is in range it prints * 'number' times.
         */
        if (number < 0)
        {
            System.out.print("Please input a number greater than or equal to zero");
            number = theInputFrame.getInt();
        }
            else
            {
                for (int i = 0; i <= number; i++)
                {
                    System.out.print("*");
                }
            }

         /* ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
         * Write your own code and comments within the area above
         */
        
    }
    
}