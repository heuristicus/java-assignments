/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package paper2005;

/**
 *
 * @author Michal
 */
public class q2a {

    public static void main(String[] args) {
        double[] t = makeArray();

        for (double d : t) {
            System.out.println(d);
        }
    }

    public static double[] makeArray() {
        double [] a = new double[8];
        for (int i = 0; i<8;i=i+1) {
            a[i]= (double)i/4;
        }
        return a;
    }

}
