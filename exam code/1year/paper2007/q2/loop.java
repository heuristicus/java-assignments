/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package paper2007.q2;

/**
 *
 * @author Michal
 */
public class loop {

    public static void main(String[] args) {
        loop n = new loop();
        n.calculate2(9);
    }


    public void calculate(int n) {
        int left = 0;
        int right = n;
        while (left < right) {
            int mid = (left + right) /2;
            if (mid*mid <= n) {
                System.out.println(left + " " + mid + " " + right);
                left = mid;
            } else {
                right = mid;
            }
        }
    }

    public void calculate2(int n) {
        int left = 0;
        int right = n;
        while (left < right) {
            int mid = (left + right + 1) /2;
            if (mid*mid <= n) {
                System.out.println(left + " " + mid + " " + right);
                left = mid;
            } else {
                right = mid;
            }
        }
    }

}
