package ex04;

import fyw.turtles.*;

public class Ex4StarDiagonal {
    
    /**
     * create an input frame
     * @param args strings from command line
     */
    public static void main(String[] args) {
        InputFrame theInputFrame = new InputFrame();
        
        /* Write your own code and comments within the area below
         *vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv
         */

        //gets an integer value from inputframe and assigns it to the variable
        //stars.

        int stars = theInputFrame.getInt();

        //this holds the value of stars and is explained later.

        int starminus = stars;

        //this initial if statement checks whether the number is greater than zero.
        //if the number isn't in range, it asks for a number in the range.

        if (stars < 0)
        {
            System.out.print("Please input a number greater than or equal to zero");
            stars = theInputFrame.getInt();
        }
            else
            {

                //This initial for loop prints the stars, and also subtracts 1 from
                //starminus.

                for (int i = 0; i <= stars; i++)
                {

                    /*Here, starminus is used to assist in printing the spaces.
                    each time the initial loop runs, the value of starminus
                    is reduced by 1.
                     */

                    starminus = starminus - 1;

                    /*This is the secondary for loop.  it takes a value of j
                     * as starminus, and then loops, printing a space and
                     * subtracting 1 from the value of j each time
                     */

                    for (int j = starminus; j != 0; j--)
                    {
                        System.out.print(" ");
                    }

                    //This prints a star and then moves onto the next line.
                    //This allows the next lot of spaces and a star to be printed

                    System.out.println("*");
                }
            }


        /* ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
         * Write your own code and comments within the area above
         */
        
    }
    
}