/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package paper2007.q5;

/**
 *
 * @author Michal
 */
public class Linear implements Filler {

    private int start;
    private int increment;

    public static void main(String[] args) {
        Linear l = new Linear(2,3);
    }

    public Linear(int start, int increment) {
        this.start = start;
        this.increment = increment;
    }

    public int element(int i) {
        return start + (i * increment);
    }
}
