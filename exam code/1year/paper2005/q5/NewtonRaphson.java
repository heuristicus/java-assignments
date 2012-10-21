/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package paper2005.q5;

/**
 *
 * @author Michal
 */
public class NewtonRaphson implements Applier {

    private Polynomial f;
    private Polynomial fDash;

    public NewtonRaphson(Polynomial f, Polynomial fDash) {
        this.f = f;
        this.fDash = fDash;
    }

    public double apply(double x) {
        return x - f.apply(x)/fDash.apply(x);
    }

}
