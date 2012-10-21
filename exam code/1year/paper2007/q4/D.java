/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package paper2007.q4;

/**
 *
 * @author Michal
 */
public class D extends C {

    public static void main(String[] args) {
        C x1 = new C(3);
        D x2 = new D(5);
        C x3 = x2;
        x2.increment();
        System.out.println(x1.toString());
        System.out.println(x2.toString());
        System.out.println(x3.toString());
    }

    private int inc;

    public D(int inc) {
        super(0);
        this.inc = inc;
    }

    public void increment() {
        super.setVal(super.getVal() + inc);
    }

    @Override
    public String toString() {
        return "val: " + super.getVal() + "inc: " + inc;
    }
}
