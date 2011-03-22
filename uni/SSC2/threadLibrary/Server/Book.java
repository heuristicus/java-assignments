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
    String title;
    String author;

    public Book(String title, String author, int ID) {
        this.ID = ID;
        this.title = title;
        this.author = author;
        onLoan = false;
        reserveQueue = new LinkedList<Integer>();
    }

    public void addReservation(int userID){
        reserveQueue.add(userID);
    }

    public void removeReservation(int userID){
        reserveQueue.remove(userID);
    }

    public void removeFirstReservation(){
        reserveQueue.remove();
    }

    public boolean isLoaned(){
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

    public int getID(){
        return ID;
    }

    @Override
    public String toString() {
        return "" + ID + ": " + title + " - " + author;
    }

    

}
