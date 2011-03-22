/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import java.util.Map;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author michal
 */
public class RequestManager {

    Map bookList;
    Lock lock;
    Condition writing;
    Condition reading;

    public RequestManager(Map bookList) {
        this.bookList = bookList;
        lock = new ReentrantLock();
        writing = lock.newCondition();
        reading=lock.newCondition();
    }

    public String getBookList() {
        return ListRequest.execute(bookList);
    }

    public String reserveBook(int bookID, int userID) {
        return ReserveRequest.execute(bookID, userID, bookList);
    }

    public String loanBook(int bookID, int userID) {
        return LoanRequest.execute(bookID, userID, bookList);
    }

    public String returnBook(int bookID, int userID) {
        return ReturnRequest.execute(bookID, userID, bookList);
    }
}
