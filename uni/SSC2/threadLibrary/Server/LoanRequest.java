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
public class LoanRequest {

    public static String execute(int bookID, int userID, Map bookList, Lock lock) {
        lock.lock();
        try {
            Book requestBook = (Book) bookList.get(bookID);
            if (requestBook == null) {
                return String.format("Book with ID %d was not found\n", bookID);
            } else {
                if (requestBook.isLoaned()) {
                    return String.format("This book is already on loan by user %d.\n", requestBook.loanedBy);
                } else if (requestBook.getFirstInQueue() == userID) {
                    requestBook.loan(userID);
                    return String.format("Successfully loaned book %s.\n", requestBook.getTitle());
                } else {
                    return "You're not the first person in the queue for this book.";
                }
            }
        } finally {
            lock.unlock();
        }
    }
}
