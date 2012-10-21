/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package paper2006.q5;

/**
 *
 * @author Michal
 */
public class Perms {

    public static void main(String[] args) {
        perms("123");
    }

    /**
     * constructor
     */
    public Perms() {
    }

    /**
     * Prints permutations of the string w, with the prefix attached, to system.out.
     * @param prefix Prefix to add to each permutation.
     * @param w String to get permutations of.
     */
    public static void prefixPerms(String prefix, String w) {
        if (w.length() == 0) {
            return;
        } else {
            System.out.println(prefix);
            perms(w);
        }
    }

    /**
     * Prints the permutations of w to system.out.
     * @param w String to get permutations of.
     */
    public static void perms(String w) {
        if (w.length() <= 1) {
            System.out.println(w);
            return;
        } else {
            for (int i = 0; i < w.length(); i++) {
                for (int j = 0; j < w.length() - 1; j++) {
                    System.out.print(w.charAt(i));
                    perms(w.substring(i + 1));
                }
            }
        }
    }
}
