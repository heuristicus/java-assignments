/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ex10;


public class CurrentAccount extends BankAccount {

    public static final int MIN_DEPOSIT = 20000;

    public CurrentAccount(String firstname, String lastname, int deposit) {
        super(firstname, lastname, deposit);
        try {
            if (deposit < MIN_DEPOSIT) {
                throw new BankException("The deposit you have is insufficient to open this account.");
            }
        } catch (BankException e) {
            System.out.println(e.getMessage());
        }
    }


    @Override
    public boolean withdrawAllowed(int amount) {
        if (super.getBalance() < amount) {
            return false;
        } else {
            return true;
        }
    }

}
