/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ex08;


public class GPRSBillUnit extends BillUnit{


    private int price;
    private int kbDownloaded;

    /**
     * Constructor.  Calls the constructor of the superclass with the value of
     * target, then assigns the other values to fields of this class.
     * @param target Target of the GPRS call.
     * @param kbDownloaded Number of kilobytes downloaded from the target
     * @param price Price per kilobyte downloaded.
     */
    public GPRSBillUnit(String target, int kbDownloaded, int price) {
        super (target);
        this.kbDownloaded = kbDownloaded;
        this.price = price;
    }

    /**
     *
     * @return Total price of the GPRS call.
     */
    @Override
    public int getPrice() {
        return kbDownloaded * price;
    }



}
