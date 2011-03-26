/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import java.util.Map;
import java.util.concurrent.locks.Lock;

/**
 *
 * @author michal
 */
public class ReturnRequest {

    public static String execute(int bookID, int userID, Map bookList, Lock lock) {
        lock.lock();
        try {
            Book requestBook = (Book) bookList.get(bookID);
            if (requestBook == null) {
                return String.format("Book with ID %d was not found\n", bookID);
            } else {
                if (!requestBook.isLoaned()) {
                    return "This book is not on loan. You have to loan it before you can return it.";
                } else if (requestBook.isLoaned() && requestBook.loanedBy == userID) {
                    requestBook.returnBook();
                    return String.format("Successfully returned book %s.\n", requestBook.getTitle());
                } else {
                    return "This book wasn't loaned by you.";
                }
            }
        } finally {
            lock.unlock();
        }
    }
}
