/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ex10;

public class StudentAccount extends BankAccount {

    public static final int MIN_DEPOSIT = 2000;
    public static final int MAX_OVERDRAFT = 350000;
    int overdraft;

    public StudentAccount(String firstname, String lastname, int deposit, int initialOverdraft) throws BankException {
        super(firstname, lastname, deposit);
        //try {
            if (initialOverdraft > MAX_OVERDRAFT) {
                throw new BankException("You cannot have an overdraft greater than £3500");
            } else {
                overdraft = initialOverdraft;
            }
            if (deposit < MIN_DEPOSIT) {
                throw new BankException("The deposit you have is insufficient to open this account.");
            }
        //} catch (BankException e) {
         //   System.out.println(e.getMessage());
        //}
    }

    /**
     * Set a new overdraft for the instance of StudentAccount.
     * @param newOverdraft Value to which you want to increase the overdraft.
     * Can never exceed 350000.
     */
    public void setOverdraft(int newOverdraft) {
        try {
            if (newOverdraft < MAX_OVERDRAFT) {
                overdraft = newOverdraft;
            } else {
                throw new BankException("You cannot have an overdraft greater than £3500");
            }
        } catch (BankException e) {
            System.out.println(e.getMessage());

        }
    }

    /**
     * Returns the current maximum overdraft.
     * @return Current overdraft value for this instance of StudentAccount.
     */
    public int getOverdraft() {
        return overdraft;
    }

    /**
     * Removes an amount to the account balance.
     * @param amount Amount to remove from balance.
     */



    @Override
    public boolean withdrawAllowed(int amount) {
        if ((super.getBalance() - amount) < (overdraft * -1)) {
            return false;
        } else {
            return true;
        }
    }
}
