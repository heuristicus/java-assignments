/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ex07;


public class TestSequence {

    /**
     *
     * Tests the functionality of the sequance interface and classes associated
     * with it.
     *
     * Creates a new instance of LinearSquareSequence with some values,
     * creates a an array of doubles from the toArray method, then prints the
     * contents of the array.  Also tests the print method in a similar fashion,
     * but as printing the values received from the getElementAtIndex method is
     * intrinsic to the print method, it does not reprint the values.
     *
     * The method should print two sets of the same data.
     */

    public static void main(String[] args) {
        Sequence seq = new LinearSquareSequence(3.12, 2.9);
        double[] test = SequenceMethods.toArray(seq, 0, 9);
        for (int i = 0; i < test.length; i++ )
            System.out.println(test[i]);
        SequenceMethods.print(seq, 0, 9);
    }
    
}





