/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 *
 * @author michal
 */
public class RequestManager {

    Map bookList;
    ReentrantReadWriteLock lock;
    Lock readLock;
    Lock writeLock;

    public RequestManager(Map bookList) {
        this.bookList = bookList;
        lock = new ReentrantReadWriteLock();
        readLock = lock.readLock();
        writeLock = lock.writeLock();
    }

    public String getBookList() {
        return ListRequest.execute(bookList, readLock);
    }

    public String reserveBook(int bookID, int userID) {
        return ReserveRequest.execute(bookID, userID, bookList, writeLock);
    }

    public String loanBook(int bookID, int userID) {
        return LoanRequest.execute(bookID, userID, bookList, writeLock);
    }

    public String returnBook(int bookID, int userID) {
        return ReturnRequest.execute(bookID, userID, bookList, writeLock);
    }
}
