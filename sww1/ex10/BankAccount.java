/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ex10;

public abstract class BankAccount {

    String firstname;
    String lastname;
    int balance;

    public BankAccount(String firstname, String lastname, int balance) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.balance = balance;
    }

    public BankAccount() {
        this("undefined", "undefined", 0);
    }

    /**
     *
     * @return Current balance in the account.
     */
    public int getBalance() {
        return balance;
    }

    /**
     * Checks if it is possible to withdraw the desired amount from the account.
     * @param amount Amount on which to check if withdrawing is possible.
     * @return True or false.
     */
    public abstract boolean withdrawAllowed(int amount);

    /**
     * Removes a specified amount from the account.
     * @param amount Amount to be withdrawn
     * @throws BankException Thrown if the withdrawAllowed method returns false.
     */
    public void withdraw(int amount) throws BankException {

        if (withdrawAllowed(amount)) {
            this.takeFromBalance(amount);
        } else {
            throw new BankException("Withdrawing will put you over your limit.");
        }
    }

    public void deposit(int amount) {
        this.addToBalance(amount);
    }

    /**
     * 
     * @return First name of the account holder.
     */
    public String getFirstname() {
        return firstname;
    }

    /**
     * Allows you to set the first name of the account holder.
     * @param lastname The desired new first name.
     */
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    /**
     *
     * @return Last name of the account holder.
     */
    public String getLastname() {
        return lastname;
    }

    /**
     * Allows you to set the last name of the account holder.
     * @param lastname The desired new last name.
     */
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    /**
     * Allows you to add a specific amount to the balance of the account.
     * @param amount Amount you want to add to the balance of this account.
     */
    public void addToBalance(int amount) {
        balance += amount;
    }

    /**
     * Effectively the same method as addToBalance, but specifically for subtractions.
     * @param amount Amount you want subtracted from balance.
     */
    public void takeFromBalance(int amount) {
        balance -= amount;
    }

    /**
     * Converts the balance in the instance of BankAccount into a string.
     * @return Returns a string in the format £pounds.pence
     */
    public String balanceToString() {
        if (balance > 100) {
            String valueString = Integer.toString(balance);
            String pence = "." + valueString.substring(valueString.length() - 2, valueString.length());
            String pounds = "£" + valueString.substring(0, valueString.length() - 2);
            return pounds + pence;
        } else {
            return balance + "p";
        }

    }

    /**
     *
     * @return A formatted string showing details of the account.
     */
    @Override
    public String toString() {
        String firstletter = firstname.substring(0, 1);
        return lastname.toUpperCase() + ", " + firstletter.toUpperCase() + ". : balance = " + balanceToString();
    }
}
