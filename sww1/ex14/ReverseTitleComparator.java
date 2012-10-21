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
public class ReverseTitleComparator implements Comparator<Book> {

    /**
     * Checks the title of the book in the opposite way to TitleComparator.  If used in a sort,
     * the books will be sorted so that later characters in the alphabet appear earlier.
     * @param b1
     * @param b2
     * @returnNegative value if lexicographic value of the first different character in the two
     * strings is smaller in b2 than in b1, zero if the strings are the same, positive value if
     * the first different character has a greater lexicographic value in b2 than b1.
     */
    public int compare(Book b1, Book b2) {

        if (b1 != null && b2 != null) {
//            System.out.println("b1" + b1.toString());
//            System.out.println("b2" + b2.toString());
//            System.out.println("comparison value normal" + b1.getTitle().compareToIgnoreCase(b2.getTitle()));
//            System.out.println("comparison value swapping books" + b2.getTitle().compareToIgnoreCase(b1.getTitle()));
            return b2.getTitle().compareToIgnoreCase(b1.getTitle());
        }
        return 0;
    }
}
