/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ex05;

public class Ex5FindNearest {

    public static void main(String[] args) {
        // Defines the value to look for.
        double theItem = 12.86;
        // Creates a random array with a specified length and range.
        double[] randArray = Ex5ArrayReporter.createRandomisedArray(9, 30, 8);
        // Assigns the value of the nearest element to the value that is being searched for to a variable.
        double nearestElement = findNearestElement(randArray, theItem);
        //Prints out the item and the value of the nearest element.
        System.out.println("theItem: " + theItem);
        System.out.println("Nearest Value: " + nearestElement);
        //Prints the Array.
        Ex5SelectionSort.arrayPrint(randArray);
    }

    // Finds the number closest to the value of item within the array values.
    public static double findNearestElement(double[] values, double item) {

        //defining some variables to use.
        double nearestElement = 0;
        double differenceLowest = 300;

        // Loop invariant is: 0 <= i < values.length.
        for (int i = 0; i < values.length; i++){

            /* Gets the absolute value for the difference between the input item
             * and the current value in the array.
             */
            double difference = java.lang.Math.abs(item - values[i]);

            /* Checks whether the current difference is less than the lowest distance.
             * if this is the case, the value of the new lowest difference is assigned
             * to the differenceLowest variable.
             */
            if (difference < differenceLowest){
                differenceLowest = difference;
                // Assigns the value of the current array element to the nearestElement variable.
                nearestElement = values[i];
            }
        }               
        return nearestElement;
    }
}
