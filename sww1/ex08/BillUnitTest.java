/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ex08;

public class BillUnitTest {

    public static void main(String[]args) {
        BillUnit[] test = new BillUnit[8];
        test[0] = new SMSBillUnit("077589321402", 123, 10);
        test[1] = new SMSBillUnit("078245123910", 24, 26);
        test[2] = new CallBillUnit("085413694712", 12, 3744);
        test[3] = new CallBillUnit("0287211657497", 30, 1954);
        test[4] = new SMSBillUnit("1024876541321", 611, 3);
        test[5] = new SMSBillUnit("0354987423107", 212, 35);
        test[6] = new GPRSBillUnit("www.superawesome.com", 2000, 10);
        test[7] = new GPRSBillUnit("www.ilikeheuristics.org", 10000, 2);

        for (int i = 0; i < test.length; i++) {
            System.out.println(test[i]);
        }

        int totalCostPence = 0;
        for (int i = 0; i < test.length; i++) {
            totalCostPence = totalCostPence + test[i].getPrice();
        }
        String testing = Integer.toString(totalCostPence);
        String pence = testing.substring(testing.length() - 2,testing.length());
        String pounds = testing.substring(0,testing.length()-2);
        System.out.println("£" + pounds + "." + pence);
    }
}

