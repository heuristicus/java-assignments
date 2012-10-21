/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package paper2005.q2b;

/**
 *
 * @author Michal
 */
public class Main {

    public static void main(String[] args) {
        IA x = new IA();
        IA y = new CC();
        y.f();
        y.g();
        x.f();
        CC y1 = y;
    }
}
