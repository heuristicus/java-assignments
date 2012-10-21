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
public class ReverseAuthorFirstNameComparator implements Comparator<Book>{


    /**
     * Checks the title of the book in the opposite way to AuthorComparatorFirstName.  If used in a sort,
     * the books will be sorted so that later characters in the alphabet appear earlier.
     * @param b1
     * @param b2
     * @return Negative value if lexicographic value of the first different character in the two
     * strings is smaller in b1 than in b2, zero if the strings are the same, positive value if
     * the first different character has a greater lexicographic value in b1 than b2.
     */
    public int compare(Book b1, Book b2) {
        if (b1 != null && b2 !=null){
        String firstAuthor = "" + b1.getAuthorPersonalName() + b1.getAuthorFamilyName();
        String secondAuthor = "" + b2.getAuthorPersonalName() + b2.getAuthorFamilyName();
        return secondAuthor.compareToIgnoreCase(firstAuthor);
        }
        return 0;
    }

}
