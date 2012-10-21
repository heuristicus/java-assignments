package ex04;

import fyw.turtles.*;

public class Ex4Reports {
    
    /**
     * create an input frame
     * @param args strings from command line
     */
    public static void main(String[] args) {
        InputFrame theInputFrame = new InputFrame();
        
        /* Write your own code and comments within the area below
         *vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv
         */

        //This defines the array marks as an array of 9 double values.
        double [] marks = new double [9];
        
        /*These variables are all used at the end of the program to output
         *the calculated values.
         */
        
        double top = 0;
        double bottom = 100;
        int pass = 0;
        int fail = 0;

        /* This loop takes the input from the input frame and check whether it
         * is greater than 0 but less than 100.  If not, the user is prompted
         * to input a different number.  If the number is in range, it is
         * allocated to the array location of the current value of i.  This
         * continues until the value of i reaches the value of the length of the
         * array, minus 1 (i.e. length of array = 9, loop ends when i = 8).
         */
        for (int i = 0; i < marks.length; i++)
        {
            double inputnumber = theInputFrame.getDouble();

            while (inputnumber < 0.0 || inputnumber > 100.0)
            {
                System.out.println("The value you have entered is invalid.  Only numbers between 0 and 100 are accepted.  Please enter a value in this range.");
                inputnumber  = theInputFrame.getDouble();
            }

            marks [i] = inputnumber;
        }

        //this variable is defined here so that the loop invariant is always true.
        double sum = marks[0];

        /* This loop contains if statements that are used to change variables
         * printed at the end.  The loop invariant is marks[i] <= sum <= sum of marks[0 to 8]
         */
        for (int i = 0; i < marks.length; i++)
        {
            /*This calculates the sum, simply by adding the value of marks[i] to
             *the previous sum.
             */
            sum = sum + marks[i];
            
            /* This loop is used to assign the highest mark to top.  It checks
             * if the current value of the marks array is greater than the current
             * value of top, and if it is, the value of top is changed to the 
             * value of marks[i]
             */
            if (marks[i] > top)
            {
                top = marks [i];
            }

            /* This loop does the opposite of the previous loop.  It checks if the
             * value of marks[i] is lower than the current value of bottom, and assigns that
             * value to bottom if it is.
             */
            if (marks[i] < bottom)
            {
                bottom = marks[i];
            }

            /*This loop processes the passes and failures.  If the mark in marks[i]
             * is above 40, 1 is added to the number of passes.  Otherwise,
             * 1 is added to the value of fail.
             */
            if (marks[i] > 40)
            {
                pass = pass + 1;
            }
                else
                {
                    fail = fail + 1;
                }


        }

        double average  = sum/marks.length;
        double range = top - bottom;

        /* These lines simply print the contents of the variables that contain
         * information about the data which was input.
         */
        System.out.println("Number of students: " + marks.length);
        System.out.println("Student marks:");

        //This for loop prints all the values in the array.
        for (int i = 0; i < marks.length; i++)
        {
            System.out.println(marks[i]);
        }

        System.out.println("Number of passes: " + pass);
        System.out.println("Number of failures: " + fail);

        //The next two lines print the average mark, rounded to 2 decimal places.
        System.out.print("Average mark: ");
        System.out.printf("%5.2f\n", average);
        System.out.println("Maximum mark: " + top);
        System.out.println("Minimum mark: " + bottom);
        System.out.println("Range: " + range);


        /* ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
         * Write your own code and comments within the area above
         */
        
    }
    
}