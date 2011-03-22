/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import java.util.Map;

/**
 *
 * @author michal
 */
public class ReserveRequest {

    public static String execute(int bookID, int userID, Map bookList) {
        Book requestBook = (Book) bookList.get(bookID);
        if (requestBook == null) {
            return String.format("Book with ID %d was not found\n", bookID);
        } else {
            boolean added = requestBook.addReservation(userID);
            if (added) {
                return String.format("Successfully added reservation for book %s.\n", requestBook.getTitle());
            } else {
                return String.format("You are already in the queue for book %s.\n", requestBook.getTitle());
            }
        }
    }
}
