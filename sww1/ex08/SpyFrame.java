/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ex08;
import fyw.turtles.InputFrame;

public class SpyFrame extends InputFrame{

    private String name;
    private SpyHQ hq;

    /**
     * Tests the methods in this class.  Note that it uses methods from
     * PrintHQ as well.
    public void main (String [] args) {
        SpyHQ hqTest = new PrintHQ();
        SpyFrame test = new SpyFrame(hqTest, "test");
        System.out.println(test.getName());
        test.getInt();
        test.getDouble();
    }
    */
    public SpyFrame(SpyHQ hq, String name) {
        this.name = name;
        this.hq = hq;
    }

    /**
     *
     * @return Name of the SpyFrame.
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Gets an integer from a method of the super class, and calls snoopInt
     * with that value and the SpyHQ instance that the object is using.
     * @return Integer received from InputFrame.getInt
     */
    @Override
    public int getInt() {
        int anInt = super.getInt();
        hq.snoopInt(this, anInt);
        return anInt;
    }

    /**
     * Works in the same way as getInt but getting double values rather than
     * int.
     * @return Double received from InputFrame.getInt
     */
    @Override
    public double getDouble() {
        double aDouble = super.getDouble();
        hq.snoopDouble(this, aDouble);
        return aDouble;
    }

}