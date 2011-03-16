/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Server;

import java.io.IOException;
import java.net.Socket;

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
        while (!Thread.interrupted() && currentConnections <= maxConnections){
            try {
                Socket sock = server.getServerSocket().accept();
                String sockName = "" + sock.hashCode();
                LibServSocket s = new LibServSocket(sock, server, sockName);
                server.addConnection(s);
            } catch (IOException ex) {
                System.out.println("Error while listening for connections.");
                ex.printStackTrace();
            }
        }
    }

    public void run() {
        listenForConnections();
    }

}
