/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * @author michal
 */
public class Book {

    Queue<String> reserveQueue;
    String title;
    String author;

    public Book(String title, String author) {
        this.title = title;
        this.author = author;
        reserveQueue = new LinkedList<String>();
    }

    public void addReservation(String name){
        reserveQueue.add(name);
    }

    public void removeReservation(String name){
        reserveQueue.remove(name);
    }

    public void removeFirstReservation(){
        reserveQueue.remove();
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

    @Override
    public String toString() {
        return title + " - " + author;
    }

    

}
