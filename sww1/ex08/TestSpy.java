/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * TestSpy.java
 */

package ex08;

import fyw.turtles.InputFrame;

public class TestSpy {

    public static void main(String[] args) {
        /*
         ********************************
         * Do NOT edit the next line!
         ********************************
         */
        InputFrame theInputFrame;

        /*
         ********************************
         * The code in the next line needs to be
         * replaced by code that creates a new
         * SpyFrame instance and assigns it to
         * theInputFrame.
         ********************************
         */
        SpyHQ hq = new PrintHQ();
        theInputFrame = new SpyFrame(hq, "007");

        double[] arr = makeArray(theInputFrame);
        printArray(arr);
    }

    private static double[] makeArray (InputFrame theInputFrame) {
        int size = theInputFrame.getInt();
        double[] arr = new double[size];

        for (int i = 0; i < size; i = i+1) {
            arr[i] = theInputFrame.getDouble();
        }
        return arr;
    }

    private static void printArray(double[] arr) {
        for (int i = 0; i < arr.length; i = i+1) {
            if (i > 0) {
                System.out.print(", ");
            }
            System.out.print(arr[i]);
        }
        System.out.println();
    }

}