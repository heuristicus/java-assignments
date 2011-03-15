/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import java.io.ObjectOutputStream;

/**
 *
 * @author michal
 */
public class LibSocketListener implements Runnable {

    private final LibServer server;
    private final ObjectOutputStream objOut;

    public LibSocketListener(ObjectOutputStream objOut, LibServer server) {
        this.objOut = objOut;
        this.server = server;
    }

    public void listen(){
        while (!Thread.interrupted()){
            
        }

    }

    public void run() {
        listen();
    }
}
