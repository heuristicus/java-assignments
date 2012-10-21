/*
 * TestAccounts.java
 */
package ex10;

import fyw.turtles.InputFrame;

/**
 * Test program for Banking system
 *
 * @author  fyw
 */
public class TestAccounts {

    /**
     * @param args the command line arguments (not used)
     */
    public static void main(String[] args) {
        InputFrame theInputFrame = new InputFrame();
        BankAccount ac = null;

        /* First set up a student account in the name of "me",
         *   with opening balance and initial overdraft limit
         *   got from the input frame.
         */
        while (ac == null) {
            int balance = getMoney(theInputFrame);
            int overdraftLimit = getMoney(theInputFrame);
            try {

                // change this line to test different kinds of account
                ac = new StudentAccount("Fred", "Smith", balance, overdraftLimit);
            } catch (BankException e) {
                System.out.println(e.getMessage());
            }
        }
        System.out.println(ac); //relies on having a toString method

        
        /* now loop forever, getting an amount to deposit or
         *   withdraw from the input frame and then performing
         *   the operation if possible.
         * If the operation is not possible, the exception message
         *   is output but the program continues.
         */
        while (true) {
            int amount = getMoney(theInputFrame);
            try {
                if (amount >= 0) {
                    ac.deposit(amount);
                } else {
                    ac.withdraw(-amount);
                }
                System.out.println(ac);
            } catch (BankException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    /** Get money amount in pence (int) from input frame
     *   as pounds (double).
     * Amount may be positive or negative.
     * Inputting repeats until pounds number input is small
     *   enough for pence to be stored as int. <p>
     * requires: theInputFrame != null
     * @param  theInputFrame input frame used
     * @return integer pence value for pounds value input
     */
    private static int getMoney(InputFrame theInputFrame) {
        boolean got = false;
        double x = 0;

        while (!got) {
            x = Math.rint(100 * theInputFrame.getDouble());
            got = Integer.MIN_VALUE <= x && x <= Integer.MAX_VALUE;
        }
        return (int) x;
    }
}
