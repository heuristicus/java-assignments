/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ex08;


public class SMSBillUnit extends BillUnit {

    private int price;
    private int messageLength;

    /**
     * Constructor.  Calls the constructor of the superclass with the value of
     * target, then assigns the other values to fields of this class.
     * @param target Target of the GPRS call.
     * @param messageLength Number of characters in a message.  1 message has
     * a maxium of 160 characters.
     * @param price Price per message sent.
     */
    public SMSBillUnit(String target, int messageLength, int price) {
        super (target);
        this.messageLength = messageLength;
        this.price = price;
    }

    /**
     *
     * @return Total cost of the messages sent.  The price is the message length
     * divided by 160, plus one.
     */
    @Override
    public int getPrice() {
        int messages = 1;
        if (messageLength > 160) {
            messages = messages + messageLength/160;
        }
        int totalCost = messages * price;
        return totalCost;
    }

}
