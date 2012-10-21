/*
 * TestSequence.java
 */

package ex10;

/**
 * Test class for Sequence.
 *
 * @author  fyw
 */
public class TestSequence {



    /**
     * Create an ArraySequence instance
     *   and test SequenceMethods.print and
     *   SequenceMethods.sum on it.
     *
     * @param args the command line arguments (not used)
     */
    public static void main (String[] args) throws UndefinedElementException
    {
        //create and populate a SIX element array
        double[] arr = new double[6];
        for (int i = 0; i < 6; i++) {
            arr[i] = (i-3.00)/(i-3.0);
        }        
        
        Sequence s = new ArraySequence(arr);
        

        
        /* try to print indexes of the array that are deliberately
           out of bounds */
        SequenceMethods.print(s, -8, 8);
        
        System.out.println("--------");

        
        //now try to sum elements of the array which will also be out of bounds
        System.out.println("Sum of elements 1 to 10 of array: "+SequenceMethods.sum(SequenceMethods.toArray(s, 1, 10)));
    }
}