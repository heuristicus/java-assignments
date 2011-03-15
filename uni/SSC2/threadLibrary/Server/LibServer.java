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
public class LibServer {

    int port;
    ArrayList<LibServSocket> connections;

    public LibServer(int port, int maxConnections){
        this.port = port;
        connections = new ArrayList<LibServSocket>();
    }

    public void shutdown(){
        for (LibServSocket libServSocket : connections) {
            libServSocket.disconnect();
        }
    }



}
