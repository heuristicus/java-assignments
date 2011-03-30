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
public class ReserveRequest {

    public static String execute(int bookID, int userID, Map bookList, Lock lock) {
        lock.lock();
        try {
            Book requestBook = (Book) bookList.get(bookID);
            if (requestBook == null) {
                lock.unlock();
                return String.format("Book with ID %d was not found\n", bookID);
            } else {
                boolean added = requestBook.addReservation(userID);
                lock.unlock();
//                try {
//                    Thread.sleep(20000);
//                } catch (InterruptedException ex) {
//                    Logger.getLogger(ReserveRequest.class.getName()).log(Level.SEVERE, null, ex);
//                }
                if (added) {
                    return String.format("Successfully added reservation for book %s.\n", requestBook.getTitle());
                } else {
                    return String.format("You are already in the queue for book %s.\n", requestBook.getTitle());
                }
            }
        } finally {
            lock.unlock();
        }
    }
}
