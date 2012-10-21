/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ex14;

import java.util.Comparator;

/*
 * Author Michal Staniaszek
 */
public class AuthorComparatorFirstName implements Comparator<Book> {


    /**
     *  Compares two books and checks the difference between the author's name, starting
     * with the first name.
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
        return firstAuthor.compareToIgnoreCase(secondAuthor);
        }
        return 0;
    }
}
