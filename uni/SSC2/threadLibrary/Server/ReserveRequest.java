/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 *
 * @author michal
 */
public class ReserveRequest implements Runnable {

    Condition c;
    Book b;
    Lock reserveLock;

    public ReserveRequest(Condition c, Book b) {
        this.c = c;
        this.b = b;
    }

    @Override
    public void run() {
        reserveLock.lock();
        try {

        } finally {
            reserveLock.unlock();
        }
    }
}
