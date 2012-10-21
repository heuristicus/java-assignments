/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package paper2005.q5;

/**
 *
 * @author Michal
 */
public class Polynomial implements Applier {

    double[] c;

    public static void main(String[] args) {
        double[] t = {2, 4, 5, 7, 8, 3};
        Polynomial p = new Polynomial(t);
        System.out.println(p.apply(1));
    }

    /**
     * Creates a new instance of this object.  The parameter is an
     * array of doubles which constitute the coefficients of a
     * polynomial function.
     * @param c
     */
    public Polynomial(double[] c) {
        this.c = c;
    }

    /**
     * Calculates the value of the polynomial function with the coefficients
     * stored in the c array, replacing values of x with the input double.
     * @param x The value of x in the polynomial.
     * @return The value of the polynomial.
     */
    public double apply(double x) {
        double value = 0;
        for (int i = 0; i < c.length; i++) {
            value += c[i] * Math.pow(x, i);
        }
        return value;
    }

    /**
     * Calculates the derivative of this polynomial function.
     * @return Derivative of this polynomial.
     */
    public Polynomial derivative() {
        double[] dv = new double[c.length - 1];
        for (int i = 1; i < c.length; i++) {
            dv[i - 1] = c[i] * i;
        }
        return new Polynomial(dv);
    }
}
