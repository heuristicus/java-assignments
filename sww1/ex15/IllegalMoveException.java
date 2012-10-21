/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ex15;

/**
 *
 * @author Michal
 */
public class IllegalMoveException extends Exception {

    public IllegalMoveException(int move) {
        super("You cannot move stones from cup " + move + "!");
    }
}
