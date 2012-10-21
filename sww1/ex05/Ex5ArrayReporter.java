/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ex05;

/**
 *
 * @author Mich
 */
public class Ex5ArrayReporter {

   /* Creates a randomised array using the createRandomisedArray method, and
    * outputs the max, min and median values in the array using printArrayReport.
    */
   public static void main(String[] args){
        double[] randomArray = createRandomisedArray(11, -12, 9);
        printArrayReport(randomArray);
    }
   
   // Creates a randomised array using some input values.
   public static double[] createRandomisedArray(int length, double lower, double upper){
        double[] randomArray = new double[length];
        double arrayRange = upper - lower;
        /* For each array location, this gets a random double value between 1 and 0,
         * then multiplies this value by the range of the array and adds the
         * lower bound to it so that the number is within the array's desired range.
         */
        for (int i = 0; i < randomArray.length; i++){

            double randomDouble = java.lang.Math.random();
            double inputValue = lower + (arrayRange * randomDouble);
            inputValue = ((double)(int) ((inputValue * 100) + 0.5)) / 100;
            randomArray[i] = inputValue;
        }

        return randomArray;

    }

   // Finds the maximum, minimum and median values of the numbers an input array of doubles.
   public static double[] findMaxMinMedianOfArrayElements(double[] nums){

        double[] arrayCopy = new double[nums.length];
        // This array is used to store max min and median later.
        double[] stats = new double[3];

        // Copies the input array into a new array.
        for (int i = 0; i < nums.length; i++){
            arrayCopy[i] = nums[i];
        }

        /* This sorts the copied array so that it is in ascending order.
         * i.e. array[0] = lowest value
         */
        Ex5SelectionSort.sort(arrayCopy);

        // Stores the maximum value in the sorted array (the last array location)
        stats[2] = arrayCopy[arrayCopy.length - 1];

        // Stores the mimimum value in the array (first array location)
        stats[0] = arrayCopy[0];
        double median = 0;

        /* Length and half the length of the array defined as variables just so
         * that it's easier to work with.
         */
        int arraySize = nums.length;
        int halfLength = nums.length/2;

        // Checks whether the array is an odd or even length.
        if (arraySize % 2 == 0){
            // takes the two middle values of the array and gets the average.
            median = (arrayCopy[halfLength]+ arrayCopy[halfLength + 1])/2;

        } else {
            // Gets the value in the middle array location.
            median = arrayCopy[halfLength + 1];

        }
        stats[1] = median;
        return stats;
    }

   // Prints some info about an input array of doubles.
   public static void printArrayReport(double[] originals){

        System.out.print("[" + originals[0]);

        for (int i = 1; i < originals.length; i++){
            System.out.print(", " + originals[i]);
        }

        System.out.println("]");
        
        double[] maxMinMedian = findMaxMinMedianOfArrayElements(originals);

        System.out.println("Minimum: " + maxMinMedian[0]);
        System.out.println("Maximum: " + maxMinMedian[2]);
        System.out.println("Median: " + maxMinMedian[1]);

    }
}
