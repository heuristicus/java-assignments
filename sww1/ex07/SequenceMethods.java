/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ex07;

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
    public static double[] toArray(Sequence s, int from, int to) {
        assert (from < to) : "from must be less than to";
        int difference = java.lang.Math.abs(to - from);
        double[] elementArray = new double[difference];
        for (int i = 0; i < elementArray.length; i++) {
            for (int j = from; j <= to; j++) {
                elementArray[i] = s.getElementAtIndex(j);
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
        assert (from < to) : "from must be less than to";
        for (int i = from; i != to + 1; i++ )
            System.out.println(s.getElementAtIndex(i));
    }

}
