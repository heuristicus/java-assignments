/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package paper2005.q3;

/**
 *
 * @author Michal
 */
public class q3a {

    public static void main(String[] args) {
        int[] arr = {3, 2, 3, 5};
        System.out.println(first(arr, 5));
    }

    public static int first(int[] a, int x) {
        int i = 0;
//        while (i < a.length && a[i] != x) {
        while (a[i] != x && i < a.length) {
            i++;
        }
        return i;
    }
}
