/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import java.util.Map;
import java.util.Set;

/**
 *
 * @author michal
 */
public class RequestManager {

//    ArrayList<Book> bookList;
    Map bookList;

    public RequestManager(Map bookList) {
        this.bookList = bookList;
    }

    public String getBookList() {
        StringBuilder build = new StringBuilder();
        Set keys = bookList.keySet();
        for (Object object : keys) {
            build.append(bookList.get(object));
            build.append("\n");
        }
        return build.toString();
    }

    public String reserveBook(int bookID, int userID) {
        Book requestBook = (Book) bookList.get(bookID);
        if (requestBook == null) {
            return String.format("Book with ID %d was not found\n", bookID);
        } else {
            boolean added = requestBook.addReservation(userID);
            if (added) {
                return String.format("Successfully added reservation for book %d.\n", bookID);
            } else {
                return String.format("You are already in the queue for book %d.\n", bookID);
            }
        }
    }

    public String loanBook(int bookID, int userID){
        Book requestBook = (Book) bookList.get(bookID);
        if (requestBook == null) {
            return String.format("Book with ID %d was not found\n", bookID);
        } else {
            if (requestBook.onLoan){
                return String.format("This book is already on loan by user %d.\n", requestBook.loanedBy);
            } else if (requestBook.getFirstInQueue()  == userID){
                requestBook.loan(userID);
                return String.format("Successfully loaned book %d.\n", bookID);
            } else {
                return "You're not the first person in the queue for this book.";
            }
        }
    }
}
