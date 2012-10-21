package ex03;

import fyw.turtles.*;

public class Ex3HoursMinsSecs {
    
    /**
     * create an input frame
     * @param args strings from command line
     */
    public static void main(String[] args) {
        InputFrame theInputFrame = new InputFrame();
        
        /* Write your own code and comments within the area below
         *vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv
         */

        int inputnumber = theInputFrame.getInt();

        //this loop ensures that the user enters a number that can be
        //worked with.

        if (inputnumber >= 0)
        {

        }
            else

            {
                System.out.println("Please input a positive real number with up to 9 digits.");
                inputnumber = theInputFrame.getInt();
            }

        //These variables are used to store the values of

        int seconds = inputnumber;
        int minutes = 0;
        int hours = 0;
        int days = 0;
        int weeks = 0;

        /*The following loops all have a similar structure.  Essentially,
        If the modulus x of the variable in use is equal to zero,
        the next 'time step', i.e. seconds->minutes->hours->days->weeks,
        is equal to the variable divided by x.  The variable is then set to
        zero.
        */
       
        if (seconds % 60 == 0)
        {
            minutes = seconds/60;
            seconds = 0;
        }
            else
            {
                minutes = seconds/60;
                seconds = seconds % 60;
            }

        if (minutes % 60 == 0)
        {
            hours = minutes/60;
            minutes = 0;
        }
            else
            {
               hours = minutes/60;
               minutes = minutes % 60;
            }

        
        if (hours % 24 == 0)
        {
            days = hours/24;
            hours = 0;
        }
            else
            {
                days = hours/24;
                hours = hours % 24;
            }

        if (days % 7 ==0)
        {
            weeks = days/7;
            days = 0;
        }
            else
            {
                weeks = days/7;
                days = days % 7;
            }

        //prints conversion of seconds

        //prints the number that was input along with seconds =

        System.out.print(inputnumber + " seconds = ");

        //prints the values of the variables minutes, seconds, hours, days,
        //weeks, if their value is not zero.  Otherwise it does nothing.

        if (weeks == 0)
        {
            
        }
            else
                if (weeks == 1)
                {
                    System.out.print(" " + weeks + " week");
                }
                    else
                    {
                        System.out.print(" " + weeks + " weeks");
                    }


        if (days == 0)
        {

        }
            else
                if (days == 1)
                {
                    System.out.print(" " + days + " day");
                }
                    else
                    {
                        System.out.print(" " + days + " days");
                    }

        if (hours == 0)
        {

        }
            else
                if (hours == 1)
                {
                    System.out.print(" " + hours + " hour");
                }
                    else
                    {
                        System.out.print(" " + hours + " hours");
                    }

        if (minutes == 0)
        {

        }
            else
                if (minutes == 1)
                {
                    System.out.print(" " + minutes + " minute");
                }
                    else
                    {
                        System.out.print(" " + minutes + " minutes");
                    }

        if (seconds == 0)
        {

        }
            else
                if (seconds == 1)
                {
                    System.out.print(" " + seconds + " second");
                }
                    else
                    {
                        System.out.print(" " + seconds + " seconds");
                    }

        /* ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
         * Write your own code and comments within the area above
         */
        
    }
    
}