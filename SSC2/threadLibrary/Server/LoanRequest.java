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
                lock.unlock();
                return String.format("Book with ID %d was not found\n", bookID);
            } else {
                if (requestBook.isLoaned()) {
                    String response = String.format("This book is already on loan by user %d.\n", requestBook.loanedBy);
                    lock.unlock();
                    return response;
                } else if (requestBook.getFirstInQueue() == userID) {
                    requestBook.loan(userID);
                    lock.unlock();
                    return String.format("Successfully loaned book %s.\n", requestBook.getTitle());
                } else {
                    lock.unlock();
                    return "You're not the first person in the queue for this book.";
                }
            }
        } finally {
            try {
                lock.unlock();
            } catch (IllegalMonitorStateException ex) {
                // catch this exception - occurs if the lock is already unlocked.
            }
        }
    }
}
