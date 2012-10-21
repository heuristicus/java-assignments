/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package paper2006.q4;

/**
 *
 * @author Michal
 */
public class BoundedCounter extends Counter{

    public static final int MAX_N = 1000;
    //Invariant: n<=MAX_N

    public BoundedCounter (int n){
        if (n<=MAX_N) {
            super(n);
        } else {
            System.out.println("N is too big.");
        }
    }

    @Override
    public void inc() {
        if (n<=MAX_N) {
            super.inc();
        } else {
            System.out.println("N cannot be incremented further.");
        }
    }

}
