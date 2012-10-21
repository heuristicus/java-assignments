/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ex14;

import java.util.Comparator;

/**
 *
 * @author Michal
 */
public class ReverseNumberComparator implements Comparator<Book>{


    /**
     *  Sorts numbers in descending order.
     * @param b1
     * @param b2
     * @return -1 if the second book's number is less than the first, 0 if they are equal, 1 if
     * the second book's number is greater than the first.
     */
    public int compare(Book b1, Book b2) {
        if (b1 != null && b2 != null) {
            if (b2.getBookNumber() < b1.getBookNumber()){
                return -1;
            } else {
                return 1;
            }
        }
        return 0;
    }

}
