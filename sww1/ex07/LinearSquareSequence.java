/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ex07;


public class LinearSquareSequence implements Sequence {

    private final double a;
    private final double b;

    /**
     * Constructor for this class.
     * @param number The value of a in a*(index^2) + b
     * @param otherNumber The value of b in a*(index^2) + b
     */
    public LinearSquareSequence(double number, double otherNumber) {
        a = number;
        b = otherNumber;
    }

    /**
     * 
     * @param index The value of index in a*(index^2) + b
     * @return Returns the value of a linear square calculation on the
     * values number, otherNumber and index, rounded to 2 decimal places.
     */
    public double getElementAtIndex(int index){
        return ((double)(int) ((((a*(Math.pow(index,2)) + b)) * 100))) / 100;
    }

}