/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ex14;

import java.util.Comparator;

/*
 * Author Michal Staniaszek
 */
public class NumberComparator implements Comparator<Book>{

    /**
     * Compares the two books received and returns a value representing whether the book number of book 1
     * is smaller, equal to or greater than the second book's book number.
     * @param b1 First book.
     * @param b2 Second book.
     * @return -1 if first book's number is less than the second, 0 if equal, 1 if first book's number is greater.
     */
    public int compare(Book b1, Book b2) {
        if (b1 != null && b2 != null) {
            if (b1.getBookNumber() < b2.getBookNumber()){
                return -1;
            } else {
                return 1;
            }
        }
        return 0;
    }

}
