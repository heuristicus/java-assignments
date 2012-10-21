/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ex10;


public class UndefinedElementException extends Exception{

    public UndefinedElementException (int x) {
        super("Undefined element at position " + x);
    }

}
