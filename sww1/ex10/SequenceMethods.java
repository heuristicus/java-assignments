/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ex10;

/**
 * This class can be used to output a sequence in 2 different ways; either in
 * an array of doubles, or printing to the console.
 *
 */
public class SequenceMethods {

    /**
     *
     * @param s An instance of a class which implements the Sequence interface.
     * @param from This is the lower bound of the data you want to work with.
     * It determines the lowest value of the index which will be used in the calculation
     * of the results of the sequence.
     * @param to This is the upper bound; the maximum value that a result will be
     * calculated with by the method getElementAtIndex.
     * @return Returns an array of doubles which contains the calculated values
     * of the specific sequence that you have called the method with.  The length is
     * the difference between from and to.
     */
    public static double[] toArray(Sequence s, int from, int to) throws UndefinedElementException {

        int difference = java.lang.Math.abs(to - from);
        double[] elementArray = new double[difference];

        for (int i = from; i <= to; i++) {
            try {
                elementArray[i] = s.getElementAtIndex(i);
            } catch (UndefinedElementException e) {
                //System.out.println("error");
            }
        }

        return elementArray;
    }

    /**
     *
     * @param s An instance of a class which implements the Sequence interface.
     * @param from This is the lower bound of the data you want to work with.
     * It determines the lowest value of the index which will be passed to
     * getElementAtIndex.
     * @param to This is the upper bound; the maximum value for which result will be
     * calculated using the method getElementAtIndex.
     */
    public static void print(Sequence s, int from, int to) {


        for (int i = from; i != to + 1; i++) {
            try {

                double currentValue = s.getElementAtIndex(i);
                if (Double.isNaN(currentValue)) {
                    System.out.println("?");
                } else {
                    System.out.println(currentValue);
                }
            } catch (UndefinedElementException e) {
                System.out.println("?");
            }
        }

    }

    /**
     *
     * @param arr An array of doubles to be added.
     * @return The sum of the array, ignoring undefined values.
     */
    public static double sum(double[] arr) {

        double sum = 0;
        //System.out.println("sum");
        for (int i = 0; i < arr.length; i++) {
            //System.out.println(arr[i]);
            if (!Double.isNaN(arr[i])) {
                sum += arr[i];
            }
        }
        return sum;
    }
}

    

