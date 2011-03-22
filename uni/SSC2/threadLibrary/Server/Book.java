/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * @author michal
 */
public class Book implements Serializable {

    int ID;
    Queue<Integer> reserveQueue;
    boolean onLoan;
    int loanedBy;
    String title;
    String author;

    public Book(String title, String author, int ID) {
        this.ID = ID;
        this.title = title;
        this.author = author;
        onLoan = false;
        reserveQueue = new LinkedList<Integer>();
    }

    public boolean addReservation(int userID) {
        if (!reserveQueue.contains(userID)) {
            reserveQueue.add(userID);
            return true;
        } else {
            return false;
        }
    }

    public void loan(int userID) {
        loanedBy = userID;
        onLoan = true;
        removeFirstReservation();
    }

    public void returnBook(){
        loanedBy = 0;
        onLoan = false;
    }

    public int getFirstInQueue() {
        return reserveQueue.peek();
    }

    public void removeReservation(int userID) {
        reserveQueue.remove(userID);
    }

    public void removeFirstReservation() {
        reserveQueue.remove();
    }

    public boolean isLoaned() {
        return onLoan;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getID() {
        return ID;
    }

    public String queueToString() {
        StringBuilder build = new StringBuilder();
        build.append("Reservation Queue for book ");
        build.append(ID);
        build.append("\n");
        build.append(title);
        build.append(" - ");
        build.append(author);
        build.append("\n");
        int count = 1;
        for (Integer integer : reserveQueue) {
            build.append("Position ");
            build.append(count);
            build.append(": ");
            build.append(integer);
            build.append("\n");
            count++;
        }
        return build.toString();
    }

    @Override
    public String toString() {
        return String.format(" %d: %s - %s", ID, title, author);
    }
}
