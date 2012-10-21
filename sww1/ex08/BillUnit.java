/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ex08;

import java.util.Currency;


public abstract class BillUnit {

    private String target;

    public BillUnit(String targ) {
        target = targ;
    }

    /**
     *
     * @return Value of Target of this instance of BillUnit.
     */
    public String getTarget() {
        return target;
    }

    /**
     *
     * @return An int calculated by the getPrice method of the instance of BillUnit.
     */
    public abstract int getPrice();

    /**
     * Depending on the cost of the item, the output string will vary slightly.
     * @return String containing the target location and price.
     */
    public String toString() {
        int cost = this.getPrice();
        // Remainder of the modulus of cost is the value of pence, as £1 = 100p.
        int pence = cost % 100;
        int pounds = 0;

        /* Loop runs until cost becomes less than 100 (i.e. below £1)
         * invariant: pounds = i
         */
        if (cost > 99) {
            for (int i = 0; cost > 100; i++) {
                cost = cost - 100;
                pounds = pounds + 1;
            }
        }

        if (pounds == 0) {
            return target + " " + pence + "p";
        } else if (pence == 0){
            return target + " £" + pounds + ".00";
        } else {
            return target + " £" + pounds + "." + pence;
        }
    }

}
