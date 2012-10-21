/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package paper2007.q5;

/**
 *
 * @author Michal
 */
public class newArray {

    public static int[] newArray(int n, Filler f) throws IllegalArgumentException {
        if (n < 0) {
            throw new IllegalArgumentException();
        } else {
            int[] result = new int[n];
            for (int i = 0; i < result.length; i++) {
                result[i] = f.element(i);
            }
            return result;
        }
    }
}
