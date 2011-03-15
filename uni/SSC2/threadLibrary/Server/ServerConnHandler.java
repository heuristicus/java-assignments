/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Server;

/**
 *
 * @author michal
 */
public class ServerConnHandler implements Runnable{

    LibServer server;
    int maxConnections;
    int currentConnections;

    public ServerConnHandler(int maxConnections, LibServer server){
        this.server = server;
        this.maxConnections = maxConnections;
        currentConnections = 0;
    }

    public void listenForConnections(){
        while (!Thread.interrupted()){
            
        }
    }

    public void run() {
        listenForConnections();
    }

}
