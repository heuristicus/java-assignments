/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ex05;

/**
 *
 * @author Mich
 */
public class Ex5SelectionSort {

    /**
     * @param args the command line arguments
     */

    // Prints the initial array, sorts it, then prints the sorted array.
    public static void main(String[] args){

        double[] numberArray = new double[] {12.2, 8.9, 5.1, 0.4, 6.3, -1.25, 13.8};
        arrayPrint(numberArray);
        sort(numberArray);
        arrayPrint(numberArray);
    }

    // Prints the contents of the input array of doubles.
    public static void arrayPrint(double[] arr){             
        System.out.print("[" + arr[0]);

        for (int i = 1; i < arr.length; i++){
            System.out.print(", " + arr[i]);
        }
        System.out.println("]");
    }

    // Attempts to find the array location of the next smallest value in the array.
    public static int findIndexOfNextSmallest(double[] unsorted, int startIndex){

        /* StartIndex is the starting point of the search in the array, so you can
         * assume that the smallest value is the one on which the startIndex is,
         * unless you're going through for the first time, in which case the
         * array is completely unsorted.
         */
        int smallestIndex = startIndex;
        double arrayValue = unsorted[startIndex];

        /* Goes through the array until it finds a value smaller than the value
         * in the starting location.
         */
        int i = startIndex;

        while (i < unsorted.length){

            if (arrayValue > unsorted[i]){

                smallestIndex = i;
                break;
            } else {
                i = i + 1;
            }
        }
        return smallestIndex;
    }

    // Sorts an input array into ascending order.
    public static void sort(double[] toBeSorted) {
        int j = 0;

        while (j < toBeSorted.length){
            int i = 0;

            while (i < toBeSorted.length){
                /*Gets the index of the value in the array that is smaller than the
                * one in the current array location.
                */
                int indexSmallest = findIndexOfNextSmallest(toBeSorted, i);

                /*Swaps the value in the array location returned by findIndexOfNextSmallest
                 * with the value in the array location i.
                 */
                 double store = toBeSorted[i];
                 toBeSorted[i] = toBeSorted[indexSmallest];
                 toBeSorted[indexSmallest] = store;
                 i = i + 1;
                 }
            j = j + 1;
        }    
    }
}
