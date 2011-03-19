/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Server;

import java.util.ArrayList;
import java.util.concurrent.locks.Condition;

/**
 *
 * @author michal
 */
public class ListRequest implements Runnable{

    Condition writeFinished;

    public ListRequest(Condition writeFinished, ArrayList<Book> bookList){
        this.writeFinished = writeFinished;
    }

    @Override
    public void run(){
    }

}
