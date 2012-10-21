/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ex08;


public class CallBillUnit extends BillUnit {

    private int pricePerMinute;
    private int time;

    /**
     * Constructor.  Calls the constructor of the superclass with the value of
     * target, then assigns the other values to fields of this class.
     * @param target The target of this call.
     * @param ppm Cost per minute.
     * @param time Total time of the call.
     */
    public CallBillUnit(String target, int ppm, int time) {
        super(target);
        pricePerMinute = ppm;
        this.time = time;
    }

    /**
     *
     * @return Total cost of the call rounded to 2 decimal places (i.e. anything
     * below 1p is not counted)
     */
    @Override
    public int getPrice() {
        int timeMinutes = (int)((double)(int)((time/60) * 100) + 0.5)/100;
        int cost = (int)(timeMinutes * pricePerMinute);
        return cost;
    }

}
