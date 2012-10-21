/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ex08;


public class PrintHQ implements SpyHQ{


    /*
     * This class is tested in the SpyFrame main method, as well as in TestSpy.
     */
    public PrintHQ() {
    }

    /**
     * Takes the data received by a specific instance of spyframe and prints it
     * along with the name of the SpyFrame.
     * @param source An instance of SpyFrame.
     * @param data An integer value received by SpyFrame from InputFrame in its
     * getInt method.
     */
    public void snoopInt(SpyFrame source, int data) {
        System.out.println(source.getName() + " to HQ! Subject has been observed using getInt: " + data);
    }

    /**
     * Takes the data received by a specific instance of spyframe and prints it
     * along with the name of the SpyFrame.
     * @param source An instance of SpyFrame.
     * @param data A double value received by SpyFrame from InputFrame in its
     * getDouble method.
     */
    public void snoopDouble(SpyFrame source, double data) {
        System.out.println(source.getName() + " to HQ! Subject has been observed using getDouble: " + data);
    }

}