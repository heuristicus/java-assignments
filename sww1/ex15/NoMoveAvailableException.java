/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ex15;

/**
 *
 * @author Michal
 */
class NoMoveAvailableException extends Exception {

    public NoMoveAvailableException() {
        super("There are no moves available!");
    }

}
