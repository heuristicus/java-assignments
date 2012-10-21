/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ex10;

/**
 *
 * @author Michal
 */
public class ArraySequence implements Sequence {

    double[] array;

    public ArraySequence(double[] inputArray) throws UndefinedElementException {
        if (inputArray == null) {
            throw new UndefinedElementException(40);
        } else {
            this.array = new double[inputArray.length];
            for (int i = 0; i < array.length; i++) {
                array[i] = inputArray[i];
            }
        }
    }

    public double getElementAtIndex(int index) throws UndefinedElementException {

        //System.out.println("ooh");
        if (index < 0 || index > array.length - 1) {
            //System.out.println("argh");
            throw new UndefinedElementException(index);
        } else {
            //System.out.println("yay");
            return array[index];
        }
    }
}
