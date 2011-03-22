/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Server;

import java.util.ArrayList;

/**
 *
 * @author michal
 */
public class RequestManager implements Runnable {

    ArrayList<Book> bookList;

    public RequestManager(ArrayList<Book> bookList){
        
    }

    private void waitForRequest(){

    }

    public void run() {
        waitForRequest();
    }

}
