/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.Lock;

/**
 *
 * @author michal
 */
public class ListRequest{

    public static String execute(Map bookList, Lock lock) {
        StringBuilder build = new StringBuilder();
        lock.lock();
        try {
            Set keys = bookList.keySet();
            for (Object object : keys) {
                build.append(bookList.get(object));
                build.append("\n");
            }
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException ex) {
//                Logger.getLogger(ListRequest.class.getName()).log(Level.SEVERE, null, ex);
//            }
            lock.unlock();
            return build.toString();
        } finally {
            lock.unlock();
        }
    }

}
